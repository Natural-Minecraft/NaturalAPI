# Server Endpoints

Base URL: `http://<server-ip>:7890/api/v1`

All Server endpoints provide real-time information about the Minecraft server's health, performance, and configuration. These are **read-only** endpoints designed for monitoring dashboards, status pages, and automation tools.

---

## Authentication

All endpoints require a valid API key passed via the `Authorization` header using the Bearer scheme.

```
Authorization: Bearer <api-key>
```

**Required Scope:** `read:server`

API keys are configured in the NaturalAPI plugin configuration. Each key can be assigned one or more scopes that control access to specific endpoint groups.

> [!IMPORTANT]
> Never expose your API key in client-side code or public repositories. Always make API calls from a backend server or secure environment.

---

## Response Format

### Success Response

All successful responses follow this wrapper structure:

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    // ... endpoint-specific data
  }
}
```

| Field       | Type    | Description                                      |
|-------------|---------|--------------------------------------------------|
| `success`   | boolean | Always `true` for successful responses           |
| `timestamp` | long    | Unix epoch timestamp in milliseconds (server time)|
| `data`      | object  | The response payload (varies per endpoint)       |

### Error Response

All error responses follow this wrapper structure:

```json
{
  "success": false,
  "error": {
    "code": "ERROR_CODE",
    "message": "Human-readable error description",
    "timestamp": 1717600000000
  }
}
```

| Field             | Type   | Description                                  |
|-------------------|--------|----------------------------------------------|
| `success`         | boolean| Always `false` for error responses           |
| `error.code`      | string | Machine-readable error code                  |
| `error.message`   | string | Human-readable description of what went wrong|
| `error.timestamp`  | long   | Unix epoch timestamp in milliseconds         |

---

## Common Error Responses

The following error responses apply to **all** Server endpoints and will not be repeated for each individual endpoint.

### `401 Unauthorized` — Missing or Invalid API Key

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

Returned when the API key is valid but does not have the `read:server` scope required for these endpoints.

```json
{
  "success": false,
  "error": {
    "code": "FORBIDDEN",
    "message": "API key does not have the required scope: read:server",
    "timestamp": 1717600000000
  }
}
```

### `429 Too Many Requests` — Rate Limit Exceeded

Returned when the client has exceeded the configured rate limit. Retry after the period indicated in the `Retry-After` header.

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Rate limit exceeded. Please retry after 30 seconds.",
    "timestamp": 1717600000000
  }
}
```

> [!TIP]
> Check the `Retry-After` response header (value in seconds) to determine when you can safely retry the request.

### `500 Internal Server Error` — Server-Side Failure

Returned when an unexpected error occurs on the server while processing the request.

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

## Endpoints

---

### 1. Get Server Overview

```
GET /server
```

Returns a comprehensive overview of the Minecraft server, including performance metrics (TPS, MSPT), memory usage, uptime, version information, and player counts. This is the most complete single-call endpoint for server monitoring.

