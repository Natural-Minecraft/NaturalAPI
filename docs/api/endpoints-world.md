# World Endpoints

> NaturalAPI — World Management & Inspection

**Base URL:** `http://<server-ip>:7890/api/v1`

All World endpoints provide read-only access to Minecraft world state, including environment details, time, weather, entities, chunks, world border configuration, and game rules. These endpoints reflect live server state and return real-time data from loaded worlds.

---

## Authentication

Every request to the World endpoints **must** include a valid Bearer token in the `Authorization` header.

```
Authorization: Bearer <api-key>
```

| Requirement | Value |
|---|---|
| **Header** | `Authorization: Bearer <api-key>` |
| **Required Scope** | `read:worlds` |

> [!IMPORTANT]
> API keys are configured on the server side. If your key does not include the `read:worlds` scope, all World endpoints will return a `403 Forbidden` response.

---

## Common Response Format

### Success Envelope

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

### Error Envelope

All error responses follow this structure:

```json
{
  "success": false,
  "error": {
    "code": "ERROR_CODE",
    "message": "Human-readable error description.",
    "timestamp": 1717600000000
  }
}
```

### Common Error Codes

| HTTP Status | Error Code | Description |
|---|---|---|
| `401` | `UNAUTHORIZED` | Missing or invalid API key. |
| `403` | `FORBIDDEN` | API key lacks the `read:worlds` scope. |
| `404` | `WORLD_NOT_FOUND` | The specified world name does not match any loaded world. |
| `429` | `RATE_LIMITED` | Too many requests — slow down. |
| `500` | `INTERNAL_ERROR` | An unexpected server-side error occurred. |

---

## Endpoints Overview

