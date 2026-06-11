# NaturalAPI Endpoints Live Request & Response Report
Generated at: 2026-06-11 14:14:35
Base URL: `http://natural.nodevoid.my.id:19133/api/v1`

## Get Server Overview

### `GET /server`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server
```
**Status:** `200` | **Latency:** `50ms`
**Response:**
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
      "visible": 3,
      "vanished": 0,
      "max": 70,
      "online": 3
    },
    "javaVersion": "25.0.3",
    "osName": "Linux",
    "version": "1.21.11",
    "platform": "Paper",
    "uptime": 174,
    "system": {
      "availableProcessors": 6,
      "systemLoadAverage": 1.22,
      "processCpuLoad": 7.5,
      "systemCpuLoad": 0.0,
      "storage": {
        "totalBytes": 126716452864,
        "usableBytes": 66423635968,
        "freeBytes": 71656247296
      },
      "network": {
        "rxBytesPerSec": 12137,
        "txBytesTotal": 10321580,
        "txBytesPerSec": 34642,
        "rxBytesTotal": 2273109
      }
    },
    "tps": {
      "now": 19.997098412940645,
      "5m": 20.0,
      "15m": 20.0,
      "1m": 20.0
    },
    "online": true,
    "mspt": 8.907484821782177,
    "ram": {
      "maxMB": 8192,
      "freeMB": 4529,
      "usedMB": 3662
    }
  },
  "success": true,
  "timestamp": 1781162075341
}
```

---

## Get Server Status

### `GET /server/status`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/status
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
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
      "visible": 3,
      "vanished": 0,
      "max": 70,
      "online": 3
    },
    "javaVersion": "25.0.3",
    "osName": "Linux",
    "version": "1.21.11",
    "platform": "Paper",
    "uptime": 174,
    "system": {
      "availableProcessors": 6,
      "systemLoadAverage": 1.22,
      "processCpuLoad": 0.0,
      "systemCpuLoad": 0.0,
      "storage": {
        "totalBytes": 126716452864,
        "usableBytes": 66423635968,
        "freeBytes": 71656247296
      },
      "network": {
        "rxBytesPerSec": 36898,
        "txBytesTotal": 10343460,
        "txBytesPerSec": 41694,
        "rxBytesTotal": 2281002
      }
    },
    "tps": {
      "now": 19.999461889226023,
      "5m": 20.0,
      "15m": 20.0,
      "1m": 19.99999489059248
    },
    "online": true,
    "mspt": 8.803267386138614,
    "ram": {
      "maxMB": 8192,
      "freeMB": 4517,
      "usedMB": 3674
    }
  },
  "success": true,
  "timestamp": 1781162075891
}
```

---

## Get Server TPS

### `GET /server/tps`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/tps
```
**Status:** `200` | **Latency:** `46ms`
**Response:**
```json
{
  "data": {
    "now": 20.0,
    "5m": 20.0,
    "15m": 20.0,
    "1m": 20.0
  },
  "success": true,
  "timestamp": 1781162076440
}
```

---

## Get Server MSPT

### `GET /server/mspt`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/mspt
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": 9.203137544554455,
  "success": true,
  "timestamp": 1781162076990
}
```

---

## Get Server RAM

### `GET /server/ram`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/ram
```
**Status:** `200` | **Latency:** `48ms`
**Response:**
```json
{
  "data": {
    "max": 8589934592,
    "used": 739545360,
    "free": 7850389232
  },
  "success": true,
  "timestamp": 1781162077540
}
```

---

## Get Server Uptime

### `GET /server/uptime`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/uptime
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": 176,
  "success": true,
  "timestamp": 1781162078090
}
```

---

## Get Server Version

### `GET /server/version`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/version
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": {
    "version": "1.21.11",
    "platform": "Paper"
  },
  "success": true,
  "timestamp": 1781162078640
}
```

---

## Get Server Player Count

### `GET /server/players/count`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/players/count
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": {
    "visible": 3,
    "vanished": 0,
    "max": 70,
    "online": 3
  },
  "success": true,
  "timestamp": 1781162079190
}
```

---

## Get Server Plugins

### `GET /server/plugins`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/plugins
```
**Status:** `200` | **Latency:** `50ms`
**Response:**
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
      "version": "1.0.4"
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
  "timestamp": 1781162079740
}
```

---

## Get Server Whitelist

### `GET /server/whitelist`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/whitelist
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": [],
  "success": true,
  "timestamp": 1781162080290
}
```

---

## Get Server Banlist

