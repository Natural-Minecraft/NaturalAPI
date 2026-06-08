# NaturalAPI Endpoints Live Request & Response Report
Generated at: Sat Jun  6 17:37:51 2026
Base URL: `http://natural.nodevoid.my.id:19133/api/v1`

## 1. Server Endpoints

### `GET /server`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server
```
**Status:** `200` | **Latency:** `15314ms`
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
    "tps": {
      "now": 20.0,
      "1m": 19.999999478101596,
      "5m": 19.956673076448787,
      "15m": 19.98555607675455
    },
    "players": {
      "visible": 2,
      "vanished": 0,
      "max": 70,
      "online": 2
    },
    "javaVersion": "25.0.3",
    "online": true,
    "mspt": 7.70308205940594,
    "osName": "Linux",
    "version": "1.21.11",
    "platform": "Paper",
    "uptime": 1459,
    "ram": {
      "maxMB": 8192,
      "freeMB": 6228,
      "usedMB": 1963
    }
  },
  "success": true,
  "timestamp": 1780742286966
}
```

### `GET /server/status`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/status
```
**Status:** `200` | **Latency:** `5140ms`
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
    "tps": {
      "now": 20.0,
      "1m": 20.0,
      "5m": 19.95667459230465,
      "15m": 19.98555635607049
    },
    "players": {
      "visible": 2,
      "vanished": 0,
      "max": 70,
      "online": 2
    },
    "javaVersion": "25.0.3",
    "online": true,
    "mspt": 6.81189802970297,
    "osName": "Linux",
    "version": "1.21.11",
    "platform": "Paper",
    "uptime": 1464,
    "ram": {
      "maxMB": 8192,
      "freeMB": 6148,
      "usedMB": 2043
    }
  },
  "success": true,
  "timestamp": 1780742292315
}
```

### `GET /server/tps`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/tps
```
**Status:** `200` | **Latency:** `5108ms`
**Response:**
```json
{
  "data": {
    "now": 20.0,
    "1m": 19.999997906411544,
    "5m": 19.95667540041535,
    "15m": 19.985555783471714
  },
  "success": true,
  "timestamp": 1780742297666
}
```

### `GET /server/mspt`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/mspt
```
**Status:** `200` | **Latency:** `5147ms`
**Response:**
```json
{
  "data": 6.828696960396039,
  "success": true,
  "timestamp": 1780742303015
}
```

### `GET /server/ram`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/ram
```
**Status:** `200` | **Latency:** `5101ms`
**Response:**
```json
{
  "data": {
    "max": 8589934592,
    "used": 2394342704,
    "free": 6195591888
  },
  "success": true,
  "timestamp": 1780742308315
}
```

### `GET /server/uptime`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/uptime
```
**Status:** `200` | **Latency:** `5106ms`
**Response:**
```json
{
  "data": 1485,
  "success": true,
  "timestamp": 1780742313615
}
```

### `GET /server/version`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/version
```
**Status:** `200` | **Latency:** `5091ms`
**Response:**
```json
{
  "data": {
    "version": "1.21.11",
    "platform": "Paper"
  },
  "success": true,
  "timestamp": 1780742318915
}
```

### `GET /server/players/count`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/players/count
```
**Status:** `200` | **Latency:** `5101ms`
**Response:**
```json
{
  "data": {
    "visible": 2,
    "vanished": 0,
    "max": 70,
    "online": 2
  },
  "success": true,
  "timestamp": 1780742324215
}
```

### `GET /server/plugins`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/plugins
```
**Status:** `200` | **Latency:** `5098ms`
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
      "version": "1.6.1"
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
      "name": "NaturalAPI",
      "version": "1.0.0"
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
  "timestamp": 1780742329515
}
```

### `GET /server/whitelist`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/whitelist
```
**Status:** `200` | **Latency:** `5098ms`
**Response:**
```json
{
  "data": [],
  "success": true,
  "timestamp": 1780742334815
}
```

### `GET /server/banlist`
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/server/banlist
```
**Status:** `200` | **Latency:** `5098ms`
**Response:**
```json
{
  "data": [],
  "success": true,
  "timestamp": 1780742340115
}
```