**Authentication:** Required — `Authorization: Bearer <api-key>`
**Scope:** `read:server`
**Query Parameters:** None

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/server" \
  -H "Authorization: Bearer napi_1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d" \
  -H "Accept: application/json"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "online": true,
    "version": "1.21.1",
    "platform": "Paper",
    "motd": "§aWelcome to §bMy Minecraft Server§a!",
    "tps": {
      "now": 20.0,
      "1m": 20.0,
      "5m": 19.98,
      "15m": 19.95
    },
    "players": {
      "online": 42,
      "visible": 40,
      "vanished": 2,
      "max": 100
    },
    "mspt": 12.45,
    "uptime": 86400,
    "ram": {
      "usedMB": 2048,
      "maxMB": 4096,
      "freeMB": 2048
    },
    "javaVersion": "21.0.2",
    "osName": "Linux",
    "worlds": [
      "world",
      "world_nether",
      "world_the_end"
    ]
  }
}
```

#### Response Fields

| Field           | Type    | Description                                                     |
|-----------------|---------|-----------------------------------------------------------------|
| `online`        | boolean | Indicates if the server is online.                              |
| `version`       | string  | Minecraft version of the server (e.g. `"1.21.1"`).              |
| `platform`      | string  | Server platform software name (e.g. `"Paper"`, `"Spigot"`).      |
| `motd`          | string  | Message of the day with formatting/color codes.                |
| `tps.now`       | double  | TPS average over the last 5 seconds (real-time/recent).         |
| `tps.1m`        | double  | TPS average over the last 1 minute.                             |
| `tps.5m`        | double  | TPS average over the last 5 minutes.                            |
| `tps.15m`       | double  | TPS average over the last 15 minutes.                           |
| `players.online`| integer | Number of total online players.                                 |
| `players.visible`| integer| Number of players visible to normal players (non-vanished).     |
| `players.vanished`| integer| Number of vanished players (e.g., in vanish mode).            |
| `players.max`   | integer | Max player slots configured.                                    |
| `mspt`          | double  | Milliseconds per tick (average tick processing time).           |
| `uptime`        | long    | Server uptime in seconds.                                       |
| `ram.usedMB`    | long    | Used memory in MB.                                              |
| `ram.freeMB`    | long    | Free memory in MB.                                              |
| `ram.maxMB`     | long    | Max allocated memory in MB.                                     |
| `javaVersion`   | string  | Java Runtime version running the server.                        |
| `osName`        | string  | Operating System name.                                          |
| `worlds`        | array   | List of loaded worlds on the server.                            |

> [!NOTE]
> A healthy server maintains TPS close to **20.0**. Values below **18.0** typically indicate performance issues. MSPT should stay below **50ms** — the budget for a single tick at 20 TPS.

---

### 2. Get Quick Status

```
GET /server/status
```

Returns a lightweight status check indicating whether the server is online, its current TPS, and the player count. Ideal for health checks and uptime monitors.

**Authentication:** Required — `Authorization: Bearer <api-key>`
**Scope:** `read:server`
**Query Parameters:** None

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/server/status" \
  -H "Authorization: Bearer napi_1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d" \
  -H "Accept: application/json"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "online": true,
    "tps": 20.0,
    "playerCount": 42
  }
}
```

#### Response Fields

| Field         | Type    | Description                                      |
|---------------|---------|--------------------------------------------------|
| `online`      | boolean | Whether the server is online and accepting connections |
| `tps`         | double  | Current ticks per second (1-minute average)      |
| `playerCount` | int     | Number of players currently online               |

> [!TIP]
> This endpoint is optimized for frequent polling. Use it for status pages and uptime bots rather than the heavier `/server` overview endpoint.

---

### 3. Get TPS

```
GET /server/tps
```

Returns the server's ticks-per-second values across three time windows: 1 minute, 5 minutes, and 15 minutes. TPS is the primary indicator of server performance.

**Authentication:** Required — `Authorization: Bearer <api-key>`
**Scope:** `read:server`
**Query Parameters:** None

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/server/tps" \
  -H "Authorization: Bearer napi_1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d" \
  -H "Accept: application/json"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "now": 20.0,
    "1m": 20.0,
    "5m": 19.98,
    "15m": 19.95
  }
}
```

#### Response Fields

| Field    | Type   | Description                                       |
|----------|--------|---------------------------------------------------|
| `now`    | double | Ticks per second averaged over the last 5 seconds (recent/instant) |
| `1m`     | double | Ticks per second averaged over the last 1 minute  |
| `5m`     | double | Ticks per second averaged over the last 5 minutes |
| `15m`    | double | Ticks per second averaged over the last 15 minutes|

> [!NOTE]
> The maximum TPS value is **20.0**. Some server implementations may report values slightly above 20 due to tick catch-up mechanics. Values are typically rounded to 2 decimal places.

---

### 4. Get MSPT

```
GET /server/mspt
```

Returns the average milliseconds per tick (MSPT). This measures how long the server takes to process a single game tick and is a key performance indicator.

**Authentication:** Required — `Authorization: Bearer <api-key>`
**Scope:** `read:server`
**Query Parameters:** None

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/server/mspt" \
  -H "Authorization: Bearer napi_1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d" \
  -H "Accept: application/json"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "averageTickTime": 12.45
  }
}
```

#### Response Fields

| Field             | Type   | Description                                        |
|-------------------|--------|----------------------------------------------------|
| `averageTickTime` | double | Average time in milliseconds to process one tick   |

> [!WARNING]
> MSPT values exceeding **50ms** will cause TPS to drop below 20, resulting in noticeable lag. At 50ms per tick, the server can only process 20 ticks per second (1000ms ÷ 50ms = 20 TPS). This endpoint may not be available on all server platforms — Spigot-based servers may return limited or less accurate data compared to Paper.

---

### 5. Get RAM Usage