### `GET /server/banlist`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/banlist
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": [],
  "success": true,
  "timestamp": 1781162080840
}
```

---

## Get Playtime Leaderboard

### `GET /server/leaderboard?type=playtime&limit=5`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/leaderboard?type=playtime&limit=5
```
**Status:** `200` | **Latency:** `65ms`
**Response:**
```json
{
  "metadata": {
    "last_sync_timestamp": 1781159181402,
    "provider": "NaturalAPI Snapshots Database",
    "sync_interval_minutes": 10,
    "next_sync_timestamp": 1781159781402
  },
  "data": [
    {
      "uuid": "f32a30cb-1902-35b0-b9e7-a04ed64d13dd",
      "username": "AdityaOkeGas",
      "value": 254633250
    },
    {
      "uuid": "e9467b23-b4d2-4d3f-8522-3b341bb7bb53",
      "username": "Joselyz",
      "value": 74986850
    },
    {
      "uuid": "00000000-0000-0000-0009-01f210430d8f",
      "username": ".FruitPunch5071",
      "value": 3820050
    },
    {
      "uuid": "9e3a9f2b-cfa7-39bc-9d0f-a26fdb9fa040",
      "username": "AiKeiBot",
      "value": 809600
    },
    {
      "uuid": "647077f2-b8b2-35f3-8de6-09c473338e06",
      "username": "AiKei",
      "value": 414450
    }
  ],
  "success": true,
  "timestamp": 1781162081406
}
```

---

## Get Balance Leaderboard

### `GET /server/leaderboard?type=balance&limit=5`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/leaderboard?type=balance&limit=5
```
**Status:** `200` | **Latency:** `44ms`
**Response:**
```json
{
  "metadata": {
    "last_sync_timestamp": 1781159181402,
    "provider": "NaturalAPI Snapshots Database",
    "sync_interval_minutes": 10,
    "next_sync_timestamp": 1781159781402
  },
  "data": [
    {
      "uuid": "f32a30cb-1902-35b0-b9e7-a04ed64d13dd",
      "username": "AdityaOkeGas",
      "value": 31000.0
    },
    {
      "uuid": "e9467b23-b4d2-4d3f-8522-3b341bb7bb53",
      "username": "Joselyz",
      "value": 7100.0
    },
    {
      "uuid": "647077f2-b8b2-35f3-8de6-09c473338e06",
      "username": "AiKei",
      "value": 0.0
    },
    {
      "uuid": "74ce0d95-4602-30f1-a1ce-7831d68d7b42",
      "username": "HermesBot",
      "value": 0.0
    },
    {
      "uuid": "9e3a9f2b-cfa7-39bc-9d0f-a26fdb9fa040",
      "username": "AiKeiBot",
      "value": 0.0
    }
  ],
  "success": true,
  "timestamp": 1781162081950
}
```

---

## Get Kills Leaderboard

### `GET /server/leaderboard?type=kills&limit=5`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/leaderboard?type=kills&limit=5
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "metadata": {
    "last_sync_timestamp": 1781159181402,
    "provider": "NaturalAPI Snapshots Database",
    "sync_interval_minutes": 10,
    "next_sync_timestamp": 1781159781402
  },
  "data": [
    {
      "uuid": "9e3a9f2b-cfa7-39bc-9d0f-a26fdb9fa040",
      "username": "AiKeiBot",
      "value": 2
    },
    {
      "uuid": "00000000-0000-0000-0009-01f210430d8f",
      "username": ".FruitPunch5071",
      "value": 1
    },
    {
      "uuid": "e9467b23-b4d2-4d3f-8522-3b341bb7bb53",
      "username": "Joselyz",
      "value": 0
    },
    {
      "uuid": "647077f2-b8b2-35f3-8de6-09c473338e06",
      "username": "AiKei",
      "value": 0
    },
    {
      "uuid": "74ce0d95-4602-30f1-a1ce-7831d68d7b42",
      "username": "HermesBot",
      "value": 0
    }
  ],
  "success": true,
  "timestamp": 1781162082500
}
```

---

## Get Online Players

### `GET /players`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players
```
**Status:** `200` | **Latency:** `39ms`
**Response:**
```json
{
  "data": [
    {
      "staffMode": false,
      "vanished": false,
      "displayName": "AdityaOkeGas2",
      "afk": false,
      "online": true,
      "uuid": "de11f3ca-003f-3713-b771-62bc481dbfca",
      "username": "AdityaOkeGas2"
    },
    {
      "staffMode": false,
      "vanished": false,
      "displayName": "AdityaOkeGas3",
      "afk": false,
      "online": true,
      "uuid": "75ed88b3-eb5c-3e45-a763-c3413042883d",
      "username": "AdityaOkeGas3"
    },
    {
      "staffMode": false,
      "vanished": false,
      "displayName": "AdityaOkeGas4",
      "afk": false,
      "online": true,
      "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
      "username": "AdityaOkeGas4"
    }
  ],
  "success": true,
  "timestamp": 1781162083040
}
```

---

## Get Paged Players

### `GET /players/all?page=1&pageSize=10`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/all?page=1&pageSize=10
```
**Status:** `200` | **Latency:** `59ms`
**Response:**
```json
{
  "data": {
    "pagination": {
      "total": 19,
      "totalPages": 2,
      "pageSize": 10,
      "page": 1
    },
    "players": [
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1780837329868,
        "displayName": "AdityaOkeGas4",
        "afk": false,
        "online": true,
        "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
        "username": "AdityaOkeGas4"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1780837329848,
        "displayName": "AdityaOkeGas3",
        "afk": false,
        "online": true,
        "uuid": "75ed88b3-eb5c-3e45-a763-c3413042883d",
        "username": "AdityaOkeGas3"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1780837329826,
        "displayName": "AdityaOkeGas2",
        "afk": false,
        "online": true,
        "uuid": "de11f3ca-003f-3713-b771-62bc481dbfca",
        "username": "AdityaOkeGas2"
      },
      {
        "staffMode": false,
        "vanished": false,
        "lastSeen": 1781159395128,
        "displayName": "AdityaOkeGas",
        "afk": false,
        "online": false,
        "uuid": "f32a30cb-1902-35b0-b9e7-a04ed64d13dd",
        "username": "AdityaOkeGas"
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
        "lastSeen": 1780989364246,
        "displayName": "Daichi_keii",
        "afk": false,
        "online": false,
        "uuid": "9c045595-a5da-4284-9d9e-c3603f86432b",
        "username": "Daichi_keii"
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
      }
    ]
  },
  "success": true,
  "timestamp": 1781162083599
}
```

