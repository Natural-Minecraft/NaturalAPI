# NaturalAPI Full Endpoint Test Report
Generated on: 2026-06-12 13:46:14
Target API base URL: `https://napi.aikeigroup.net/api/v1`

This document contains test results for **every registered endpoint** of the NaturalAPI plugin.

## Table of Contents
- [Server](#server)
- [Online Player Details](#online-player-details)
- [Offline Player Details](#offline-player-details)
- [Worlds](#worlds)
- [Integrations](#integrations)
- [Admin](#admin)

## Server

| Endpoint Name | Method | Path | Status | Details |
|---|---|---|---|---|
| Server General Status | `GET` | `/server` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#server-general-status) |
| Server Detail Status | `GET` | `/server/status` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#server-detail-status) |
| Server TPS | `GET` | `/server/tps` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#server-tps) |
| Server MSPT | `GET` | `/server/mspt` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#server-mspt) |
| Server RAM | `GET` | `/server/ram` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#server-ram) |
| Server Uptime | `GET` | `/server/uptime` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#server-uptime) |
| Server Version | `GET` | `/server/version` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#server-version) |
| Server Players Count | `GET` | `/server/players/count` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#server-players-count) |
| Server Plugins | `GET` | `/server/plugins` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#server-plugins) |
| Server Whitelist | `GET` | `/server/whitelist` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#server-whitelist) |
| Server Banlist | `GET` | `/server/banlist` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#server-banlist) |
| Server Playtime Leaderboard | `GET` | `/server/leaderboard?type=playtime&limit=5` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#server-playtime-leaderboard) |

### Server General Status
- **Method:** `GET`
- **Path:** `/server`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "motd": "A Minecraft Server",
    "worlds": [
      "world",
      "world_nether",
      "world_the_end",
      "flat",
      "schoolmap"
    ],
    "players": {
      "visible": 0,
      "vanished": 0,
      "max": 70,
      "online": 0
    },
    "javaVersion": "25.0.3",
    "osName": "Linux",
    "version": "1.21.11",
    "platform": "Paper",
    "uptime": 27,
    "system": {
      "availableProcessors": 6,
      "systemLoadAverage": 1.32,
      "processCpuLoad": 13.33,
      "systemCpuLoad": 13.21,
      "storage": {
        "totalBytes": 126716452864,
        "usableBytes": 59139444736,
        "freeBytes": 64372056064
      },
      "network": {
        "rxBytesPerSec": 2719,
        "txBytesTotal": 244376,
        "txBytesPerSec": 1308,
        "rxBytesTotal": 1282770
      }
    },
    "tps": {
      "now": 19.999507155579426,
      "5m": 20.0,
      "15m": 20.0,
      "1m": 20.0
    },
    "online": true,
    "mspt": 0.4626442772277227,
    "ram": {
      "maxMB": 8192,
      "freeMB": 6877,
      "usedMB": 1314
    }
  },
  "success": true,
  "timestamp": 1781246774870
}
```
</details>

[Back to Server Summary](#server)

---

### Server Detail Status
- **Method:** `GET`
- **Path:** `/server/status`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "motd": "A Minecraft Server",
    "worlds": [
      "world",
      "world_nether",
      "world_the_end",
      "flat",
      "schoolmap"
    ],
    "players": {
      "visible": 0,
      "vanished": 0,
      "max": 70,
      "online": 0
    },
    "javaVersion": "25.0.3",
    "osName": "Linux",
    "version": "1.21.11",
    "platform": "Paper",
    "uptime": 27,
    "system": {
      "availableProcessors": 6,
      "systemLoadAverage": 1.32,
      "processCpuLoad": 45.0,
      "systemCpuLoad": 46.28,
      "storage": {
        "totalBytes": 126716452864,
        "usableBytes": 59139444736,
        "freeBytes": 64372056064
      },
      "network": {
        "rxBytesPerSec": 12967,
        "txBytesTotal": 246140,
        "txBytesPerSec": 11760,
        "rxBytesTotal": 1284715
      }
    },
    "tps": {
      "now": 20.0,
      "5m": 20.0,
      "15m": 20.0,
      "1m": 20.0
    },
    "online": true,
    "mspt": 1.1122424653465346,
    "ram": {
      "maxMB": 8192,
      "freeMB": 6839,
      "usedMB": 1352
    }
  },
  "success": true,
  "timestamp": 1781246775020
}
```
</details>

[Back to Server Summary](#server)

---

### Server TPS
- **Method:** `GET`
- **Path:** `/server/tps`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "now": 19.99999495353663,
    "5m": 20.0,
    "15m": 20.0,
    "1m": 20.0
  },
  "success": true,
  "timestamp": 1781246775818
}
```
</details>

[Back to Server Summary](#server)

---

### Server MSPT
- **Method:** `GET`
- **Path:** `/server/mspt`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": 1.1336733465346536,
  "success": true,
  "timestamp": 1781246775918
}
```
</details>

[Back to Server Summary](#server)

---

### Server RAM
- **Method:** `GET`
- **Path:** `/server/ram`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "max": 8589934592,
    "used": 1418686624,
    "free": 7171247968
  },
  "success": true,
  "timestamp": 1781246776018
}
```
</details>

[Back to Server Summary](#server)

---

### Server Uptime
- **Method:** `GET`
- **Path:** `/server/uptime`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": 30,
  "success": true,
  "timestamp": 1781246778368
}
```
</details>

[Back to Server Summary](#server)

---

### Server Version
- **Method:** `GET`
- **Path:** `/server/version`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "version": "1.21.11",
    "platform": "Paper"
  },
  "success": true,
  "timestamp": 1781246778468
}
```
</details>

[Back to Server Summary](#server)

---

### Server Players Count
- **Method:** `GET`
- **Path:** `/server/players/count`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "visible": 0,
    "vanished": 0,
    "max": 70,
    "online": 0
  },
  "success": true,
  "timestamp": 1781246778568
}
```
</details>

[Back to Server Summary](#server)

---

### Server Plugins
- **Method:** `GET`
- **Path:** `/server/plugins`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": [
    {
      "name": "LuckPerms",
      "version": "5.5.53"
    },
    {
      "name": "Vault",
      "version": "1.7.3-b131"
    },
    {
      "name": "FastAsyncWorldEdit",
      "version": "2.15.2-SNAPSHOT-1335+6730f84"
    },
    {
      "name": "WorldGuard",
      "version": "7.0.16+2355-f7fded2"
    },
    {
      "name": "ProtocolLib",
      "version": "5.5.0-SNAPSHOT-5a9afed"
    },
    {
      "name": "PlaceholderAPI",
      "version": "2.12.2"
    },
    {
      "name": "nightcore",
      "version": "2.15.2"
    },
    {
      "name": "Skript",
      "version": "2.12.2"
    },
    {
      "name": "floodgate",
      "version": "2.2.5-SNAPSHOT (b132-5a72b6a)"
    },
    {
      "name": "ItemsAdder",
      "version": "4.0.17"
    },
    {
      "name": "NaturalSchool",
      "version": "1.6.8"
    },
    {
      "name": "NaturalCore",
      "version": "2.2.3"
    },
    {
      "name": "SkBee",
      "version": "3.24.0"
    },
    {
      "name": "RealDualWield",
      "version": "1.2.0"
    },
    {
      "name": "Multiverse-Core",
      "version": "5.6.2"
    },
    {
      "name": "NaturalAuthPaper",
      "version": "1.0-SNAPSHOT"
    },
    {
      "name": "Chunky",
      "version": "1.4.40"
    },
    {
      "name": "Plan",
      "version": "5.7 build 3306"
    },
    {
      "name": "LoneLibs",
      "version": "1.0.65"
    },
    {
      "name": "NaturalAPI",
      "version": "1.0.6"
    },
    {
      "name": "SkQuery",
      "version": "4.3.2"
    },
    {
      "name": "NaturalUpdater",
      "version": "1.0-SNAPSHOT"
    },
    {
      "name": "TAB",
      "version": "5.4.0"
    },
    {
      "name": "ExcellentEconomy",
      "version": "2.8.0"
    }
  ],
  "success": true,
  "timestamp": 1781246778618
}
```
</details>

[Back to Server Summary](#server)

---

### Server Whitelist
- **Method:** `GET`
- **Path:** `/server/whitelist`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": [],
  "success": true,
  "timestamp": 1781246779119
}
```
</details>

