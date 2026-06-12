# Player Endpoints

> **Base URL:** `http://<server-ip>:7890/api/v1`
> **Authentication:** Bearer Token — `Authorization: Bearer <api-key>`
> **Required Scope:** `read:players`

All Player endpoints return data about Minecraft players on the server. Responses follow a consistent envelope format.

> [!NOTE]
> All player lookups (`/players/{uuid}`, `/players/name/{username}`) and their sub-resource endpoints (e.g. location, health, inventory, effects) **automatically fall back** to the last saved database snapshot if the player is currently offline. This provides a unified API interface for querying both online and offline players.

---

## Table of Contents

- [Response Format](#response-format)
- [Error Format](#error-format)
- [Common Error Responses](#common-error-responses)
- [Item Serialization Format](#item-serialization-format)
- [Endpoints](#endpoints)
  - [List Online Players](#1-list-online-players)
  - [List All Players](#11-list-all-players)
  - [Get Player by UUID](#2-get-player-by-uuid)
  - [Get Player by Username](#3-get-player-by-username)
  - [Get Player Location](#4-get-player-location)
  - [Get Player Health](#5-get-player-health)
  - [Get Player Experience](#6-get-player-experience)
  - [Get Player Gamemode](#7-get-player-gamemode)
  - [Get Player Inventory](#8-get-player-inventory)
  - [Get Player Hotbar](#9-get-player-hotbar)
  - [Get Player Armor](#10-get-player-armor)
  - [Get Player Offhand](#11-get-player-offhand)
  - [Get Player Effects](#12-get-player-effects)
  - [Get Player Skin](#13-get-player-skin)
  - [Get Player Ping](#14-get-player-ping)
  - [Get Player Network Info](#15-get-player-network-info)
  - [Get Player Stats](#16-get-player-stats)
  - [Get Player Vault Data](#17-get-player-vault-data)
  - [Get Player LuckPerms Data](#18-get-player-luckperms-data)
  - [Get Offline Player by UUID](#19-get-offline-player-by-uuid)
  - [Get Offline Player by Username](#20-get-offline-player-by-username)

---

## Response Format

All successful responses are wrapped in a standard envelope:

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    // ... endpoint-specific payload
  }
}
```

| Field       | Type    | Description                                      |
|-------------|---------|--------------------------------------------------|
| `success`   | boolean | Always `true` for successful responses.          |
| `timestamp` | long    | Unix epoch timestamp in milliseconds.             |
| `data`      | object  | The endpoint-specific response payload.           |

---

## Error Format

All error responses are wrapped in a standard envelope:

```json
{
  "success": false,
  "error": {
    "code": "ERROR_CODE",
    "message": "Human-readable description of the error.",
    "timestamp": 1717600000000
  }
}
```

| Field             | Type    | Description                                           |
|-------------------|---------|-------------------------------------------------------|
| `success`         | boolean | Always `false` for error responses.                   |
| `error.code`      | string  | Machine-readable error code (e.g. `PLAYER_NOT_FOUND`).|
| `error.message`   | string  | Human-readable error description.                     |
| `error.timestamp` | long    | Unix epoch timestamp in milliseconds.                 |

---

## Common Error Responses

The following errors may be returned by **any** Player endpoint:

### `401 Unauthorized` — Missing or Invalid Token

Returned when the `Authorization` header is missing, malformed, or contains an invalid API key.

```json
{
  "success": false,
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Missing or invalid API key.",
    "timestamp": 1717600000000
  }
}
```

### `403 Forbidden` — Insufficient Scope

Returned when the API key is valid but does not have the `read:players` scope.

```json
{
  "success": false,
  "error": {
    "code": "FORBIDDEN",
    "message": "API key does not have the required scope: read:players",
    "timestamp": 1717600000000
  }
}
```

### `404 Not Found` — Player Not Found

Returned when the target player (by UUID or username) cannot be found on the server.

```json
{
  "success": false,
  "error": {
    "code": "PLAYER_NOT_FOUND",
    "message": "No online player found with the specified identifier.",
    "timestamp": 1717600000000
  }
}
```

### `429 Too Many Requests` — Rate Limit Exceeded

Returned when the client has exceeded the configured rate limit.

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Rate limit exceeded. Try again later.",
    "timestamp": 1717600000000
  }
}
```

### `500 Internal Server Error`

Returned when an unexpected error occurs on the server.

```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_ERROR",
    "message": "An unexpected internal error occurred.",
    "timestamp": 1717600000000
  }
}
```

---

## Item Serialization Format

Inventory-related endpoints serialize each item slot into a consistent JSON object. Empty slots are represented as `null`.

```json
{
  "slot": 0,
  "material": "DIAMOND_SWORD",
  "amount": 1,
  "displayName": "§6Excalibur",
  "lore": ["§7Legendary sword"],
  "damage": 0,
  "enchantments": {
    "SHARPNESS": 5,
    "UNBREAKING": 3
  }
}
```

| Field           | Type              | Description                                                                 |
|-----------------|-------------------|-----------------------------------------------------------------------------|
| `slot`          | integer           | The inventory slot index (0–35 for main inventory, named keys for armor).   |
| `material`      | string            | Bukkit `Material` enum name (e.g. `DIAMOND_SWORD`, `AIR`).                 |
| `amount`        | integer           | Stack size (1–64).                                                          |
| `displayName`   | string \| null    | Custom display name with `§` color codes, or `null` if not renamed.         |
| `lore`          | string[] \| null  | Array of lore lines with `§` color codes, or `null` if no lore is set.      |
| `damage`        | integer           | Current durability damage (0 = undamaged). Only meaningful for tools/armor. |
| `enchantments`  | object \| null    | Map of enchantment name → level, or `null` if no enchantments are applied.  |

> [!NOTE]
> Material names follow the Bukkit `Material` enum and may differ between Minecraft versions. `displayName` and `lore` use the legacy `§` color-code format.

---

## Endpoints

---

### 1. List Online Players

Retrieves a list of all currently online players. By default, vanished players (e.g. via VanishNoPacket, SuperVanish, PremiumVanish) are **excluded** from the results.

| Property   | Value                   |
|------------|-------------------------|
| **Method** | `GET`                   |
| **Path**   | `/players`              |
| **Auth**   | Bearer Token            |
| **Scope**  | `read:players`          |

#### Query Parameters

| Parameter          | Type    | Default | Description                                                         |
|--------------------|---------|---------|---------------------------------------------------------------------|
| `includeVanished`  | boolean | `false` | Set to `true` to include vanished players in the response.          |

#### curl Example

```bash
# List online players (excluding vanished)
curl -X GET "http://localhost:7890/api/v1/players" \
  -H "Authorization: Bearer your-api-key-here"

# List all online players (including vanished)
curl -X GET "http://localhost:7890/api/v1/players?includeVanished=true" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": [
    {
      "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5",
      "username": "Notch",
      "displayName": "§6Notch",
      "online": true,
      "vanished": false,
      "afk": false,
      "staffMode": false
    },
    {
      "uuid": "61699b2e-d327-4a01-9f1e-0ea8c3f06bc6",
      "username": "Dinnerbone",
      "displayName": "Dinnerbone",
      "online": true,
      "vanished": false,
      "afk": true,
      "staffMode": false
    },
    {
      "uuid": "853c80ef-3c37-49fd-aa49-938b674adae6",
      "username": "jeb_",
      "displayName": "§bjeb_",
      "online": true,
      "vanished": false,
      "afk": false,
      "staffMode": true
    }
  ]
}

---

### 1.1. List All Players

Retrieves a paginated list of all players (both online and offline) who have joined the server. Online players are prioritized in sorting, followed by offline players sorted by their last seen timestamp.

| Property   | Value                   |
|------------|-------------------------|
| **Method** | `GET`                   |
| **Path**   | `/players/all`          |
| **Auth**   | Bearer Token            |
| **Scope**  | `read:players`          |

#### Query Parameters

| Parameter         | Type    | Default | Description                                                                          |
|-------------------|---------|---------|--------------------------------------------------------------------------------------|
| `page`            | integer | `1`     | Page number to retrieve (1-indexed).                                                 |
| `pageSize`        | integer | `50`    | Number of players per page. Min: 1, Max: 100.                                        |
| `search`          | string  | —       | Filter players by username or displayName (case-insensitive, partial match).         |
| `status`          | string  | `all`   | Filter by status: `all` (default), `online`, or `offline`.                           |
| `includeVanished` | boolean | `false` | Set to `true` to include online players who are currently vanished in the response.  |

#### curl Examples

```bash
# All players (default page 1, pageSize 50)
curl -X GET "http://localhost:7890/api/v1/players/all" \
  -H "Authorization: Bearer your-api-key-here"

# Second page, 20 players per page
curl -X GET "http://localhost:7890/api/v1/players/all?page=2&pageSize=20" \
  -H "Authorization: Bearer your-api-key-here"

# Search by name
curl -X GET "http://localhost:7890/api/v1/players/all?search=Aditya" \
  -H "Authorization: Bearer your-api-key-here"

# Only online players
curl -X GET "http://localhost:7890/api/v1/players/all?status=online" \
  -H "Authorization: Bearer your-api-key-here"

# Only offline players
curl -X GET "http://localhost:7890/api/v1/players/all?status=offline" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

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

#### Response Fields

**`players[]` (array of objects)**

| Field         | Type    | Description                                                                   |
|---------------|---------|-------------------------------------------------------------------------------|
| `uuid`        | string  | Player's Mojang UUID.                                                         |
| `username`    | string  | Player's current Minecraft username.                                          |
| `displayName` | string  | Player's display name (may include color codes).                              |
| `online`      | boolean | `true` if the player is currently online on the server.                       |
| `vanished`    | boolean | `true` if the player is currently vanished (only meaningful when `online: true`). |
| `afk`         | boolean | `true` if the player is AFK (only meaningful when `online: true`).            |
| `staffMode`   | boolean | `true` if the player is in staff mode (only meaningful when `online: true`).  |
| `lastSeen`    | long    | Unix timestamp (ms) of the player's last recorded activity.                   |

**`pagination` (object)**

| Field        | Type    | Description                               |
|--------------|---------|-------------------------------------------|
| `page`       | integer | Current page number.                      |
| `pageSize`   | integer | Number of entries per page.               |
| `total`      | integer | Total number of players matching filters. |
| `totalPages` | integer | Total number of pages available.          |

#### Error Responses

| Status | Code             | Description                        |
|--------|------------------|------------------------------------|
| 401    | `UNAUTHORIZED`   | Missing or invalid API key.        |
| 403    | `FORBIDDEN`      | Insufficient scope.                |
| 429    | `RATE_LIMITED`    | Rate limit exceeded.               |
| 500    | `INTERNAL_ERROR` | Unexpected server error.           |

---

### 2. Get Player by UUID

Retrieves full player data for a specific online player identified by their Mojang UUID.

| Property   | Value                           |
|------------|---------------------------------|
| **Method** | `GET`                           |
| **Path**   | `/players/{uuid}`               |
| **Auth**   | Bearer Token                    |
| **Scope**  | `read:players`                  |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5",
    "username": "Notch",
    "displayName": "§6Notch",
    "online": true,
    "vanished": false,
    "afk": false,
    "staffMode": false,
    "location": {
      "world": "world",
      "x": 128.5,
      "y": 64.0,
      "z": -256.3,
      "yaw": 90.0,
      "pitch": 0.0
    },
    "health": 20.0,
    "maxHealth": 20.0,
    "foodLevel": 18,
    "saturation": 5.0,
    "expLevel": 30,
    "expProgress": 0.72,
    "totalExp": 1395,
    "gamemode": "SURVIVAL",
    "ping": 42,
    "locale": "en_US",
    "clientBrand": "vanilla",
    "ipAddress": "180.252.124.52",
    "country": "Indonesia",
    "region": "Jakarta",
    "city": "Jakarta",
    "isp": "PT Telekomunikasi Indonesia",
    "asn": "AS17974 PT Telekomunikasi Indonesia",
    "ipHistory": [
      {
        "ipAddress": "180.252.124.52",
        "country": "Indonesia",
        "region": "Jakarta",
        "city": "Jakarta",
        "isp": "PT Telekomunikasi Indonesia",
        "asn": "AS17974 PT Telekomunikasi Indonesia",
        "firstSeen": 1717600000000,
        "lastSeen": 1717600000000
      }
    ],
    "firstJoin": 1609459200000,
    "lastSeen": 1717600000000,
    "totalPlaytimeMs": 8640000000,
    "school": {
      "nis": "2026-0004",
      "academicStage": "SMA",
      "academicClass": 10,
      "currentSemester": "GANJIL",
      "rank": {
        "id": "SMA_10",
        "displayName": "§dSiswa X SMA",
        "priority": 30,
        "type": "STUDENT"
      },
      "isStaff": false,
      "isManagement": false
    }
  }
}
```

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 3. Get Player by Username

Retrieves full player data for a specific online player identified by their current username. The response body is **identical** to [Get Player by UUID](#2-get-player-by-uuid).

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/name/{username}`         |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

#### Path Parameters

| Parameter   | Type   | Description                                  |
|-------------|--------|----------------------------------------------|
| `username`  | string | The player's current Minecraft username.     |

> [!NOTE]
> Username lookups are **case-insensitive**. `Notch`, `notch`, and `NOTCH` all resolve to the same player.

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/name/Notch" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

Response body is identical to [Get Player by UUID](#2-get-player-by-uuid).

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given username. |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 4. Get Player Location

Retrieves the current location of an online player, including world name, coordinates, and look direction.

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/{uuid}/location`         |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/location" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "world": "world",
    "x": 128.5,
    "y": 64.0,
    "z": -256.3,
    "yaw": 90.0,
    "pitch": 0.0
  }
}
```

| Field   | Type   | Description                                                  |
|---------|--------|--------------------------------------------------------------|
| `world` | string | The name of the world the player is currently in.            |
| `x`     | double | X coordinate (east/west). Decimal precision.                 |
| `y`     | double | Y coordinate (altitude). 0 = void, 64 ≈ sea level.          |
| `z`     | double | Z coordinate (north/south). Decimal precision.               |
| `yaw`   | float  | Horizontal rotation in degrees (0–360). 0 = south.           |
| `pitch` | float  | Vertical rotation in degrees (−90 = up, 90 = down).          |

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 5. Get Player Health

Retrieves the current health, food level, and saturation of an online player.

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/{uuid}/health`           |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/health" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "health": 20.0,
    "maxHealth": 20.0,
    "absorptionAmount": 0.0,
    "foodLevel": 18,
    "saturation": 5.0,
    "exhaustion": 1.2
  }
}
```

| Field              | Type    | Description                                                      |
|--------------------|---------|------------------------------------------------------------------|
| `health`           | double  | Current health points (0.0–`maxHealth`). 1.0 = half a heart.    |
| `maxHealth`        | double  | Maximum health. Default is 20.0 (10 hearts).                    |
| `absorptionAmount` | double  | Extra golden-heart absorption HP.                                |
| `foodLevel`        | integer | Hunger bar level (0–20). Each unit = half a drumstick.           |
| `saturation`       | float   | Hidden saturation value. Higher = slower hunger drain.           |
| `exhaustion`       | float   | Exhaustion counter. Causes saturation/hunger to deplete.         |

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 6. Get Player Experience

Retrieves the current experience level, progress, and total experience of an online player.

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/{uuid}/experience`       |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/experience" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "level": 30,
    "exp": 0.72,
    "totalExperience": 1395
  }
}
```

| Field             | Type    | Description                                                               |
|-------------------|---------|---------------------------------------------------------------------------|
| `level`           | integer | Current experience level (displayed above the hotbar).                    |
| `exp`             | float   | Progress toward the next level as a fraction (0.0–1.0). 0.72 = 72%.      |
| `totalExperience` | integer | Total experience points collected (Bukkit's `Player#getTotalExperience`). |

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 7. Get Player Gamemode

Retrieves the current gamemode of an online player.

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/{uuid}/gamemode`         |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/gamemode" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "gamemode": "SURVIVAL"
  }
}
```

| Field      | Type   | Description                                                                  |
|------------|--------|------------------------------------------------------------------------------|
| `gamemode` | string | One of: `SURVIVAL`, `CREATIVE`, `ADVENTURE`, `SPECTATOR`.                   |

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 8. Get Player Inventory

Retrieves the full 36-slot main inventory of an online player. Each slot is serialized using the [Item Serialization Format](#item-serialization-format).

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/{uuid}/inventory`        |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/inventory" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "size": 36,
    "items": [
      {
        "slot": 0,
        "material": "DIAMOND_SWORD",
        "amount": 1,
        "displayName": "§6Excalibur",
        "lore": ["§7Legendary sword"],
        "damage": 0,
        "enchantments": {
          "SHARPNESS": 5,
          "UNBREAKING": 3
        }
      },
      {
        "slot": 1,
        "material": "GOLDEN_APPLE",
        "amount": 16,
        "displayName": null,
        "lore": null,
        "damage": 0,
        "enchantments": null
      },
      null,
      null,
      {
        "slot": 4,
        "material": "COBBLESTONE",
        "amount": 64,
        "displayName": null,
        "lore": null,
        "damage": 0,
        "enchantments": null
      }
    ]
  }
}
```

> [!NOTE]
> The `items` array always contains exactly 36 entries (slots 0–35). Slots 0–8 correspond to the hotbar. Empty slots are represented as `null`.

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 9. Get Player Hotbar

Retrieves only the hotbar portion (slots 0–8) of an online player's inventory. Items use the [Item Serialization Format](#item-serialization-format).

| Property   | Value                                  |
|------------|----------------------------------------|
| **Method** | `GET`                                  |
| **Path**   | `/players/{uuid}/inventory/hotbar`     |
| **Auth**   | Bearer Token                           |
| **Scope**  | `read:players`                         |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/inventory/hotbar" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "size": 9,
    "items": [
      {
        "slot": 0,
        "material": "DIAMOND_SWORD",
        "amount": 1,
        "displayName": "§6Excalibur",
        "lore": ["§7Legendary sword"],
        "damage": 0,
        "enchantments": {
          "SHARPNESS": 5,
          "UNBREAKING": 3
        }
      },
      {
        "slot": 1,
        "material": "BOW",
        "amount": 1,
        "displayName": null,
        "lore": null,
        "damage": 50,
        "enchantments": {
          "POWER": 4,
          "INFINITY": 1
        }
      },
      null,
      null,
      null,
      null,
      null,
      null,
      {
        "slot": 8,
        "material": "GOLDEN_APPLE",
        "amount": 3,
        "displayName": null,
        "lore": null,
        "damage": 0,
        "enchantments": null
      }
    ]
  }
}
```

> [!TIP]
> The hotbar endpoint is useful for building lightweight HUD overlays or stream widgets without fetching the full 36-slot inventory.

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 10. Get Player Armor

Retrieves the four armor slots (helmet, chestplate, leggings, boots) of an online player. Items use the [Item Serialization Format](#item-serialization-format).

| Property   | Value                                  |
|------------|----------------------------------------|
| **Method** | `GET`                                  |
| **Path**   | `/players/{uuid}/inventory/armor`      |
| **Auth**   | Bearer Token                           |
| **Scope**  | `read:players`                         |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/inventory/armor" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "helmet": {
      "slot": "helmet",
      "material": "DIAMOND_HELMET",
      "amount": 1,
      "displayName": "§bCrown of the Deep",
      "lore": ["§7Protects from the abyss"],
      "damage": 12,
      "enchantments": {
        "PROTECTION": 4,
        "AQUA_AFFINITY": 1,
        "RESPIRATION": 3
      }
    },
    "chestplate": {
      "slot": "chestplate",
      "material": "NETHERITE_CHESTPLATE",
      "amount": 1,
      "displayName": null,
      "lore": null,
      "damage": 0,
      "enchantments": {
        "PROTECTION": 4,
        "UNBREAKING": 3,
        "MENDING": 1
      }
    },
    "leggings": {
      "slot": "leggings",
      "material": "DIAMOND_LEGGINGS",
      "amount": 1,
      "displayName": null,
      "lore": null,
      "damage": 45,
      "enchantments": {
        "PROTECTION": 4,
        "UNBREAKING": 3
      }
    },
    "boots": null
  }
}
```

| Field        | Type         | Description                               |
|--------------|--------------|-------------------------------------------|
| `helmet`     | object\|null | Helmet slot. `null` if nothing equipped.  |
| `chestplate` | object\|null | Chestplate slot.                          |
| `leggings`   | object\|null | Leggings slot.                            |
| `boots`      | object\|null | Boots slot.                               |

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 11. Get Player Offhand

Retrieves the offhand (shield/secondary) slot item of an online player. Uses the [Item Serialization Format](#item-serialization-format).

| Property   | Value                                    |
|------------|------------------------------------------|
| **Method** | `GET`                                    |
| **Path**   | `/players/{uuid}/inventory/offhand`      |
| **Auth**   | Bearer Token                             |
| **Scope**  | `read:players`                           |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/inventory/offhand" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "item": {
      "slot": "offhand",
      "material": "SHIELD",
      "amount": 1,
      "displayName": null,
      "lore": null,
      "damage": 100,
      "enchantments": {
        "UNBREAKING": 3,
        "MENDING": 1
      }
    }
  }
}
```

> [!NOTE]
> If the offhand slot is empty, `item` will be `null`.

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 12. Get Player Effects

Retrieves all active potion effects currently applied to an online player.

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/{uuid}/effects`          |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/effects" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "effects": [
      {
        "type": "SPEED",
        "amplifier": 1,
        "duration": 3600,
        "ambient": false,
        "particles": true,
        "icon": true
      },
      {
        "type": "FIRE_RESISTANCE",
        "amplifier": 0,
        "duration": 1800,
        "ambient": true,
        "particles": true,
        "icon": true
      },
      {
        "type": "NIGHT_VISION",
        "amplifier": 0,
        "duration": 6000,
        "ambient": false,
        "particles": false,
        "icon": true
      }
    ]
  }
}
```

| Field       | Type    | Description                                                                      |
|-------------|---------|----------------------------------------------------------------------------------|
| `type`      | string  | Bukkit `PotionEffectType` name (e.g. `SPEED`, `FIRE_RESISTANCE`).               |
| `amplifier` | integer | Effect amplifier (0 = level I, 1 = level II, etc.).                              |
| `duration`  | integer | Remaining duration in ticks (20 ticks = 1 second).                               |
| `ambient`   | boolean | `true` if the effect is from a beacon (reduced particles).                       |
| `particles` | boolean | `true` if the effect renders particles around the player.                        |
| `icon`      | boolean | `true` if the effect icon is shown in the player's HUD.                          |

> [!TIP]
> To convert `duration` ticks to seconds, divide by 20. For example, `3600` ticks = `180` seconds = `3` minutes.

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 13. Get Player Skin

Retrieves the player's skin texture URL and signature as provided by the Mojang session server.

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/{uuid}/skin`             |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/skin" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "textureUrl": "http://textures.minecraft.net/texture/a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2",
    "signature": "base64-encoded-yggdrasil-signature-string...",
    "model": "default"
  }
}
```

| Field        | Type   | Description                                                                              |
|--------------|--------|------------------------------------------------------------------------------------------|
| `textureUrl` | string | Direct URL to the player's skin texture on Mojang's texture server.                      |
| `signature`  | string | Base64-encoded Yggdrasil signature. Used to verify the texture originated from Mojang.   |
| `model`      | string | Skin model type: `"default"` (Steve, 4px arms) or `"slim"` (Alex, 3px arms).            |

> [!NOTE]
> The `textureUrl` points directly to the PNG image of the skin. You can render this in web UIs or use it with libraries like Crafatar or Minotar for head/body renders.

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 14. Get Player Ping

Retrieves the network latency (ping) in milliseconds for an online player.

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/{uuid}/ping`             |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/ping" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "ping": 42
  }
}
```

| Field  | Type    | Description                                                |
|--------|---------|------------------------------------------------------------|
| `ping` | integer | Round-trip network latency in milliseconds.                |

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 15. Get Player Network Info

Retrieves detailed network and client information for an online player, including ping, locale, client brand, and protocol version.

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/{uuid}/network`          |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/network" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "ping": 42,
    "locale": "en_US",
    "clientBrand": "vanilla",
    "ipAddress": "180.252.124.52",
    "country": "Indonesia",
    "region": "Jakarta",
    "city": "Jakarta",
    "isp": "PT Telekomunikasi Indonesia",
    "asn": "AS17974 PT Telekomunikasi Indonesia",
    "ipHistory": [
      {
        "ipAddress": "180.252.124.52",
        "country": "Indonesia",
        "region": "Jakarta",
        "city": "Jakarta",
        "isp": "PT Telekomunikasi Indonesia",
        "asn": "AS17974 PT Telekomunikasi Indonesia",
        "firstSeen": 1717600000000,
        "lastSeen": 1717600000000
      }
    ]
  }
}
```

| Field             | Type    | Description                                                                                       |
|-------------------|---------|---------------------------------------------------------------------------------------------------|
| `ping`            | integer | Round-trip network latency in milliseconds.                                                       |
| `locale`          | string  | Client language/locale setting (e.g. `en_US`, `de_DE`, `ja_JP`).                                |
| `clientBrand`     | string  | Client brand string (e.g. `"vanilla"`, `"fabric"`, `"forge"`, `"lunarclient:v1.0"`).             |
| `ipAddress`       | string  | Current IP address of the player.                                                                 |
| `country`         | string  | Country name determined from GeoIP lookup.                                                        |
| `region`          | string  | Region/state name determined from GeoIP lookup.                                                   |
| `city`            | string  | City name determined from GeoIP lookup.                                                           |
| `isp`             | string  | Internet Service Provider (ISP) name.                                                             |
| `asn`             | string  | Autonomous System Number (ASN) provider description.                                              |
| `ipHistory`       | array   | History list of all unique IP addresses used by this player, including their GeoIP details and timestamps. |
| `ipHistory[].ipAddress` | string  | The historical IP address.                                                                  |
| `ipHistory[].firstSeen` | long | Timestamp (ms) when this IP was first used by the player.                                          |
| `ipHistory[].lastSeen`  | long | Timestamp (ms) when this IP was last used by the player.                                           |

> [!TIP]
> The `clientBrand` field can be used to detect modified clients. Vanilla Minecraft reports `"vanilla"`, while modded clients like Fabric, Forge, or Lunar Client report their own brand strings.

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 16. Get Player Stats

Retrieves playtime statistics for an online player, including first join date, last seen timestamp, and total accumulated playtime.

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/{uuid}/stats`            |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/stats" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "firstJoin": 1609459200000,
    "lastSeen": 1717600000000,
    "totalPlaytimeMs": 8640000000,
    "kills": 42,
    "deaths": 10,
    "mobKills": 150
  }
}
```

| Field                    | Type    | Description                                                                  |
|--------------------------|---------|------------------------------------------------------------------------------|
| `firstJoin`              | long    | Unix epoch timestamp (ms) of the player's first join.                        |
| `lastSeen`               | long    | Unix epoch timestamp (ms) of the player's last activity.                     |
| `totalPlaytimeMs`        | long    | Total playtime in milliseconds across all sessions.                          |
| `kills`                  | integer | Total player kills.                                                          |
| `deaths`                 | integer | Total deaths.                                                                |
| `mobKills`               | integer | Total mob kills.                                                             |

#### Error Responses

| Status | Code               | Description                               |
|--------|--------------------|-----------------------------------------  |
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.               |
| 403    | `FORBIDDEN`        | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND` | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                     |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                  |

---

### 17. Get Player Vault Data

Retrieves Vault-integrated permission and chat metadata for an online player. This includes the player's primary permission group, prefix, and suffix as registered in the Vault-compatible permissions plugin.

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/{uuid}/vault`            |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

> [!IMPORTANT]
> This endpoint requires the [Vault](https://github.com/MilkBowl/Vault) plugin to be installed on the server. If Vault is not available, a `500` error with code `DEPENDENCY_MISSING` will be returned.

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/vault" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "group": "admin",
    "prefix": "§c[Admin] ",
    "suffix": " §7[VIP]",
    "balance": 15000.50
  }
}
```

| Field     | Type         | Description                                                             |
|-----------|--------------|-------------------------------------------------------------------------|
| `group`   | string       | Primary permission group from Vault (e.g. `"admin"`, `"default"`).     |
| `prefix`  | string\|null | Chat prefix with color codes. `null` if no prefix is configured.        |
| `suffix`  | string\|null | Chat suffix with color codes. `null` if no suffix is configured.        |
| `balance` | double\|null | Economy balance via Vault Economy. `null` if no economy plugin exists.  |

#### Error Responses

| Status | Code                 | Description                               |
|--------|----------------------|-------------------------------------------|
| 401    | `UNAUTHORIZED`       | Missing or invalid API key.               |
| 403    | `FORBIDDEN`          | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND`   | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`        | Rate limit exceeded.                     |
| 500    | `DEPENDENCY_MISSING` | Vault plugin is not installed.            |
| 500    | `INTERNAL_ERROR`     | Unexpected server error.                  |

---

### 18. Get Player LuckPerms Data

Retrieves LuckPerms permission data for an online player, including all inherited groups and individual permission nodes.

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/{uuid}/luckperms`        |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

> [!IMPORTANT]
> This endpoint requires the [LuckPerms](https://luckperms.net/) plugin to be installed on the server. If LuckPerms is not available, a `500` error with code `DEPENDENCY_MISSING` will be returned.

#### Path Parameters

| Parameter | Type   | Description                                         |
|-----------|--------|-----------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes).  |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/069a79f4-44e9-4726-a5be-fca90e38aaf5/luckperms" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "primaryGroup": "admin",
    "groups": [
      "admin",
      "moderator",
      "default"
    ],
    "prefix": "§c[Admin]",
    "suffix": "",
    "meta": {
      "home-limit": "10",
      "fly-speed": "0.2"
    },
    "nodes": [
      {
        "key": "group.admin",
        "value": true,
        "context": {}
      },
      {
        "key": "essentials.fly",
        "value": true,
        "context": {
          "world": "world"
        }
      },
      {
        "key": "minecraft.command.ban",
        "value": true,
        "context": {}
      },
      {
        "key": "naturalapi.admin",
        "value": true,
        "context": {}
      }
    ]
  }
}
```

| Field          | Type     | Description                                                                              |
|----------------|----------|------------------------------------------------------------------------------------------|
| `primaryGroup` | string   | The player's primary group in LuckPerms.                                                 |
| `groups`       | string[] | All groups the player inherits from (including the primary group).                        |
| `prefix`       | string   | Highest-weight prefix from LuckPerms. Empty string if none.                              |
| `suffix`       | string   | Highest-weight suffix from LuckPerms. Empty string if none.                              |
| `meta`         | object   | Key-value map of LuckPerms meta values assigned to the player.                           |
| `nodes`        | array    | List of all permission nodes. Each node includes `key`, `value`, and `context`.          |

**Node Object:**

| Field     | Type    | Description                                                                 |
|-----------|---------|-----------------------------------------------------------------------------|
| `key`     | string  | The permission node key (e.g. `"essentials.fly"`, `"group.admin"`).        |
| `value`   | boolean | `true` if granted, `false` if explicitly negated.                          |
| `context` | object  | Context restrictions (e.g. `{"world": "world"}`). Empty `{}` means global. |

#### Error Responses

| Status | Code                 | Description                               |
|--------|----------------------|-------------------------------------------|
| 401    | `UNAUTHORIZED`       | Missing or invalid API key.               |
| 403    | `FORBIDDEN`          | Insufficient scope.                       |
| 404    | `PLAYER_NOT_FOUND`   | No online player with the given UUID.     |
| 429    | `RATE_LIMITED`        | Rate limit exceeded.                     |
| 500    | `DEPENDENCY_MISSING` | LuckPerms plugin is not installed.        |
| 500    | `INTERNAL_ERROR`     | Unexpected server error.                  |

---

### 19. Get Offline Player by UUID

Retrieves cached/stored data for a player who is **not currently online**, identified by UUID. Data is loaded from the server's database snapshot and may not reflect real-time state.

| Property   | Value                              |
|------------|------------------------------------|
| **Method** | `GET`                              |
| **Path**   | `/players/offline/{uuid}`          |
| **Auth**   | Bearer Token                       |
| **Scope**  | `read:players`                     |

#### Path Parameters

| Parameter | Type   | Description                                                                       |
|-----------|--------|-----------------------------------------------------------------------------------|
| `uuid`    | string | The player's Mojang UUID (with or without dashes). Player must have joined before.|

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/offline/069a79f4-44e9-4726-a5be-fca90e38aaf5" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5",
    "username": "Notch",
    "online": false,
    "firstJoin": 1609459200000,
    "lastSeen": 1717500000000,
    "totalPlaytimeMs": 8640000000,
    "kills": 42,
    "deaths": 10,
    "mobKills": 150,
    "lastKnownLocation": {
      "world": "world",
      "x": 128.5,
      "y": 64.0,
      "z": -256.3,
      "yaw": 90.0,
      "pitch": 0.0
    },
    "lastKnownHealth": 20.0,
    "lastKnownFoodLevel": 20,
    "lastKnownLevel": 30,
    "lastKnownGamemode": "SURVIVAL",
    "isOp": true,
    "isBanned": false,
    "isWhitelisted": true
  }
}
```

| Field                   | Type         | Description                                                      |
|-------------------------|--------------|------------------------------------------------------------------|
| `uuid`                  | string       | Player's Mojang UUID.                                            |
| `username`              | string       | Last known username.                                             |
| `online`                | boolean      | Always `false` for offline player endpoints.                     |
| `firstJoin`             | long         | Unix epoch timestamp (ms) of first join.                         |
| `lastSeen`              | long         | Unix epoch timestamp (ms) of last disconnect.                    |
| `totalPlaytimeMs`       | long         | Total accumulated playtime in milliseconds.                      |
| `kills`                 | integer      | Total player kills.                                              |
| `deaths`                | integer      | Total deaths.                                                    |
| `mobKills`              | integer      | Total mob kills.                                                 |
| `lastKnownLocation`     | object\|null | Last saved location. `null` if never recorded.                   |
| `lastKnownHealth`       | double       | Health at last disconnect.                                       |
| `lastKnownFoodLevel`    | integer      | Food level at last disconnect.                                   |
| `lastKnownLevel`        | integer      | XP level at last disconnect.                                     |
| `lastKnownGamemode`     | string       | Gamemode at last disconnect.                                     |
| `isOp`                  | boolean      | Whether the player is a server operator.                         |
| `isBanned`              | boolean      | Whether the player is currently banned.                          |
| `isWhitelisted`         | boolean      | Whether the player is on the whitelist.                           |

> [!WARNING]
> Offline player data comes from the server's last saved snapshot. Fields like `lastKnownLocation`, `lastKnownHealth`, and `lastKnownLevel` reflect the player's state at their last disconnect and may be stale.

#### Error Responses

| Status | Code               | Description                                                        |
|--------|--------------------|--------------------------------------------------------------------|
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.                                        |
| 403    | `FORBIDDEN`        | Insufficient scope.                                                |
| 404    | `PLAYER_NOT_FOUND` | No player with this UUID has ever joined the server.               |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                                              |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                                           |

---

### 20. Get Offline Player by Username

Retrieves cached/stored data for a player who is **not currently online**, identified by their last known username. The response body is **identical** to [Get Offline Player by UUID](#19-get-offline-player-by-uuid).

| Property   | Value                                 |
|------------|---------------------------------------|
| **Method** | `GET`                                 |
| **Path**   | `/players/offline/name/{username}`    |
| **Auth**   | Bearer Token                          |
| **Scope**  | `read:players`                        |

#### Path Parameters

| Parameter   | Type   | Description                                                         |
|-------------|--------|---------------------------------------------------------------------|
| `username`  | string | The player's last known Minecraft username. Case-insensitive.       |

> [!NOTE]
> Username lookups are **case-insensitive**. If a player has changed their username since they last played, you must use either their **current** Mojang username or their UUID.

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/players/offline/name/Notch" \
  -H "Authorization: Bearer your-api-key-here"
```

#### Success Response — `200 OK`

Response body is identical to [Get Offline Player by UUID](#19-get-offline-player-by-uuid).

#### Error Responses

| Status | Code               | Description                                                        |
|--------|--------------------|--------------------------------------------------------------------|
| 401    | `UNAUTHORIZED`     | Missing or invalid API key.                                        |
| 403    | `FORBIDDEN`        | Insufficient scope.                                                |
| 404    | `PLAYER_NOT_FOUND` | No player with this username has ever joined the server.           |
| 429    | `RATE_LIMITED`      | Rate limit exceeded.                                              |
| 500    | `INTERNAL_ERROR`   | Unexpected server error.                                           |

---

## Quick Reference

| # | Method | Endpoint                                  | Description                    |
|---|--------|-------------------------------------------|--------------------------------|
| 1 | GET    | `/players`                                | List online players            |
| 1.1 | GET  | `/players/all`                            | List all players (online + offline, paged) |
| 2 | GET    | `/players/{uuid}`                         | Full player data (by UUID)     |
| 3 | GET    | `/players/name/{username}`                | Full player data (by name)     |
| 4 | GET    | `/players/{uuid}/location`                | Player location                |
| 5 | GET    | `/players/{uuid}/health`                  | Health, food, saturation       |
| 6 | GET    | `/players/{uuid}/experience`              | XP level & progress            |
| 7 | GET    | `/players/{uuid}/gamemode`                | Current gamemode               |
| 8 | GET    | `/players/{uuid}/inventory`               | Full inventory (36 slots)      |
| 9 | GET    | `/players/{uuid}/inventory/hotbar`        | Hotbar (slots 0–8)            |
| 10| GET    | `/players/{uuid}/inventory/armor`         | Armor slots                    |
| 11| GET    | `/players/{uuid}/inventory/offhand`       | Offhand item                   |
| 12| GET    | `/players/{uuid}/effects`                 | Active potion effects          |
| 13| GET    | `/players/{uuid}/skin`                    | Skin texture & signature       |
| 14| GET    | `/players/{uuid}/ping`                    | Ping (ms)                      |
| 15| GET    | `/players/{uuid}/network`                 | Network & client info          |
| 16| GET    | `/players/{uuid}/stats`                   | Playtime & join stats          |
| 17| GET    | `/players/{uuid}/vault`                   | Vault group/prefix/suffix      |
| 18| GET    | `/players/{uuid}/luckperms`               | LuckPerms groups & nodes       |
| 19| GET    | `/players/offline/{uuid}`                 | Offline player data (by UUID)  |
| 20| GET    | `/players/offline/name/{username}`        | Offline player data (by name)  |