---

## Get Player Details

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca
```
**Status:** `200` | **Latency:** `52ms`
**Response:**
```json
{
  "data": {
    "country": "Singapore",
    "city": "Singapore",
    "displayName": "AdityaOkeGas2",
    "ping": 4,
    "isp": "Oracle Corporation",
    "totalPlaytimeMs": 24225600,
    "locale": "en",
    "uuid": "de11f3ca-003f-3713-b771-62bc481dbfca",
    "gamemode": "SURVIVAL",
    "saturation": 4.0,
    "firstJoin": 1780588217858,
    "school": {
      "academicClass": 0,
      "academicStage": "NONE",
      "currentSemester": "GANJIL",
      "isStaff": false,
      "nis": null,
      "rank": {
        "displayName": "<gray>Belum Terdaftar</gray>",
        "id": "NONE",
        "priority": 0,
        "type": "NONE"
      },
      "isManagement": false
    },
    "expLevel": 0,
    "maxHealth": 20.0,
    "vault": {
      "balance": 1000.0,
      "prefix": "",
      "permissionPlugin": "LuckPerms",
      "economyPlugin": "Money",
      "groups": [
        "default"
      ],
      "currency": "Money",
      "suffix": "",
      "group": "default"
    },
    "ipHistory": [
      {
        "country": "Singapore",
        "lastSeen": 1781162028019,
        "city": "Singapore",
        "firstSeen": 1781019787927,
        "isp": "Oracle Corporation",
        "ipAddress": "140.245.116.11",
        "region": "South East",
        "asn": "AS31898 Oracle Corporation"
      }
    ],
    "vanished": false,
    "afk": false,
    "ipAddress": "140.245.116.11",
    "health": 20.0,
    "expProgress": 0.0,
    "totalExp": 0,
    "staffMode": false,
    "clientBrand": "vanilla",
    "lastSeen": 1780837329826,
    "luckperms": {
      "inheritedGroups": [
        "default"
      ],
      "permissionNodes": [
        {
          "node": "group.default",
          "value": true
        }
      ],
      "primaryGroup": "default"
    },
    "online": true,
    "location": {
      "world": "world",
      "x": -3272.5,
      "y": 101.5,
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
  "timestamp": 1781162084152
}
```

---

## Get Player Location

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/location`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/location
```
**Status:** `200` | **Latency:** `36ms`
**Response:**
```json
{
  "data": {
    "world": "world",
    "x": -3272.5,
    "y": 101.5,
    "z": 3167.5,
    "pitch": 0.0,
    "yaw": 0.0
  },
  "success": true,
  "timestamp": 1781162084690
}
```

---

## Get Player Health

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/health`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/health
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": {
    "saturation": 4.0,
    "health": 20.0,
    "maxHealth": 20.0,
    "foodLevel": 20
  },
  "success": true,
  "timestamp": 1781162085240
}
```

---

## Get Player Experience

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/experience`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/experience
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": {
    "expLevel": 0,
    "expProgress": 0.0,
    "totalExp": 0
  },
  "success": true,
  "timestamp": 1781162085790
}
```

---

## Get Player Gamemode

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/gamemode`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/gamemode
```
**Status:** `200` | **Latency:** `48ms`
**Response:**
```json
{
  "data": "SURVIVAL",
  "success": true,
  "timestamp": 1781162086340
}
```

---

## Get Player Inventory

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory
```
**Status:** `200` | **Latency:** `50ms`
**Response:**
```json
{
  "data": [],
  "success": true,
  "timestamp": 1781162086891
}
```

---

## Get Player Hotbar

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory/hotbar`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory/hotbar
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": [],
  "success": true,
  "timestamp": 1781162087440
}
```