[Back to Server Summary](#server)

---

### Server Banlist
- **Method:** `GET`
- **Path:** `/server/banlist`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": [],
  "success": true,
  "timestamp": 1781246779169
}
```
</details>

[Back to Server Summary](#server)

---

### Server Playtime Leaderboard
- **Method:** `GET`
- **Path:** `/server/leaderboard?type=playtime&limit=5`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "metadata": {
    "last_sync_timestamp": 1781245756722,
    "provider": "NaturalAPI Snapshots Database",
    "sync_interval_minutes": 10,
    "next_sync_timestamp": 1781246356722
  },
  "data": [
    {
      "uuid": "f32a30cb-1902-35b0-b9e7-a04ed64d13dd",
      "username": "AdityaOkeGas",
      "value": 275169250
    },
    {
      "uuid": "75ed88b3-eb5c-3e45-a763-c3413042883d",
      "username": "AdityaOkeGas3",
      "value": 142127750
    },
    {
      "uuid": "e9467b23-b4d2-4d3f-8522-3b341bb7bb53",
      "username": "Joselyz",
      "value": 74986850
    },
    {
      "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
      "username": "AdityaOkeGas4",
      "value": 69937100
    },
    {
      "uuid": "de11f3ca-003f-3713-b771-62bc481dbfca",
      "username": "AdityaOkeGas2",
      "value": 61362050
    }
  ],
  "success": true,
  "timestamp": 1781246779294
}
```
</details>

