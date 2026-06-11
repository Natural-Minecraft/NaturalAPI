# NaturalAPI

<div align="center">

![NaturalAPI](https://img.shields.io/badge/NaturalAPI-v1.0.0-brightgreen?style=for-the-badge)
![Minecraft](https://img.shields.io/badge/Minecraft-1.21.x-blue?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-21_Temurin-orange?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**Plugin REST API bertenaga tinggi untuk server Minecraft 1.21.x**

[📖 Dokumentasi](#dokumentasi) • [🚀 Instalasi](#instalasi) • [⚙️ Konfigurasi](#konfigurasi) • [🔑 API Key](#api-key) • [📡 Endpoints](#endpoints)

</div>

---

## Tentang NaturalAPI

**NaturalAPI** adalah plugin Paper/Spigot yang menyediakan REST API lengkap untuk mengakses data server Minecraft secara real-time dari luar server — cocok untuk dashboard web, bot Discord, panel admin, integrasi dengan sistem lain, dan otomasi.

### ✨ Fitur Utama

| Fitur | Keterangan |
|---|---|
| 🌐 REST API | HTTP API lengkap dengan Javalin + embedded Jetty |
| 📊 Server Stats | TPS (1m/5m/15m), MSPT, RAM, uptime, versi |
| 👤 Player Data | Inventory, armor, hotbar, skin, lokasi, health, ping, XP, gamemode |
| 👻 Vanish-Aware | Support SuperVanish, PremiumVanish, CMI, Essentials |
| 💰 Vault | Group, prefix, suffix, balance, permission nodes |
| 🎮 LuckPerms | Primary group, all groups, nodes, meta, weight, context |
| 📝 PlaceholderAPI | Evaluasi placeholder PAPI via API |
| 🌍 World Data | Time, weather, entities, chunks, border, gamerules |
| 💾 Snapshot | Simpan/ambil data player ke SQLite/MySQL/MariaDB |
| 📚 Swagger UI | Self-documenting API dengan OpenAPI 3.1 |
| 🔒 Security | Bearer token, IP allowlist, rate limiter, CORS, scopes |
| 🔄 True Reload | `/napi reload` = restart penuh plugin tanpa restart server |

---

## Instalasi

### Prasyarat

- Paper / Spigot **1.21.x**
- Java **21 (Temurin)** atau lebih baru
- (Opsional) Vault, LuckPerms, PlaceholderAPI, SuperVanish

### Langkah

1. Download `NaturalAPI-1.0.0.jar` dari releases
2. Letakkan di folder `plugins/` server kamu
3. Restart server (atau `/reload confirm` — tapi restart lebih disarankan)
4. Plugin akan generate `plugins/NaturalAPI/config.yml`
5. Edit konfigurasi sesuai kebutuhan
6. Jalankan `/napi reload` untuk menerapkan perubahan

### Build dari Source

```bash
git clone https://github.com/naturalsmp/NaturalAPI.git
cd NaturalAPI
mvn clean package -P shade
# Output: target/NaturalAPI-1.0.0.jar
```

---

## Konfigurasi

File konfigurasi utama ada di `plugins/NaturalAPI/config.yml`.

```yaml
server:
  port: 7890              # Port HTTP server
  bind-address: "0.0.0.0" # Bind ke semua interface
  swagger-enabled: true   # Aktifkan Swagger UI

database:
  provider: sqlite        # sqlite | mysql | mariadb
  sqlite:
    file: "plugins/NaturalAPI/data.db"
  # Untuk MySQL/MariaDB, uncomment section mysql/mariadb

security:
  ip-allowlist:
    enabled: false        # Set true untuk whitelist IP
    ips: ["127.0.0.1"]
  rate-limit:
    enabled: true
    requests-per-minute: 120
```

Lihat dokumentasi lengkap konfigurasi di [`docs/config/config.md`](docs/config/config.md).

---

## API Key

Semua endpoint (kecuali health check) memerlukan API key.

### Generate API Key

Via command in-game atau console:
```
/napi key generate MyDashboard read:server,read:players
```

Output:
```
[NaturalAPI] API Key generated!
Name  : MyDashboard
ID    : a1b2c3d4-...
Key   : napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
Scopes: read:server, read:players
⚠ Simpan key ini! Tidak bisa ditampilkan ulang.
```

### Penggunaan

```http
GET /api/v1/server HTTP/1.1
Host: yourserver.com:7890
Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

---

## Endpoints

> **Base URL:** `http://<server>:<port>/api/v1`

### 🖥️ Server
```
GET /server                    → Overview lengkap server
GET /server/status             → Status ringkas (tps, players, online)
GET /server/tps                → TPS 1m/5m/15m
GET /server/mspt               → Rata-rata MSPT
GET /server/ram                → Penggunaan RAM
GET /server/uptime             → Uptime dalam detik
GET /server/players/count      → Jumlah player (real + vanish-aware)
GET /server/plugins            → Daftar plugin yang ter-load
GET /server/whitelist          → Whitelist players
GET /server/banlist            → Daftar ban
```

### 👤 Player
```
GET /players                          → Daftar semua player online
GET /players?includeVanished=true     → Termasuk player vanish
GET /players/{uuid}                   → Data lengkap by UUID
GET /players/name/{username}          → Data lengkap by username
GET /players/{uuid}/location          → Koordinat & world
GET /players/{uuid}/health            → HP, food, saturasi
GET /players/{uuid}/inventory         → Full inventory (36 slot)
GET /players/{uuid}/inventory/hotbar  → Hotbar saja (slot 0-8)
GET /players/{uuid}/inventory/armor   → Armor (helm, dada, kaki, sepatu)
GET /players/{uuid}/skin              → URL skin + signature
GET /players/{uuid}/ping              → Ping dalam ms
GET /players/{uuid}/vault             → Data Vault (group, prefix, balance)
GET /players/{uuid}/luckperms         → Data LuckPerms
GET /players/{uuid}/stats             → Playtime, first join, last seen
GET /players/offline/{uuid}           → Data snapshot player offline
```

### 🌍 World
```
GET /worlds                    → Semua world yang ter-load
GET /worlds/{name}             → Info world
GET /worlds/{name}/time        → Waktu in-game
GET /worlds/{name}/weather     → Cuaca
GET /worlds/{name}/players     → Player di world ini
GET /worlds/{name}/entities    → Jumlah entity per tipe
GET /worlds/{name}/border      → World border
GET /worlds/{name}/gamerules   → Semua gamerule
```

### 💰 Vault
```
GET /vault/player/{uuid}                  → Data Vault lengkap
GET /vault/player/{uuid}/group            → Group utama
GET /vault/player/{uuid}/balance          → Saldo ekonomi
GET /vault/player/{uuid}/permission/{node} → Cek satu permission
GET /vault/groups                         → Semua group terdaftar
```

### 🎮 LuckPerms
```
GET /luckperms/player/{uuid}           → Data LP lengkap
GET /luckperms/player/{uuid}/groups    → Semua group
GET /luckperms/player/{uuid}/nodes     → Semua permission node
GET /luckperms/groups                  → Daftar semua group
GET /luckperms/groups/{group}/members  → Member dari group
```

### 📝 PAPI
```
POST /papi/evaluate            → Evaluasi placeholder untuk player
GET  /papi/plugins             → Daftar ekspansi PAPI terdaftar
```

### 🏫 NaturalSchool
```
GET  /naturalschool/player/{uuid}      → Data akademik siswa (NaturalSchool)
POST /naturalschool/refresh            → Event handler untuk sync/update data
```

### 🔧 Admin
```
GET    /admin/keys             → Daftar API keys
POST   /admin/keys             → Buat API key baru
DELETE /admin/keys/{id}        → Revoke API key
GET    /admin/health           → Status plugin (no auth required)
POST   /admin/reload           → Reload plugin via API
GET    /admin/openapi.json     → Spec OpenAPI JSON
```

### 📖 Swagger UI
```
GET /swagger                   → Swagger UI (browser)
GET /swagger/openapi.json      → OpenAPI 3.1 JSON spec
```

---

## Contoh Respons

### `GET /api/v1/server/status`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "online": true,
    "version": "1.21.1",
    "platform": "Paper",
    "tps": {
      "1m": 19.98,
      "5m": 19.95,
      "15m": 19.91
    },
    "players": {
      "online": 12,
      "visible": 10,
      "vanished": 2,
      "max": 100
    },
    "mspt": 3.21,
    "uptime": 86400
  }
}
```

### `GET /api/v1/players/{uuid}/inventory`

```json
{
  "success": true,
  "data": {
    "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5",
    "username": "Notch",
    "inventory": [
      {
        "slot": 0,
        "material": "DIAMOND_SWORD",
        "amount": 1,
        "displayName": "§6Excalibur",
        "lore": ["§7Legendary sword"],
        "damage": 0,
        "enchantments": { "SHARPNESS": 5, "UNBREAKING": 3 }
      }
    ],
    "armor": {
      "helmet": { "slot": 39, "material": "NETHERITE_HELMET", "amount": 1 },
      "chestplate": { "slot": 38, "material": "NETHERITE_CHESTPLATE", "amount": 1 },
      "leggings": { "slot": 37, "material": "NETHERITE_LEGGINGS", "amount": 1 },
      "boots": { "slot": 36, "material": "NETHERITE_BOOTS", "amount": 1 }
    },
    "offhand": { "slot": 40, "material": "SHIELD", "amount": 1 }
  }
}
```

---

## Commands

| Command | Keterangan |
|---|---|
| `/napi` | Info plugin |
| `/napi help` | Bantuan |
| `/napi reload` | Reload penuh plugin |
| `/napi status` | Status HTTP + database |
| `/napi key generate <nama> [scopes]` | Buat API key |
| `/napi key list` | Daftar API keys |
| `/napi key revoke <id>` | Hapus API key |
| `/napi snapshot <player>` | Snapshot manual player |
| `/napi debug` | Status semua integrasi |

---

## Permissions

| Permission | Default | Keterangan |
|---|---|---|
| `naturalapi.use` | Semua player | Lihat info dasar plugin |
| `naturalapi.admin` | OP | Semua command admin |

---

## Integrasi Plugin

| Plugin | Status | Fitur |
|---|---|---|
| Vault | Soft-depend | Group, prefix, suffix, balance, permissions |
| LuckPerms | Soft-depend | Groups, nodes, meta, weight, contexts |
| PlaceholderAPI | Soft-depend | Evaluasi placeholder via API |
| SuperVanish | Soft-depend | Deteksi status vanish |
| PremiumVanish | Soft-depend | Deteksi status vanish |
| CMI | Soft-depend | Deteksi status vanish |
| Essentials | Soft-depend | Deteksi status vanish |
| SkinsRestorer | Soft-depend | Ambil skin kustom player |
| NaturalSchool | Soft-depend | Data akademik & event sync |
| NaturalCore | Soft-depend | AFK & status staf |

Semua integrasi bersifat opsional — plugin tetap berjalan normal tanpa plugin di atas.

---

## Dokumentasi Lengkap

| Dokumen | Link |
|---|---|
| PRD (Product Requirements) | [`PRD.md`](PRD.md) |
| API Endpoints — Server | [`docs/api/endpoints-server.md`](docs/api/endpoints-server.md) |
| API Endpoints — Player | [`docs/api/endpoints-player.md`](docs/api/endpoints-player.md) |
| API Endpoints — World | [`docs/api/endpoints-world.md`](docs/api/endpoints-world.md) |
| API Endpoints — Vault | [`docs/api/endpoints-vault.md`](docs/api/endpoints-vault.md) |
| API Endpoints — LuckPerms | [`docs/api/endpoints-luckperms.md`](docs/api/endpoints-luckperms.md) |
| API Endpoints — PAPI | [`docs/api/endpoints-papi.md`](docs/api/endpoints-papi.md) |
| API Endpoints — NaturalSchool | [`docs/api/endpoints-naturalschool.md`](docs/api/endpoints-naturalschool.md) |
| API Endpoints — Admin | [`docs/api/endpoints-admin.md`](docs/api/endpoints-admin.md) |
| OpenAPI Spec | [`docs/api/openapi.yaml`](docs/api/openapi.yaml) |
| Konfigurasi | [`docs/config/config.md`](docs/config/config.md) |
| Database | [`docs/config/database.md`](docs/config/database.md) |
| Commands | [`docs/commands/commands.md`](docs/commands/commands.md) |
| Permissions | [`docs/permissions/permissions.md`](docs/permissions/permissions.md) |
| Changelog | [`CHANGELOG.md`](CHANGELOG.md) |

---

## License

MIT License — lihat file [LICENSE](LICENSE)

---

<div align="center">
Made with ❤️ by NaturalSMP Team
</div>