---

## Get Player Armor

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory/armor`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory/armor
```
**Status:** `200` | **Latency:** `48ms`
**Response:**
```json
{
  "data": {
    "chestplate": null,
    "helmet": null,
    "boots": null,
    "leggings": null
  },
  "success": true,
  "timestamp": 1781162087990
}
```

---

## Get Player Offhand

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory/offhand`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/inventory/offhand
```
**Status:** `200` | **Latency:** `50ms`
**Response:**
```json
{
  "data": null,
  "success": true,
  "timestamp": 1781162088540
}
```

---

## Get Player Effects

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/effects`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/effects
```
**Status:** `200` | **Latency:** `48ms`
**Response:**
```json
{
  "data": [
    {
      "durationTicks": -1,
      "durationSeconds": -0.05,
      "icon": false,
      "amplifier": 255,
      "ambient": false,
      "type": "DAMAGE_RESISTANCE",
      "particles": false
    },
    {
      "durationTicks": -1,
      "durationSeconds": -0.05,
      "icon": false,
      "amplifier": 255,
      "ambient": false,
      "type": "REGENERATION",
      "particles": false
    }
  ],
  "success": true,
  "timestamp": 1781162089090
}
```

---

## Get Player Skin

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/skin`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/skin
```
**Status:** `404` | **Latency:** `346ms`
**Response:**
```json
{
  "success": false,
  "error": {
    "code": "PLAYER_NOT_FOUND",
    "message": "Player or skin not found.",
    "timestamp": 1781162089937
  }
}
```

---

## Get Player Ping

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/ping`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/ping
```
**Status:** `200` | **Latency:** `52ms`
**Response:**
```json
{
  "data": 6,
  "success": true,
  "timestamp": 1781162090490
}
```

---

## Get Player Network Details

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/network`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/network
```
**Status:** `200` | **Latency:** `17ms`
**Response:**
```json
{
  "data": {
    "clientBrand": "vanilla",
    "ipHistory": [
      {
        "country": "Singapore",
        "lastSeen": 1781162028019,
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
  "timestamp": 1781162091009
}
```

---

## Get Player Stats

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/stats`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/stats
```
**Status:** `200` | **Latency:** `31ms`
**Response:**
```json
{
  "data": {
    "firstJoin": 1780588217858,
    "lastSeen": 1780837329826,
    "totalPlaytimeMs": 24233000
  },
  "success": true,
  "timestamp": 1781162091540
}
```

---

## Get Player Permissions

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/permissions`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/permissions
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": [
    "group.default"
  ],
  "success": true,
  "timestamp": 1781162092091
}
```

---

## Check Player Permission node

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/permission/naturalapi.use`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/permission/naturalapi.use
```
**Status:** `200` | **Latency:** `48ms`
**Response:**
```json
{
  "data": {
    "hasPermission": true,
    "permission": "naturalapi.use"
  },
  "success": true,
  "timestamp": 1781162092640
}
```

---

## Get Player Snapshot

### `GET /players/de11f3ca-003f-3713-b771-62bc481dbfca/snapshot`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/snapshot
```
**Status:** `404` | **Latency:** `55ms`
**Response:**
```json
{
  "success": false,
  "error": {
    "code": "PLAYER_NOT_FOUND",
    "message": "Player snapshot not found.",
    "timestamp": 1781162093197
  }
}
```

---

## Create Player Snapshot

### `POST /players/de11f3ca-003f-3713-b771-62bc481dbfca/snapshot`
**Curl Example:**
```bash
curl -X POST -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/snapshot
```
**Status:** `200` | **Latency:** `48ms`
**Response:**
```json
{
  "data": {
    "player": "AdityaOkeGas2",
    "message": "Snapshot triggered successfully."
  },
  "success": true,
  "timestamp": 1781162093747
}
```

---

## Get Offline Player Snapshot

### `GET /players/offline/de11f3ca-003f-3713-b771-62bc481dbfca`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/offline/de11f3ca-003f-3713-b771-62bc481dbfca
```
**Status:** `200` | **Latency:** `48ms`
**Response:**
```json
{
  "data": {
    "country": "Singapore",
    "city": "Singapore",
    "displayName": "AdityaOkeGas2",
    "ping": 13,
    "isp": "Oracle Corporation",
    "totalPlaytimeMs": 24235750,
    "locale": "en",
    "uuid": "de11f3ca-003f-3713-b771-62bc481dbfca",
    "gamemode": "SURVIVAL",
    "saturation": 4.0,
    "firstJoin": 1780588217858,
    "school": {
      "academicClass": 0,
      "academicStage": "NONE",
      "currentSemester": "GANJIL",
      "isStaff": false,
      "nis": null,
      "rank": {
        "displayName": "<gray>Belum Terdaftar</gray>",
        "id": "NONE",
        "priority": 0,
        "type": "NONE"
      },
      "isManagement": false
    },
    "expLevel": 0,
    "maxHealth": 20.0,
    "vault": {
      "balance": 1000.0,
      "prefix": "",
      "permissionPlugin": "LuckPerms",
      "economyPlugin": "Money",
      "groups": [
        "default"
      ],
      "currency": "Money",
      "suffix": "",
      "group": "default"
    },
    "ipHistory": [
      {
        "country": "Singapore",
        "lastSeen": 1781162028019,
        "city": "Singapore",
        "firstSeen": 1781019787927,
        "isp": "Oracle Corporation",
        "ipAddress": "140.245.116.11",
        "region": "South East",
        "asn": "AS31898 Oracle Corporation"
      }
    ],
    "vanished": false,
    "afk": false,
    "ipAddress": "140.245.116.11",
    "health": 20.0,
    "expProgress": 0.0,
    "totalExp": 0,
    "staffMode": false,
    "clientBrand": "vanilla",
    "lastSeen": 1780837329826,
    "luckperms": {
      "inheritedGroups": [
        "default"
      ],
      "permissionNodes": [
        {
          "node": "group.default",
          "value": true
        }
      ],
      "primaryGroup": "default"
    },
    "online": true,
    "location": {
      "world": "world",
      "x": -3272.5,
      "y": 101.5,
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
  "timestamp": 1781162094295
}
```