## 2. Online Players List

### `GET /players` (Default / includeVanished=false)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players
```
**Status:** `200` | **Latency:** `5150ms`
**Response:**
```json
{
  "data": [
    {
      "staffMode": false,
      "vanished": false,
      "displayName": "AdityaOkeGas4",
      "afk": true,
      "online": true,
      "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
      "username": "AdityaOkeGas4"
    },
    {
      "staffMode": false,
      "vanished": false,
      "displayName": "AdityaOkeGas3",
      "afk": true,
      "online": true,
      "uuid": "75ed88b3-eb5c-3e45-a763-c3413042883d",
      "username": "AdityaOkeGas3"
    }
  ],
  "success": true,
  "timestamp": 1780742345465
}
```

### `GET /players?includeVanished=true` (Query param test)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players?includeVanished=true
```
**Status:** `200` | **Latency:** `4099ms`
**Response:**
```json
{
  "data": [
    {
      "staffMode": false,
      "vanished": false,
      "displayName": "AdityaOkeGas4",
      "afk": true,
      "online": true,
      "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
      "username": "AdityaOkeGas4"
    },
    {
      "staffMode": false,
      "vanished": false,
      "displayName": "AdityaOkeGas3",
      "afk": true,
      "online": true,
      "uuid": "75ed88b3-eb5c-3e45-a763-c3413042883d",
      "username": "AdityaOkeGas3"
    }
  ],
  "success": true,
  "timestamp": 1780742349565
}
```

### `GET /players/all` (List all players - online & offline, paged)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" "http://natural.nodevoid.my.id:19133/api/v1/players/all?page=1&pageSize=50"
```
**Status:** `200` | **Latency:** `N/A`
**Response:**
```json
{
  "success": true,
  "timestamp": 1780742345465,
  "data": {
    "players": [
      {
        "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
        "username": "AdityaOkeGas4",
        "displayName": "AdityaOkeGas4",
        "online": true,
        "vanished": false,
        "afk": true,
        "staffMode": false,
        "lastSeen": 1780742345465
      },
      {
        "uuid": "75ed88b3-eb5c-3e45-a763-c3413042883d",
        "username": "AdityaOkeGas3",
        "displayName": "AdityaOkeGas3",
        "online": false,
        "vanished": false,
        "afk": false,
        "staffMode": false,
        "lastSeen": 1780656400000
      }
    ],
    "pagination": {
      "page": 1,
      "pageSize": 50,
      "total": 2,
      "totalPages": 1
    }
  }
}
```

## 9. WebSocket Endpoints (Real-time Data)

Base WebSocket URL: `ws://<server-ip>:7890`

Semua koneksi WebSocket memerlukan autentikasi via query parameter `token`.
Format: `ws://<server-ip>:7890/ws/server?token=<keyId.rawSecret>`

**Config Master Switch:** `features.websocket.enabled` (default: `true`)
**Config Per-Endpoint:** `features.websocket.endpoints.<name>` (default: `true`)

---

### `ws://<server-ip>:7890/ws/server` — Real-time Server Stats

**Scope:** `read:server` | **Config:** `features.websocket.endpoints.server-stats`

Mengirimkan full server status setiap 1 detik.

**Subscribe:**
```javascript
const ws = new WebSocket('ws://localhost:7890/ws/server?token=napi_xxx');
ws.onmessage = (e) => {
  const msg = JSON.parse(e.data);
  console.log('TPS:', msg.data.tps.now, 'Players:', msg.data.players.online);
};
```

**Message (setiap ~1 detik):**
```json
{
  "type": "stats",
  "timestamp": 1780742286966,
  "data": {
    "online": true,
    "version": "1.21.11",
    "platform": "Paper",
    "motd": "A Minecraft Server",
    "tps": { "now": 20.0, "1m": 20.0, "5m": 19.95, "15m": 19.98 },
    "players": { "online": 2, "visible": 2, "vanished": 0, "max": 70 },
    "mspt": 6.82,
    "uptime": 1464,
    "ram": { "usedMB": 2043, "freeMB": 6148, "maxMB": 8192 },
    "javaVersion": "25.0.3",
    "osName": "Linux",
    "worlds": ["world", "world_nether", "world_the_end", "flat", "schoolmap"]
  }
}
```

