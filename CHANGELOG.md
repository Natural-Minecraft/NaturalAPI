# Changelog

Semua perubahan penting pada NaturalAPI akan didokumentasikan di sini.

Format mengikuti [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), dan proyek ini mengikuti [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [Unreleased]

### Planned
- In-game GUI panel (`/napi gui`)
- Player achievement/statistic tracking endpoint
- Economy transaction history endpoint (Vault)
- Multi-server aggregator mode

---

## [1.0.5] — 2026-06-11

### Security
- Menutup celah keamanan bypass otentikasi pada endpoint `/players/{player}/health` dengan memperbaiki validasi `basePath` pada middleware.
- Menghentikan kebocoran status online/offline staf ter-vanish di WebSocket join/leave channel.

### Fixed
- Memperbaiki kebocoran memori (memory leak) dan duplikasi event listener serta task scheduler saat plugin direload (`onDisable`).
- Memperbaiki kebocoran memori (OOM) dengan membatasi jumlah cache alamat GeoIP (limit 2000) dan token bucket rate limiter (limit 5000).
- Memperbaiki database write lock congestion (`SQLITE_BUSY`) pada SQLite dengan mengimplementasikan batch writing menggunakan single transaction.
- Memperbaiki kegagalan broadcast WebSocket agar error dari satu koneksi klien tidak mengganggu pengiriman data klien lainnya.
- Memperbaiki payload data barang/item detail (`lore`, `displayName`, `enchantments`, `customModelData`) agar selalu mengembalikan nilai default untuk konsistensi skema JSON.
- Memperbaiki endpoint `/players/{id}/inventory` agar mengembalikan wrapper data lengkap yang berisi objek `uuid`, `username`, `inventory`, `armor`, dan `offhand`.

### Added / Changed
- Mengubah endpoint `/skin` dan `/network` menjadi sepenuhnya asinkron agar web request ke Mojang/GeoIP tidak membekukan tick-loop utama server Minecraft (menghilangkan TPS drop).
- Mengimplementasikan database skin cache (`napi_skin_cache`) untuk menghindari rate-limit Mojang.
- Mengubah background stats loop ke sinkronisasi Scheduler Bukkit thread utama demi menjamin keamanan data multithread.
- Mengimplementasikan penulisan metadata NBT ItemStack ke field `nbtJson` menggunakan serialisasi internal Bukkit ItemStack secara aman.

---

## [1.0.0] — 2025-06-05

### Added

#### Core
- Plugin bootstrap `NaturalAPI.java` dengan lifecycle enable/disable/reload penuh
- `ConfigManager` — load, validate, dan hot-reload `config.yml`
- `DatabaseManager` — abstraksi multi-provider (SQLite, MySQL, MariaDB) via HikariCP
- `MigrationRunner` — schema migration otomatis via Flyway
- True reload system: HTTP stop → DB close → listener unreg → re-init semua komponen

#### HTTP Server
- Embedded HTTP server berbasis Javalin 6 + Jetty 11
- Base path `/api/v1` yang dapat dikonfigurasi
- `ApiRouter` — routing terpusat, semua endpoint terdaftar di sini
- Virtual thread support (Java 21 Project Loom)

#### Endpoints — Server
- `GET /server` — full server overview
- `GET /server/status` — status ringkas
- `GET /server/tps` — TPS 1m/5m/15m via Paper API
- `GET /server/mspt` — rata-rata MSPT
- `GET /server/ram` — heap used/max/free
- `GET /server/uptime` — uptime sejak startup
- `GET /server/version` — versi MC + platform
- `GET /server/players/count` — jumlah player dengan vanish-awareness
- `GET /server/plugins` — semua plugin ter-load + versi
- `GET /server/whitelist` — daftar whitelist
- `GET /server/banlist` — daftar ban dengan expiry

#### Endpoints — Player
- `GET /players` — daftar player online (vanish-aware, query param `includeVanished`)
- `GET /players/{uuid}` — data lengkap player by UUID
- `GET /players/name/{username}` — data lengkap by username
- `GET /players/{uuid}/location` — world, x, y, z, yaw, pitch
- `GET /players/{uuid}/health` — HP, maxHP, food, saturation
- `GET /players/{uuid}/experience` — level, progress, total XP
- `GET /players/{uuid}/gamemode` — mode permainan aktif
- `GET /players/{uuid}/inventory` — full 36-slot inventory (JSON serialized)
- `GET /players/{uuid}/inventory/hotbar` — slot 0–8 saja
- `GET /players/{uuid}/inventory/armor` — helm, dada, kaki, sepatu
- `GET /players/{uuid}/inventory/offhand` — offhand item
- `GET /players/{uuid}/effects` — semua potion effect aktif
- `GET /players/{uuid}/skin` — texture URL + Mojang signature
- `GET /players/{uuid}/ping` — ping dalam ms
- `GET /players/{uuid}/network` — ping, locale, brand, protocol version
- `GET /players/{uuid}/vault` — data Vault
- `GET /players/{uuid}/luckperms` — data LuckPerms
- `GET /players/{uuid}/permissions` — semua permissions
- `GET /players/{uuid}/permission/{node}` — cek single permission
- `GET /players/{uuid}/stats` — playtime, first join, last seen
- `GET /players/{uuid}/snapshot` — snapshot terakhir dari DB
- `POST /players/{uuid}/snapshot` — trigger snapshot manual
- Mirror semua endpoint UUID ke `/players/name/{username}/...`
- `GET /players/offline/{uuid}` — data offline dari snapshot DB
- `GET /players/offline/name/{username}` — data offline by username

#### Endpoints — World
- `GET /worlds` — semua world ter-load
- `GET /worlds/{name}` — info world lengkap
- `GET /worlds/{name}/time` — in-game time + day
- `GET /worlds/{name}/weather` — clear/rain/thunder
- `GET /worlds/{name}/players` — player yang ada di world ini
- `GET /worlds/{name}/entities` — entity count per tipe
- `GET /worlds/{name}/chunks` — loaded chunk count
- `GET /worlds/{name}/border` — world border center, size, damage
- `GET /worlds/{name}/gamerules` — semua gamerule dan nilainya

#### Endpoints — Vault
- `GET /vault/player/{uuid}` — full Vault data
- `GET /vault/player/{uuid}/group` — primary group
- `GET /vault/player/{uuid}/groups` — semua group
- `GET /vault/player/{uuid}/prefix` — chat prefix
- `GET /vault/player/{uuid}/suffix` — chat suffix
- `GET /vault/player/{uuid}/balance` — saldo ekonomi
- `GET /vault/player/{uuid}/permissions` — semua permission
- `GET /vault/player/{uuid}/permission/{node}` — cek permission
- `GET /vault/groups` — daftar semua group
- `GET /vault/groups/{group}` — detail group
- `GET /vault/economy/status` — info economy plugin

#### Endpoints — LuckPerms
- `GET /luckperms/player/{uuid}` — full LP data
- `GET /luckperms/player/{uuid}/groups` — direct + inherited groups
- `GET /luckperms/player/{uuid}/primary-group` — primary group
- `GET /luckperms/player/{uuid}/nodes` — semua permission nodes
- `GET /luckperms/player/{uuid}/meta` — meta key-value
- `GET /luckperms/player/{uuid}/weight` — group weight
- `GET /luckperms/groups` — semua LP group
- `GET /luckperms/groups/{group}` — detail LP group
- `GET /luckperms/groups/{group}/members` — member dari group
- `GET /luckperms/groups/{group}/permissions` — permission nodes group

#### Endpoints — PlaceholderAPI
- `POST /papi/evaluate` — evaluasi satu atau banyak placeholder untuk satu player
- `GET /papi/plugins` — daftar ekspansi PAPI yang terdaftar

#### Endpoints — Admin
- `GET /admin/keys` — daftar API key (tanpa secret)
- `POST /admin/keys` — generate API key baru
- `DELETE /admin/keys/{id}` — revoke API key
- `GET /admin/health` — health check (no auth)
- `GET /admin/config` — konfigurasi aktif (sensitive fields masked)
- `POST /admin/reload` — trigger reload via API
- `GET /admin/rate-limits` — counter rate limit per IP
- `DELETE /admin/rate-limits/{ip}` — reset rate limit untuk IP tertentu
- `GET /admin/snapshot/history` — riwayat snapshot
- `DELETE /admin/snapshot/purge` — purge snapshot lama
- `GET /admin/openapi.json` — OpenAPI JSON spec
- `GET /admin/openapi.yaml` — OpenAPI YAML spec

#### Swagger / OpenAPI
- Swagger UI 5.x terintegrasi di `/swagger`
- OpenAPI 3.1.0 spec auto-generated dari annotations
- Semua DTO teranotasi `@Schema` untuk dokumentasi model
- Security scheme `BearerAuth` terdokumentasi

#### Security
- Bearer token authentication (SHA-256 hashed + salt di DB)
- API key scopes: `read:server`, `read:players`, `read:worlds`, `read:vault`, `read:luckperms`, `read:papi`, `admin`, `*`
- IP allowlist dengan CIDR notation support
- Token bucket rate limiter per IP
- CORS middleware dengan konfigurasi allowed origins/methods/headers
- Response headers: `X-RateLimit-Limit`, `X-RateLimit-Remaining`, `X-RateLimit-Reset`

#### Database
- SQLite support (default, zero-config) via `org.xerial:sqlite-jdbc`
- MySQL 8+ support via `com.mysql:mysql-connector-j`
- MariaDB 10.6+ support via `org.mariadb.jdbc:mariadb-java-client`
- HikariCP connection pool untuk semua provider
- Flyway schema migration otomatis
- Tabel: `napi_api_keys`, `napi_player_snapshots`, `napi_skin_cache`, `napi_rate_limits`

#### Integrations
- `IntegrationManager` — auto-detect semua plugin saat enable
- **Vault:** group, prefix, suffix, balance, permission check
- **LuckPerms:** primary group, all groups, nodes, meta, weight, context
- **PlaceholderAPI:** evaluasi placeholder
- **Vanish adapters:**
  - SuperVanish (`de.myzelyam.api.vanish.VanishAPI`)
  - PremiumVanish (kompatibel SuperVanish API)
  - CMI (`com.Zrips.CMI.CMI`)
  - Essentials (`com.earth2me.essentials.Essentials`)
  - Fallback: metadata tag `vanished`
- **SkinsRestorer:** ambil skin kustom jika ada, fallback ke Mojang Session Server

#### Commands
- `/napi` — info plugin + versi
- `/napi help` — bantuan command
- `/napi reload` — true full reload
- `/napi status` — status HTTP server + database
- `/napi key generate <name> [scopes]` — generate API key
- `/napi key list` — daftar semua API key
- `/napi key revoke <id>` — revoke API key
- `/napi key info <id>` — detail API key
- `/napi snapshot <player>` — force snapshot player
- `/napi debug` — status semua integrasi (Vault, LP, PAPI, vanish adapters)

#### Permissions
- `naturalapi.use` — akses dasar (default: true)
- `naturalapi.admin` — semua admin command (default: op)
- `naturalapi.bypass.ratelimit` — bypass rate limiter (default: op)

#### Services
- `ServerService` — collect TPS, MSPT, RAM, uptime (cache setiap 1 detik)
- `PlayerService` — resolve player data sync/async
- `WorldService` — world data collection
- `VanishService` — vanish detection dengan priority chain
- `SnapshotService` — auto-snapshot dengan interval yang dapat dikonfigurasi

#### Serializers / Utilities
- `ItemSerializer` — ItemStack → JSON (material, amount, displayName, lore, enchantments, damage, NBT)
- `LocationSerializer` — Location → JSON (world, x, y, z, yaw, pitch)
- `SkinResolver` — Mojang Session Server + cache DB + SkinsRestorer support
- `TpsUtil` — Paper TPS + MSPT helper
- `ResponseBuilder` — standar response wrapper `{success, timestamp, data}`

#### Documentation
- `PRD.md` — Product Requirements Document lengkap
- `README.md` — Panduan instalasi & penggunaan
- `CHANGELOG.md` — Dokumen ini
- `docs/api/` — Dokumentasi endpoint per grup
- `docs/api/openapi.yaml` — OpenAPI 3.1 spec lengkap
- `docs/config/config.md` — Panduan konfigurasi
- `docs/config/database.md` — Panduan setup database
- `docs/commands/commands.md` — Referensi command
- `docs/permissions/permissions.md` — Referensi permission

---

## [0.1.0-SNAPSHOT] — 2025-05-01

### Added
- Inisialisasi proyek Maven
- Setup skeleton plugin.yml
- Proof of concept HTTP server

---

[Unreleased]: https://github.com/naturalsmp/NaturalAPI/compare/v1.0.5...HEAD
[1.0.5]: https://github.com/naturalsmp/NaturalAPI/compare/v1.0.0...v1.0.5
[1.0.0]: https://github.com/naturalsmp/NaturalAPI/releases/tag/v1.0.0
[0.1.0-SNAPSHOT]: https://github.com/naturalsmp/NaturalAPI/releases/tag/v0.1.0-SNAPSHOT