---

## Get Worlds List

### `GET /worlds`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds
```
**Status:** `200` | **Latency:** `45ms`
**Response:**
```json
{
  "data": [
    {
      "difficulty": "EASY",
      "environment": "NORMAL",
      "seed": 6641214828919989498,
      "entityCount": 415,
      "playerCount": 3,
      "name": "world",
      "weather": "CLEAR",
      "fullTime": 21655002,
      "time": 7002,
      "pvp": true,
      "loadedChunks": 1875
    },
    {
      "difficulty": "EASY",
      "environment": "NETHER",
      "seed": 6641214828919989498,
      "entityCount": 0,
      "playerCount": 0,
      "name": "world_nether",
      "weather": "CLEAR",
      "fullTime": 23056968,
      "time": 16968,
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
      "fullTime": 23056968,
      "time": 16968,
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
      "weather": "RAIN",
      "fullTime": 14449841,
      "time": 1841,
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
  "timestamp": 1781162094841
}
```

---

## Get World Details

### `GET /worlds/world`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world
```
**Status:** `200` | **Latency:** `48ms`
**Response:**
```json
{
  "data": {
    "difficulty": "EASY",
    "environment": "NORMAL",
    "seed": 6641214828919989498,
    "entityCount": 412,
    "playerCount": 3,
    "name": "world",
    "weather": "CLEAR",
    "fullTime": 21655013,
    "time": 7013,
    "pvp": true,
    "loadedChunks": 1875
  },
  "success": true,
  "timestamp": 1781162095391
}
```

---

## Get World Time

### `GET /worlds/world/time`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/time
```
**Status:** `200` | **Latency:** `48ms`
**Response:**
```json
{
  "data": {
    "dayCount": 902,
    "fullTime": 21655024,
    "time": 7024
  },
  "success": true,
  "timestamp": 1781162095940
}
```

---

## Get World Weather

### `GET /worlds/world/weather`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/weather
```
**Status:** `200` | **Latency:** `50ms`
**Response:**
```json
{
  "data": "CLEAR",
  "success": true,
  "timestamp": 1781162096490
}
```

---

## Get World Players

### `GET /worlds/world/players`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/players
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": [
    {
      "staffMode": false,
      "vanished": false,
      "displayName": "AdityaOkeGas2",
      "afk": false,
      "online": true,
      "uuid": "de11f3ca-003f-3713-b771-62bc481dbfca",
      "username": "AdityaOkeGas2"
    },
    {
      "staffMode": false,
      "vanished": false,
      "displayName": "AdityaOkeGas3",
      "afk": false,
      "online": true,
      "uuid": "75ed88b3-eb5c-3e45-a763-c3413042883d",
      "username": "AdityaOkeGas3"
    },
    {
      "staffMode": false,
      "vanished": false,
      "displayName": "AdityaOkeGas4",
      "afk": false,
      "online": true,
      "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
      "username": "AdityaOkeGas4"
    }
  ],
  "success": true,
  "timestamp": 1781162097040
}
```

---

## Get World Entities

### `GET /worlds/world/entities`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/entities
```
**Status:** `200` | **Latency:** `50ms`
**Response:**
```json
{
  "data": {
    "ENDERMAN": 5,
    "CHEST_MINECART": 12,
    "FROG": 1,
    "LLAMA": 4,
    "SHEEP": 47,
    "WITCH": 1,
    "TROPICAL_FISH": 6,
    "SPIDER": 1,
    "ARMADILLO": 9,
    "SKELETON": 13,
    "CREEPER": 13,
    "CHICKEN": 79,
    "TRADER_LLAMA": 2,
    "SLIME": 1,
    "GLOW_SQUID": 16,
    "DROWNED": 1,
    "BEE": 6,
    "PHANTOM": 4,
    "SQUID": 15,
    "HORSE": 4,
    "FALLING_BLOCK": 11,
    "COW": 51,
    "WANDERING_TRADER": 1,
    "FOX": 6,
    "PIG": 53,
    "AXOLOTL": 10,
    "PLAYER": 3,
    "ITEM": 7,
    "BAT": 3,
    "ZOMBIE": 12,
    "SALMON": 7,
    "CAT": 1,
    "WOLF": 3,
    "RABBIT": 3,
    "DONKEY": 1
  },
  "success": true,
  "timestamp": 1781162097591
}
```