---

### `ws://<server-ip>:7890/ws/players` — Player Join/Leave Events

**Scope:** `read:players` | **Config:** `features.websocket.endpoints.player-events`

Mendapatkan event real-time saat player join dan leave.

**Player Join:**
```json
{
  "type": "join",
  "timestamp": 1780742286966,
  "data": {
    "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
    "username": "AdityaOkeGas4",
    "displayName": "AdityaOkeGas4",
    "online": true,
    "vanished": false,
    "afk": false,
    "staffMode": false
  }
}
```

**Player Leave:**
```json
{
  "type": "leave",
  "timestamp": 1780742286966,
  "data": {
    "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
    "username": "AdityaOkeGas4"
  }
}
```

---

### `ws://<server-ip>:7890/ws/chat` — Real-time Chat Messages

**Scope:** `read:server` | **Config:** `features.websocket.endpoints.chat`

Mendapatkan semua chat message dari server secara real-time.

**Message:**
```json
{
  "type": "message",
  "timestamp": 1780742286966,
  "data": {
    "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
    "username": "AdityaOkeGas4",
    "displayName": "AdityaOkeGas4",
    "message": "Halo semuanya!",
    "format": "<AdityaOkeGas4> Halo semuanya!"
  }
}
```

---

### `ws://<server-ip>:7890/ws/player/{uuid}` — Individual Player Detail

**Scope:** `read:players` | **Config:** `features.websocket.endpoints.player-detail`

Mengirimkan full player snapshot saat connect. Client bisa kirim `ping` untuk keep-alive.

**Initial snapshot (diterima saat connect):**
```json
{
  "type": "snapshot",
  "timestamp": 1780742286966,
  "data": {
    "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
    "username": "AdityaOkeGas4",
    "displayName": "AdityaOkeGas4",
    "online": true,
    "location": { "world": "world", "x": 3525.6, "y": 68.0, "z": 4069.1, "yaw": 0.0, "pitch": 0.0 },
    "health": 20.0, "maxHealth": 20.0, "foodLevel": 20, "saturation": 5.0,
    "expLevel": 0, "expProgress": 0.0, "totalExp": 0,
    "gamemode": "SURVIVAL", "ping": 3
  }
}
```

---

### WebSocket Error Codes

| Close Code | Deskripsi |
|---|---|
| `4001` | Token tidak ditemukan (missing `token` query parameter) |
| `4003` | Token invalid atau scope tidak mencukupi |
| `1001` | Server shutting down |

## 3. Player Endpoints

Tested using active player: **AdityaOkeGas4** (`eea40d5a-6e98-3de2-9a0d-631505df935f`)

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f` (Full player data by UUID)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f
```
**Status:** `200` | **Latency:** `5103ms`
**Response:**
```json
{
  "data": {
    "country": "Singapore",
    "city": "Singapore",
    "displayName": "AdityaOkeGas4",
    "ping": 3,
    "isp": "Oracle Corporation",
    "totalPlaytimeMs": 11371950,
    "locale": "en",
    "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
    "gamemode": "SURVIVAL",
    "saturation": 5.0,
    "firstJoin": 1780588599806,
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
        "lastSeen": 1780741132632,
        "city": "Singapore",
        "firstSeen": 1780741132632,
        "isp": "Oracle Corporation",
        "ipAddress": "140.245.116.11",
        "region": "South East",
        "asn": "AS31898 Oracle Corporation"
      }
    ],
    "vanished": false,
    "afk": true,
    "ipAddress": "140.245.116.11",
    "health": 20.0,
    "expProgress": 0.0,
    "totalExp": 0,
    "staffMode": false,
    "clientBrand": "vanilla",
    "lastSeen": 1780656473635,
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
      "x": 3525.6085005501955,
      "y": 68.0,
      "z": 4069.1164896734786,
      "pitch": 0.0,
      "yaw": 0.0
    },
    "foodLevel": 20,
    "region": "South East",
    "asn": "AS31898 Oracle Corporation",
    "username": "AdityaOkeGas4"
  },
  "success": true,
  "timestamp": 1780742354669
}
```

