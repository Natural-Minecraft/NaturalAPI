# Vault Endpoints

Base Path: `/api/v1/vault`

Provides access to Vault-integrated economy and permission data. Vault acts as a bridge between NaturalAPI and your installed economy plugin (e.g., EssentialsX Economy) and permission plugin (e.g., LuckPerms, GroupManager).

> [!IMPORTANT]
> The **Vault** plugin must be installed and enabled on the Minecraft server for these endpoints to function. If Vault is not available, all endpoints in this group will return a `503 Service Unavailable` response with error code `VAULT_UNAVAILABLE`.

---

## Authentication

All endpoints in this group require a valid API key with the **`read:vault`** scope.

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

### `GET /vault/player/{uuid}`

Retrieves the full Vault data for a specific player, including their primary permission group, all assigned groups, chat prefix/suffix, economy balance, and the names of the underlying permission and economy plugins.

#### Path Parameters

| Parameter | Type   | Required | Description                                                                 |
|-----------|--------|----------|-----------------------------------------------------------------------------|
| `uuid`    | string | Yes      | The UUID of the player (with dashes). Example: `069a79f4-44e9-4726-a5be-fca90e38aaf5` |

#### Request

```bash
curl -X GET "http://<server-ip>:7890/api/v1/vault/player/069a79f4-44e9-4726-a5be-fca90e38aaf5" \
  -H "Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Accept: application/json"
```

#### Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "group": "admin",
    "groups": [
      "admin",
      "moderator",
      "default"
    ],
    "prefix": "§c[Admin] ",
    "suffix": " §7✦",
    "balance": 15000.50,
    "currency": "Dollars",
    "permissionPlugin": "LuckPerms",
    "economyPlugin": "EssentialsX"
  }
}
```

#### Response Fields

| Field              | Type       | Nullable | Description                                                                 |
|--------------------|------------|----------|-----------------------------------------------------------------------------|
| `group`            | `string`   | Yes      | The player's primary permission group as reported by Vault.                 |
| `groups`           | `string[]` | Yes      | All permission groups the player belongs to, including inherited groups.    |
| `prefix`           | `string`   | Yes      | The player's chat prefix. May contain `§` color codes or MiniMessage tags. |
| `suffix`           | `string`   | Yes      | The player's chat suffix. May contain `§` color codes or MiniMessage tags. |
| `balance`          | `number`   | Yes      | The player's economy balance (double precision). `null` if no economy plugin is hooked. |
| `currency`         | `string`   | Yes      | The name of the currency (e.g., `"Dollars"`, `"Coins"`). `null` if no economy plugin is hooked. |
| `permissionPlugin` | `string`   | Yes      | The name of the permission plugin backing Vault (e.g., `"LuckPerms"`, `"GroupManager"`). |
| `economyPlugin`    | `string`   | Yes      | The name of the economy plugin backing Vault (e.g., `"EssentialsX"`, `"CMI"`). |

> [!NOTE]
> Fields return `null` when the corresponding Vault provider is not registered. For example, if no economy plugin is installed, `balance`, `currency`, and `economyPlugin` will all be `null`, but permission-related fields (`group`, `groups`, `prefix`, `suffix`) may still be populated if a permission plugin is hooked.

#### Response — Player with no Economy Plugin

If Vault is installed but no economy plugin is hooked:

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "group": "default",
    "groups": [
      "default"
    ],
    "prefix": "§7[Member] ",
    "suffix": null,
    "balance": null,
    "currency": null,
    "permissionPlugin": "LuckPerms",
    "economyPlugin": null
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

| HTTP Status | Error Code            | Description                                                                                     |
|-------------|-----------------------|-------------------------------------------------------------------------------------------------|
| `401`       | `INVALID_TOKEN`       | The `Authorization` header is missing, malformed, or contains an invalid/expired API key.       |
| `403`       | `INSUFFICIENT_SCOPE`  | The API key is valid but does not have the `read:vault` scope.                                  |
| `404`       | `PLAYER_NOT_FOUND`    | No player exists with the given UUID, or the player has never joined the server.                |
| `429`       | `RATE_LIMITED`         | Too many requests. The rate limit (default 120/min) has been exceeded for this IP.              |
| `503`       | `VAULT_UNAVAILABLE`   | The Vault plugin is not installed or not enabled on the server.                                 |

### Error Examples

#### `401 Unauthorized` — Missing or Invalid Token

```bash
curl -X GET "http://<server-ip>:7890/api/v1/vault/player/069a79f4-44e9-4726-a5be-fca90e38aaf5" \
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
# API key only has "read:server" scope, missing "read:vault"
curl -X GET "http://<server-ip>:7890/api/v1/vault/player/069a79f4-44e9-4726-a5be-fca90e38aaf5" \
  -H "Authorization: Bearer napi_serveronly_key_without_vault" \
  -H "Accept: application/json"
```

```json
{
  "success": false,
  "error": {
    "code": "INSUFFICIENT_SCOPE",
    "message": "API key does not have the required scope: read:vault",
    "timestamp": 1717600000000
  }
}
```

#### `404 Not Found` — Player Not Found

```bash
curl -X GET "http://<server-ip>:7890/api/v1/vault/player/00000000-0000-0000-0000-000000000000" \
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

#### `503 Service Unavailable` — Vault Not Installed

```bash
curl -X GET "http://<server-ip>:7890/api/v1/vault/player/069a79f4-44e9-4726-a5be-fca90e38aaf5" \
  -H "Authorization: Bearer napi_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
  -H "Accept: application/json"
```

```json
{
  "success": false,
  "error": {
    "code": "VAULT_UNAVAILABLE",
    "message": "Vault plugin is not installed or not enabled on this server",
    "timestamp": 1717600000000
  }
}
```

---

## Tips & Notes

> [!TIP]
> **Looking up a player's UUID?** Use the Players endpoint `GET /players/name/{username}` to resolve a username to a UUID before querying Vault data.

> [!NOTE]
> The Vault API returns data from whatever permission and economy plugins are hooked into Vault. The actual data (group names, prefix format, balance precision) depends on the underlying plugin configuration — NaturalAPI simply passes through what Vault reports.

> [!TIP]
> If you need more granular permission data (individual permission nodes, meta, contexts), use the [LuckPerms endpoints](endpoints-luckperms.md) instead. Vault provides a simplified, plugin-agnostic view.

---

## See Also

- [LuckPerms Endpoints](endpoints-luckperms.md) — Detailed LuckPerms permission data
- [PlaceholderAPI Endpoints](endpoints-papi.md) — Evaluate arbitrary placeholders including `%vault_eco_balance%`
- [Player Endpoints](endpoints-players.md) — Full player data (includes embedded Vault data at `GET /players/{uuid}`)