[Back to Server Summary](#server)

---

## Online Player Details

| Endpoint Name | Method | Path | Status | Details |
|---|---|---|---|---|
| Online Players Summary | `GET` | `/players` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#online-players-summary) |
| All Players (Paged) | `GET` | `/players/all` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#all-players-paged) |
| Player Details (UUID) | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-details-uuid) |
| Player Details (Name) | `GET` | `/players/name/AdityaOkeGas2` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-details-name) |
| Player Location | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/location` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-location) |
| Player Health | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/health` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-health) |
| Player Experience | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/experience` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-experience) |
| Player Gamemode | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/gamemode` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-gamemode) |
| Player Inventory | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-inventory) |
| Player Inventory Hotbar | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory/hotbar` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-inventory-hotbar) |
| Player Inventory Armor | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory/armor` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-inventory-armor) |
| Player Inventory Offhand | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory/offhand` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-inventory-offhand) |
| Player Active Effects | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/effects` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-active-effects) |
| Player Skin | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/skin` | ![Status 404](https://img.shields.io/badge/Status-404-red) | [View Details](#player-skin) |
| Player Ping | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/ping` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-ping) |
| Player Network Data | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/network` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-network-data) |
| Player Stats | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/stats` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-stats) |
| Player Permissions | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/permissions` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-permissions) |
| Player Permission Check (essentials.fly) | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/permission/essentials.fly` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-permission-check-essentials.fly) |
| Player Snapshot details | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/snapshot` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#player-snapshot-details) |
| Trigger Player Snapshot | `POST` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/snapshot` | ![Status 404](https://img.shields.io/badge/Status-404-red) | [View Details](#trigger-player-snapshot) |

### Online Players Summary
- **Method:** `GET`
- **Path:** `/players`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": [],
  "success": true,
  "timestamp": 1781246779368
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### All Players (Paged)
- **Method:** `GET`
- **Path:** `/players/all`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "pagination": {
      "total": 19,
      "totalPages": 1,
      "pageSize": 50,
      "page": 1
    },
    "players": [
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1781245988658,
        "displayName": "AdityaOkeGas2",
        "afk": false,
        "online": false,
        "uuid": "de11f3ca-003f-3713-b771-62bc481dbfca",
        "username": "AdityaOkeGas2"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1781245988642,
        "displayName": "AdityaOkeGas",
        "afk": false,
        "online": false,
        "uuid": "f32a30cb-1902-35b0-b9e7-a04ed64d13dd",
        "username": "AdityaOkeGas"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1781221256460,
        "displayName": ".AnakTentara7379",
        "afk": false,
        "online": false,
        "uuid": "00000000-0000-0000-0009-01fccf2fb034",
        "username": ".AnakTentara7379"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1781199184515,
        "displayName": "AdityaOkeGas3",
        "afk": false,
        "online": false,
        "uuid": "75ed88b3-eb5c-3e45-a763-c3413042883d",
        "username": "AdityaOkeGas3"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1781199183964,
        "displayName": "AdityaOkeGas4",
        "afk": false,
        "online": false,
        "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
        "username": "AdityaOkeGas4"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1781194551166,
        "displayName": "Daichi_keii",
        "afk": false,
        "online": false,
        "uuid": "9c045595-a5da-4284-9d9e-c3603f86432b",
        "username": "Daichi_keii"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1781114008924,
        "displayName": "Joselyz",
        "afk": false,
        "online": false,
        "uuid": "e9467b23-b4d2-4d3f-8522-3b341bb7bb53",
        "username": "Joselyz"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1780995278136,
        "displayName": "AnakTentara",
        "afk": false,
        "online": false,
        "uuid": "a91a4636-cf4e-3ea0-ae3d-cf0ec0ded240",
        "username": "AnakTentara"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1780886497580,
        "displayName": ".reknah8402",
        "afk": false,
        "online": false,
        "uuid": "00000000-0000-0000-0009-01f90c772e5e",
        "username": ".reknah8402"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1780837329887,
        "displayName": "AdityaOkeGas5",
        "afk": false,
        "online": false,
        "uuid": "f1d3d44a-9488-3fee-84c9-522c62336d23",
        "username": "AdityaOkeGas5"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1780746518153,
        "displayName": ".ItzJope",
        "afk": false,
        "online": false,
        "uuid": "00000000-0000-0000-0009-01f145b763cd",
        "username": ".ItzJope"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1780652966440,
        "displayName": "Inozzzzz",
        "afk": false,
        "online": false,
        "uuid": "83e040b7-9fe1-3cb8-8b6d-c0b4920525d1",
        "username": "Inozzzzz"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1780651760302,
        "displayName": "AiKei_Agent",
        "afk": false,
        "online": false,
        "uuid": "1cdcc3b3-4d33-30e2-82cf-b1ea066f3078",
        "username": "AiKei_Agent"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1780646170756,
        "displayName": "MRPSGAMING",
        "afk": false,
        "online": false,
        "uuid": "27428ad9-59f3-34ae-ba82-d527d164bee4",
        "username": "MRPSGAMING"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1780645700277,
        "displayName": "JopeeeBot",
        "afk": false,
        "online": false,
        "uuid": "cc79539a-a7ce-317c-9081-16e82fb2d353",
        "username": "JopeeeBot"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1780059899564,
        "displayName": "Jenype",
        "afk": false,
        "online": false,
        "uuid": "8dac7d16-e2c2-3d6a-9f84-fbcf943badaa",
        "username": "Jenype"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1779907111613,
        "displayName": "Zeronthh",
        "afk": false,
        "online": false,
        "uuid": "cd6d95d7-2866-48a5-9f45-4e260a830d44",
        "username": "Zeronthh"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1779903729603,
        "displayName": "Joselyz",
        "afk": false,
        "online": false,
        "uuid": "5f8caa1e-4c5c-3cf2-aa9f-10f9db13c494",
        "username": "Joselyz"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1779843232248,
        "displayName": "Liiyym14",
        "afk": false,
        "online": false,
        "uuid": "6b8ee461-6ddb-3ef0-98f1-4f07a70cfd27",
        "username": "Liiyym14"
      }
    ]
  },
  "success": true,
  "timestamp": 1781246779426
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Details (UUID)
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "kills": 0,
    "country": "Singapore",
    "city": "Singapore",
    "displayName": "AdityaOkeGas2",
    "ping": 3,
    "isp": "Oracle Corporation",
    "totalPlaytimeMs": 61362050,
    "locale": "en",
    "inventory": [
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "DIAMOND",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 0,
        "enchantments": {}
      }
    ],
    "uuid": "de11f3ca-003f-3713-b771-62bc481dbfca",
    "isWhitelisted": false,
    "gamemode": "SURVIVAL",
    "saturation": 4.0,
    "firstJoin": 1780588217858,
    "school": null,
    "isOp": false,
    "isBanned": false,
    "maxHealth": 20.0,
    "exp": 0.0,
    "deaths": 0,
    "vault": {
      "prefix": "",
      "suffix": "",
      "group": "default"
    },
    "ipHistory": [
      {
        "country": "Singapore",
        "lastSeen": 1781245667513,
        "city": "Singapore",
        "firstSeen": 1781019787927,
        "isp": "Oracle Corporation",
        "ipAddress": "140.245.116.11",
        "region": "South East",
        "asn": "AS31898 Oracle Corporation"
      }
    ],
    "vanished": false,
    "level": 0,
    "ipAddress": "140.245.116.11",
    "health": 20.0,
    "clientBrand": "vanilla",
    "effects": [
      {
        "durationTicks": -1,
        "durationSeconds": -0.05,
        "icon": false,
        "amplifier": 255,
        "ambient": false,
        "type": "REGENERATION",
        "particles": false
      },
      {
        "durationTicks": -1,
        "durationSeconds": -0.05,
        "icon": false,
        "amplifier": 255,
        "ambient": false,
        "type": "DAMAGE_RESISTANCE",
        "particles": false
      }
    ],
    "lastSeen": 1781245988658,
    "armor": {
      "chestplate": null,
      "helmet": null,
      "boots": null,
      "leggings": null
    },
    "luckperms": {
      "primaryGroup": "default"
    },
    "mobKills": 0,
    "totalExperience": 0,
    "online": false,
    "location": {
      "world": "world",
      "x": -3272.5,
      "y": 101.3447679954834,
      "z": 3167.5,
      "pitch": 0.0,
      "yaw": 0.0
    },
    "foodLevel": 20,
    "region": "South East",
    "asn": "AS31898 Oracle Corporation",
    "username": "AdityaOkeGas2"
  },
  "success": true,
  "timestamp": 1781246779485
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Details (Name)
- **Method:** `GET`
- **Path:** `/players/name/AdityaOkeGas2`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "kills": 0,
    "country": "Singapore",
    "city": "Singapore",
    "displayName": "AdityaOkeGas2",
    "ping": 3,
    "isp": "Oracle Corporation",
    "totalPlaytimeMs": 61362050,
    "locale": "en",
    "inventory": [
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "DIAMOND",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 0,
        "enchantments": {}
      }
    ],
    "uuid": "de11f3ca-003f-3713-b771-62bc481dbfca",
    "isWhitelisted": false,
    "gamemode": "SURVIVAL",
    "saturation": 4.0,
    "firstJoin": 1780588217858,
    "school": null,
    "isOp": false,
    "isBanned": false,
    "maxHealth": 20.0,
    "exp": 0.0,
    "deaths": 0,
    "vault": {
      "prefix": "",
      "suffix": "",
      "group": "default"
    },
    "ipHistory": [
      {
        "country": "Singapore",
        "lastSeen": 1781245667513,
        "city": "Singapore",
        "firstSeen": 1781019787927,
        "isp": "Oracle Corporation",
        "ipAddress": "140.245.116.11",
        "region": "South East",
        "asn": "AS31898 Oracle Corporation"
      }
    ],
    "vanished": false,
    "level": 0,
    "ipAddress": "140.245.116.11",
    "health": 20.0,
    "clientBrand": "vanilla",
    "effects": [
      {
        "durationTicks": -1,
        "durationSeconds": -0.05,
        "icon": false,
        "amplifier": 255,
        "ambient": false,
        "type": "REGENERATION",
        "particles": false
      },
      {
        "durationTicks": -1,
        "durationSeconds": -0.05,
        "icon": false,
        "amplifier": 255,
        "ambient": false,
        "type": "DAMAGE_RESISTANCE",
        "particles": false
      }
    ],
    "lastSeen": 1781245988658,
    "armor": {
      "chestplate": null,
      "helmet": null,
      "boots": null,
      "leggings": null
    },
    "luckperms": {
      "primaryGroup": "default"
    },
    "mobKills": 0,
    "totalExperience": 0,
    "online": false,
    "location": {
      "world": "world",
      "x": -3272.5,
      "y": 101.3447679954834,
      "z": 3167.5,
      "pitch": 0.0,
      "yaw": 0.0
    },
    "foodLevel": 20,
    "region": "South East",
    "asn": "AS31898 Oracle Corporation",
    "username": "AdityaOkeGas2"
  },
  "success": true,
  "timestamp": 1781246779579
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Location
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/location`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "world": "world",
    "x": -3272.5,
    "y": 101.3447679954834,
    "z": 3167.5,
    "pitch": 0.0,
    "yaw": 0.0
  },
  "success": true,
  "timestamp": 1781246779678
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Health
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/health`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "saturation": 4.0,
    "health": 20.0,
    "maxHealth": 20.0,
    "foodLevel": 20
  },
  "success": true,
  "timestamp": 1781246779726
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Experience
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/experience`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "expLevel": 0,
    "expProgress": 0.0,
    "totalExp": 0
  },
  "success": true,
  "timestamp": 1781246780027
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Gamemode
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/gamemode`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": "SURVIVAL",
  "success": true,
  "timestamp": 1781246780126
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Inventory
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "armor": {
      "chestplate": null,
      "helmet": null,
      "boots": null,
      "leggings": null
    },
    "inventory": [
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "DIAMOND",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 0,
        "enchantments": {}
      }
    ],
    "uuid": "de11f3ca-003f-3713-b771-62bc481dbfca",
    "offhand": null,
    "username": "AdityaOkeGas2"
  },
  "success": true,
  "timestamp": 1781246780176
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Inventory Hotbar
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory/hotbar`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": [
    {
      "damage": 0,
      "customModelData": 0,
      "amount": 1,
      "material": "DIAMOND",
      "lore": [],
      "displayName": null,
      "nbtJson": "{}",
      "slot": 0,
      "enchantments": {}
    }
  ],
  "success": true,
  "timestamp": 1781246780276
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Inventory Armor
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory/armor`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "chestplate": null,
    "helmet": null,
    "boots": null,
    "leggings": null
  },
  "success": true,
  "timestamp": 1781246780326
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Inventory Offhand
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory/offhand`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": null,
  "success": true,
  "timestamp": 1781246780426
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Active Effects
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/effects`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": [
    {
      "durationTicks": -1,
      "durationSeconds": -0.05,
      "icon": false,
      "amplifier": 255,
      "ambient": false,
      "type": "REGENERATION",
      "particles": false
    },
    {
      "durationTicks": -1,
      "durationSeconds": -0.05,
      "icon": false,
      "amplifier": 255,
      "ambient": false,
      "type": "DAMAGE_RESISTANCE",
      "particles": false
    }
  ],
  "success": true,
  "timestamp": 1781246780526
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Skin
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/skin`
- **HTTP Status:** `404`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "PLAYER_NOT_FOUND",
    "message": "Player or skin not found.",
    "timestamp": 1781246781457
  }
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Ping
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/ping`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": null,
  "success": true,
  "timestamp": 1781246781529
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Network Data
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/network`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "clientBrand": "vanilla",
    "ipHistory": [
      {
        "country": "Singapore",
        "lastSeen": 1781245667513,
        "city": "Singapore",
        "firstSeen": 1781019787927,
        "isp": "Oracle Corporation",
        "ipAddress": "140.245.116.11",
        "region": "South East",
        "asn": "AS31898 Oracle Corporation"
      }
    ],
    "country": "Singapore",
    "city": "Singapore",
    "ping": null,
    "isp": "Oracle Corporation",
    "ipAddress": "140.245.116.11",
    "locale": "en",
    "region": "South East",
    "asn": "AS31898 Oracle Corporation"
  },
  "success": true,
  "timestamp": 1781246781583
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Stats
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/stats`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "kills": 0,
    "firstJoin": 1780588217858,
    "lastSeen": 1781245988658,
    "mobKills": 0,
    "totalPlaytimeMs": 61362050,
    "deaths": 0
  },
  "success": true,
  "timestamp": 1781246781676
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Permissions
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/permissions`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": [],
  "success": true,
  "timestamp": 1781246781825
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Permission Check (essentials.fly)
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/permission/essentials.fly`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "permission": "essentials.fly",
    "hasPermission": false
  },
  "success": true,
  "timestamp": 1781246781875
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Player Snapshot details
- **Method:** `GET`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/snapshot`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "kills": 0,
    "country": "Singapore",
    "city": "Singapore",
    "displayName": "AdityaOkeGas2",
    "ping": 3,
    "isp": "Oracle Corporation",
    "totalPlaytimeMs": 61362050,
    "locale": "en",
    "inventory": [
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "DIAMOND",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 0,
        "enchantments": {}
      }
    ],
    "uuid": "de11f3ca-003f-3713-b771-62bc481dbfca",
    "isWhitelisted": false,
    "gamemode": "SURVIVAL",
    "saturation": 4.0,
    "firstJoin": 1780588217858,
    "school": null,
    "isOp": false,
    "isBanned": false,
    "maxHealth": 20.0,
    "exp": 0.0,
    "deaths": 0,
    "vault": {
      "prefix": "",
      "suffix": "",
      "group": "default"
    },
    "ipHistory": [
      {
        "country": "Singapore",
        "lastSeen": 1781245667513,
        "city": "Singapore",
        "firstSeen": 1781019787927,
        "isp": "Oracle Corporation",
        "ipAddress": "140.245.116.11",
        "region": "South East",
        "asn": "AS31898 Oracle Corporation"
      }
    ],
    "vanished": false,
    "level": 0,
    "ipAddress": "140.245.116.11",
    "health": 20.0,
    "clientBrand": "vanilla",
    "effects": [
      {
        "durationTicks": -1,
        "durationSeconds": -0.05,
        "icon": false,
        "amplifier": 255,
        "ambient": false,
        "type": "REGENERATION",
        "particles": false
      },
      {
        "durationTicks": -1,
        "durationSeconds": -0.05,
        "icon": false,
        "amplifier": 255,
        "ambient": false,
        "type": "DAMAGE_RESISTANCE",
        "particles": false
      }
    ],
    "lastSeen": 1781245988658,
    "armor": {
      "chestplate": null,
      "helmet": null,
      "boots": null,
      "leggings": null
    },
    "luckperms": {
      "primaryGroup": "default"
    },
    "mobKills": 0,
    "totalExperience": 0,
    "online": false,
    "location": {
      "world": "world",
      "x": -3272.5,
      "y": 101.3447679954834,
      "z": 3167.5,
      "pitch": 0.0,
      "yaw": 0.0
    },
    "foodLevel": 20,
    "region": "South East",
    "asn": "AS31898 Oracle Corporation",
    "username": "AdityaOkeGas2"
  },
  "success": true,
  "timestamp": 1781246781925
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

### Trigger Player Snapshot
- **Method:** `POST`
- **Path:** `/players/de11f3ca-003f-3713-b771-62bc481dbfca/snapshot`
- **HTTP Status:** `404`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "PLAYER_NOT_FOUND",
    "message": "Player not found or offline.",
    "timestamp": 1781246783268
  }
}
```
</details>

