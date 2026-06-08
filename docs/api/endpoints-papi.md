# PlaceholderAPI (PAPI) Endpoints

Base Path: `/api/v1/papi`

Provides the ability to evaluate PlaceholderAPI placeholders for a specific player via the REST API. This allows external dashboards, bots, and applications to resolve dynamic placeholder values (e.g., player health, balance, rank, statistics) without connecting to the Minecraft server directly.

> [!IMPORTANT]
> The **PlaceholderAPI** plugin must be installed and enabled on the Minecraft server for these endpoints to function. If PlaceholderAPI is not available, all endpoints in this group will return a `503 Service Unavailable` response with error code `PAPI_UNAVAILABLE`.

---

## Authentication

All endpoints in this group require a valid API key with the **`read:papi`** scope.

```
Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

---

## Rate Limiting

Default: **120 requests/minute** per IP. Rate limit headers are included in every response:

| Header                  | Description                              |
|-------------------------|------------------------------------------|
| `X-RateLimit-Limit`     | Maximum requests allowed per minute      |
| `X-RateLimit-Remaining` | Remaining requests in current window     |
| `X-RateLimit-Reset`     | Unix timestamp (ms) of next window reset |

---

## Endpoints

### `POST /papi/evaluate`

Evaluates one or more PlaceholderAPI placeholders in the context of a specific player. The player can be identified by either their **username** or **UUID**. Each placeholder is resolved server-side and the result is returned as a string value.

#### Request Headers

| Header          | Value              | Required | Description                    |
|-----------------|--------------------|----------|--------------------------------|
| `Authorization` | `Bearer <api-key>` | Yes      | API key with `read:papi` scope |
| `Content-Type`  | `application/json` | Yes      | Request body format            |
| `Accept`        | `application/json` | No       | Preferred response format      |

#### Request Body

```json
{
  "player": "Rifqi_",
  "placeholders": [
    "%player_health%",
    "%vault_rank%"
  ]
}
```

#### Request Body Fields

| Field          | Type       | Required | Description                                                                                                                              |
|----------------|------------|----------|------------------------------------------------------------------------------------------------------------------------------------------|
| `player`       | `string`   | Yes      | The player's **username** or **UUID** (with dashes). The player must be online for most placeholders to resolve correctly.               |
| `placeholders` | `string[]` | Yes      | An array of PlaceholderAPI placeholder strings to evaluate. Each must be wrapped in `%` delimiters (e.g., `"%player_health%"`).          |

#### Request

```bash
curl -X POST "http://<server-ip>:7890/api/v1/papi/evaluate" \
  -H "Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "player": "Rifqi_",
    "placeholders": [
      "%player_health%",
      "%vault_rank%"
    ]
  }'
```

#### Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "player": "Rifqi_",
    "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5",
    "results": {
      "%player_health%": "20.0",
      "%vault_rank%": "Admin"
    }
  }
}
```

#### Response Fields

| Field     | Type     | Description                                                                                          |
|-----------|----------|------------------------------------------------------------------------------------------------------|
| `player`  | `string` | The resolved username of the player.                                                                 |
| `uuid`    | `string` | The UUID of the player.                                                                              |
| `results` | `object` | A key-value map where each key is the original placeholder string and each value is the resolved result as a string. |

#### Extended Example — Multiple Placeholders

```bash
curl -X POST "http://<server-ip>:7890/api/v1/papi/evaluate" \
  -H "Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Content-Type: application/json" \
  -d '{
    "player": "Notch",
    "placeholders": [
      "%player_health%",
      "%player_food_level%",
      "%player_world%",
      "%player_x%",
      "%player_y%",
      "%player_z%",
      "%vault_rank%",
      "%vault_eco_balance%",
      "%luckperms_primary_group%",
      "%server_online%",
      "%server_tps_1%"
    ]
  }'
```

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "player": "Notch",
    "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5",
    "results": {
      "%player_health%": "20.0",
      "%player_food_level%": "18",
      "%player_world%": "world",
      "%player_x%": "128",
      "%player_y%": "64",
      "%player_z%": "-256",
      "%vault_rank%": "Admin",
      "%vault_eco_balance%": "15,000.50",
      "%luckperms_primary_group%": "admin",
      "%server_online%": "12",
      "%server_tps_1%": "19.98"
    }
  }
}
```

#### Using UUID Instead of Username

```bash
curl -X POST "http://<server-ip>:7890/api/v1/papi/evaluate" \
  -H "Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Content-Type: application/json" \
  -d '{
    "player": "069a79f4-44e9-4726-a5be-fca90e38aaf5",
    "placeholders": [
      "%player_health%",
      "%vault_rank%"
    ]
  }'
```

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "player": "Notch",
    "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5",
    "results": {
      "%player_health%": "20.0",
      "%vault_rank%": "Admin"
    }
  }
}
```

