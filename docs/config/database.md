# NaturalAPI — Database Guide

Panduan lengkap tentang database yang digunakan oleh NaturalAPI, termasuk skema tabel, migrasi, dan troubleshooting.

---

## Table of Contents

- [Overview](#overview)
- [Provider yang Didukung](#provider-yang-didukung)
- [Skema Database](#skema-database)
- [Migrasi (Flyway)](#migrasi-flyway)
- [Backup & Restore](#backup--restore)
- [Troubleshooting](#troubleshooting)

---

## Overview

NaturalAPI menggunakan database untuk menyimpan:
- **API Keys** — Token autentikasi yang di-hash (SHA-256 + salt)
- **Player Snapshots** — Data lengkap player (inventory, stats, lokasi) yang disimpan secara periodik
- **Skin Cache** — Cache tekstur skin dari Mojang Session Server

Teknologi yang digunakan:
- **HikariCP** — Connection pooling berkinerja tinggi
- **JDBI 3** — Lightweight SQL mapping (DAO pattern)
- **Flyway** — Migrasi skema database otomatis

---

## Provider yang Didukung

| Provider | Driver | Use Case | Auto-Create DB? |
|---|---|---|---|
| **SQLite** | `org.xerial:sqlite-jdbc` | Development, server kecil | ✅ Ya (file-based) |
| **MySQL 8+** | `com.mysql:mysql-connector-j` | Production, multi-server | ❌ Tidak (buat manual) |
| **MariaDB 10.6+** | `org.mariadb.jdbc:mariadb-java-client` | Production alternative | ❌ Tidak (buat manual) |

### SQLite

```yaml
database:
  provider: sqlite
  sqlite:
    file: "data.db"
```

File database akan otomatis dibuat di lokasi yang ditentukan. Tidak perlu install apapun.

### MySQL

```yaml
database:
  provider: mysql
  mysql:
    host: "localhost"
    port: 3306
    database: "naturalapi"
    username: "napi_user"
    password: "your_password"
    pool-size: 10
    connection-timeout: 30000
```

**Persiapan MySQL:**
```sql
-- 1. Buat database
CREATE DATABASE naturalapi CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. Buat user khusus (JANGAN pakai root di production!)
CREATE USER 'napi_user'@'localhost' IDENTIFIED BY 'your_secure_password';

-- 3. Berikan hak akses
GRANT ALL PRIVILEGES ON naturalapi.* TO 'napi_user'@'localhost';
FLUSH PRIVILEGES;
```

### MariaDB

```yaml
database:
  provider: mariadb
  mariadb:
    host: "localhost"
    port: 3306
    database: "naturalapi"
    username: "napi_user"
    password: "your_password"
    pool-size: 10
```

Setup identik dengan MySQL.

---

## Skema Database

### Tabel: `napi_api_keys`

Menyimpan API key untuk autentikasi REST API.

```sql
CREATE TABLE napi_api_keys (
  id          VARCHAR(36) PRIMARY KEY,   -- UUID unik untuk key
  name        VARCHAR(100) NOT NULL,     -- Label deskriptif (e.g., "dashboard")
  key_hash    VARCHAR(128) NOT NULL,     -- SHA-256 hash dari secret key
  key_salt    VARCHAR(64)  NOT NULL,     -- Salt unik untuk hashing
  scopes      TEXT         NOT NULL,     -- Scope dipisahkan koma (e.g., "read:server,read:players")
  created_at  BIGINT       NOT NULL,     -- Unix timestamp ms saat key dibuat
  expires_at  BIGINT,                    -- Unix timestamp ms kadaluarsa (NULL = tidak expire)
  last_used   BIGINT,                    -- Unix timestamp ms terakhir digunakan
  enabled     BOOLEAN      DEFAULT TRUE  -- Aktif/nonaktif tanpa menghapus
);
```

**Contoh data:**
| id | name | scopes | enabled |
|---|---|---|---|
| `a1b2c3d4-...` | my-dashboard | `*` | true |
| `b2c3d4e5-...` | discord-bot | `read:server,read:players` | true |
| `c3d4e5f6-...` | old-app | `read:server` | false |

> ⚠️ `key_hash` dan `key_salt` adalah data sensitif. Secret key asli **tidak pernah** disimpan di database.

---

### Tabel: `napi_player_snapshots`

Menyimpan snapshot lengkap data player secara periodik.

```sql
CREATE TABLE napi_player_snapshots (
  id              VARCHAR(36) PRIMARY KEY,  -- UUID unik snapshot
  player_uuid     VARCHAR(36) NOT NULL,     -- UUID player Minecraft
  player_name     VARCHAR(16) NOT NULL,     -- Username saat snapshot
  snapshot_time   BIGINT      NOT NULL,     -- Unix timestamp ms
  world           VARCHAR(255),             -- Nama world
  x               DOUBLE,                   -- Koordinat X
  y               DOUBLE,                   -- Koordinat Y
  z               DOUBLE,                   -- Koordinat Z
  yaw             FLOAT,                    -- Rotasi horizontal
  pitch           FLOAT,                    -- Rotasi vertikal
  health          DOUBLE,                   -- HP saat ini
  max_health      DOUBLE,                   -- HP maksimal
  food_level      INT,                      -- Level makanan (0-20)
  saturation      FLOAT,                    -- Saturasi makanan
  exp_level       INT,                      -- Level experience
  exp_progress    FLOAT,                    -- Progress ke level berikutnya (0.0-1.0)
  total_exp       INT,                      -- Total EXP yang pernah didapat
  gamemode        VARCHAR(20),              -- SURVIVAL/CREATIVE/ADVENTURE/SPECTATOR
  inventory_json  TEXT,                     -- Full 36-slot inventory (JSON array)
  armor_json      TEXT,                     -- 4 slot armor (JSON array)
  effects_json    TEXT,                     -- Efek potion aktif (JSON array)
  skin_texture    TEXT,                     -- Base64 texture value dari Mojang
  skin_signature  TEXT,                     -- Signature texture
  vault_group     VARCHAR(255),             -- Primary group Vault
  vault_prefix    VARCHAR(255),             -- Chat prefix Vault
  vault_suffix    VARCHAR(255),             -- Chat suffix Vault
  lp_group        VARCHAR(255)              -- Primary group LuckPerms
);

CREATE INDEX idx_snapshots_uuid ON napi_player_snapshots(player_uuid);
CREATE INDEX idx_snapshots_name ON napi_player_snapshots(player_name);
```

**Contoh query manual:**
```sql
-- Ambil snapshot terbaru untuk player tertentu
SELECT * FROM napi_player_snapshots
WHERE player_uuid = 'a1b2c3d4-5678-9abc-def0-123456789abc'
ORDER BY snapshot_time DESC
LIMIT 1;

-- Hitung jumlah snapshot per player
SELECT player_name, COUNT(*) as total_snapshots
FROM napi_player_snapshots
GROUP BY player_uuid
ORDER BY total_snapshots DESC;

-- Hapus snapshot yang lebih tua dari 30 hari
DELETE FROM napi_player_snapshots
WHERE snapshot_time < (UNIX_TIMESTAMP() * 1000 - 30 * 24 * 60 * 60 * 1000);
```

---

### Tabel: `napi_skin_cache`

Cache skin texture dari Mojang Session Server untuk mengurangi rate limiting.

```sql
CREATE TABLE napi_skin_cache (
  player_uuid   VARCHAR(36) PRIMARY KEY,  -- UUID player
  texture_url   TEXT,                     -- URL gambar skin
  texture_value TEXT,                     -- Base64 encoded texture data
  signature     TEXT,                     -- Signature dari Mojang
  cached_at     BIGINT NOT NULL,          -- Waktu cache dibuat (Unix ms)
  expires_at    BIGINT NOT NULL           -- Waktu cache kadaluarsa (Unix ms)
);
```

**TTL default:** 6 jam (dikonfigurasi via `features.skin.cache-ttl-hours`)

---

## Migrasi (Flyway)

NaturalAPI menggunakan **Flyway** untuk mengelola versi skema database secara otomatis.

### Cara Kerja

1. Saat plugin pertama kali start, Flyway menjalankan semua file migrasi dari `V1` sampai versi terbaru
2. Saat plugin di-update, Flyway hanya menjalankan migrasi yang belum pernah dijalankan
3. Status migrasi disimpan di tabel internal `flyway_schema_history`

### File Migrasi

| File | Deskripsi |
|---|---|
| `V1__initial_schema.sql` | Tabel dasar dan setup awal |
| `V2__player_snapshots.sql` | Tabel `napi_player_snapshots` + indeks |
| `V3__api_keys.sql` | Tabel `napi_api_keys` |
| `V4__skin_cache.sql` | Tabel `napi_skin_cache` |

### Cek Status Migrasi (MySQL/MariaDB)

```sql
SELECT version, description, installed_on, success
FROM flyway_schema_history
ORDER BY installed_rank;
```

Output:
```
+----+--------------------+---------------------+---------+
| 1  | initial schema     | 2024-01-15 10:00:00 | 1       |
| 2  | player snapshots   | 2024-01-15 10:00:01 | 1       |
| 3  | api keys           | 2024-01-15 10:00:01 | 1       |
| 4  | skin cache         | 2024-01-15 10:00:01 | 1       |
+----+--------------------+---------------------+---------+
```

> ⚠️ **JANGAN hapus atau modify tabel `flyway_schema_history` secara manual!** Ini bisa menyebabkan error migrasi.

---

## Backup & Restore

### SQLite

**Backup:**
```bash
# Stop server terlebih dahulu (atau pastikan tidak ada write)
cp plugins/NaturalAPI/data.db plugins/NaturalAPI/data.db.backup

# Atau dengan timestamp
cp plugins/NaturalAPI/data.db "plugins/NaturalAPI/backup_$(date +%Y%m%d_%H%M%S).db"
```

**Restore:**
```bash
# Stop server
cp plugins/NaturalAPI/data.db.backup plugins/NaturalAPI/data.db
# Start server
```

### MySQL/MariaDB

**Backup:**
```bash
mysqldump -u napi_user -p naturalapi > naturalapi_backup.sql
```

**Restore:**
```bash
mysql -u napi_user -p naturalapi < naturalapi_backup.sql
```

---

## Troubleshooting

### Error: "Database connection failed"

**Penyebab umum:**
1. MySQL/MariaDB server tidak berjalan
2. Kredensial salah di `config.yml`
3. Database belum dibuat
4. Firewall memblokir port 3306

**Solusi:**
```bash
# Cek apakah MySQL berjalan
sudo systemctl status mysql

# Cek koneksi manual
mysql -u napi_user -p -h localhost naturalapi

# Buat database jika belum ada
mysql -u root -p -e "CREATE DATABASE naturalapi;"
```

### Error: "Flyway migration failed"

**Penyebab umum:**
1. File migrasi rusak
2. Skema database diubah manual dan tidak konsisten

**Solusi:**
```bash
# Cek status migrasi
mysql -u napi_user -p naturalapi -e "SELECT * FROM flyway_schema_history;"

# Jika perlu reset (⚠️ HAPUS SEMUA DATA!)
mysql -u napi_user -p naturalapi -e "DROP ALL TABLES;"
# Lalu restart plugin — Flyway akan re-create semua tabel
```

### Error: "Too many connections"

**Penyebab:** `pool-size` di config terlalu besar, atau ada connection leak.

**Solusi:**
```yaml
database:
  mysql:
    pool-size: 5   # Kurangi dari 10
```

### SQLite: "Database is locked"

**Penyebab:** Multiple proses mengakses file SQLite bersamaan.

**Solusi:** Pastikan hanya satu instance server Minecraft yang menggunakan file database. Untuk multi-server, gunakan MySQL/MariaDB.