```
GET /server/ram
```

Returns the server's current JVM memory usage, including used, maximum, and free memory in megabytes.

**Authentication:** Required — `Authorization: Bearer <api-key>`
**Scope:** `read:server`
**Query Parameters:** None

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/server/ram" \
  -H "Authorization: Bearer napi_1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d" \
  -H "Accept: application/json"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "usedMB": 2048,
    "maxMB": 4096,
    "freeMB": 2048
  }
}
```

#### Response Fields

| Field    | Type | Description                                           |
|----------|------|-------------------------------------------------------|
| `usedMB` | int  | Currently used heap memory in megabytes               |
| `maxMB`  | int  | Maximum heap memory allocated to the JVM in megabytes |
| `freeMB` | int  | Free heap memory available in megabytes               |

> [!TIP]
> The relationship between these values is: `freeMB ≈ maxMB - usedMB`. Monitor the `usedMB / maxMB` ratio — if it consistently stays above **85%**, consider increasing the JVM heap size (`-Xmx` flag).

---

### 6. Get Uptime

```
GET /server/uptime
```

Returns the server's uptime since the last start, both as raw seconds and a human-readable formatted string.

**Authentication:** Required — `Authorization: Bearer <api-key>`
**Scope:** `read:server`
**Query Parameters:** None

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/server/uptime" \
  -H "Authorization: Bearer napi_1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d" \
  -H "Accept: application/json"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "uptimeSeconds": 86400,
    "uptimeFormatted": "1d 0h 0m 0s"
  }
}
```

#### Response Fields

| Field             | Type   | Description                                              |
|-------------------|--------|----------------------------------------------------------|
| `uptimeSeconds`   | long   | Total uptime in seconds since the server last started    |
| `uptimeFormatted` | string | Human-readable uptime string (e.g., `"1d 0h 0m 0s"`)   |

---

### 7. Get Server Version

```
GET /server/version
```

Returns the Minecraft version, full server software version string, and the server platform identifier.

**Authentication:** Required — `Authorization: Bearer <api-key>`
**Scope:** `read:server`
**Query Parameters:** None

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/server/version" \
  -H "Authorization: Bearer napi_1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d" \
  -H "Accept: application/json"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "minecraftVersion": "1.21.4",
    "serverVersion": "git-Paper-195",
    "platform": "Paper"
  }
}
```

#### Response Fields

| Field              | Type   | Description                                                        |
|--------------------|--------|--------------------------------------------------------------------|
| `minecraftVersion` | string | The Minecraft game version (e.g., `"1.21.4"`)                     |
| `serverVersion`    | string | Full server software version string (e.g., `"git-Paper-195"`)     |
| `platform`         | string | Server platform identifier (e.g., `"Paper"`, `"Spigot"`, `"Purpur"`, `"Folia"`) |

> [!NOTE]
> The `platform` field is determined by detecting the server implementation at runtime. Common values include `Paper`, `Spigot`, `Purpur`, `Folia`, and `CraftBukkit`.

---

### 8. Get Player Count

```
GET /server/players/count
```

Returns the current online player count, the maximum player limit, and the number of vanished players. This endpoint is **vanish-aware** and will exclude vanished players from the `online` count if a compatible vanish plugin is detected.

**Authentication:** Required — `Authorization: Bearer <api-key>`
**Scope:** `read:server`
**Query Parameters:** None

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/server/players/count" \
  -H "Authorization: Bearer napi_1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d" \
  -H "Accept: application/json"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "online": 40,
    "max": 100,
    "vanished": 2
  }
}
```

#### Response Fields

| Field      | Type | Description                                                            |
|------------|------|------------------------------------------------------------------------|
| `online`   | int  | Number of visible (non-vanished) players currently online              |
| `max`      | int  | Maximum number of players allowed (from `server.properties`)           |
| `vanished` | int  | Number of players currently in vanish mode                             |

> [!IMPORTANT]
> The `vanished` count is only accurate when a compatible vanish plugin (e.g., SuperVanish, PremiumVanish, CMI) is installed. If no vanish plugin is detected, `vanished` will always return `0` and `online` will reflect the total player count including any vanished players.

---

### 9. Get Plugins

```
GET /server/plugins
```

Returns a list of all loaded plugins on the server along with their version strings.