---

## Get World Chunks

### `GET /worlds/world/chunks`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/chunks
```
**Status:** `200` | **Latency:** `48ms`
**Response:**
```json
{
  "data": 1875,
  "success": true,
  "timestamp": 1781162098141
}
```

---

## Get World Border

### `GET /worlds/world/border`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/border
```
**Status:** `200` | **Latency:** `50ms`
**Response:**
```json
{
  "data": {
    "damageBuffer": 5.0,
    "size": 59999968.0,
    "center": {
      "z": 0.0,
      "x": 0.0
    },
    "damageAmount": 0.2
  },
  "success": true,
  "timestamp": 1781162098690
}
```

---

## Get World Gamerules

### `GET /worlds/world/gamerules`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/gamerules
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": {
    "freeze_damage": true,
    "natural_health_regeneration": true,
    "forgive_dead_players": true,
    "tnt_explosion_drop_decay": false,
    "max_entity_cramming": 24,
    "allow_entering_nether_using_portals": true,
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
    "spawn_monsters": true,
    "command_blocks_work": true,
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
  "timestamp": 1781162099242
}
```

---

## Get Vault Player Data

### `GET /vault/player/de11f3ca-003f-3713-b771-62bc481dbfca`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/vault/player/de11f3ca-003f-3713-b771-62bc481dbfca
```
**Status:** `200` | **Latency:** `47ms`
**Response:**
```json
{
  "data": {
    "balance": 1000.0,
    "prefix": "",
    "permissionPlugin": "LuckPerms",
    "economyPlugin": "Money",
    "groups": [
      "default"
    ],
    "currency": "Money",
    "suffix": "",
    "group": "default"
  },
  "success": true,
  "timestamp": 1781162099790
}
```

---

## Get Vault Groups

### `GET /vault/groups`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/vault/groups
```
**Status:** `200` | **Latency:** `50ms`
**Response:**
```json
{
  "data": [
    "owner",
    "default",
    "dev"
  ],
  "success": true,
  "timestamp": 1781162100341
}
```

---

## Get Vault Group Details

### `GET /vault/groups/default`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/vault/groups/default
```
**Status:** `200` | **Latency:** `54ms`
**Response:**
```json
{
  "data": {
    "prefix": "",
    "name": "default",
    "suffix": ""
  },
  "success": true,
  "timestamp": 1781162100897
}
```

---

## Get Vault Economy Status

### `GET /vault/economy/status`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/vault/economy/status
```
**Status:** `200` | **Latency:** `43ms`
**Response:**
```json
{
  "data": {
    "currencySingular": "Money",
    "name": "Money",
    "currencyPlural": "Money",
    "enabled": true
  },
  "success": true,
  "timestamp": 1781162101440
}
```

---

## Get LuckPerms Player Data

### `GET /luckperms/player/de11f3ca-003f-3713-b771-62bc481dbfca`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/luckperms/player/de11f3ca-003f-3713-b771-62bc481dbfca
```
**Status:** `200` | **Latency:** `50ms`
**Response:**
```json
{
  "data": {
    "inheritedGroups": [
      "default"
    ],
    "permissionNodes": [
      {
        "node": "group.default",
        "value": true
      }
    ],
    "primaryGroup": "default"
  },
  "success": true,
  "timestamp": 1781162101990
}
```

---

## Get LuckPerms Groups

### `GET /luckperms/groups`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/luckperms/groups
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": [
    "owner",
    "default",
    "dev"
  ],
  "success": true,
  "timestamp": 1781162102541
}
```

---

## Get LuckPerms Group Details

### `GET /luckperms/groups/default`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/luckperms/groups/default
```
**Status:** `200` | **Latency:** `47ms`
**Response:**
```json
{
  "data": {
    "displayName": null,
    "name": "default",
    "weight": 0
  },
  "success": true,
  "timestamp": 1781162103090
}
```

---

## Get LuckPerms Group Members