[Back to Online Player Details Summary](#online-player-details)

---

## Offline Player Details

| Endpoint Name | Method | Path | Status | Details |
|---|---|---|---|---|
| Offline Player Details (UUID) | `GET` | `/players/offline/9c045595-a5da-4284-9d9e-c3603f86432b` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#offline-player-details-uuid) |
| Offline Player Details (Name) | `GET` | `/players/offline/name/Daichi_keii` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#offline-player-details-name) |
| Offline Player Stats | `GET` | `/players/9c045595-a5da-4284-9d9e-c3603f86432b/stats` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#offline-player-stats) |

### Offline Player Details (UUID)
- **Method:** `GET`
- **Path:** `/players/offline/9c045595-a5da-4284-9d9e-c3603f86432b`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "kills": 0,
    "country": "Indonesia",
    "city": "Ungaran",
    "displayName": "Daichi_keii",
    "ping": 35,
    "isp": "PT Lintas Data Prima",
    "totalPlaytimeMs": 25647950,
    "locale": "en",
    "inventory": [
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "ELYTRA",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 0,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "NETHERITE_SWORD",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 1,
        "enchantments": {
          "SHARPNESS": 5
        }
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_HELMET",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 2,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "CHICKEN",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 3,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_LEGGINGS",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 4,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 62,
        "material": "FIREWORK_ROCKET",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 7,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "ELYTRA",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 8,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_BOOTS",
        "lore": [
          "aduh"
        ],
        "displayName": "\u00a7aaduh bahh",
        "nbtJson": "{}",
        "slot": 9,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_BOOTS",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 36,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_LEGGINGS",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 37,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_CHESTPLATE",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 38,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_HELMET",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 39,
        "enchantments": {}
      }
    ],
    "uuid": "9c045595-a5da-4284-9d9e-c3603f86432b",
    "isWhitelisted": false,
    "gamemode": "SPECTATOR",
    "saturation": 0.0,
    "firstJoin": 1780386089704,
    "school": null,
    "isOp": true,
    "isBanned": false,
    "maxHealth": 20.0,
    "exp": 0.111111,
    "deaths": 2,
    "vault": {
      "prefix": "",
      "suffix": "",
      "group": "default"
    },
    "ipHistory": [
      {
        "country": "Indonesia",
        "lastSeen": 1781193119075,
        "city": "Ungaran",
        "firstSeen": 1781167779708,
        "isp": "PT Lintas Data Prima",
        "ipAddress": "103.28.112.172",
        "region": "Central Java",
        "asn": "AS45305 Lintas Data Prima, PT"
      }
    ],
    "vanished": false,
    "level": 1,
    "ipAddress": "103.28.112.172",
    "health": 12.5,
    "clientBrand": "fabric",
    "effects": [],
    "lastSeen": 1781194551166,
    "armor": {
      "chestplate": {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_CHESTPLATE",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 38,
        "enchantments": {}
      },
      "helmet": {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_HELMET",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 39,
        "enchantments": {}
      },
      "boots": {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_BOOTS",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 36,
        "enchantments": {}
      },
      "leggings": {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_LEGGINGS",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 37,
        "enchantments": {}
      }
    },
    "luckperms": {
      "primaryGroup": "default"
    },
    "mobKills": 0,
    "totalExperience": 8,
    "online": false,
    "location": {
      "world": "world",
      "x": -1655.0305918899621,
      "y": 168.05145766118883,
      "z": -560.4397616822695,
      "pitch": -46.1753,
      "yaw": -77.2872
    },
    "foodLevel": 15,
    "region": "Central Java",
    "asn": "AS45305 Lintas Data Prima, PT",
    "username": "Daichi_keii"
  },
  "success": true,
  "timestamp": 1781246783379
}
```
</details>

[Back to Offline Player Details Summary](#offline-player-details)

---

### Offline Player Details (Name)
- **Method:** `GET`
- **Path:** `/players/offline/name/Daichi_keii`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "kills": 0,
    "country": "Indonesia",
    "city": "Ungaran",
    "displayName": "Daichi_keii",
    "ping": 35,
    "isp": "PT Lintas Data Prima",
    "totalPlaytimeMs": 25647950,
    "locale": "en",
    "inventory": [
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "ELYTRA",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 0,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "NETHERITE_SWORD",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 1,
        "enchantments": {
          "SHARPNESS": 5
        }
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_HELMET",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 2,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "CHICKEN",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 3,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_LEGGINGS",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 4,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 62,
        "material": "FIREWORK_ROCKET",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 7,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "ELYTRA",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 8,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_BOOTS",
        "lore": [
          "aduh"
        ],
        "displayName": "\u00a7aaduh bahh",
        "nbtJson": "{}",
        "slot": 9,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_BOOTS",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 36,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_LEGGINGS",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 37,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_CHESTPLATE",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 38,
        "enchantments": {}
      },
      {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_HELMET",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 39,
        "enchantments": {}
      }
    ],
    "uuid": "9c045595-a5da-4284-9d9e-c3603f86432b",
    "isWhitelisted": false,
    "gamemode": "SPECTATOR",
    "saturation": 0.0,
    "firstJoin": 1780386089704,
    "school": null,
    "isOp": true,
    "isBanned": false,
    "maxHealth": 20.0,
    "exp": 0.111111,
    "deaths": 2,
    "vault": {
      "prefix": "",
      "suffix": "",
      "group": "default"
    },
    "ipHistory": [
      {
        "country": "Indonesia",
        "lastSeen": 1781193119075,
        "city": "Ungaran",
        "firstSeen": 1781167779708,
        "isp": "PT Lintas Data Prima",
        "ipAddress": "103.28.112.172",
        "region": "Central Java",
        "asn": "AS45305 Lintas Data Prima, PT"
      }
    ],
    "vanished": false,
    "level": 1,
    "ipAddress": "103.28.112.172",
    "health": 12.5,
    "clientBrand": "fabric",
    "effects": [],
    "lastSeen": 1781194551166,
    "armor": {
      "chestplate": {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_CHESTPLATE",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 38,
        "enchantments": {}
      },
      "helmet": {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_HELMET",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 39,
        "enchantments": {}
      },
      "boots": {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_BOOTS",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 36,
        "enchantments": {}
      },
      "leggings": {
        "damage": 0,
        "customModelData": 0,
        "amount": 1,
        "material": "COPPER_LEGGINGS",
        "lore": [],
        "displayName": null,
        "nbtJson": "{}",
        "slot": 37,
        "enchantments": {}
      }
    },
    "luckperms": {
      "primaryGroup": "default"
    },
    "mobKills": 0,
    "totalExperience": 8,
    "online": false,
    "location": {
      "world": "world",
      "x": -1655.0305918899621,
      "y": 168.05145766118883,
      "z": -560.4397616822695,
      "pitch": -46.1753,
      "yaw": -77.2872
    },
    "foodLevel": 15,
    "region": "Central Java",
    "asn": "AS45305 Lintas Data Prima, PT",
    "username": "Daichi_keii"
  },
  "success": true,
  "timestamp": 1781246783477
}
```
</details>