#### Response — Unresolved Placeholders

If a placeholder expansion is not installed or the placeholder is invalid, PlaceholderAPI returns the placeholder string unchanged:

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "player": "Rifqi_",
    "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5",
    "results": {
      "%player_health%": "20.0",
      "%nonexistent_placeholder%": "%nonexistent_placeholder%"
    }
  }
}
```

> [!NOTE]
> When a placeholder cannot be resolved (e.g., the required PAPI expansion is not installed), the result value will be the **original placeholder string** itself. This is standard PlaceholderAPI behavior — it does not throw an error, it simply returns the input unchanged. Check if the result equals the input to detect unresolved placeholders.

---

## Error Responses

All error responses follow the standard NaturalAPI error envelope:

```json
{
  "success": false,
  "error": {
    "code": "ERROR_CODE",
    "message": "Human-readable description of the error",
    "timestamp": 1717600000000
  }
}
```

### Error Codes

| HTTP Status | Error Code           | Description                                                                                     |
|-------------|----------------------|-------------------------------------------------------------------------------------------------|
| `400`       | `INVALID_REQUEST`    | The request body is missing, malformed, or fails validation (e.g., missing `player` or `placeholders` field). |
| `401`       | `INVALID_TOKEN`      | The `Authorization` header is missing, malformed, or contains an invalid/expired API key.       |
| `403`       | `INSUFFICIENT_SCOPE` | The API key is valid but does not have the `read:papi` scope.                                   |
| `404`       | `PLAYER_NOT_FOUND`   | No online player found matching the given username or UUID.                                     |
| `429`       | `RATE_LIMITED`        | Too many requests. The rate limit (default 120/min) has been exceeded for this IP.              |
| `503`       | `PAPI_UNAVAILABLE`   | The PlaceholderAPI plugin is not installed or not enabled on the server.                        |

### Error Examples

#### `400 Bad Request` — Missing Required Fields

```bash
curl -X POST "http://<server-ip>:7890/api/v1/papi/evaluate" \
  -H "Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Content-Type: application/json" \
  -d '{}'
```

```json
{
  "success": false,
  "error": {
    "code": "INVALID_REQUEST",
    "message": "Missing required field: 'player'",
    "timestamp": 1717600000000
  }
}
```

#### `400 Bad Request` — Empty Placeholders Array

```bash
curl -X POST "http://<server-ip>:7890/api/v1/papi/evaluate" \
  -H "Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Content-Type: application/json" \
  -d '{"player": "Notch", "placeholders": []}'
```

```json
{
  "success": false,
  "error": {
    "code": "INVALID_REQUEST",
    "message": "Field 'placeholders' must contain at least one placeholder string",
    "timestamp": 1717600000000
  }
}
```

#### `400 Bad Request` — Invalid JSON

```bash
curl -X POST "http://<server-ip>:7890/api/v1/papi/evaluate" \
  -H "Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Content-Type: application/json" \
  -d 'not valid json'
```

```json
{
  "success": false,
  "error": {
    "code": "INVALID_REQUEST",
    "message": "Request body is not valid JSON",
    "timestamp": 1717600000000
  }
}
```

#### `401 Unauthorized` — Missing or Invalid Token

```bash
curl -X POST "http://<server-ip>:7890/api/v1/papi/evaluate" \
  -H "Content-Type: application/json" \
  -d '{"player": "Notch", "placeholders": ["%player_health%"]}'
```

```json
{
  "success": false,
  "error": {
    "code": "INVALID_TOKEN",
    "message": "Bearer token not found or invalid",
    "timestamp": 1717600000000
  }
}
```

#### `403 Forbidden` — Insufficient Scope

```bash
# API key only has "read:server" scope, missing "read:papi"
curl -X POST "http://<server-ip>:7890/api/v1/papi/evaluate" \
  -H "Authorization: Bearer napi_serveronly_key_without_papi" \
  -H "Content-Type: application/json" \
  -d '{"player": "Notch", "placeholders": ["%player_health%"]}'
```

```json
{
  "success": false,
  "error": {
    "code": "INSUFFICIENT_SCOPE",
    "message": "API key does not have the required scope: read:papi",
    "timestamp": 1717600000000
  }
}
```

#### `404 Not Found` — Player Not Found / Not Online

```bash
curl -X POST "http://<server-ip>:7890/api/v1/papi/evaluate" \
  -H "Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Content-Type: application/json" \
  -d '{"player": "NonExistentPlayer123", "placeholders": ["%player_health%"]}'
```

```json
{
  "success": false,
  "error": {
    "code": "PLAYER_NOT_FOUND",
    "message": "No online player found with name: NonExistentPlayer123",
    "timestamp": 1717600000000
  }
}
```

#### `429 Too Many Requests` — Rate Limited

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMITED",
    "message": "Rate limit exceeded. Try again in 45 seconds.",
    "timestamp": 1717600000000
  }
}
```

