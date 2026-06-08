# NaturalAPI — Permission Reference

Dokumentasi lengkap semua permission node yang digunakan oleh plugin NaturalAPI.

---

## Table of Contents

- [Overview](#overview)
- [Permission: `naturalapi.use`](#permission-naturalapiuse)
- [Permission: `naturalapi.admin`](#permission-naturalapiadmin)
- [Permission: `naturalapi.bypass.ratelimit`](#permission-naturalapibypassratelimit)
- [Contoh Konfigurasi LuckPerms](#contoh-konfigurasi-luckperms)
- [Contoh Konfigurasi permissions.yml](#contoh-konfigurasi-permissionsyml)

---

## Overview

| Permission Node | Default | Deskripsi |
|---|---|---|
| `naturalapi.use` | `true` (semua player) | Akses dasar ke plugin — lihat info dan help |
| `naturalapi.admin` | `op` (operator only) | Akses penuh ke semua command admin |
| `naturalapi.bypass.ratelimit` | `op` (operator only) | Bypass rate limit pada HTTP API |

---

## Permission: `naturalapi.use`

**Default:** `true` — diberikan ke **semua player** secara otomatis.

**Deskripsi:**  
Permission dasar untuk menggunakan plugin NaturalAPI. Memberikan akses ke command informatif yang tidak mengubah state server.

### Command yang Membutuhkan Permission Ini

| Command | Fungsi |
|---|---|
| `/napi` | Menampilkan info plugin (nama, versi, author) |
| `/napi help` | Menampilkan menu bantuan dengan daftar semua command |

### Apa yang BISA Dilakukan

- ✅ Melihat versi plugin yang terpasang
- ✅ Melihat daftar command yang tersedia
- ✅ Mengetahui apakah NaturalAPI aktif di server

### Apa yang TIDAK BISA Dilakukan

- ❌ Tidak bisa reload plugin
- ❌ Tidak bisa generate/revoke API key
- ❌ Tidak bisa melihat status server/database
- ❌ Tidak bisa trigger snapshot player
- ❌ Tidak bisa mengakses REST API (API menggunakan token, bukan permission Bukkit)

### Contoh Use Case

**Skenario 1: Player biasa ingin tahu versi plugin**
```
[Player] /napi
[Server] §6NaturalAPI §7v1.0.0
```

**Skenario 2: Player ingin tahu command apa saja yang tersedia**
```
[Player] /napi help
[Server] §e=== NaturalAPI Help ===
         §6/napi §7- Show plugin info
         ... (hanya menampilkan command yang player punya permission)
```

### Catatan
- Karena default `true`, permission ini tidak perlu di-set manual kecuali Anda ingin **mencabutnya** dari group tertentu.
- Untuk mencabut: `lp group default permission set naturalapi.use false`

---

## Permission: `naturalapi.admin`

**Default:** `op` — hanya diberikan ke **server operator** secara default.

**Deskripsi:**  
Permission admin yang memberikan akses penuh ke semua command administratif NaturalAPI. Ini termasuk manajemen API key, reload plugin, trigger snapshot, dan melihat status internal.

### Command yang Membutuhkan Permission Ini

| Command | Fungsi | Dampak |
|---|---|---|
| `/napi status` | Lihat status HTTP server & DB | Read-only, aman |
| `/napi reload` | Full reload plugin | ⚠️ Memutus semua koneksi HTTP aktif |
| `/napi key generate <name> [scopes]` | Buat API key baru | ⚠️ Key memberikan akses ke data server |
| `/napi key list` | Daftar semua API key | Read-only, menampilkan ID dan nama |
| `/napi key revoke <id>` | Hapus API key | ⚠️ Aplikasi yang pakai key ini akan putus |
| `/napi snapshot <player>` | Simpan snapshot player ke DB | Menulis ke database |
| `/napi debug` | Lihat status integrasi plugin | Read-only, aman |

### Apa yang BISA Dilakukan

- ✅ Semua yang `naturalapi.use` bisa lakukan
- ✅ Mengelola API key (generate, list, revoke)
- ✅ Melihat status HTTP server dan database
- ✅ Reload seluruh plugin tanpa restart server
- ✅ Memaksa snapshot data player ke database
- ✅ Melihat status integrasi Vault/LuckPerms/PAPI/Vanish

### Apa yang TIDAK BISA Dilakukan

- ❌ Tidak bisa mengubah `config.yml` dari in-game (harus edit file manual, lalu `/napi reload`)
- ❌ Tidak bisa mengakses REST API secara langsung (API menggunakan token terpisah)

### Contoh Use Case

**Skenario 1: Admin membuat key untuk Discord bot**
```
[Admin] /napi key generate discord-bot read:server,read:players
[Server] §aAPI Key generated successfully.
         §7Name: §fdiscord-bot
         §7Scopes: §fread:server,read:players
         §cKeep this key safe! It won't be shown again:
         §ea1b2c3d4-...<key>
```

**Skenario 2: Admin troubleshoot API tidak merespons**
```
[Admin] /napi status
[Server] §aNaturalAPI Status:
         §7HTTP Server: §cStopped      ← Masalah ditemukan!
         §7Database: §aConnected

[Admin] /napi reload               ← Coba reload
[Server] §aNaturalAPI has been reloaded successfully.

[Admin] /napi status
[Server] §aNaturalAPI Status:
         §7HTTP Server: §aRunning      ← Berhasil!
         §7Database: §aConnected
```

**Skenario 3: API key bocor, admin segera revoke**
```
[Admin] /napi key list
[Server] §eAPI Keys:
         §7- ID: §fa1b2c3d4-... §7(Name: my-dashboard)
         §7- ID: §fb2c3d4e5-... §7(Name: leaked-key)     ← Key ini bocor!

[Admin] /napi key revoke b2c3d4e5-6789-abcd-ef01-23456789abcd
[Server] §aKey revoked: b2c3d4e5-6789-abcd-ef01-23456789abcd
```

**Skenario 4: Admin menyimpan snapshot player sebelum maintenance**
```
[Admin] /napi snapshot Rifqi_
[Server] §aSnapshot triggered for Rifqi_
```
Sekarang data Rifqi_ bisa diakses via API offline endpoint (`/players/offline/name/Rifqi_`) bahkan setelah dia logout.

### Keamanan

> ⚠️ **JANGAN berikan permission ini ke player biasa!**
> 
> Permission `naturalapi.admin` memungkinkan:
> - Membuat API key yang bisa membaca semua data player (termasuk inventory, lokasi, IP)
> - Me-reload plugin (bisa menyebabkan downtime singkat pada API)
> - Melihat informasi sensitif server (database status, port, dll)

### Cara Memberikan Permission

**Via LuckPerms:**
```
lp user <username> permission set naturalapi.admin true
lp group admin permission set naturalapi.admin true
```

**Via permissions.yml (Bukkit native):**
```yaml
groups:
  admin:
    permissions:
      naturalapi.admin: true
```

---

## Permission: `naturalapi.bypass.ratelimit`

**Default:** `op` — hanya diberikan ke **server operator** secara default.

**Deskripsi:**  
Permission khusus yang memungkinkan pemegangnya untuk melewati (bypass) batasan rate limit pada HTTP API. Permission ini **BUKAN** untuk command in-game, melainkan untuk **request HTTP ke REST API**.

### Cara Kerja

Rate limiter NaturalAPI menggunakan algoritma **Token Bucket** per IP address:
- Setiap IP mendapat sejumlah token (sesuai `security.rate-limit.burst`)
- Setiap request mengonsumsi 1 token
- Token diisi ulang sesuai `security.rate-limit.requests-per-minute`
- Jika token habis → respons `429 Too Many Requests`

Dengan permission `naturalapi.bypass.ratelimit`, request dari IP yang terasosiasi **tidak** dibatasi oleh rate limiter.

### Apa yang BISA Dilakukan

- ✅ Melakukan request HTTP tanpa batasan jumlah per menit
- ✅ Tidak mendapat respons `429 Too Many Requests`
- ✅ Berguna untuk internal monitoring tools yang perlu polling cepat

### Apa yang TIDAK BISA Dilakukan

- ❌ Tidak memberikan akses ke command admin
- ❌ Tidak bypass autentikasi Bearer token (tetap perlu API key)
- ❌ Tidak bypass IP allowlist

### Contoh Use Case

**Skenario: Monitoring tool internal yang polling setiap 5 detik**

Tanpa bypass, rate limit default `120 requests/menit` mungkin tidak cukup untuk tool yang membutuhkan polling agresif.

**Konfigurasi rate limit di `config.yml`:**
```yaml
security:
  rate-limit:
    enabled: true
    requests-per-minute: 120   # Batas normal
    burst: 30                  # Burst awal
```

**Tanpa bypass (error setelah burst habis):**
```
HTTP/1.1 429 Too Many Requests
X-RateLimit-Limit: 120
X-RateLimit-Remaining: 0

{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Too many requests.",
    "timestamp": 1717600000000
  }
}
```

**Dengan bypass: request selalu berhasil tanpa batas.**

### Kapan Menggunakan

| Situasi | Gunakan? |
|---|---|
| Dashboard web publik | ❌ Tidak perlu, rate limit normal cukup |
| Monitoring internal (Prometheus/Grafana) | ✅ Ya, butuh polling cepat |
| Discord bot | ❌ Tidak perlu, polling biasanya lambat |
| Load testing / stress testing | ✅ Ya, untuk menguji kapasitas server |
| Aplikasi production dengan traffic tinggi | ⚠️ Pertimbangkan naikkan rate limit di config |

---

## Contoh Konfigurasi LuckPerms

Berikut contoh lengkap setup permission menggunakan LuckPerms:

### Setup Group "Admin"
```bash
# Berikan semua permission NaturalAPI ke group admin
lp group admin permission set naturalapi.use true
lp group admin permission set naturalapi.admin true
lp group admin permission set naturalapi.bypass.ratelimit true
```

### Setup Group "Moderator" (Akses terbatas)
```bash
# Moderator hanya bisa lihat status, tidak bisa manage key
lp group moderator permission set naturalapi.use true
# JANGAN berikan naturalapi.admin
```

### Setup User Spesifik
```bash
# Berikan admin permission ke user tertentu
lp user Rifqi_ permission set naturalapi.admin true

# Cabut permission dari user tertentu
lp user SomePlayer permission set naturalapi.admin false
```

### Cek Permission User
```bash
lp user Rifqi_ permission check naturalapi.admin
# Output: naturalapi.admin: true (inherited from group admin)
```

---

## Contoh Konfigurasi permissions.yml

Untuk server yang tidak menggunakan LuckPerms:

```yaml
groups:
  default:
    default: true
    permissions:
      naturalapi.use: true        # Semua player bisa lihat info

  moderator:
    inheritance:
      - default
    permissions:
      naturalapi.use: true

  admin:
    inheritance:
      - moderator
    permissions:
      naturalapi.admin: true            # Akses penuh ke command admin
      naturalapi.bypass.ratelimit: true  # Bypass rate limit HTTP API

  owner:
    inheritance:
      - admin
    permissions:
      naturalapi.*: true                 # Wildcard — semua permission
```

---

## Hubungan Permission vs API Key Scope

Penting untuk dipahami bahwa **permission Bukkit** dan **API key scope** adalah dua sistem yang berbeda:

| Aspek | Permission Bukkit | API Key Scope |
|---|---|---|
| Digunakan oleh | Player in-game (via command) | Aplikasi eksternal (via HTTP request) |
| Disimpan di | Permission plugin (LuckPerms, dll) | Database NaturalAPI (napi_api_keys) |
| Contoh | `naturalapi.admin` | `read:server`, `admin` |
| Validasi | Saat player menjalankan command | Saat HTTP request masuk ke API |

**Contoh alur lengkap:**
1. Admin dengan permission `naturalapi.admin` menjalankan `/napi key generate bot read:server`
2. Key dibuat dengan scope `read:server`
3. Discord bot menggunakan key tersebut untuk `GET /api/v1/server/tps`
4. API memvalidasi key dan scope → berhasil
5. Discord bot coba akses `GET /api/v1/players` → **gagal** (`403 Forbidden`) karena scope hanya `read:server`