| Method | Path | Description |
|---|---|---|
| `GET` | [`/worlds`](#get-worlds) | List all loaded worlds |
| `GET` | [`/worlds/{name}`](#get-worldsname) | World overview |
| `GET` | [`/worlds/{name}/time`](#get-worldsnametime) | In-game time |
| `GET` | [`/worlds/{name}/weather`](#get-worldsnameweather) | Weather state |
| `GET` | [`/worlds/{name}/players`](#get-worldsnameplayers) | Players in world |
| `GET` | [`/worlds/{name}/entities`](#get-worldsnameentities) | Entity type count summary |
| `GET` | [`/worlds/{name}/chunks`](#get-worldsnamechunks) | Loaded chunk count |
| `GET` | [`/worlds/{name}/border`](#get-worldsnameborder) | World border configuration |
| `GET` | [`/worlds/{name}/gamerules`](#get-worldsnamegamerules) | All game rules |

---

## `GET /worlds`

**List All Loaded Worlds**

Returns an array of world names currently loaded on the server. This is the starting point for discovering which worlds are available to query. Only worlds that are actively loaded by the Minecraft server will appear in this list — unloaded or disabled worlds are excluded.

### Authentication

| Header | Required Scope |
|---|---|
| `Authorization: Bearer <api-key>` | `read:worlds` |

### Parameters

This endpoint takes no path or query parameters.

### Example Request

```bash
curl -X GET "http://localhost:7890/api/v1/worlds" \
  -H "Authorization: Bearer your-api-key-here" \
  -H "Accept: application/json"
```

### Success Response

**Status:** `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "worlds": [
      "world",
      "world_nether",
      "world_the_end"
    ]
  }
}
```

| Field | Type | Description |
|---|---|---|
| `worlds` | `string[]` | Array of loaded world names. |

### Error Responses

<details>
<summary><code>401 Unauthorized</code> — Missing or invalid API key</summary>

```json
{
  "success": false,
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Missing or invalid Authorization header. Provide a valid Bearer token.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>403 Forbidden</code> — Insufficient scope</summary>

```json
{
  "success": false,
  "error": {
    "code": "FORBIDDEN",
    "message": "Your API key does not have the required scope: read:worlds.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>429 Too Many Requests</code> — Rate limited</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Rate limit exceeded. Please wait before making another request.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>500 Internal Server Error</code> — Server error</summary>

```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_ERROR",
    "message": "An unexpected error occurred while processing the request.",
    "timestamp": 1717600000000
  }
}
```

</details>

---

## `GET /worlds/{name}`

**World Overview**

Returns a comprehensive overview of a specific world, including its environment type, seed, current time, weather state, difficulty setting, entity count, loaded chunk count, and a list of online players currently in that world. This is the primary endpoint for getting a snapshot of a world's state.

### Authentication

| Header | Required Scope |
|---|---|
| `Authorization: Bearer <api-key>` | `read:worlds` |

### Path Parameters

| Parameter | Type | Required | Description |
|---|---|---|---|
| `name` | `string` | Yes | The name of the world (e.g., `world`, `world_nether`). Case-sensitive. |

### Example Request

```bash
curl -X GET "http://localhost:7890/api/v1/worlds/world" \
  -H "Authorization: Bearer your-api-key-here" \
  -H "Accept: application/json"
```

### Success Response

**Status:** `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "name": "world",
    "environment": "NORMAL",
    "seed": -4172144997902289642,
    "time": 6000,
    "weather": "CLEAR",
    "difficulty": "NORMAL",
    "entityCount": 1523,
    "loadedChunks": 625,
    "players": [
      "Steve",
      "Alex",
      "Notch"
    ]
  }
}
```

| Field | Type | Description |
|---|---|---|
| `name` | `string` | The world name. |
| `environment` | `string` | World environment type. One of `NORMAL`, `NETHER`, or `THE_END`. |
| `seed` | `long` | The world generation seed. |
| `time` | `long` | Current in-game time in ticks (0–23999 per day cycle). |
| `weather` | `string` | Current weather state (e.g., `CLEAR`, `RAIN`, `THUNDER`). |
| `difficulty` | `string` | Server difficulty for this world (e.g., `PEACEFUL`, `EASY`, `NORMAL`, `HARD`). |
| `entityCount` | `integer` | Total number of entities currently loaded in the world. |
| `loadedChunks` | `integer` | Number of chunks currently loaded in memory. |
| `players` | `string[]` | List of player names currently in this world. Empty array if none. |

> [!NOTE]
> The `environment` field maps directly to Bukkit's `World.Environment` enum. Custom dimension plugins may introduce additional values.

### Error Responses

<details>
<summary><code>401 Unauthorized</code> — Missing or invalid API key</summary>

```json
{
  "success": false,
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Missing or invalid Authorization header. Provide a valid Bearer token.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>403 Forbidden</code> — Insufficient scope</summary>

```json
{
  "success": false,
  "error": {
    "code": "FORBIDDEN",
    "message": "Your API key does not have the required scope: read:worlds.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>404 Not Found</code> — World not found</summary>

```json
{
  "success": false,
  "error": {
    "code": "WORLD_NOT_FOUND",
    "message": "No loaded world found with name: 'my_missing_world'.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>429 Too Many Requests</code> — Rate limited</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Rate limit exceeded. Please wait before making another request.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>500 Internal Server Error</code> — Server error</summary>

```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_ERROR",
    "message": "An unexpected error occurred while processing the request.",
    "timestamp": 1717600000000
  }
}
```

</details>

---

## `GET /worlds/{name}/time`

**In-Game Time**

Returns the current in-game time for the specified world. The response includes the raw tick value, the day count (number of full days elapsed), and a human-readable formatted time string. Minecraft uses a 24000-tick day cycle, where `0` ticks is sunrise (06:00) and `12000` ticks is sunset (18:00).

### Authentication

| Header | Required Scope |
|---|---|
| `Authorization: Bearer <api-key>` | `read:worlds` |

### Path Parameters

| Parameter | Type | Required | Description |
|---|---|---|---|
| `name` | `string` | Yes | The name of the world. Case-sensitive. |

### Example Request

```bash
curl -X GET "http://localhost:7890/api/v1/worlds/world/time" \
  -H "Authorization: Bearer your-api-key-here" \
  -H "Accept: application/json"
```

### Success Response

**Status:** `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "time": 6000,
    "dayCount": 142,
    "formatted": "12:00 PM"
  }
}
```

| Field | Type | Description |
|---|---|---|
| `time` | `long` | Current time in ticks within the current day cycle (0–23999). |
| `dayCount` | `long` | Total number of full in-game days elapsed since world creation. |
| `formatted` | `string` | Human-readable time string (12-hour format). |

> [!TIP]
> Use the `time` field for precise calculations (e.g., checking if it's nighttime: `time >= 13000 && time <= 23000`). Use `formatted` for display purposes in dashboards or UIs.

### Error Responses

<details>
<summary><code>401 Unauthorized</code> — Missing or invalid API key</summary>

```json
{
  "success": false,
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Missing or invalid Authorization header. Provide a valid Bearer token.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>403 Forbidden</code> — Insufficient scope</summary>

```json
{
  "success": false,
  "error": {
    "code": "FORBIDDEN",
    "message": "Your API key does not have the required scope: read:worlds.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>404 Not Found</code> — World not found</summary>

```json
{
  "success": false,
  "error": {
    "code": "WORLD_NOT_FOUND",
    "message": "No loaded world found with name: 'my_missing_world'.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>429 Too Many Requests</code> — Rate limited</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Rate limit exceeded. Please wait before making another request.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>500 Internal Server Error</code> — Server error</summary>

```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_ERROR",
    "message": "An unexpected error occurred while processing the request.",
    "timestamp": 1717600000000
  }
}
```

</details>

---

## `GET /worlds/{name}/weather`

**Weather State**

Returns the current weather conditions for the specified world. This includes whether a storm is active, whether thunder is occurring, and the remaining duration of the current weather state in ticks. Weather duration counts down each tick and a new weather state is selected when it reaches zero.

### Authentication

| Header | Required Scope |
|---|---|
| `Authorization: Bearer <api-key>` | `read:worlds` |

### Path Parameters

| Parameter | Type | Required | Description |
|---|---|---|---|
| `name` | `string` | Yes | The name of the world. Case-sensitive. |

### Example Request

```bash
curl -X GET "http://localhost:7890/api/v1/worlds/world/weather" \
  -H "Authorization: Bearer your-api-key-here" \
  -H "Accept: application/json"
```

### Success Response

**Status:** `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "isStorm": true,
    "isThundering": false,
    "weatherDuration": 14400
  }
}
```

| Field | Type | Description |
|---|---|---|
| `isStorm` | `boolean` | `true` if rain/snow is currently active in the world. |
| `isThundering` | `boolean` | `true` if thunderstorm lightning is active. Always `false` when `isStorm` is `false`. |
| `weatherDuration` | `integer` | Remaining ticks until the current weather state changes. At 20 ticks/second, `14400` ticks ≈ 12 minutes. |

> [!NOTE]
> In the Nether and The End dimensions, weather has no visual effect, but these fields are still tracked internally by the server and will be returned by this endpoint.

### Error Responses

<details>
<summary><code>401 Unauthorized</code> — Missing or invalid API key</summary>

```json
{
  "success": false,
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Missing or invalid Authorization header. Provide a valid Bearer token.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>403 Forbidden</code> — Insufficient scope</summary>

```json
{
  "success": false,
  "error": {
    "code": "FORBIDDEN",
    "message": "Your API key does not have the required scope: read:worlds.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>404 Not Found</code> — World not found</summary>

```json
{
  "success": false,
  "error": {
    "code": "WORLD_NOT_FOUND",
    "message": "No loaded world found with name: 'my_missing_world'.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>429 Too Many Requests</code> — Rate limited</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Rate limit exceeded. Please wait before making another request.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>500 Internal Server Error</code> — Server error</summary>

```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_ERROR",
    "message": "An unexpected error occurred while processing the request.",
    "timestamp": 1717600000000
  }
}
```

</details>

---

## `GET /worlds/{name}/players`

**Players in World**

Returns a list of all online players currently located in the specified world. Each entry contains the player's name. This is useful for monitoring player distribution across worlds or building presence-based features.

### Authentication

| Header | Required Scope |
|---|---|
| `Authorization: Bearer <api-key>` | `read:worlds` |

### Path Parameters

| Parameter | Type | Required | Description |
|---|---|---|---|
| `name` | `string` | Yes | The name of the world. Case-sensitive. |

### Example Request

```bash
curl -X GET "http://localhost:7890/api/v1/worlds/world/players" \
  -H "Authorization: Bearer your-api-key-here" \
  -H "Accept: application/json"
```

### Success Response

**Status:** `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "world": "world",
    "players": [
      "Steve",
      "Alex",
      "Notch"
    ]
  }
}
```

| Field | Type | Description |
|---|---|---|
| `world` | `string` | The world name for confirmation. |
| `players` | `string[]` | List of player names in the world. Empty array `[]` if no players are present. |

> [!TIP]
> If you need full player details (health, location, game mode, etc.), use the dedicated Player endpoints at `/players/{name}` after discovering player names from this endpoint.

### Error Responses

<details>
<summary><code>401 Unauthorized</code> — Missing or invalid API key</summary>

```json
{
  "success": false,
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Missing or invalid Authorization header. Provide a valid Bearer token.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>403 Forbidden</code> — Insufficient scope</summary>

```json
{
  "success": false,
  "error": {
    "code": "FORBIDDEN",
    "message": "Your API key does not have the required scope: read:worlds.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>404 Not Found</code> — World not found</summary>

```json
{
  "success": false,
  "error": {
    "code": "WORLD_NOT_FOUND",
    "message": "No loaded world found with name: 'my_missing_world'.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>429 Too Many Requests</code> — Rate limited</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Rate limit exceeded. Please wait before making another request.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>500 Internal Server Error</code> — Server error</summary>

```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_ERROR",
    "message": "An unexpected error occurred while processing the request.",
    "timestamp": 1717600000000
  }
}
```

</details>

---

## `GET /worlds/{name}/entities`

**Entity Type Count Summary**

Returns a summary of all entities in the specified world, grouped by entity type. Each key in the returned map is a Minecraft entity type identifier, and its value is the total count of that entity type currently loaded. This is invaluable for server performance monitoring — high entity counts in specific categories (e.g., `ITEM`, `ZOMBIE`) often point to lag sources.

### Authentication

| Header | Required Scope |
|---|---|
| `Authorization: Bearer <api-key>` | `read:worlds` |

### Path Parameters

| Parameter | Type | Required | Description |
|---|---|---|---|
| `name` | `string` | Yes | The name of the world. Case-sensitive. |

### Example Request

```bash
curl -X GET "http://localhost:7890/api/v1/worlds/world/entities" \
  -H "Authorization: Bearer your-api-key-here" \
  -H "Accept: application/json"
```

### Success Response

**Status:** `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "world": "world",
    "totalCount": 1523,
    "entities": {
      "ZOMBIE": 87,
      "SKELETON": 64,
      "CREEPER": 42,
      "SPIDER": 38,
      "COW": 156,
      "PIG": 112,
      "SHEEP": 98,
      "CHICKEN": 134,
      "VILLAGER": 45,
      "ITEM": 312,
      "EXPERIENCE_ORB": 27,
      "ARMOR_STAND": 8,
      "ITEM_FRAME": 22,
      "DROPPED_ITEM": 78,
      "ARROW": 15,
      "BAT": 53,
      "ENDERMAN": 12,
      "WOLF": 19,
      "CAT": 11,
      "SQUID": 34,
      "SALMON": 76,
      "COD": 80
    }
  }
}
```

| Field | Type | Description |
|---|---|---|
| `world` | `string` | The world name for confirmation. |
| `totalCount` | `integer` | Total number of entities across all types. |
| `entities` | `object` | Map of `entityType` (string) → `count` (integer). Only types with count ≥ 1 are included. |

> [!WARNING]
> On worlds with very high entity counts (10,000+), this endpoint may take slightly longer to respond as it iterates over all loaded entities to produce the summary. Consider caching results client-side if polling frequently.

### Error Responses

<details>
<summary><code>401 Unauthorized</code> — Missing or invalid API key</summary>

```json
{
  "success": false,
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Missing or invalid Authorization header. Provide a valid Bearer token.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>403 Forbidden</code> — Insufficient scope</summary>

```json
{
  "success": false,
  "error": {
    "code": "FORBIDDEN",
    "message": "Your API key does not have the required scope: read:worlds.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>404 Not Found</code> — World not found</summary>

```json
{
  "success": false,
  "error": {
    "code": "WORLD_NOT_FOUND",
    "message": "No loaded world found with name: 'my_missing_world'.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>429 Too Many Requests</code> — Rate limited</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Rate limit exceeded. Please wait before making another request.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>500 Internal Server Error</code> — Server error</summary>

```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_ERROR",
    "message": "An unexpected error occurred while processing the request.",
    "timestamp": 1717600000000
  }
}
```

</details>

---

## `GET /worlds/{name}/chunks`

**Loaded Chunk Count**

Returns the number of chunks currently loaded in memory for the specified world. Each chunk is a 16×16×384 block column. The loaded chunk count is a key indicator of server memory usage and processing load — more loaded chunks mean more entities being ticked, more block updates, and higher RAM consumption.

### Authentication

| Header | Required Scope |
|---|---|
| `Authorization: Bearer <api-key>` | `read:worlds` |

### Path Parameters

| Parameter | Type | Required | Description |
|---|---|---|---|
| `name` | `string` | Yes | The name of the world. Case-sensitive. |

### Example Request

```bash
curl -X GET "http://localhost:7890/api/v1/worlds/world/chunks" \
  -H "Authorization: Bearer your-api-key-here" \
  -H "Accept: application/json"
```

### Success Response

**Status:** `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "world": "world",
    "loadedChunks": 625
  }
}
```

| Field | Type | Description |
|---|---|---|
| `world` | `string` | The world name for confirmation. |
| `loadedChunks` | `integer` | Number of chunks currently loaded in memory for this world. |

> [!TIP]
> A single player with a view distance of 10 will keep approximately `(10×2+1)² = 441` chunks loaded. Use this endpoint to monitor chunk sprawl when many players are spread across the map.

### Error Responses

<details>
<summary><code>401 Unauthorized</code> — Missing or invalid API key</summary>

```json
{
  "success": false,
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Missing or invalid Authorization header. Provide a valid Bearer token.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>403 Forbidden</code> — Insufficient scope</summary>

```json
{
  "success": false,
  "error": {
    "code": "FORBIDDEN",
    "message": "Your API key does not have the required scope: read:worlds.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>404 Not Found</code> — World not found</summary>

```json
{
  "success": false,
  "error": {
    "code": "WORLD_NOT_FOUND",
    "message": "No loaded world found with name: 'my_missing_world'.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>429 Too Many Requests</code> — Rate limited</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Rate limit exceeded. Please wait before making another request.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>500 Internal Server Error</code> — Server error</summary>

```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_ERROR",
    "message": "An unexpected error occurred while processing the request.",
    "timestamp": 1717600000000
  }
}
```

</details>

---

## `GET /worlds/{name}/border`

**World Border Configuration**

Returns the current world border configuration for the specified world. The world border defines the playable area boundary — players outside it take damage, and chunks beyond it are not generated. This endpoint returns the border's center coordinates, current size, damage parameters, and warning settings.

### Authentication

| Header | Required Scope |
|---|---|
| `Authorization: Bearer <api-key>` | `read:worlds` |

### Path Parameters

| Parameter | Type | Required | Description |
|---|---|---|---|
| `name` | `string` | Yes | The name of the world. Case-sensitive. |

### Example Request

```bash
curl -X GET "http://localhost:7890/api/v1/worlds/world/border" \
  -H "Authorization: Bearer your-api-key-here" \
  -H "Accept: application/json"
```

### Success Response

**Status:** `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "world": "world",
    "centerX": 0.0,
    "centerZ": 0.0,
    "size": 60000000.0,
    "damageAmount": 0.2,
    "damageBuffer": 5.0,
    "warningDistance": 5,
    "warningTime": 15
  }
}
```

| Field | Type | Description |
|---|---|---|
| `world` | `string` | The world name for confirmation. |
| `centerX` | `double` | X-coordinate of the world border center. |
| `centerZ` | `double` | Z-coordinate of the world border center. |
| `size` | `double` | Total diameter of the world border in blocks. Default is `60000000.0` (60 million). |
| `damageAmount` | `double` | Damage dealt per block per second when a player is outside the border beyond the buffer. |
| `damageBuffer` | `double` | Distance in blocks outside the border before damage begins. |
| `warningDistance` | `integer` | Distance in blocks from the border at which the player's screen turns red. |
| `warningTime` | `integer` | Time in seconds before a shrinking border reaches the player at which the screen turns red. |

> [!NOTE]
> The `size` value represents the full diameter, not the radius. A `size` of `1000.0` means the border extends 500 blocks in each direction from the center. The default value of `60000000.0` effectively means no border restriction.

### Error Responses

<details>
<summary><code>401 Unauthorized</code> — Missing or invalid API key</summary>

```json
{
  "success": false,
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Missing or invalid Authorization header. Provide a valid Bearer token.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>403 Forbidden</code> — Insufficient scope</summary>

```json
{
  "success": false,
  "error": {
    "code": "FORBIDDEN",
    "message": "Your API key does not have the required scope: read:worlds.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>404 Not Found</code> — World not found</summary>

```json
{
  "success": false,
  "error": {
    "code": "WORLD_NOT_FOUND",
    "message": "No loaded world found with name: 'my_missing_world'.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>429 Too Many Requests</code> — Rate limited</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Rate limit exceeded. Please wait before making another request.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>500 Internal Server Error</code> — Server error</summary>

```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_ERROR",
    "message": "An unexpected error occurred while processing the request.",
    "timestamp": 1717600000000
  }
}
```

</details>

---

## `GET /worlds/{name}/gamerules`

**All Game Rules**

Returns all game rules and their current values for the specified world. Game rules control fundamental world mechanics — mob spawning, fire spread, daylight cycle, keep inventory, command block output, and more. Values are returned as strings since game rules can be either boolean or integer types.

### Authentication

| Header | Required Scope |
|---|---|
| `Authorization: Bearer <api-key>` | `read:worlds` |

### Path Parameters

| Parameter | Type | Required | Description |
|---|---|---|---|
| `name` | `string` | Yes | The name of the world. Case-sensitive. |

### Example Request

```bash
curl -X GET "http://localhost:7890/api/v1/worlds/world/gamerules" \
  -H "Authorization: Bearer your-api-key-here" \
  -H "Accept: application/json"
```

### Success Response

**Status:** `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "world": "world",
    "gamerules": {
      "announceAdvancements": "true",
      "commandBlockOutput": "true",
      "disableElytraMovementCheck": "false",
      "disableRaids": "false",
      "doDaylightCycle": "true",
      "doEntityDrops": "true",
      "doFireTick": "true",
      "doImmediateRespawn": "false",
      "doInsomnia": "true",
      "doLimitedCrafting": "false",
      "doMobLoot": "true",
      "doMobSpawning": "true",
      "doPatrolSpawning": "true",
      "doTileDrops": "true",
      "doTraderSpawning": "true",
      "doWardenSpawning": "true",
      "doWeatherCycle": "true",
      "drowningDamage": "true",
      "fallDamage": "true",
      "fireDamage": "true",
      "forgiveDeadPlayers": "true",
      "freezeDamage": "true",
      "keepInventory": "false",
      "logAdminCommands": "true",
      "maxCommandChainLength": "65536",
      "maxEntityCramming": "24",
      "mobGriefing": "true",
      "naturalRegeneration": "true",
      "playersSleepingPercentage": "100",
      "randomTickSpeed": "3",
      "reducedDebugInfo": "false",
      "sendCommandFeedback": "true",
      "showDeathMessages": "true",
      "spawnRadius": "10",
      "spectatorsGenerateChunks": "true",
      "universalAnger": "false"
    }
  }
}
```

| Field | Type | Description |
|---|---|---|
| `world` | `string` | The world name for confirmation. |
| `gamerules` | `object` | Map of `gameruleName` (string) → `value` (string). All values are strings — parse `"true"`/`"false"` as booleans and numeric strings as integers client-side. |

> [!IMPORTANT]
> The set of game rules varies by Minecraft version. Newer server versions may include additional rules not listed in this example. Always handle unknown keys gracefully in your client implementation.

> [!TIP]
> Key game rules for server admins to monitor:
> - `keepInventory` — whether players lose items on death
> - `mobGriefing` — whether mobs can modify blocks (Creeper explosions, Endermen picking up blocks)
> - `randomTickSpeed` — controls crop growth and leaf decay speed (default `3`)
> - `playersSleepingPercentage` — percentage of players needed to skip night

### Error Responses

<details>
<summary><code>401 Unauthorized</code> — Missing or invalid API key</summary>

```json
{
  "success": false,
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Missing or invalid Authorization header. Provide a valid Bearer token.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>403 Forbidden</code> — Insufficient scope</summary>

```json
{
  "success": false,
  "error": {
    "code": "FORBIDDEN",
    "message": "Your API key does not have the required scope: read:worlds.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>404 Not Found</code> — World not found</summary>

```json
{
  "success": false,
  "error": {
    "code": "WORLD_NOT_FOUND",
    "message": "No loaded world found with name: 'my_missing_world'.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>429 Too Many Requests</code> — Rate limited</summary>

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Rate limit exceeded. Please wait before making another request.",
    "timestamp": 1717600000000
  }
}
```

</details>

<details>
<summary><code>500 Internal Server Error</code> — Server error</summary>

```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_ERROR",
    "message": "An unexpected error occurred while processing the request.",
    "timestamp": 1717600000000
  }
}
```

</details>

---

## Rate Limiting

All World endpoints share the same rate limit pool. When the limit is exceeded, the API returns a `429` status code. Implement exponential backoff in your client to handle rate limiting gracefully.

| Header | Description |
|---|---|
| `X-RateLimit-Limit` | Maximum requests allowed in the current window. |
| `X-RateLimit-Remaining` | Requests remaining in the current window. |
| `X-RateLimit-Reset` | Unix timestamp (seconds) when the rate limit window resets. |

---

## World Name Reference

World names are case-sensitive and must match exactly. The default Minecraft server worlds are:

| World Name | Environment | Description |
|---|---|---|
| `world` | `NORMAL` | The Overworld — default survival world. |
| `world_nether` | `NETHER` | The Nether dimension. |
| `world_the_end` | `THE_END` | The End dimension. |

> [!NOTE]
> Custom worlds created by plugins (e.g., Multiverse-Core) will use whatever name was configured at creation time. Use the [`GET /worlds`](#get-worlds) endpoint to discover all available world names.

---

*NaturalAPI Documentation — World Endpoints*