Response headers when rate limited:

```
X-RateLimit-Limit: 120
X-RateLimit-Remaining: 0
X-RateLimit-Reset: 1717600045000
```

#### `503 Service Unavailable` — PlaceholderAPI Not Installed

```bash
curl -X POST "http://<server-ip>:7890/api/v1/papi/evaluate" \
  -H "Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Content-Type: application/json" \
  -d '{"player": "Notch", "placeholders": ["%player_health%"]}'
```

```json
{
  "success": false,
  "error": {
    "code": "PAPI_UNAVAILABLE",
    "message": "PlaceholderAPI plugin is not installed or not enabled on this server",
    "timestamp": 1717600000000
  }
}
```

---

## Common Placeholders

Here are some commonly used PlaceholderAPI placeholders for reference. The actual availability depends on which PAPI expansions are installed on the server.

### Built-in Player Placeholders

| Placeholder              | Example Output | Description                      |
|--------------------------|----------------|----------------------------------|
| `%player_name%`          | `Notch`        | Player's username                |
| `%player_display_name%`  | `§cNotch`      | Player's display name with color |
| `%player_uuid%`          | `069a79f4-...` | Player's UUID                    |
| `%player_health%`        | `20.0`         | Current health (0–20)            |
| `%player_max_health%`    | `20.0`         | Maximum health                   |
| `%player_food_level%`    | `18`           | Current food level (0–20)        |
| `%player_world%`         | `world`        | Current world name               |
| `%player_x%`             | `128`          | X coordinate                     |
| `%player_y%`             | `64`           | Y coordinate                     |
| `%player_z%`             | `-256`         | Z coordinate                     |
| `%player_gamemode%`      | `SURVIVAL`     | Current gamemode                 |
| `%player_ping%`          | `42`           | Ping in milliseconds             |

### Vault Expansion Placeholders

| Placeholder              | Example Output | Description                      |
|--------------------------|----------------|----------------------------------|
| `%vault_rank%`           | `Admin`        | Primary Vault group              |
| `%vault_prefix%`         | `§c[Admin] `   | Vault chat prefix                |
| `%vault_suffix%`         | ` §7✦`         | Vault chat suffix                |
| `%vault_eco_balance%`    | `15,000.50`    | Economy balance (formatted)      |

### LuckPerms Expansion Placeholders

| Placeholder                        | Example Output | Description                         |
|------------------------------------|----------------|-------------------------------------|
| `%luckperms_primary_group%`        | `admin`        | Primary LuckPerms group             |
| `%luckperms_prefix%`               | `§c[Admin] `   | LuckPerms prefix                    |
| `%luckperms_suffix%`               | ` §7✦`         | LuckPerms suffix                    |
| `%luckperms_meta_<key>%`           | `10`           | Custom meta value by key            |
| `%luckperms_has_permission_<node>%`| `true`         | Check if player has a specific node |

### Server Placeholders

| Placeholder         | Example Output | Description                      |
|---------------------|----------------|----------------------------------|
| `%server_online%`   | `12`           | Online player count              |
| `%server_max%`      | `100`          | Max player slots                 |
| `%server_tps_1%`    | `19.98`        | Server TPS (1 minute average)    |
| `%server_ram_used%` | `2048`         | RAM used in MB                   |

> [!TIP]
> To see which PAPI expansions are installed on the server, use `GET /papi/plugins` to list all registered expansions.

---

## Tips & Notes

> [!TIP]
> **Batch your requests.** Instead of making separate API calls for each placeholder, include all desired placeholders in a single `placeholders` array. This is much more efficient as it only requires one server-side player lookup.

> [!WARNING]
> **The player must be online** for most player-specific placeholders to resolve. If the player is offline, most placeholders will either return the raw placeholder string unchanged or return a default/empty value, depending on the expansion.

> [!NOTE]
> All placeholder results are returned as **strings**, even if the underlying value is numeric. Your client application should handle type conversion as needed (e.g., parsing `"20.0"` as a float for health).

> [!TIP]
> **Detecting unresolved placeholders:** Compare the result value with the input placeholder key. If they are identical (e.g., `"%nonexistent_placeholder%"` → `"%nonexistent_placeholder%"`), the placeholder was not resolved. This typically means the required PAPI expansion is not installed.

---

## See Also

- [Vault Endpoints](endpoints-vault.md) — Direct Vault permission and economy data
- [LuckPerms Endpoints](endpoints-luckperms.md) — Direct LuckPerms permission data
- [Player Endpoints](endpoints-players.md) — Full player data (alternative to evaluating player-related placeholders individually)