**Authentication:** Required — `Authorization: Bearer <api-key>`
**Scope:** `read:server`
**Query Parameters:** None

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/server/plugins" \
  -H "Authorization: Bearer napi_1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d" \
  -H "Accept: application/json"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "plugins": [
      {
        "name": "NaturalAPI",
        "version": "1.0.0"
      },
      {
        "name": "WorldEdit",
        "version": "7.3.6"
      },
      {
        "name": "EssentialsX",
        "version": "2.20.1"
      },
      {
        "name": "LuckPerms",
        "version": "5.4.137"
      },
      {
        "name": "Vault",
        "version": "1.7.3"
      }
    ]
  }
}
```

#### Response Fields

| Field              | Type   | Description                                |
|--------------------|--------|--------------------------------------------|
| `plugins`          | array  | Array of plugin objects                    |
| `plugins[].name`   | string | The plugin name as registered with Bukkit  |
| `plugins[].version`| string | The plugin version string                  |

> [!CAUTION]
> Exposing the full plugin list publicly can reveal potential attack vectors. Ensure your API key is kept secure and consider restricting access to this endpoint to trusted monitoring systems only.

---

### 10. Get Whitelist

```
GET /server/whitelist
```

Returns the list of all whitelisted players on the server.

**Authentication:** Required — `Authorization: Bearer <api-key>`
**Scope:** `read:server`
**Query Parameters:** None

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/server/whitelist" \
  -H "Authorization: Bearer napi_1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d" \
  -H "Accept: application/json"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "whitelistEnabled": true,
    "players": [
      {
        "name": "Notch",
        "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5"
      },
      {
        "name": "jeb_",
        "uuid": "853c80ef-3c37-49fd-aa49-938b674adae6"
      },
      {
        "name": "Dinnerbone",
        "uuid": "61699b2e-d327-4a01-9f1e-0ea8c3f06bc6"
      }
    ]
  }
}
```

#### Response Fields

| Field               | Type    | Description                                         |
|---------------------|---------|-----------------------------------------------------|
| `whitelistEnabled`  | boolean | Whether the whitelist is currently enforced          |
| `players`           | array   | Array of whitelisted player objects                  |
| `players[].name`    | string  | The player's Minecraft username                     |
| `players[].uuid`    | string  | The player's UUID (with dashes)                     |

> [!NOTE]
> The `whitelistEnabled` field reflects whether the whitelist is actively enforced (equivalent to the `white-list` property in `server.properties`). Players can still be on the whitelist even when enforcement is disabled.

---

### 11. Get Ban List

```
GET /server/banlist
```

Returns a list of all banned players, including ban reason, the source of the ban, and expiration information.

**Authentication:** Required — `Authorization: Bearer <api-key>`
**Scope:** `read:server`
**Query Parameters:** None

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/server/banlist" \
  -H "Authorization: Bearer napi_1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d" \
  -H "Accept: application/json"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "bans": [
      {
        "name": "Griefer123",
        "uuid": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
        "reason": "Griefing and use of hacked client",
        "source": "Console",
        "expiry": null
      },
      {
        "name": "SpamBot",
        "uuid": "f9e8d7c6-b5a4-3210-fedc-ba0987654321",
        "reason": "Chat spam / advertising",
        "source": "AdminPlayer",
        "expiry": "2026-07-05T18:48:44+07:00"
      }
    ]
  }
}
```

#### Response Fields

| Field           | Type         | Description                                                        |
|-----------------|--------------|--------------------------------------------------------------------|
| `bans`          | array        | Array of banned player objects                                     |
| `bans[].name`   | string       | The banned player's Minecraft username                             |
| `bans[].uuid`   | string       | The banned player's UUID (with dashes)                             |
| `bans[].reason`  | string       | The reason provided when the ban was issued                        |
| `bans[].source`  | string       | Who or what issued the ban (e.g., `"Console"`, a player name)      |
| `bans[].expiry`  | string\|null | ISO 8601 timestamp when the ban expires, or `null` for permanent bans |

> [!NOTE]
> Permanent bans will have `expiry` set to `null`. Temporary bans include an ISO 8601 formatted expiration timestamp. Expired bans may still appear in the list until the server clears them.

---

### 12. Get Leaderboard

```
GET /server/leaderboard
```

Returns a leaderboard of players sorted by a specific metric, dynamic PlaceholderAPI placeholder, or ajLeaderboards board.
* **Historical metrics** (such as `playtime`, `balance`, `kills`, `deaths`, `votes`, `exp`) retrieve data from the database player snapshots (supporting both online and offline players).
* **ajLeaderboards boards** (using the prefix `ajlb:<board_name>[:time_type]`, e.g. `ajlb:statistic_player_kills` or `ajlb:statistic_player_kills:weekly`) fetch leaderboard rankings directly from the ajLeaderboards plugin (supporting offline players and cached rankings).
* **Custom PlaceholderAPI placeholders** (e.g. `%some_placeholder%`) evaluate and sort values dynamically for currently online players.

**Authentication:** Required — `Authorization: Bearer <api-key>`
**Scope:** `read:server`

#### Query Parameters

| Parameter | Type   | Required | Description |
|-----------|--------|----------|-------------|
| `type`    | string | **Yes**  | The metric or placeholder to sort by. Supported values: `playtime`, `balance`, `kills` (or `kill`), `deaths` (or `death`), `votes` (or `vote`), `exp` (or `experience`), `ajlb:<board_name>[:time_type]` (for ajLeaderboards integration supporting offline players), or any valid custom PlaceholderAPI placeholder starting and ending with `%` (e.g. `%essentials_home_count%`). |
| `limit`   | integer| No       | Maximum number of entries to return (between 1 and 100). Defaults to `10`. |

#### curl Example

```bash
curl -X GET "http://localhost:7890/api/v1/server/leaderboard?type=playtime&limit=5" \
  -H "Authorization: Bearer napi_1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d" \
  -H "Accept: application/json"