### `GET /luckperms/groups/default/members`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/luckperms/groups/default/members
```
**Status:** `500` | **Latency:** `94ms`
**Response:**
```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_ERROR",
    "message": "Error fetching LuckPerms group members: No serializer found for class net.luckperms.api.node.SimpleNodeType and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: java.util.HashMap[\"data\"]->java.util.ArrayList[0]->java.util.HashMap[\"username\"]->com.google.common.collect.SingletonImmutableList[0]->me.lucko.luckperms.common.node.types.Inheritance[\"type\"])",
    "timestamp": 1781162103685
  }
}
```

---

## Get LuckPerms Group Permissions

### `GET /luckperms/groups/default/permissions`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/luckperms/groups/default/permissions
```
**Status:** `200` | **Latency:** `55ms`
**Response:**
```json
{
  "data": [],
  "success": true,
  "timestamp": 1781162104240
}
```

---

## Evaluate PlaceholderAPI

### `POST /papi/evaluate`
**Curl Example:**
```bash
curl -X POST -H "Authorization: Bearer <masked>" -d '{"player": "de11f3ca-003f-3713-b771-62bc481dbfca", "placeholders": ["%player_name%", "%server_tps%"]}' http://natural.nodevoid.my.id:19133/api/v1/papi/evaluate
```
**Status:** `200` | **Latency:** `51ms`
**Response:**
```json
{
  "data": {
    "%server_tps%": "\u00a7a*20.0\u00a77, \u00a7a*20.0\u00a77, \u00a7a*20.0",
    "%player_name%": "AdityaOkeGas2"
  },
  "success": true,
  "timestamp": 1781162104793
}
```

---

## Get Registered PAPI expansions

### `GET /papi/plugins`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/papi/plugins
```
**Status:** `200` | **Latency:** `44ms`
**Response:**
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
  "timestamp": 1781162105340
}
```

---

## Get NaturalSchool Player Data

### `GET /naturalschool/player/de11f3ca-003f-3713-b771-62bc481dbfca`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/naturalschool/player/de11f3ca-003f-3713-b771-62bc481dbfca
```
**Status:** `200` | **Latency:** `49ms`
**Response:**
```json
{
  "data": {
    "academicClass": 0,
    "academicStage": "NONE",
    "currentSemester": "GANJIL",
    "isStaff": false,
    "nis": null,
    "rank": {
      "displayName": "<gray>Belum Terdaftar</gray>",
      "id": "NONE",
      "priority": 0,
      "type": "NONE"
    },
    "isManagement": false
  },
  "success": true,
  "timestamp": 1781162105890
}
```

---

## Refresh NaturalSchool Integration

### `POST /naturalschool/refresh`
**Curl Example:**
```bash
curl -X POST -H "Authorization: Bearer <masked>" -d '{"uuid": "de11f3ca-003f-3713-b771-62bc481dbfca"}' http://natural.nodevoid.my.id:19133/api/v1/naturalschool/refresh
```
**Status:** `200` | **Latency:** `53ms`
**Response:**
```json
{
  "data": {
    "message": "Player snapshot update triggered successfully.",
    "uuid": "de11f3ca-003f-3713-b771-62bc481dbfca"
  },
  "success": true,
  "timestamp": 1781162106447
}
```

---

## Get Admin Health Check

### `GET /admin/health`
**Curl Example:**
```bash
curl -X GET  http://natural.nodevoid.my.id:19133/api/v1/admin/health
```
**Status:** `200` | **Latency:** `7ms`
**Response:**
```json
{
  "data": {
    "status": "ok"
  },
  "success": true,
  "timestamp": 1781162106954
}
```

---

## Get OpenAPI Specs YAML

### `GET /admin/openapi.yaml`
**Curl Example:**
```bash
curl -X GET  http://natural.nodevoid.my.id:19133/api/v1/admin/openapi.yaml
```
**Status:** `0` | **Latency:** `17ms`
**Response:**
```json
'utf-8' codec can't decode byte 0xa7 in position 3754: invalid start byte
```

---

## Get OpenAPI Specs JSON

### `GET /admin/openapi.json`
**Curl Example:**
```bash
curl -X GET  http://natural.nodevoid.my.id:19133/api/v1/admin/openapi.json
```
**Status:** `501` | **Latency:** `7ms`
**Response:**
```json
{
  "success": false,
  "error": {
    "code": "NOT_IMPLEMENTED",
    "message": "Please use /admin/openapi.yaml for the OpenAPI spec.",
    "timestamp": 1781162107981
  }
}
```

---

## Get API Keys List

### `GET /admin/keys`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/admin/keys
```
**Status:** `200` | **Latency:** `20ms`
**Response:**
```json
{
  "data": [
    {
      "expires_at": null,
      "last_used": 1781162106440,
      "name": "test",
      "created_at": 1781161852460,
      "id": "2f27591d-d0ba-4ef9-a796-cf8a9f35c175",
      "scopes": "*",
      "enabled": true
    },
    {
      "expires_at": null,
      "last_used": 1781162091890,
      "name": "web",
      "created_at": 1781015698653,
      "id": "38fd1267-b237-45a0-ae36-8c86a9304908",
      "scopes": "*",
      "enabled": true
    }
  ],
  "success": true,
  "timestamp": 1781162108498
}
```