[Back to Offline Player Details Summary](#offline-player-details)

---

### Offline Player Stats
- **Method:** `GET`
- **Path:** `/players/9c045595-a5da-4284-9d9e-c3603f86432b/stats`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "kills": 0,
    "firstJoin": 1780386089704,
    "lastSeen": 1781194551166,
    "mobKills": 0,
    "totalPlaytimeMs": 25647950,
    "deaths": 2
  },
  "success": true,
  "timestamp": 1781246783576
}
```
</details>

[Back to Offline Player Details Summary](#offline-player-details)

---

## Worlds

| Endpoint Name | Method | Path | Status | Details |
|---|---|---|---|---|
| Worlds List | `GET` | `/worlds` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#worlds-list) |
| World Detail | `GET` | `/worlds/world` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#world-detail) |
| World Time | `GET` | `/worlds/world/time` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#world-time) |
| World Weather | `GET` | `/worlds/world/weather` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#world-weather) |
| World Players | `GET` | `/worlds/world/players` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#world-players) |
| World Entities | `GET` | `/worlds/world/entities` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#world-entities) |
| World Loaded Chunks | `GET` | `/worlds/world/chunks` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#world-loaded-chunks) |
| World Border | `GET` | `/worlds/world/border` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#world-border) |
| World Gamerules | `GET` | `/worlds/world/gamerules` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#world-gamerules) |

### Worlds List
- **Method:** `GET`
- **Path:** `/worlds`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": [
    {
      "difficulty": "EASY",
      "environment": "NORMAL",
      "seed": 6641214828919989498,
      "entityCount": 0,
      "playerCount": 0,
      "name": "world",
      "weather": "CLEAR",
      "fullTime": 1048114,
      "time": 16114,
      "pvp": true,
      "loadedChunks": 0
    },
    {
      "difficulty": "EASY",
      "environment": "NETHER",
      "seed": 6641214828919989498,
      "entityCount": 0,
      "playerCount": 0,
      "name": "world_nether",
      "weather": "CLEAR",
      "fullTime": 24747884,
      "time": 3884,
      "pvp": true,
      "loadedChunks": 0
    },
    {
      "difficulty": "EASY",
      "environment": "THE_END",
      "seed": 6641214828919989498,
      "entityCount": 0,
      "playerCount": 0,
      "name": "world_the_end",
      "weather": "CLEAR",
      "fullTime": 24747884,
      "time": 3884,
      "pvp": true,
      "loadedChunks": 0
    },
    {
      "difficulty": "EASY",
      "environment": "NORMAL",
      "seed": -2633167103675812141,
      "entityCount": 0,
      "playerCount": 0,
      "name": "flat",
      "weather": "CLEAR",
      "fullTime": 16140757,
      "time": 12757,
      "pvp": true,
      "loadedChunks": 0
    },
    {
      "difficulty": "HARD",
      "environment": "NORMAL",
      "seed": -1064462851042033081,
      "entityCount": 0,
      "playerCount": 0,
      "name": "schoolmap",
      "weather": "CLEAR",
      "fullTime": 600,
      "time": 600,
      "pvp": true,
      "loadedChunks": 0
    }
  ],
  "success": true,
  "timestamp": 1781246783619
}
```
</details>