```

#### Success Response — `200 OK` (Database Metric - e.g. `playtime`)

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "metadata": {
    "provider": "NaturalAPI Snapshots Database",
    "sync_interval_minutes": 10,
    "last_sync_timestamp": 1717600000000,
    "next_sync_timestamp": 1717600600000
  },
  "data": [
    {
      "uuid": "f32a30cb-1902-35b0-b9e7-a04ed64d13dd",
      "username": "AdityaOkeGas",
      "value": 150230.0
    },
    {
      "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5",
      "username": "Notch",
      "value": 98200.0
    }
  ]
}
```

#### Success Response — `200 OK` (PAPI Placeholder - e.g. `%player_level%`)

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "metadata": {
    "provider": "PlaceholderAPI (Dynamic)",
    "note": "Evaluated in real-time for online players only"
  },
  "data": [
    {
      "uuid": "f32a30cb-1902-35b0-b9e7-a04ed64d13dd",
      "username": "AdityaOkeGas",
      "value": "42"
    },
    {
      "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5",
      "username": "Notch",
      "value": "12"
    }
  ]
}
```

#### Response Fields

| Field             | Type           | Description                                                           |
|-------------------|----------------|-----------------------------------------------------------------------|
| `data`            | array          | Array of leaderboard entry objects.                                   |
| `data[].uuid`     | string         | The unique UUID of the player.                                        |
| `data[].username` | string         | The last known username of the player.                                |
| `data[].value`    | number\|string | The value of the metric (numeric double for DB metrics, string for PAPI). |

---

## Rate Limiting

All endpoints are subject to rate limiting. The default limits are configured in the NaturalAPI plugin configuration. When a rate limit is exceeded, the API returns a `429 Too Many Requests` response.

**Rate limit headers** are included in every response:

| Header                  | Description                                     |
|-------------------------|-------------------------------------------------|
| `X-RateLimit-Limit`     | Maximum number of requests allowed per window   |
| `X-RateLimit-Remaining` | Number of requests remaining in the current window |
| `X-RateLimit-Reset`     | Unix timestamp (seconds) when the window resets |
| `Retry-After`           | Seconds until the rate limit resets (only on 429)|

---

## Quick Reference

| Method | Endpoint                  | Description              |
|--------|---------------------------|--------------------------|
| GET    | `/server`                 | Full server overview     |
| GET    | `/server/status`          | Quick online status      |
| GET    | `/server/tps`             | TPS metrics              |
| GET    | `/server/mspt`            | MSPT metric              |
| GET    | `/server/ram`             | RAM usage                |
| GET    | `/server/uptime`          | Server uptime            |
| GET    | `/server/version`         | Version & platform info  |
| GET    | `/server/players/count`   | Player counts (vanish-aware) |
| GET    | `/server/plugins`         | Installed plugins list   |
| GET    | `/server/whitelist`       | Whitelisted players      |
| GET    | `/server/banlist`         | Banned players list      |
| GET    | `/server/leaderboard`     | Server leaderboard statistics |
