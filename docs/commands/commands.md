# NaturalAPI — Command Reference

Semua command NaturalAPI menggunakan prefix `/napi` (alias dari `/naturalapi`).

---

## Table of Contents

- [Overview](#overview)
- [Command: `/napi`](#command-napi)
- [Command: `/napi help`](#command-napi-help)
- [Command: `/napi status`](#command-napi-status)
- [Command: `/napi reload`](#command-napi-reload)
- [Command: `/napi key generate`](#command-napi-key-generate)
- [Command: `/napi key list`](#command-napi-key-list)
- [Command: `/napi key revoke`](#command-napi-key-revoke)
- [Command: `/napi snapshot`](#command-napi-snapshot)
- [Command: `/napi debug`](#command-napi-debug)

---

## Overview

| Command | Deskripsi Singkat | Permission |
|---|---|---|
| `/napi` | Tampilkan info plugin & versi | `naturalapi.use` |
| `/napi help` | Tampilkan menu bantuan | `naturalapi.use` |
| `/napi status` | Cek status HTTP server & database | `naturalapi.admin` |
| `/napi reload` | Full reload plugin (config, DB, HTTP) | `naturalapi.admin` |
| `/napi key generate <name> [scopes]` | Buat API key baru | `naturalapi.admin` |
| `/napi key list` | Daftar semua API key yang tersimpan | `naturalapi.admin` |
| `/napi key revoke <id>` | Hapus/cabut API key | `naturalapi.admin` |
| `/napi snapshot <player>` | Paksa simpan snapshot data player ke DB | `naturalapi.admin` |
| `/napi debug` | Tampilkan status integrasi plugin | `naturalapi.admin` |

---

## Command: `/napi`

**Deskripsi:**  
Menampilkan informasi dasar tentang plugin NaturalAPI, termasuk versi, author, dan link dokumentasi.

**Permission:** `naturalapi.use` (default: semua player)

**Argumen:** Tidak ada

**Contoh Penggunaan:**
```
/napi
```

**Output:**
```
§6NaturalAPI §7v1.0.0
§7Minecraft REST API Plugin by NaturalSMP Team
§7Ketik /napi help untuk bantuan.
```

**Use Case:**  
- Seorang player ingin mengecek apakah plugin NaturalAPI sudah ter-install di server.
- Admin ingin melihat versi plugin yang sedang berjalan.

---

## Command: `/napi help`

**Deskripsi:**  
Menampilkan daftar lengkap semua command yang tersedia beserta penjelasan singkatnya.

**Permission:** `naturalapi.use` (default: semua player)

**Argumen:** Tidak ada

**Contoh Penggunaan:**
```
/napi help
```

**Output:**
```
§e=== NaturalAPI Help ===
§6/napi §7- Show plugin info
§6/napi status §7- HTTP & DB status
§6/napi reload §7- Reload plugin
§6/napi key generate <name> [scopes] §7- Generate API key
§6/napi key list §7- List API keys
§6/napi key revoke <id> §7- Revoke API key
§6/napi snapshot <player> §7- Snapshot player data
```

---

## Command: `/napi status`

**Deskripsi:**  
Menampilkan status terkini dari HTTP server Javalin dan koneksi database (SQLite/MySQL/MariaDB). Berguna untuk troubleshooting apakah API sedang aktif dan apakah koneksi DB berhasil.

**Permission:** `naturalapi.admin` (default: operator)

**Argumen:** Tidak ada

**Contoh Penggunaan:**
```
/napi status
```

**Output (Berhasil):**
```
§aNaturalAPI Status:
§7HTTP Server: §aRunning
§7Database: §aConnected
```

**Output (Bermasalah):**
```
§aNaturalAPI Status:
§7HTTP Server: §cStopped
§7Database: §cDisconnected
```

**Use Case:**  
- Admin melakukan troubleshooting karena dashboard web tidak bisa terhubung ke API.
- Setelah restart server, admin ingin memastikan plugin sudah berjalan normal.
- Memverifikasi bahwa koneksi database tidak terputus.

---

## Command: `/napi reload`

**Deskripsi:**  
Melakukan **full reload** pada seluruh komponen plugin. Ini bukan soft reload — proses yang dilakukan:

1. Stop HTTP server (Javalin.stop())
2. Tutup connection pool database (HikariCP.close())
3. Unregister semua Bukkit event listener
4. Cancel semua scheduled task
5. Clear semua referensi integrasi
6. Baca ulang `config.yml` dari disk
7. Re-inisialisasi database + jalankan migrasi pending
8. Re-inisialisasi integrasi (Vault, LuckPerms, PAPI, Vanish)
9. Restart HTTP server (port baru jika diubah di config)
10. Re-register event listener & scheduler
11. Broadcast reload success ke console

**Permission:** `naturalapi.admin` (default: operator)

**Argumen:** Tidak ada

**Contoh Penggunaan:**
```
/napi reload
```

**Output:**
```
§aNaturalAPI has been reloaded successfully.
```

**Use Case:**  
- Admin baru saja mengubah port di `config.yml` dari `7890` ke `8080` dan ingin menerapkan perubahan tanpa restart server.
- Admin mengganti provider database dari `sqlite` ke `mysql` di config.
- API key security diubah (IP allowlist, rate limit settings).

**⚠️ Peringatan:**  
- Reload akan memutus semua koneksi HTTP yang sedang aktif.
- Jika port baru sudah digunakan oleh proses lain, HTTP server akan gagal start.

---

## Command: `/napi key generate`

**Deskripsi:**  
Membuat API key baru yang disimpan secara aman di database (di-hash dengan SHA-256 + salt). Key yang dihasilkan hanya ditampilkan **satu kali** — tidak bisa dilihat lagi setelahnya.

**Permission:** `naturalapi.admin` (default: operator)

**Sintaks:**
```
/napi key generate <name> [scopes]
```

**Argumen:**

| Argumen | Wajib | Tipe | Deskripsi |
|---|---|---|---|
| `<name>` | ✅ Ya | String | Nama label untuk key ini (contoh: `dashboard`, `discord-bot`, `mobile-app`) |
| `[scopes]` | ❌ Opsional | String | Scope yang diizinkan, dipisahkan koma. Default: `*` (semua akses) |

**Scope yang tersedia:**

| Scope | Deskripsi |
|---|---|
| `read:server` | Akses endpoint server stats |
| `read:players` | Akses endpoint data player |
| `read:worlds` | Akses endpoint data world |
| `read:vault` | Akses endpoint Vault |
| `read:luckperms` | Akses endpoint LuckPerms |
| `read:papi` | Akses endpoint PlaceholderAPI |
| `admin` | Akses endpoint admin |
| `*` | Semua scope (super key) |

**Contoh Penggunaan:**

**Contoh 1: Key dengan semua akses**
```
/napi key generate my-dashboard
```
Output:
```
§aAPI Key generated successfully.
§7Name: §fmy-dashboard
§7Scopes: §f*
§cKeep this key safe! It won't be shown again:
§ea1b2c3d4-5678-9abc-def0-123456789abc.f47ac10b58cc4372a5670e02b2c3d479
```

**Contoh 2: Key dengan scope terbatas**
```
/napi key generate discord-bot read:server,read:players
```
Output:
```
§aAPI Key generated successfully.
§7Name: §fdiscord-bot
§7Scopes: §fread:server,read:players
§cKeep this key safe! It won't be shown again:
§eb2c3d4e5-6789-abcd-ef01-23456789abcd.8e7f6a5b4c3d2e1f0a9b8c7d6e5f4a3b
```

**Use Case:**  
- Admin membuat key khusus untuk Discord bot yang hanya perlu membaca status server dan daftar player online.
- Admin membuat key super (`*`) untuk dashboard web internal.
- Admin membuat key terpisah untuk setiap aplikasi eksternal agar bisa di-revoke secara individual.

**⚠️ Penting:**  
- **Salin key segera!** Key hanya ditampilkan sekali. Jika hilang, harus generate key baru.
- Key disimpan dalam format `<uuid>.<secret>` — keduanya diperlukan untuk autentikasi.

---

## Command: `/napi key list`

**Deskripsi:**  
Menampilkan daftar semua API key yang tersimpan di database. **Secret key TIDAK ditampilkan** — hanya ID dan nama.

**Permission:** `naturalapi.admin` (default: operator)

**Argumen:** Tidak ada

**Contoh Penggunaan:**
```
/napi key list
```

**Output:**
```
§eAPI Keys:
§7- ID: §fa1b2c3d4-5678-9abc-def0-123456789abc §7(Name: my-dashboard)
§7- ID: §fb2c3d4e5-6789-abcd-ef01-23456789abcd §7(Name: discord-bot)
§7- ID: §fc3d4e5f6-789a-bcde-f012-3456789abcde §7(Name: mobile-app)
```

**Output (Kosong):**
```
§eAPI Keys:
```

**Use Case:**  
- Admin ingin mengetahui berapa key yang sudah dibuat.
- Admin mencari ID key tertentu yang akan di-revoke.
- Audit keamanan untuk melihat semua key aktif.

---

## Command: `/napi key revoke`

**Deskripsi:**  
Menghapus/mencabut sebuah API key secara permanen dari database. Setelah di-revoke, key tersebut tidak bisa digunakan lagi untuk autentikasi.

**Permission:** `naturalapi.admin` (default: operator)

**Sintaks:**
```
/napi key revoke <id>
```

**Argumen:**

| Argumen | Wajib | Tipe | Deskripsi |
|---|---|---|---|
| `<id>` | ✅ Ya | UUID String | ID dari key yang akan dihapus (dapatkan dari `/napi key list`) |

**Contoh Penggunaan:**
```
/napi key revoke a1b2c3d4-5678-9abc-def0-123456789abc
```

**Output:**
```
§aKey revoked: a1b2c3d4-5678-9abc-def0-123456789abc
```

**Use Case:**  
- Sebuah API key bocor ke publik — admin segera me-revoke key tersebut.
- Aplikasi eksternal sudah tidak digunakan lagi, key-nya dicabut.
- Rotasi key secara berkala untuk keamanan.

**⚠️ Peringatan:**  
- Tindakan ini **tidak bisa di-undo**. Key yang sudah di-revoke tidak bisa dikembalikan.
- Semua request yang menggunakan key ini akan langsung mendapat `401 Unauthorized`.

---

## Command: `/napi snapshot`

**Deskripsi:**  
Memaksa server untuk langsung menyimpan snapshot data seorang player yang sedang online ke database. Data yang disimpan meliputi: lokasi, health, food, exp, gamemode, full inventory (dalam format JSON), armor, efek potion, data Vault, dan data LuckPerms.

Secara default, snapshot dilakukan otomatis setiap `interval-minutes` (default: 10 menit). Command ini berguna untuk memaksa snapshot segera.

**Permission:** `naturalapi.admin` (default: operator)

**Sintaks:**
```
/napi snapshot <player>
```

**Argumen:**

| Argumen | Wajib | Tipe | Deskripsi |
|---|---|---|---|
| `<player>` | ✅ Ya | String | Nama player yang sedang online |

**Contoh Penggunaan:**
```
/napi snapshot Rifqi_
```

**Output (Berhasil):**
```
§aSnapshot triggered for Rifqi_
```

**Output (Player tidak ditemukan):**
```
§cPlayer not found.
```

**Use Case:**  
- Player akan logout dan admin ingin memastikan data terakhirnya tersimpan sebelum offline.
- Admin ingin meng-update snapshot data player sebelum mengaksesnya via API offline endpoint.
- Debugging — memastikan snapshot service berjalan dengan benar.

**Data yang disimpan dalam snapshot:**

| Field | Deskripsi |
|---|---|
| `player_uuid` | UUID player |
| `player_name` | Username saat snapshot |
| `world` | Nama world saat snapshot |
| `x, y, z` | Koordinat lokasi |
| `yaw, pitch` | Rotasi pandangan |
| `health, max_health` | HP saat ini dan maksimal |
| `food_level, saturation` | Level makanan dan saturasi |
| `exp_level, exp_progress, total_exp` | Data experience |
| `gamemode` | Mode game (SURVIVAL, CREATIVE, dll) |
| `inventory_json` | Full inventory dalam JSON |
| `armor_json` | Armor yang dipakai dalam JSON |
| `effects_json` | Efek potion aktif dalam JSON |
| `skin_texture, skin_signature` | Data skin dari Mojang |
| `vault_group, vault_prefix, vault_suffix` | Data Vault (jika ada) |
| `lp_group` | Primary group LuckPerms (jika ada) |

---

## Command: `/napi debug`

**Deskripsi:**  
Menampilkan status lengkap semua integrasi plugin pihak ketiga yang terdeteksi oleh NaturalAPI.

**Permission:** `naturalapi.admin` (default: operator)

**Argumen:** Tidak ada

**Contoh Penggunaan:**
```
/napi debug
```

**Output:**
```
§e=== NaturalAPI Debug ===
§7Vault: §aDetected (EssentialsX Economy)
§7LuckPerms: §aDetected (v5.4)
§7PlaceholderAPI: §aDetected (v2.11.5)
§7Vanish: §aSuperVanish detected
§7Database: §aSQLite (plugins/NaturalAPI/data.db)
§7HTTP Port: §f7890
§7Snapshot Interval: §f10 minutes
§7Feature Toggles:
§7  - Server endpoints: §aEnabled
§7  - Player endpoints: §aEnabled
§7  - World endpoints: §aEnabled
§7  - Offline player endpoints: §aEnabled
```

**Use Case:**  
- Troubleshooting mengapa endpoint Vault mengembalikan error `VAULT_UNAVAILABLE`.
- Memastikan LuckPerms terdeteksi dengan benar setelah install.
- Cek apakah vanish detection berfungsi.