[Back to Worlds Summary](#worlds)

---

### World Detail
- **Method:** `GET`
- **Path:** `/worlds/world`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "difficulty": "EASY",
    "environment": "NORMAL",
    "seed": 6641214828919989498,
    "entityCount": 0,
    "playerCount": 0,
    "name": "world",
    "weather": "CLEAR",
    "fullTime": 1048115,
    "time": 16115,
    "pvp": true,
    "loadedChunks": 0
  },
  "success": true,
  "timestamp": 1781246783668
}
```
</details>

[Back to Worlds Summary](#worlds)

---

### World Time
- **Method:** `GET`
- **Path:** `/worlds/world/time`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "dayCount": 43,
    "fullTime": 1048116,
    "time": 16116
  },
  "success": true,
  "timestamp": 1781246783718
}
```
</details>

[Back to Worlds Summary](#worlds)

---

### World Weather
- **Method:** `GET`
- **Path:** `/worlds/world/weather`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": "CLEAR",
  "success": true,
  "timestamp": 1781246783818
}
```
</details>

[Back to Worlds Summary](#worlds)

---

### World Players
- **Method:** `GET`
- **Path:** `/worlds/world/players`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": [],
  "success": true,
  "timestamp": 1781246783869
}
```
</details>

[Back to Worlds Summary](#worlds)

---

### World Entities
- **Method:** `GET`
- **Path:** `/worlds/world/entities`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {},
  "success": true,
  "timestamp": 1781246783918
}
```
</details>

[Back to Worlds Summary](#worlds)

---

### World Loaded Chunks
- **Method:** `GET`
- **Path:** `/worlds/world/chunks`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": 0,
  "success": true,
  "timestamp": 1781246784668
}
```
</details>

