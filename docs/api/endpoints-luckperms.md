# LuckPerms Endpoints

Base Path: `/api/v1/luckperms`

Provides direct access to LuckPerms permission data, including a player's primary group, parent groups, individual permission nodes, metadata, weight, and contexts. This endpoint exposes richer, more granular data than the Vault permission abstraction layer.

> [!IMPORTANT]
> The **LuckPerms** plugin must be installed and enabled on the Minecraft server for these endpoints to function. If LuckPerms is not available, all endpoints in this group will return a `503 Service Unavailable` response with error code `LUCKPERMS_UNAVAILABLE`.

---

## Authentication

All endpoints in this group require a valid API key with the **`read:luckperms`** scope.

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

### `GET /luckperms/player/{uuid}`

Retrieves the full LuckPerms permission data for a specific player. This includes their primary group, all directly assigned parent groups, inherited groups (resolved from the group hierarchy), every permission node with its value and context, all metadata key-value pairs, and the player's calculated weight.

#### Path Parameters

| Parameter | Type   | Required | Description                                                                 |
|-----------|--------|----------|-----------------------------------------------------------------------------|
| `uuid`    | string | Yes      | The UUID of the player (with dashes). Example: `069a79f4-44e9-4726-a5be-fca90e38aaf5` |

#### Request

```bash
curl -X GET "http://<server-ip>:7890/api/v1/luckperms/player/069a79f4-44e9-4726-a5be-fca90e38aaf5" \
  -H "Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Accept: application/json"
```

#### Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "primaryGroup": "admin",
    "parentGroups": [
      "admin",
      "moderator"
    ],
    "inheritedGroups": [
      "admin",
      "moderator",
      "helper",
      "vip",
      "default"
    ],
    "permissionNodes": [
      {
        "node": "minecraft.command.gamemode",
        "value": true,
        "context": {}
      },
      {
        "node": "essentials.fly",
        "value": true,
        "context": {}
      },
      {
        "node": "worldedit.*",
        "value": true,
        "context": {
          "world": "creative"
        }
      },
      {
        "node": "essentials.kits.daily",
        "value": true,
        "context": {
          "server": "survival"
        }
      },
      {
        "node": "some.denied.node",
        "value": false,
        "context": {}
      }
    ],
    "meta": {
      "prefix": "§c[Admin] ",
      "suffix": " §7✦",
      "home-limit": "10",
      "fly-speed": "2"
    },
    "weight": 100
  }
}
```

#### Response Fields

| Field              | Type       | Nullable | Description                                                                                                   |
|--------------------|------------|----------|---------------------------------------------------------------------------------------------------------------|
| `primaryGroup`     | `string`   | No       | The player's primary group in LuckPerms.                                                                      |
| `parentGroups`     | `string[]` | No       | Groups directly assigned to the player (not inherited). These are the groups explicitly set on the player.     |
| `inheritedGroups`  | `string[]` | No       | All groups the player is a member of, including indirectly inherited groups resolved from the full hierarchy.  |
| `permissionNodes`  | `array`    | No       | All permission nodes assigned to the player (both directly and through group inheritance).                     |
| `meta`             | `object`   | No       | Key-value metadata map. Includes `prefix`, `suffix`, and any custom meta keys defined in LuckPerms.           |
| `weight`           | `integer`  | Yes      | The calculated weight/priority of the player's primary group. `null` if no weight is configured.               |

#### Permission Node Object

Each entry in the `permissionNodes` array has the following structure:

| Field     | Type      | Description                                                                                                |
|-----------|-----------|------------------------------------------------------------------------------------------------------------|
| `node`    | `string`  | The permission node string (e.g., `"essentials.fly"`, `"worldedit.*"`).                                    |
| `value`   | `boolean` | Whether the node is granted (`true`) or explicitly denied (`false`).                                       |
| `context` | `object`  | A map of context key-value pairs restricting when this node applies. Empty `{}` means the node is global.  |

> [!NOTE]
> **Context examples:** Common context keys include `world` (restrict to a specific world), `server` (restrict to a specific server in a network), and `gamemode`. An empty context `{}` means the permission applies globally without restrictions.

#### Response — Player with Minimal Permissions

A player in the default group with no custom nodes or meta:

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "primaryGroup": "default",
    "parentGroups": [
      "default"
    ],
    "inheritedGroups": [
      "default"
    ],
    "permissionNodes": [],
    "meta": {
      "prefix": "§7[Member] ",
      "suffix": ""
    },
    "weight": 0
  }
}
```

#### Response — Player with Context-Specific Permissions

