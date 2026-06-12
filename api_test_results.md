# NaturalAPI Endpoint Test Results
Generated on: 2026-06-12 13:34:47
Target API: `https://napi.aikeigroup.net/api/v1`

## Summary Table

| Endpoint Name | Method | Path | Status | Result |
|---|---|---|---|---|
| Server Status | `GET` | `/server` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [Go to Details](#server-status) |
| Server Plugins | `GET` | `/server/plugins` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [Go to Details](#server-plugins) |
| Online Players Summary | `GET` | `/players` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [Go to Details](#online-players-summary) |
| All Players (Paged) | `GET` | `/players/all` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [Go to Details](#all-players-paged) |
| Online Player Details (UUID) | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [Go to Details](#online-player-details-uuid) |
| Offline Player Details (UUID) | `GET` | `/players/9c045595-a5da-4284-9d9e-c3603f86432b` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [Go to Details](#offline-player-details-uuid) |
| Online Player Stats | `GET` | `/players/de11f3ca-003f-3713-b771-62bc481dbfca/stats` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [Go to Details](#online-player-stats) |
| Offline Player Stats | `GET` | `/players/9c045595-a5da-4284-9d9e-c3603f86432b/stats` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [Go to Details](#offline-player-stats) |
| Offline Player Snapshot | `GET` | `/players/offline/9c045595-a5da-4284-9d9e-c3603f86432b` | ![Status 200](https://img.shields.io/badge/Status-200-green) | [Go to Details](#offline-player-snapshot) |

---

### Server Status
- **Method:** `GET`
- **URL:** `https://napi.aikeigroup.net/api/v1/server`
- **Status Code:** `200`

Response:
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
    "uptime": 63,
    "system": {
      "availableProcessors": 6,
      "systemLoadAverage": 0.6,
      "processCpuLoad": 1.0,
      "systemCpuLoad": 1.12,
      "storage": {
        "totalBytes": 126716452864,
        "usableBytes": 59105251328,
        "freeBytes": 64337862656
      },
      "network": {
        "rxBytesPerSec": 2638,
        "txBytesTotal": 435628,
        "txBytesPerSec": 21261,
        "rxBytesTotal": 36406066
      }
    },
    "tps": {
      "now": 20.0,
      "5m": 20.0,
      "15m": 20.0,
      "1m": 20.0
    },
    "online": true,
    "mspt": 0.34253849504950495,
    "ram": {
      "maxMB": 8192,
      "freeMB": 6581,
      "usedMB": 1610
    }
  },
  "success": true,
  "timestamp": 1781246087716
}
```

[Back to Summary](#summary-table)

---

### Server Plugins
- **Method:** `GET`
- **URL:** `https://napi.aikeigroup.net/api/v1/server/plugins`
- **Status Code:** `200`

Response:
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
      "version": "1.0.5"
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
  "timestamp": 1781246087815
}
```

[Back to Summary](#summary-table)

---

### Online Players Summary
- **Method:** `GET`
- **URL:** `https://napi.aikeigroup.net/api/v1/players`
- **Status Code:** `200`

Response:
```json
{
  "data": [],
  "success": true,
  "timestamp": 1781246093118
}
```

[Back to Summary](#summary-table)

---

### All Players (Paged)
- **Method:** `GET`
- **URL:** `https://napi.aikeigroup.net/api/v1/players/all`
- **Status Code:** `200`

Response:
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
  "timestamp": 1781246093174
}
```

[Back to Summary](#summary-table)

---

### Online Player Details (UUID)
- **Method:** `GET`
- **URL:** `https://napi.aikeigroup.net/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca`
- **Status Code:** `200`

Response:
```json
{
  "data": {
    "country": "Singapore",
    "city": "Singapore",
    "displayName": "AdityaOkeGas2",
    "ping": 3,
    "isp": "Oracle Corporation",
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
    "school": null,
    "isOp": false,
    "isBanned": false,
    "maxHealth": 20.0,
    "exp": 0.0,
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
    "firstPlayed": 1780588217858,
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
  "timestamp": 1781246093273
}
```

[Back to Summary](#summary-table)

---

### Offline Player Details (UUID)
- **Method:** `GET`
- **URL:** `https://napi.aikeigroup.net/api/v1/players/9c045595-a5da-4284-9d9e-c3603f86432b`
- **Status Code:** `200`

Response:
```json
{
  "data": {
    "country": "Indonesia",
    "city": "Ungaran",
    "displayName": "Daichi_keii",
    "ping": 35,
    "isp": "PT Lintas Data Prima",
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
    "school": null,
    "isOp": true,
    "isBanned": false,
    "maxHealth": 20.0,
    "exp": 0.111111,
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
    "firstPlayed": 1780386089704,
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
  "timestamp": 1781246093373
}
```

[Back to Summary](#summary-table)

---

### Online Player Stats
- **Method:** `GET`
- **URL:** `https://napi.aikeigroup.net/api/v1/players/de11f3ca-003f-3713-b771-62bc481dbfca/stats`
- **Status Code:** `200`

Response:
```json
{
  "data": {
    "firstJoin": 1780588217858,
    "lastSeen": 1781245988658,
    "totalPlaytimeMs": null
  },
  "success": true,
  "timestamp": 1781246093472
}
```

[Back to Summary](#summary-table)

---

### Offline Player Stats
- **Method:** `GET`
- **URL:** `https://napi.aikeigroup.net/api/v1/players/9c045595-a5da-4284-9d9e-c3603f86432b/stats`
- **Status Code:** `200`

Response:
```json
{
  "data": {
    "firstJoin": 1780386089704,
    "lastSeen": 1781194551166,
    "totalPlaytimeMs": null
  },
  "success": true,
  "timestamp": 1781246093572
}
```

[Back to Summary](#summary-table)

---

### Offline Player Snapshot
- **Method:** `GET`
- **URL:** `https://napi.aikeigroup.net/api/v1/players/offline/9c045595-a5da-4284-9d9e-c3603f86432b`
- **Status Code:** `200`

Response:
```json
{
  "data": {
    "country": "Indonesia",
    "city": "Ungaran",
    "displayName": "Daichi_keii",
    "ping": 35,
    "isp": "PT Lintas Data Prima",
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
    "school": null,
    "isOp": true,
    "isBanned": false,
    "maxHealth": 20.0,
    "exp": 0.111111,
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
    "firstPlayed": 1780386089704,
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
  "timestamp": 1781246093672
}
```

[Back to Summary](#summary-table)

---