[Back to Worlds Summary](#worlds)

---

### World Border
- **Method:** `GET`
- **Path:** `/worlds/world/border`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "damageBuffer": 5.0,
    "size": 59999968.0,
    "center": {
      "x": 0.0,
      "z": 0.0
    },
    "damageAmount": 0.2
  },
  "success": true,
  "timestamp": 1781246784768
}
```
</details>

[Back to Worlds Summary](#worlds)

---

### World Gamerules
- **Method:** `GET`
- **Path:** `/worlds/world/gamerules`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "freeze_damage": true,
    "natural_health_regeneration": true,
    "forgive_dead_players": true,
    "tnt_explosion_drop_decay": false,
    "allow_entering_nether_using_portals": true,
    "max_entity_cramming": 24,
    "max_snow_accumulation_height": 1,
    "advance_weather": true,
    "spawn_phantoms": true,
    "fire_spread_radius_around_player": 128,
    "player_movement_check": true,
    "spawner_blocks_work": true,
    "show_death_messages": true,
    "send_command_feedback": true,
    "spread_vines": true,
    "block_explosion_drop_decay": true,
    "global_sound_events": true,
    "locator_bar": false,
    "max_block_modifications": 32768,
    "pvp": true,
    "spawn_wandering_traders": true,
    "players_nether_portal_creative_delay": 0,
    "fire_damage": true,
    "lava_source_conversion": false,
    "mob_griefing": true,
    "respawn_radius": 10,
    "players_nether_portal_default_delay": 80,
    "immediate_respawn": false,
    "limited_crafting": false,
    "show_advancement_messages": true,
    "mob_explosion_drop_decay": true,
    "players_sleeping_percentage": 100,
    "drowning_damage": true,
    "max_command_forks": 65536,
    "command_block_output": true,
    "elytra_movement_check": true,
    "spawn_patrols": true,
    "random_tick_speed": 3,
    "log_admin_commands": true,
    "ender_pearls_vanish_on_death": true,
    "fall_damage": true,
    "raids": true,
    "mob_drops": true,
    "spawn_mobs": true,
    "tnt_explodes": true,
    "spectators_generate_chunks": true,
    "block_drops": true,
    "command_blocks_work": true,
    "spawn_monsters": true,
    "advance_time": true,
    "universal_anger": false,
    "entity_drops": true,
    "projectiles_can_break_blocks": true,
    "spawn_wardens": true,
    "water_source_conversion": true,
    "keep_inventory": false,
    "reduced_debug_info": false,
    "max_command_sequence_length": 65536
  },
  "success": true,
  "timestamp": 1781246784870
}
```
</details>