---

## Generate API Key

### `POST /admin/keys`
**Curl Example:**
```bash
curl -X POST -H "Authorization: Bearer <masked>" -d '{"name": "test_script_key", "scopes": "*", "expiresAt": null}' http://natural.nodevoid.my.id:19133/api/v1/admin/keys
```
**Status:** `500` | **Latency:** `23ms`
**Response:**
```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_ERROR",
    "message": "An unexpected error occurred: null",
    "timestamp": 1781162109026
  }
}
```

---

## Get Active Configuration

### `GET /admin/config`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/admin/config
```
**Status:** `200` | **Latency:** `11ms`
**Response:**
```json
{
  "data": {
    "server": {
      "bind-address": "0.0.0.0",
      "port": 19133,
      "swagger-path": "/swagger",
      "base-path": "/api/v1",
      "public-url": "http://natural.nodevoid.my.id:19133",
      "swagger-enabled": true
    },
    "features": {
      "leaderboard": {
        "providers": {
          "kills": "database",
          "balance": "database",
          "votes": "database",
          "playtime": "database",
          "exp": "database",
          "deaths": "database"
        }
      },
      "endpoints": {
        "server": true,
        "worlds": true,
        "offline-players": true,
        "players": true
      },
      "luckperms": {
        "enabled": true
      },
      "websocket": {
        "server-stats-interval": 40,
        "endpoints": {
          "chat": true,
          "player-detail": true,
          "server-stats": true,
          "player-events": true
        },
        "enabled": true
      },
      "skin": {
        "cache-ttl-hours": 6
      },
      "vanish": {
        "show-vanished-in-count": false,
        "show-vanished-in-list": false
      },
      "snapshot": {
        "interval-minutes": 10,
        "retention-days": 30,
        "auto-save": true
      },
      "papi": {
        "enabled": true
      },
      "vault": {
        "enabled": true
      }
    },
    "database": {
      "mariadb": {
        "pool-size": 10,
        "database": "pl_dev",
        "port": 3306,
        "host": "oc.keidev.my.id",
        "username": "pl_dev"
      },
      "sqlite": {
        "file": "data.db"
      },
      "provider": "mariadb",
      "mysql": {
        "pool-size": 10,
        "connection-timeout": 30000,
        "database": "naturalapi",
        "port": 3306,
        "host": "localhost",
        "username": "root"
      }
    },
    "security": {
      "rate-limit": {
        "burst": 30,
        "requests-per-minute": 120,
        "enabled": true
      },
      "cors": {
        "allowed-methods": [
          "GET",
          "POST",
          "DELETE"
        ],
        "allowed-headers": [
          "Authorization",
          "Content-Type"
        ],
        "allowed-origins": [
          "*"
        ],
        "enabled": true
      },
      "ip-allowlist": {
        "ips": [],
        "enabled": false
      },
      "swagger-auth-required": true
    },
    "logging": {
      "log-requests": true,
      "log-level": "INFO",
      "log-auth-failures": true
    }
  },
  "success": true,
  "timestamp": 1781162109538
}
```

---

## Get Snapshot History

### `GET /admin/snapshot/history`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/admin/snapshot/history
```
**Status:** `501` | **Latency:** `10ms`
**Response:**
```json
{
  "success": false,
  "error": {
    "code": "NOT_IMPLEMENTED",
    "message": "Snapshot history not implemented yet.",
    "timestamp": 1781162110049
  }
}
```

---

## Purge Snapshots

### `DELETE /admin/snapshot/purge`
**Curl Example:**
```bash
curl -X DELETE -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/admin/snapshot/purge
```
**Status:** `200` | **Latency:** `13ms`
**Response:**
```json
{
  "data": {
    "purged": true
  },
  "success": true,
  "timestamp": 1781162110562
}
```

---

## Get Rate Limits Status

### `GET /admin/rate-limits`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/admin/rate-limits
```
**Status:** `501` | **Latency:** `10ms`
**Response:**
```json
{
  "success": false,
  "error": {
    "code": "NOT_IMPLEMENTED",
    "message": "Rate limit viewing not implemented yet.",
    "timestamp": 1781162111074
  }
}
```

---

## Reset Rate Limits

### `DELETE /admin/rate-limits/127.0.0.1`
**Curl Example:**
```bash
curl -X DELETE -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/admin/rate-limits/127.0.0.1
```
**Status:** `200` | **Latency:** `13ms`
**Response:**
```json
{
  "data": {
    "reset": true
  },
  "success": true,
  "timestamp": 1781162111588
}
```

---

## Trigger Plugin Reload

### `POST /admin/reload`
**Curl Example:**
```bash
curl -X POST -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/admin/reload
```
**Status:** `200` | **Latency:** `12ms`
**Response:**
```json
{
  "data": {
    "status": "reloading"
  },
  "success": true,
  "timestamp": 1781162112100
}
```

---