### `GET /players/name/AdityaOkeGas4` (Full player data by Username)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/name/AdityaOkeGas4
```
**Status:** `200` | **Latency:** `5099ms`
**Response:**
```json
{
  "data": {
    "country": "Singapore",
    "city": "Singapore",
    "displayName": "AdityaOkeGas4",
    "ping": 3,
    "isp": "Oracle Corporation",
    "totalPlaytimeMs": 11377250,
    "locale": "en",
    "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
    "gamemode": "SURVIVAL",
    "saturation": 5.0,
    "firstJoin": 1780588599806,
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
        "lastSeen": 1780741132632,
        "city": "Singapore",
        "firstSeen": 1780741132632,
        "isp": "Oracle Corporation",
        "ipAddress": "140.245.116.11",
        "region": "South East",
        "asn": "AS31898 Oracle Corporation"
      }
    ],
    "vanished": false,
    "afk": true,
    "ipAddress": "140.245.116.11",
    "health": 20.0,
    "expProgress": 0.0,
    "totalExp": 0,
    "staffMode": false,
    "clientBrand": "vanilla",
    "lastSeen": 1780656473635,
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
      "x": 3525.6085005501955,
      "y": 68.0,
      "z": 4069.1164896734786,
      "pitch": 0.0,
      "yaw": 0.0
    },
    "foodLevel": 20,
    "region": "South East",
    "asn": "AS31898 Oracle Corporation",
    "username": "AdityaOkeGas4"
  },
  "success": true,
  "timestamp": 1780742359969
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/location` (Player Location)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/location
```
**Status:** `200` | **Latency:** `5095ms`
**Response:**
```json
{
  "data": {
    "world": "world",
    "x": 3525.6085005501955,
    "y": 68.0,
    "z": 4069.1164896734786,
    "pitch": 0.0,
    "yaw": 0.0
  },
  "success": true,
  "timestamp": 1780742365265
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/health` (Player Health & Food)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/health
```
**Status:** `200` | **Latency:** `5099ms`
**Response:**
```json
{
  "data": {
    "saturation": 5.0,
    "health": 20.0,
    "maxHealth": 20.0,
    "foodLevel": 20
  },
  "success": true,
  "timestamp": 1780742370565
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/experience` (Player Experience)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/experience
```
**Status:** `200` | **Latency:** `5098ms`
**Response:**
```json
{
  "data": {
    "expLevel": 0,
    "expProgress": 0.0,
    "totalExp": 0
  },
  "success": true,
  "timestamp": 1780742375865
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/gamemode` (Player Gamemode)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/gamemode
```
**Status:** `200` | **Latency:** `5099ms`
**Response:**
```json
{
  "data": "SURVIVAL",
  "success": true,
  "timestamp": 1780742381165
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/inventory` (Full Inventory)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/inventory
```
**Status:** `200` | **Latency:** `5104ms`
**Response:**
```json
{
  "data": [],
  "success": true,
  "timestamp": 1780742386465
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/inventory/hotbar` (Hotbar Inventory (slots 0-8))
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/inventory/hotbar
```
**Status:** `200` | **Latency:** `5094ms`
**Response:**
```json
{
  "data": [],
  "success": true,
  "timestamp": 1780742391765
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/inventory/armor` (Armor Slots)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/inventory/armor
```
**Status:** `200` | **Latency:** `5099ms`
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
  "timestamp": 1780742397065
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/inventory/offhand` (Offhand Item)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/inventory/offhand
```
**Status:** `200` | **Latency:** `5099ms`
**Response:**
```json
{
  "data": null,
  "success": true,
  "timestamp": 1780742402365
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/effects` (Active Potion Effects)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/effects
```
**Status:** `200` | **Latency:** `5248ms`
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
  "timestamp": 1780742407715
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/skin` (Player Skin texture metadata)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/skin
```
**Status:** `200` | **Latency:** `5396ms`
**Response:**
```json
{
  "data": {},
  "success": true,
  "timestamp": 1780742413374
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/ping` (Player Connection Ping)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/ping
```
**Status:** `200` | **Latency:** `5104ms`
**Response:**
```json
{
  "data": 3,
  "success": true,
  "timestamp": 1780742418715
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/network` (Detailed Network, IP, & Geolocation History)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/network
```
**Status:** `200` | **Latency:** `5444ms`
**Response:**
```json
{
  "data": {
    "clientBrand": "vanilla",
    "ipHistory": [
      {
        "country": "Singapore",
        "lastSeen": 1780741132632,
        "city": "Singapore",
        "firstSeen": 1780741132632,
        "isp": "Oracle Corporation",
        "ipAddress": "140.245.116.11",
        "region": "South East",
        "asn": "AS31898 Oracle Corporation"
      }
    ],
    "country": "Singapore",
    "city": "Singapore",
    "ping": 3,
    "isp": "Oracle Corporation",
    "ipAddress": "140.245.116.11",
    "locale": "en",
    "region": "South East",
    "asn": "AS31898 Oracle Corporation"
  },
  "success": true,
  "timestamp": 1780742424219
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/stats` (Player Playtime & Seen Statistics)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/stats
```
**Status:** `200` | **Latency:** `5216ms`
**Response:**
```json
{
  "data": {
    "firstJoin": 1780588599806,
    "lastSeen": 1780656473635,
    "totalPlaytimeMs": 11446950
  },
  "success": true,
  "timestamp": 1780742429665
}
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/vault` (Vault Integration Data)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/vault
```
**Status:** `404` | **Latency:** `5088ms`
**Response:**
```json
Endpoint GET /api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/vault not found
```

### `GET /players/eea40d5a-6e98-3de2-9a0d-631505df935f/luckperms` (LuckPerms Integration Data)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/luckperms
```
**Status:** `404` | **Latency:** `5078ms`
**Response:**
```json
Endpoint GET /api/v1/players/eea40d5a-6e98-3de2-9a0d-631505df935f/luckperms not found
```

## 4. Offline/Database Player Endpoints

### `GET /players/offline/eea40d5a-6e98-3de2-9a0d-631505df935f` (Offline Player Database Snapshot by UUID)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/offline/eea40d5a-6e98-3de2-9a0d-631505df935f
```
**Status:** `200` | **Latency:** `5120ms`
**Response:**
```json
{
  "data": {
    "country": "Singapore",
    "city": "Singapore",
    "displayName": "AdityaOkeGas4",
    "ping": 3,
    "isp": "Oracle Corporation",
    "totalPlaytimeMs": 11462950,
    "locale": "en",
    "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
    "gamemode": "SURVIVAL",
    "saturation": 5.0,
    "firstJoin": 1780588599806,
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
        "lastSeen": 1780741132632,
        "city": "Singapore",
        "firstSeen": 1780741132632,
        "isp": "Oracle Corporation",
        "ipAddress": "140.245.116.11",
        "region": "South East",
        "asn": "AS31898 Oracle Corporation"
      }
    ],
    "vanished": false,
    "afk": true,
    "ipAddress": "140.245.116.11",
    "health": 20.0,
    "expProgress": 0.0,
    "totalExp": 0,
    "staffMode": false,
    "clientBrand": "vanilla",
    "lastSeen": 1780656473635,
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
      "x": 3525.6085005501955,
      "y": 68.0,
      "z": 4069.1164896734786,
      "pitch": 0.0,
      "yaw": 0.0
    },
    "foodLevel": 20,
    "region": "South East",
    "asn": "AS31898 Oracle Corporation",
    "username": "AdityaOkeGas4"
  },
  "success": true,
  "timestamp": 1780742445669
}
```

### `GET /players/offline/name/AdityaOkeGas4` (Offline Player Database Snapshot by Username)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/players/offline/name/AdityaOkeGas4
```
**Status:** `200` | **Latency:** `5100ms`
**Response:**
```json
{
  "data": {
    "country": "Singapore",
    "city": "Singapore",
    "displayName": "AdityaOkeGas4",
    "ping": 3,
    "isp": "Oracle Corporation",
    "totalPlaytimeMs": 11468250,
    "locale": "en",
    "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
    "gamemode": "SURVIVAL",
    "saturation": 5.0,
    "firstJoin": 1780588599806,
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
        "lastSeen": 1780741132632,
        "city": "Singapore",
        "firstSeen": 1780741132632,
        "isp": "Oracle Corporation",
        "ipAddress": "140.245.116.11",
        "region": "South East",
        "asn": "AS31898 Oracle Corporation"
      }
    ],
    "vanished": false,
    "afk": true,
    "ipAddress": "140.245.116.11",
    "health": 20.0,
    "expProgress": 0.0,
    "totalExp": 0,
    "staffMode": false,
    "clientBrand": "vanilla",
    "lastSeen": 1780656473635,
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
      "x": 3525.6085005501955,
      "y": 68.0,
      "z": 4069.1164896734786,
      "pitch": 0.0,
      "yaw": 0.0
    },
    "foodLevel": 20,
    "region": "South East",
    "asn": "AS31898 Oracle Corporation",
    "username": "AdityaOkeGas4"
  },
  "success": true,
  "timestamp": 1780742450969
}
```

## 5. World Endpoints

### `GET /worlds` (List loaded worlds)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds
```
**Status:** `200` | **Latency:** `5094ms`
**Response:**
```json
{
  "data": [
    {
      "difficulty": "EASY",
      "environment": "NORMAL",
      "seed": 6641214828919989498,
      "entityCount": 344,
      "playerCount": 2,
      "name": "world",
      "weather": "THUNDER",
      "fullTime": 14222105,
      "time": 14105,
      "pvp": true,
      "loadedChunks": 1250
    },
    {
      "difficulty": "EASY",
      "environment": "NETHER",
      "seed": 6641214828919989498,
      "entityCount": 0,
      "playerCount": 0,
      "name": "world_nether",
      "weather": "CLEAR",
      "fullTime": 15630536,
      "time": 6536,
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
      "fullTime": 15630536,
      "time": 6536,
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
      "weather": "THUNDER",
      "fullTime": 7023409,
      "time": 15409,
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
  "timestamp": 1780742456266
}
```

### `GET /worlds/world` (World Overview)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world
```
**Status:** `200` | **Latency:** `5100ms`
**Response:**
```json
{
  "data": {
    "difficulty": "EASY",
    "environment": "NORMAL",
    "seed": 6641214828919989498,
    "entityCount": 344,
    "playerCount": 2,
    "name": "world",
    "weather": "THUNDER",
    "fullTime": 14222207,
    "time": 14207,
    "pvp": true,
    "loadedChunks": 1250
  },
  "success": true,
  "timestamp": 1780742461366
}
```

### `GET /worlds/world/time` (World In-game Time)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/time
```
**Status:** `200` | **Latency:** `5098ms`
**Response:**
```json
{
  "data": {
    "dayCount": 592,
    "fullTime": 14222313,
    "time": 14313
  },
  "success": true,
  "timestamp": 1780742466665
}
```

### `GET /worlds/world/weather` (World Weather)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/weather
```
**Status:** `200` | **Latency:** `2699ms`
**Response:**
```json
{
  "data": "CLEAR",
  "success": true,
  "timestamp": 1780742469565
}
```

### `GET /worlds/world/players` (Players in World)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/players
```
**Status:** `200` | **Latency:** `5099ms`
**Response:**
```json
{
  "data": [
    {
      "staffMode": false,
      "vanished": false,
      "displayName": "AdityaOkeGas4",
      "afk": true,
      "online": true,
      "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f",
      "username": "AdityaOkeGas4"
    },
    {
      "staffMode": false,
      "vanished": false,
      "displayName": "AdityaOkeGas3",
      "afk": true,
      "online": true,
      "uuid": "75ed88b3-eb5c-3e45-a763-c3413042883d",
      "username": "AdityaOkeGas3"
    }
  ],
  "success": true,
  "timestamp": 1780742474866
}
```

### `GET /worlds/world/entities` (Entity Type Counters)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/entities
```
**Status:** `200` | **Latency:** `5149ms`
**Response:**
```json
{
  "data": {
    "ENDERMAN": 2,
    "CHEST_MINECART": 15,
    "FROG": 26,
    "SHEEP": 24,
    "SPIDER": 6,
    "SKELETON": 5,
    "VILLAGER": 18,
    "POLAR_BEAR": 5,
    "CREEPER": 5,
    "CHICKEN": 50,
    "GLOW_SQUID": 10,
    "BEE": 3,
    "SQUID": 5,
    "HORSE": 13,
    "FALLING_BLOCK": 16,
    "COW": 23,
    "FOX": 6,
    "PIG": 28,
    "PLAYER": 2,
    "TEXT_DISPLAY": 2,
    "BAT": 2,
    "SALMON": 5,
    "ZOMBIE": 14,
    "CAT": 7,
    "RABBIT": 36,
    "WOLF": 13,
    "IRON_GOLEM": 2
  },
  "success": true,
  "timestamp": 1780742480216
}
```

### `GET /worlds/world/chunks` (Loaded Chunks Count)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/chunks
```
**Status:** `200` | **Latency:** `5098ms`
**Response:**
```json
{
  "data": 1250,
  "success": true,
  "timestamp": 1780742485516
}
```

### `GET /worlds/world/border` (World Border status)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/border
```
**Status:** `200` | **Latency:** `5100ms`
**Response:**
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
  "timestamp": 1780742490815
}
```

### `GET /worlds/world/gamerules` (World Gamerules Map)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/worlds/world/gamerules
```
**Status:** `200` | **Latency:** `5102ms`
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
    "max_block_modifications": 32768,
    "locator_bar": false,
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
  "timestamp": 1780742496117
}
```

## 6. Leaderboard Endpoints

### `GET /leaderboard/playtime` (Default limit)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/leaderboard/playtime
```
**Status:** `404` | **Latency:** `5118ms`
**Response:**
```json
Endpoint GET /api/v1/leaderboard/playtime not found
```

### `GET /leaderboard/playtime?limit=1` (Query param limit=1)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/leaderboard/playtime?limit=1
```
**Status:** `404` | **Latency:** `5067ms`
**Response:**
```json
Endpoint GET /api/v1/leaderboard/playtime not found
```

### `GET /leaderboard/balance` (Default limit)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/leaderboard/balance
```
**Status:** `404` | **Latency:** `5066ms`
**Response:**
```json
Endpoint GET /api/v1/leaderboard/balance not found
```

### `GET /leaderboard/balance?limit=1` (Query param limit=1)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/leaderboard/balance?limit=1
```
**Status:** `404` | **Latency:** `5070ms`
**Response:**
```json
Endpoint GET /api/v1/leaderboard/balance not found
```

### `GET /leaderboard/kills` (Default limit)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/leaderboard/kills
```
**Status:** `404` | **Latency:** `5068ms`
**Response:**
```json
Endpoint GET /api/v1/leaderboard/kills not found
```

### `GET /leaderboard/kills?limit=1` (Query param limit=1)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/leaderboard/kills?limit=1
```
**Status:** `404` | **Latency:** `5062ms`
**Response:**
```json
Endpoint GET /api/v1/leaderboard/kills not found
```

### `GET /leaderboard/deaths` (Default limit)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/leaderboard/deaths
```
**Status:** `404` | **Latency:** `5092ms`
**Response:**
```json
Endpoint GET /api/v1/leaderboard/deaths not found
```

### `GET /leaderboard/deaths?limit=1` (Query param limit=1)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/leaderboard/deaths?limit=1
```
**Status:** `404` | **Latency:** `5068ms`
**Response:**
```json
Endpoint GET /api/v1/leaderboard/deaths not found
```

### `GET /leaderboard/votes` (Default limit)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/leaderboard/votes
```
**Status:** `404` | **Latency:** `5063ms`
**Response:**
```json
Endpoint GET /api/v1/leaderboard/votes not found
```

### `GET /leaderboard/votes?limit=1` (Query param limit=1)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/leaderboard/votes?limit=1
```
**Status:** `404` | **Latency:** `5066ms`
**Response:**
```json
Endpoint GET /api/v1/leaderboard/votes not found
```

### `GET /leaderboard/exp` (Default limit)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/leaderboard/exp
```
**Status:** `404` | **Latency:** `5106ms`
**Response:**
```json
Endpoint GET /api/v1/leaderboard/exp not found
```

### `GET /leaderboard/exp?limit=1` (Query param limit=1)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/leaderboard/exp?limit=1
```
**Status:** `404` | **Latency:** `5067ms`
**Response:**
```json
Endpoint GET /api/v1/leaderboard/exp not found
```

## 7. PlaceholderAPI Evaluation Endpoint

### `POST /papi/evaluate` (Evaluate list of PAPI placeholders)
**Curl Example:**
```bash
curl -X POST -H "Authorization: Bearer <masked>" -d '{"player": "AdityaOkeGas4", "placeholders": ["%player_name%", "%player_health%", "%vault_group%"]}' http://natural.nodevoid.my.id:19133/api/v1/papi/evaluate
```
**Status:** `200` | **Latency:** `5117ms`
**Response:**
```json
{
  "data": {
    "%player_name%": "AdityaOkeGas4",
    "%vault_group%": "default",
    "%player_health%": "20.0"
  },
  "success": true,
  "timestamp": 1780742563566
}
```

## 8. Admin Endpoints

### `GET /admin/health` (Health Check)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/admin/health
```
**Status:** `200` | **Latency:** `5066ms`
**Response:**
```json
{
  "data": {
    "status": "ok"
  },
  "success": true,
  "timestamp": 1780742568633
}
```

### `GET /admin/keys` (List API Keys)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/admin/keys
```
**Status:** `200` | **Latency:** `5078ms`
**Response:**
```json
{
  "data": [
    {
      "expires_at": null,
      "last_used": 1780721828768,
      "name": "swagger",
      "created_at": 1780664051503,
      "id": "713f6c25-a166-43e9-b4c6-a528e3b56427",
      "scopes": "*",
      "enabled": true
    },
    {
      "expires_at": null,
      "last_used": 1780742563565,
      "name": "swagger",
      "created_at": 1780723316823,
      "id": "9bdfa38f-171c-4351-84f4-dd0d55cdcba2",
      "scopes": "*",
      "enabled": true
    }
  ],
  "success": true,
  "timestamp": 1780742573912
}
```

### `GET /admin/config` (Plugin Configuration)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/admin/config
```
**Status:** `200` | **Latency:** `5073ms`
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
      "skin": {
        "cache-ttl-hours": 6
      },
      "vanish": {
        "show-vanished-in-count": false,
        "show-vanished-in-list": false
      },
      "snapshot": {
        "interval-minutes": 1,
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
      "swagger-auth-required": false
    },
    "logging": {
      "log-requests": true,
      "log-level": "INFO",
      "log-auth-failures": true
    }
  },
  "success": true,
  "timestamp": 1780742579185
}
```

### `GET /admin/rate-limits` (Rate Limit Statistics)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/admin/rate-limits
```
**Status:** `501` | **Latency:** `5082ms`
**Response:**
```json
{
  "success": false,
  "error": {
    "code": "NOT_IMPLEMENTED",
    "message": "Rate limit viewing not implemented yet.",
    "timestamp": 1780742584467
  }
}
```

### `GET /admin/snapshot/history` (Snapshot Execution History)
**Curl Example:**
```bash
curl -X GET -H "Authorization: Bearer <masked>" http://natural.nodevoid.my.id:19133/api/v1/admin/snapshot/history
```
**Status:** `501` | **Latency:** `4876ms`
**Response:**
```json
{
  "success": false,
  "error": {
    "code": "NOT_IMPLEMENTED",
    "message": "Snapshot history not implemented yet.",
    "timestamp": 1780742589546
  }
}
```