[Back to Worlds Summary](#worlds)

---

## Integrations

| Endpoint Name | Method | Path | Status | Details |
|---|---|---|---|---|
| Vault Player Data | `GET` | `/vault/player/de11f3ca-003f-3713-b771-62bc481dbfca` | ![Status 404](https://img.shields.io/badge/Status-404-red) | [View Details](#vault-player-data) |
| Vault Groups | `GET` | `/vault/groups` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#vault-groups) |
| Vault Group Details | `GET` | `/vault/groups/default` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#vault-group-details) |
| Vault Economy Status | `GET` | `/vault/economy/status` | ![Status 429](https://img.shields.io/badge/Status-429-red) | [View Details](#vault-economy-status) |
| LuckPerms Player Data | `GET` | `/luckperms/player/de11f3ca-003f-3713-b771-62bc481dbfca` | ![Status 404](https://img.shields.io/badge/Status-404-red) | [View Details](#luckperms-player-data) |
| LuckPerms Groups | `GET` | `/luckperms/groups` | ![Status 429](https://img.shields.io/badge/Status-429-red) | [View Details](#luckperms-groups) |
| LuckPerms Group Details | `GET` | `/luckperms/groups/default` | ![Status 429](https://img.shields.io/badge/Status-429-red) | [View Details](#luckperms-group-details) |
| LuckPerms Group Members | `GET` | `/luckperms/groups/default/members` | ![Status 429](https://img.shields.io/badge/Status-429-red) | [View Details](#luckperms-group-members) |
| LuckPerms Group Permissions | `GET` | `/luckperms/groups/default/permissions` | ![Status 429](https://img.shields.io/badge/Status-429-red) | [View Details](#luckperms-group-permissions) |
| PAPI Plugins | `GET` | `/papi/plugins` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#papi-plugins) |
| PAPI Evaluate Placeholders | `POST` | `/papi/evaluate` | ![Status 400](https://img.shields.io/badge/Status-400-red) | [View Details](#papi-evaluate-placeholders) |
| NaturalSchool Player Data | `GET` | `/naturalschool/player/de11f3ca-003f-3713-b771-62bc481dbfca` | ![Status 429](https://img.shields.io/badge/Status-429-red) | [View Details](#naturalschool-player-data) |
| NaturalSchool Refresh | `POST` | `/naturalschool/refresh` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#naturalschool-refresh) |

### Vault Player Data
- **Method:** `GET`
- **Path:** `/vault/player/de11f3ca-003f-3713-b771-62bc481dbfca`
- **HTTP Status:** `404`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "PLAYER_NOT_FOUND",
    "message": "Player not found",
    "timestamp": 1781246784918
  }
}
```
</details>

[Back to Integrations Summary](#integrations)

---

### Vault Groups
- **Method:** `GET`
- **Path:** `/vault/groups`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": [
    "owner",
    "default",
    "dev"
  ],
  "success": true,
  "timestamp": 1781246785024
}
```
</details>

[Back to Integrations Summary](#integrations)

---

### Vault Group Details
- **Method:** `GET`
- **Path:** `/vault/groups/default`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "prefix": "",
    "name": "default",
    "suffix": ""
  },
  "success": true,
  "timestamp": 1781246785136
}
```
</details>

[Back to Integrations Summary](#integrations)

---

### Vault Economy Status
- **Method:** `GET`
- **Path:** `/vault/economy/status`
- **HTTP Status:** `429`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Too many requests.",
    "timestamp": 1781246785169
  }
}
```
</details>

[Back to Integrations Summary](#integrations)

---

### LuckPerms Player Data
- **Method:** `GET`
- **Path:** `/luckperms/player/de11f3ca-003f-3713-b771-62bc481dbfca`
- **HTTP Status:** `404`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "PLAYER_NOT_FOUND",
    "message": "Player not found",
    "timestamp": 1781246785568
  }
}
```
</details>

[Back to Integrations Summary](#integrations)

---

### LuckPerms Groups
- **Method:** `GET`
- **Path:** `/luckperms/groups`
- **HTTP Status:** `429`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Too many requests.",
    "timestamp": 1781246785623
  }
}
```
</details>

[Back to Integrations Summary](#integrations)

---

### LuckPerms Group Details
- **Method:** `GET`
- **Path:** `/luckperms/groups/default`
- **HTTP Status:** `429`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Too many requests.",
    "timestamp": 1781246785683
  }
}
```
</details>

[Back to Integrations Summary](#integrations)

---

### LuckPerms Group Members
- **Method:** `GET`
- **Path:** `/luckperms/groups/default/members`
- **HTTP Status:** `429`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Too many requests.",
    "timestamp": 1781246785721
  }
}
```
</details>

[Back to Integrations Summary](#integrations)

---

### LuckPerms Group Permissions
- **Method:** `GET`
- **Path:** `/luckperms/groups/default/permissions`
- **HTTP Status:** `429`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Too many requests.",
    "timestamp": 1781246785762
  }
}
```
</details>

[Back to Integrations Summary](#integrations)

---

### PAPI Plugins
- **Method:** `GET`
- **Path:** `/papi/plugins`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": [
    "excellenteconomy",
    "localtime",
    "server",
    "img",
    "naturalschool",
    "servertime",
    "iaplayerstat",
    "naturalcore",
    "multiverse-core",
    "plan",
    "vault",
    "player"
  ],
  "success": true,
  "timestamp": 1781246786519
}
```
</details>

[Back to Integrations Summary](#integrations)

---

### PAPI Evaluate Placeholders
- **Method:** `POST`
- **Path:** `/papi/evaluate`
- **HTTP Status:** `400`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "INVALID_REQUEST",
    "message": "Invalid body format",
    "timestamp": 1781246786621
  }
}
```
</details>

[Back to Integrations Summary](#integrations)

---

### NaturalSchool Player Data
- **Method:** `GET`
- **Path:** `/naturalschool/player/de11f3ca-003f-3713-b771-62bc481dbfca`
- **HTTP Status:** `429`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Too many requests.",
    "timestamp": 1781246786666
  }
}
```
</details>

[Back to Integrations Summary](#integrations)

---

### NaturalSchool Refresh
- **Method:** `POST`
- **Path:** `/naturalschool/refresh`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "message": "NaturalSchool integration reloaded.",
    "success": true
  },
  "success": true,
  "timestamp": 1781246787372
}
```
</details>

[Back to Integrations Summary](#integrations)

---

## Admin

| Endpoint Name | Method | Path | Status | Details |
|---|---|---|---|---|
| Admin Health | `GET` | `/admin/health` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [View Details](#admin-health) |
| Admin OpenAPI (JSON) | `GET` | `/admin/openapi.json` | ![Status 429](https://img.shields.io/badge/Status-429-red) | [View Details](#admin-openapi-json) |
| Admin API Keys | `GET` | `/admin/keys` | ![Status 429](https://img.shields.io/badge/Status-429-red) | [View Details](#admin-api-keys) |
| Admin Config | `GET` | `/admin/config` | ![Status 429](https://img.shields.io/badge/Status-429-red) | [View Details](#admin-config) |
| Admin Rate Limits | `GET` | `/admin/rate-limits` | ![Status 429](https://img.shields.io/badge/Status-429-red) | [View Details](#admin-rate-limits) |
| Admin Snapshot History | `GET` | `/admin/snapshot/history` | ![Status 429](https://img.shields.io/badge/Status-429-red) | [View Details](#admin-snapshot-history) |

### Admin Health
- **Method:** `GET`
- **Path:** `/admin/health`
- **HTTP Status:** `200`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "data": {
    "status": "ok"
  },
  "success": true,
  "timestamp": 1781246787414
}
```
</details>

[Back to Admin Summary](#admin)

---

### Admin OpenAPI (JSON)
- **Method:** `GET`
- **Path:** `/admin/openapi.json`
- **HTTP Status:** `429`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Too many requests.",
    "timestamp": 1781246787463
  }
}
```
</details>

[Back to Admin Summary](#admin)

---

### Admin API Keys
- **Method:** `GET`
- **Path:** `/admin/keys`
- **HTTP Status:** `429`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Too many requests.",
    "timestamp": 1781246787508
  }
}
```
</details>

[Back to Admin Summary](#admin)

---

### Admin Config
- **Method:** `GET`
- **Path:** `/admin/config`
- **HTTP Status:** `429`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Too many requests.",
    "timestamp": 1781246787549
  }
}
```
</details>

[Back to Admin Summary](#admin)

---

### Admin Rate Limits
- **Method:** `GET`
- **Path:** `/admin/rate-limits`
- **HTTP Status:** `429`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Too many requests.",
    "timestamp": 1781246787596
  }
}
```
</details>

[Back to Admin Summary](#admin)

---

### Admin Snapshot History
- **Method:** `GET`
- **Path:** `/admin/snapshot/history`
- **HTTP Status:** `429`

<details>
<summary>🔍 Click to expand/collapse full JSON Response</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Too many requests.",
    "timestamp": 1781246787642
  }
}
```
</details>

[Back to Admin Summary](#admin)

---