A player who has world-specific and server-specific permission nodes:

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "primaryGroup": "builder",
    "parentGroups": [
      "builder",
      "vip"
    ],
    "inheritedGroups": [
      "builder",
      "vip",
      "default"
    ],
    "permissionNodes": [
      {
        "node": "worldedit.*",
        "value": true,
        "context": {
          "world": "creative"
        }
      },
      {
        "node": "essentials.fly",
        "value": true,
        "context": {
          "server": "lobby"
        }
      },
      {
        "node": "essentials.nick",
        "value": true,
        "context": {}
      }
    ],
    "meta": {
      "prefix": "§a[Builder] ",
      "suffix": "",
      "home-limit": "5"
    },
    "weight": 50
  }
}
```

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

| HTTP Status | Error Code               | Description                                                                                     |
|-------------|--------------------------|------------------------------------------------------------------------------------------------|
| `401`       | `INVALID_TOKEN`          | The `Authorization` header is missing, malformed, or contains an invalid/expired API key.      |
| `403`       | `INSUFFICIENT_SCOPE`     | The API key is valid but does not have the `read:luckperms` scope.                             |
| `404`       | `PLAYER_NOT_FOUND`       | No player exists with the given UUID, or the player has never joined the server.               |
| `429`       | `RATE_LIMITED`            | Too many requests. The rate limit (default 120/min) has been exceeded for this IP.             |
| `503`       | `LUCKPERMS_UNAVAILABLE`  | The LuckPerms plugin is not installed or not enabled on the server.                            |

### Error Examples

#### `401 Unauthorized` — Missing or Invalid Token

```bash
curl -X GET "http://<server-ip>:7890/api/v1/luckperms/player/069a79f4-44e9-4726-a5be-fca90e38aaf5" \
  -H "Accept: application/json"
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
# API key only has "read:server" scope, missing "read:luckperms"
curl -X GET "http://<server-ip>:7890/api/v1/luckperms/player/069a79f4-44e9-4726-a5be-fca90e38aaf5" \
  -H "Authorization: Bearer napi_serveronly_key_without_luckperms" \
  -H "Accept: application/json"
```

```json
{
  "success": false,
  "error": {
    "code": "INSUFFICIENT_SCOPE",
    "message": "API key does not have the required scope: read:luckperms",
    "timestamp": 1717600000000
  }
}
```

#### `404 Not Found` — Player Not Found

```bash
curl -X GET "http://<server-ip>:7890/api/v1/luckperms/player/00000000-0000-0000-0000-000000000000" \
  -H "Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Accept: application/json"
```

```json
{
  "success": false,
  "error": {
    "code": "PLAYER_NOT_FOUND",
    "message": "No player found with UUID: 00000000-0000-0000-0000-000000000000",
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

#### `503 Service Unavailable` — LuckPerms Not Installed

```bash
curl -X GET "http://<server-ip>:7890/api/v1/luckperms/player/069a79f4-44e9-4726-a5be-fca90e38aaf5" \
  -H "Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Accept: application/json"
```

```json
{
  "success": false,
  "error": {
    "code": "LUCKPERMS_UNAVAILABLE",
    "message": "LuckPerms plugin is not installed or not enabled on this server",
    "timestamp": 1717600000000
  }
}
```

---

## Tips & Notes

> [!TIP]
> **`parentGroups` vs `inheritedGroups`:** Use `parentGroups` when you need only the groups directly assigned to the player. Use `inheritedGroups` for the full resolved list including all groups inherited through the LuckPerms group hierarchy (e.g., if `admin` inherits `moderator` which inherits `helper`).

> [!NOTE]
> **Permission node values:** A `value` of `false` means the permission is **explicitly denied** (negated). This is different from the permission simply not being set. Negated nodes are commonly used to override inherited permissions from parent groups.

> [!TIP]
> **Looking up a player's UUID?** Use the Players endpoint `GET /players/name/{username}` to resolve a username to a UUID before querying LuckPerms data.

> [!NOTE]
> The `meta` object contains **all** metadata defined in LuckPerms for the player's resolved context, including `prefix` and `suffix` if they are set as meta values. Custom meta keys (e.g., `home-limit`, `fly-speed`) are also included.

> [!TIP]
> For a simplified, plugin-agnostic view of permission groups and prefixes, use the [Vault endpoints](endpoints-vault.md) instead. The LuckPerms endpoint is best when you need full node-level detail, contexts, and metadata.

---

## See Also

- [Vault Endpoints](endpoints-vault.md) — Simplified permission and economy data via Vault abstraction
- [PlaceholderAPI Endpoints](endpoints-papi.md) — Evaluate LuckPerms placeholders like `%luckperms_primary_group%`
- [Player Endpoints](endpoints-players.md) — Full player data (includes embedded LuckPerms data at `GET /players/{uuid}`)
