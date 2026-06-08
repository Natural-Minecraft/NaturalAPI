# NaturalAPI — Configuration Guide

Panduan lengkap untuk mengkonfigurasi plugin NaturalAPI melalui file `config.yml`.

---

## Table of Contents

- [Lokasi File](#lokasi-file)
- [Menerapkan Perubahan](#menerapkan-perubahan)
- [Server Configuration](#server-configuration)
- [Database Configuration](#database-configuration)
- [Security Configuration](#security-configuration)
- [Feature Toggles](#feature-toggles)
- [Logging Configuration](#logging-configuration)
- [Full Config Reference](#full-config-reference)

---

## Lokasi File

```
plugins/NaturalAPI/config.yml
```

File ini akan dibuat secara otomatis saat plugin pertama kali dijalankan dengan nilai default.

## Menerapkan Perubahan

Setelah mengedit `config.yml`, jalankan:

```
/napi reload
```

Ini akan melakukan full reload — HTTP server, database, dan semua integrasi akan di-restart dengan konfigurasi baru.

> ⚠️ **Perhatian:** Reload akan memutus semua koneksi HTTP yang sedang aktif. Pastikan tidak ada proses kritis yang sedang mengakses API.

---

## Server Configuration

Mengontrol HTTP server Javalin yang melayani REST API.

```yaml
server:
  port: 7890
  bind-address: "0.0.0.0"
  public-url: "http://localhost:7890"
  base-path: "/api/v1"
  swagger-enabled: true
  swagger-path: "/swagger"
```

### Detail Opsi

- `port`: Port HTTP untuk API (default: `7890`).
- `bind-address`: IP yang bisa mengakses. Gunakan `0.0.0.0` atau biarkan kosong `""` agar terbuka untuk publik. Gunakan `127.0.0.1` jika diletakkan di belakang Reverse Proxy.
- `public-url`: URL publik dari server API ini (termasuk http/https dan port). Nilai ini akan secara dinamis mengganti *server base URL* di dalam Swagger UI dan file `openapi.yaml`. Sangat berguna jika Anda menggunakan domain (misalnya `https://api.naturalsmp.id`).
- `base-path`: Prefix untuk semua endpoint API (default: `/api/v1`).
- `swagger-enabled`: Aktifkan atau matikan dokumentasi interaktif Swagger UI.
- `swagger-path`: URL path untuk mengakses Swagger UI (default: `/swagger`).

### Contoh Konfigurasi

**Development (lokal):**
```yaml
server:
  port: 7890
  bind-address: "127.0.0.1"   # Hanya bisa diakses dari localhost
  swagger-enabled: true
```

**Production (remote access):**
```yaml
server:
  port: 8443
  bind-address: "0.0.0.0"     # Bisa diakses dari luar
  swagger-enabled: false       # Nonaktifkan Swagger di production
```

### Port yang Umum Digunakan

| Port | Kegunaan |
|---|---|
| `7890` | Default NaturalAPI |
| `8080` | Alternative umum |
| `8443` | Cocok untuk reverse proxy HTTPS |
| `25565` | ❌ Jangan gunakan! Ini port Minecraft |

> 💡 **Tip:** Jika Anda menggunakan reverse proxy (Nginx/Caddy), bind ke `127.0.0.1` dan proxy dari port 80/443.

---

## Database Configuration

NaturalAPI mendukung 3 provider database.

```yaml
database:
  provider: sqlite
  sqlite:
    file: "plugins/NaturalAPI/data.db"
  mysql:
    host: "localhost"
    port: 3306
    database: "naturalapi"
    username: "root"
    password: "change_me"
    pool-size: 10
    connection-timeout: 30000
  mariadb:
    host: "localhost"
    port: 3306
    database: "naturalapi"
    username: "root"
    password: "change_me"
    pool-size: 10
```

### Provider: SQLite (Default)

**Rekomendasi:** Development, server kecil (< 50 player)

```yaml
database:
  provider: sqlite
  sqlite:
    file: "plugins/NaturalAPI/data.db"
```

| Key | Tipe | Default | Deskripsi |
|---|---|---|---|
| `file` | String | `"plugins/NaturalAPI/data.db"` | Path file database SQLite. Relatif ke folder server Minecraft. |

**Kelebihan:**
- Zero configuration — langsung jalan
- Tidak perlu install database server
- File tunggal, mudah di-backup

**Kekurangan:**
- Tidak cocok untuk akses bersamaan tinggi
- Performa lebih lambat untuk data besar

---

### Provider: MySQL

**Rekomendasi:** Production, server besar, multiple server (BungeeCord/Velocity)

```yaml
database:
  provider: mysql
  mysql:
    host: "localhost"
    port: 3306
    database: "naturalapi"
    username: "napi_user"
    password: "super_secret_password"
    pool-size: 10
    connection-timeout: 30000
```

| Key | Tipe | Default | Deskripsi |
|---|---|---|---|
| `host` | String | `"localhost"` | Hostname/IP server MySQL |
| `port` | Integer | `3306` | Port MySQL |
| `database` | String | `"naturalapi"` | Nama database (harus sudah dibuat) |
| `username` | String | `"root"` | Username MySQL |
| `password` | String | `"change_me"` | Password MySQL |
| `pool-size` | Integer | `10` | Jumlah maksimal koneksi di connection pool (HikariCP) |
| `connection-timeout` | Integer | `30000` | Timeout koneksi dalam milidetik |

**Setup MySQL:**
```sql
CREATE DATABASE naturalapi CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'napi_user'@'localhost' IDENTIFIED BY 'super_secret_password';
GRANT ALL PRIVILEGES ON naturalapi.* TO 'napi_user'@'localhost';
FLUSH PRIVILEGES;
```

---

### Provider: MariaDB

**Rekomendasi:** Alternatif MySQL yang lebih ringan

```yaml
database:
  provider: mariadb
  mariadb:
    host: "localhost"
    port: 3306
    database: "naturalapi"
    username: "napi_user"
    password: "super_secret_password"
    pool-size: 10
```

Konfigurasi identik dengan MySQL. MariaDB menggunakan driver khusus (`mariadb-java-client`) yang lebih optimal.

---

### Connection Pool (HikariCP)

NaturalAPI menggunakan **HikariCP** untuk connection pooling. Parameter `pool-size` mengontrol jumlah koneksi yang di-maintain.

| Skala Server | Rekomendasi `pool-size` |
|---|---|
| Kecil (< 20 player) | 5 |
| Medium (20-100 player) | 10 (default) |
| Besar (100+ player) | 15-20 |

> ⚠️ **Jangan set pool-size terlalu tinggi!** Setiap koneksi mengkonsumsi memori. `pool-size: 10` sudah cukup untuk kebanyakan server.

---

## Security Configuration

Mengontrol autentikasi, rate limiting, IP filtering, dan CORS.

```yaml
security:
  ip-allowlist:
    enabled: false
    ips: []
  rate-limit:
    enabled: true
    requests-per-minute: 120
    burst: 30
  cors:
    enabled: true
    allowed-origins:
      - "*"
    allowed-methods: ["GET", "POST", "DELETE"]
    allowed-headers: ["Authorization", "Content-Type"]
  swagger-auth-required: false
```

### IP Allowlist

Membatasi akses API hanya dari IP tertentu.

| Key | Tipe | Default | Deskripsi |
|---|---|---|---|
| `enabled` | Boolean | `false` | Aktifkan/nonaktifkan IP filtering |
| `ips` | List<String> | `[]` | Daftar IP yang diizinkan |

**Contoh: Hanya izinkan dari localhost dan jaringan lokal:**
```yaml
security:
  ip-allowlist:
    enabled: true
    ips:
      - "127.0.0.1"
      - "192.168.1.100"
      - "10.0.0.5"
```

Request dari IP yang tidak ada di daftar akan mendapat:
```json
{
  "success": false,
  "error": {
    "code": "IP_BLOCKED",
    "message": "Your IP address is not allowed.",
    "timestamp": 1717600000000
  }
}
```

### Rate Limiting

Membatasi jumlah request per IP menggunakan algoritma Token Bucket.

| Key | Tipe | Default | Deskripsi |
|---|---|---|---|
| `enabled` | Boolean | `true` | Aktifkan/nonaktifkan rate limiter |
| `requests-per-minute` | Integer | `120` | Jumlah request maksimal per menit per IP |
| `burst` | Integer | `30` | Kapasitas burst awal (token bucket size) |

**Cara kerja Token Bucket:**
1. Setiap IP mendapat `burst` token di awal
2. Setiap request mengonsumsi 1 token
3. Token diisi ulang dengan rate `requests-per-minute / 60` per detik
4. Jika token habis → `429 Too Many Requests`

**Response header yang ditambahkan:**
```
X-RateLimit-Limit: 120
X-RateLimit-Remaining: 85
```

**Contoh konfigurasi untuk traffic tinggi:**
```yaml
security:
  rate-limit:
    enabled: true
    requests-per-minute: 300
    burst: 50
```

### CORS (Cross-Origin Resource Sharing)

Mengontrol akses dari browser web (domain yang berbeda).

| Key | Tipe | Default | Deskripsi |
|---|---|---|---|
| `enabled` | Boolean | `true` | Aktifkan/nonaktifkan CORS headers |
| `allowed-origins` | List<String> | `["*"]` | Domain yang diizinkan. `*` = semua domain |
| `allowed-methods` | List<String> | `["GET", "POST", "DELETE"]` | HTTP method yang diizinkan |
| `allowed-headers` | List<String> | `["Authorization", "Content-Type"]` | Header yang diizinkan |

**Contoh untuk production (hanya izinkan dashboard):**
```yaml
security:
  cors:
    enabled: true
    allowed-origins:
      - "https://dashboard.example.com"
      - "https://admin.example.com"
    allowed-methods: ["GET", "POST", "DELETE"]
    allowed-headers: ["Authorization", "Content-Type"]
```

> ⚠️ **Jangan gunakan `"*"` di production!** Ini memungkinkan website manapun mengakses API Anda.

### Swagger Auth

| Key | Tipe | Default | Deskripsi |
|---|---|---|---|
| `swagger-auth-required` | Boolean | `false` | Jika `true`, Swagger UI membutuhkan Bearer token |

---

## Feature Toggles

Mengontrol modul apa saja yang aktif. Berguna untuk menghemat resource server.

```yaml
features:
  endpoints:
    server: true
    players: true
    worlds: true
    offline-players: true
  websocket:
    enabled: true
    endpoints:
      server-stats: true
      player-events: true
      chat: true
      player-detail: true
  vanish:
    show-vanished-in-count: false
    show-vanished-in-list: false
  snapshot:
    auto-save: true
    interval-minutes: 10
    retention-days: 30
  skin:
    cache-ttl-hours: 6
  papi:
    enabled: true
  vault:
    enabled: true
  luckperms:
    enabled: true
```

### Endpoint Toggles

Nonaktifkan endpoint yang tidak diperlukan untuk mengurangi beban.

| Key | Default | Deskripsi | Dampak jika `false` |
|---|---|---|---|
| `endpoints.server` | `true` | Endpoint `/server/*` | Tidak bisa akses server stats |
| `endpoints.players` | `true` | Endpoint `/players/*` | Tidak bisa akses data player |
| `endpoints.worlds` | `true` | Endpoint `/worlds/*` | Tidak bisa akses data world |
| `endpoints.offline-players` | `true` | Endpoint `/players/offline/*` | Tidak bisa akses data player offline |

### WebSocket Configuration

| Key | Default | Deskripsi |
|---|---|---|
| `enabled` | `true` | Master switch untuk seluruh WebSocket system |
| `server-stats-interval` | `20` | Interval broadcast `/ws/server` (dalam tick, 20 = 1 detik) |
| `server-stats` | `true` | `/ws/server` — real-time server stats broadcast |
| `player-events` | `true` | `/ws/players` — player join/leave events |
| `chat` | `true` | `/ws/chat` — real-time chat messages |
| `player-detail` | `true` | `/ws/player/{uuid}` — per-player detail stream |

**Contoh: Nonaktifkan chat WebSocket (hanya monitoring):**
```yaml
features:
  websocket:
    enabled: true
    endpoints:
      server-stats: true
      player-events: true
      chat: false
      player-detail: false
```

**Contoh: Server hanya butuh monitoring TPS (nonaktifkan fitur lain):**
```yaml
features:
  endpoints:
    server: true
    players: false      # Hemat resource
    worlds: false       # Hemat resource
    offline-players: false
```

### Vanish Configuration

| Key | Default | Deskripsi |
|---|---|---|
| `show-vanished-in-count` | `false` | Tampilkan player vanished di `/server/players/count` |
| `show-vanished-in-list` | `false` | Tampilkan player vanished di `/players` list |

### Snapshot Configuration

| Key | Default | Deskripsi |
|---|---|---|
| `auto-save` | `true` | Otomatis simpan snapshot player secara berkala |
| `interval-minutes` | `10` | Interval antar snapshot otomatis (dalam menit) |
| `retention-days` | `30` | Hapus snapshot yang lebih tua dari N hari |

**Contoh: Snapshot lebih sering untuk server competitive:**
```yaml
features:
  snapshot:
    auto-save: true
    interval-minutes: 5    # Setiap 5 menit
    retention-days: 90     # Simpan 3 bulan
```

### Skin Cache

| Key | Default | Deskripsi |
|---|---|---|
| `cache-ttl-hours` | `6` | Waktu cache skin dari Mojang (dalam jam) |

> 💡 **Tip:** Mojang API memiliki rate limit. Cache 6 jam cukup karena skin jarang berubah.

### Integration Toggles

| Key | Default | Deskripsi |
|---|---|---|
| `papi.enabled` | `true` | Aktifkan integrasi PlaceholderAPI |
| `vault.enabled` | `true` | Aktifkan integrasi Vault |
| `luckperms.enabled` | `true` | Aktifkan integrasi LuckPerms |

Jika plugin yang bersangkutan tidak ter-install, integrasi secara otomatis di-skip meskipun `enabled: true`.

---

## Logging Configuration

```yaml
logging:
  log-requests: true
  log-auth-failures: true
  log-level: INFO
```

| Key | Tipe | Default | Deskripsi |
|---|---|---|---|
| `log-requests` | Boolean | `true` | Log setiap HTTP request ke console |
| `log-auth-failures` | Boolean | `true` | Log percobaan autentikasi yang gagal |
| `log-level` | String | `INFO` | Level logging: `DEBUG`, `INFO`, `WARN`, `ERROR` |

**Contoh log output (log-requests: true):**
```
[NaturalAPI] GET /api/v1/server/tps [200] 5ms (192.168.1.100)
[NaturalAPI] GET /api/v1/players [200] 12ms (192.168.1.100)
```

**Contoh log output (log-auth-failures: true):**
```
[NaturalAPI] AUTH FAILURE: Invalid token from 10.0.0.5 - GET /api/v1/players
```

---

## Full Config Reference

```yaml
# ============================================
# NaturalAPI Configuration
# Plugin: NaturalAPI v1.0.0
# ============================================

# --- HTTP Server ---
server:
  port: 7890                    # Port untuk REST API
  bind-address: "0.0.0.0"      # Bind address ("0.0.0.0" = semua interface)
  base-path: "/api/v1"         # Base path untuk semua endpoint
  swagger-enabled: true         # Aktifkan Swagger UI
  swagger-path: "/swagger"      # Path Swagger UI

# --- Database ---
database:
  provider: sqlite              # sqlite | mysql | mariadb
  sqlite:
    file: "plugins/NaturalAPI/data.db"
  mysql:
    host: "localhost"
    port: 3306
    database: "naturalapi"
    username: "root"
    password: "change_me"
    pool-size: 10
    connection-timeout: 30000
  mariadb:
    host: "localhost"
    port: 3306
    database: "naturalapi"
    username: "root"
    password: "change_me"
    pool-size: 10

# --- Security ---
security:
  ip-allowlist:
    enabled: false
    ips: []
  rate-limit:
    enabled: true
    requests-per-minute: 120
    burst: 30
  cors:
    enabled: true
    allowed-origins:
      - "*"
    allowed-methods: ["GET", "POST", "DELETE"]
    allowed-headers: ["Authorization", "Content-Type"]
  swagger-auth-required: false

# --- Features ---
features:
  endpoints:
    server: true
    players: true
    worlds: true
    offline-players: true
  vanish:
    show-vanished-in-count: false
    show-vanished-in-list: false
  snapshot:
    auto-save: true
    interval-minutes: 10
    retention-days: 30
  skin:
    cache-ttl-hours: 6
  papi:
    enabled: true
  vault:
    enabled: true
  luckperms:
    enabled: true

# --- Logging ---
logging:
  log-requests: true
  log-auth-failures: true
  log-level: INFO
```
