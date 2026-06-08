# NaturalAPI — Admin Endpoints

> **Base URL:** `http://<server-ip>:7890/api/v1`

This document covers all administrative endpoints exposed by the NaturalAPI plugin.
Admin endpoints allow you to manage API keys, view configuration, monitor rate limits,
control plugin lifecycle, and inspect snapshot history.

---

## Table of Contents

- [Authentication](#authentication)
- [Response Format](#response-format)
- [Scopes Reference](#scopes-reference)
- [Endpoints](#endpoints)
  - [Health Check](#1-health-check)
  - [OpenAPI Spec (YAML)](#2-openapi-spec-yaml)
  - [OpenAPI Spec (JSON)](#3-openapi-spec-json)
  - [List API Keys](#4-list-api-keys)
  - [Generate API Key](#5-generate-api-key)
  - [Revoke API Key](#6-revoke-api-key)
  - [View Config](#7-view-config)
  - [Reload Plugin](#8-reload-plugin)
  - [View Rate Limits](#9-view-rate-limits)
  - [Reset Rate Limit](#10-reset-rate-limit)
  - [Snapshot History](#11-snapshot-history)
  - [Purge Snapshots](#12-purge-snapshots)

---

## Authentication

Most admin endpoints require an API key with the **`admin`** scope. Pass the key via the
`Authorization` header using the `Bearer` scheme:

```
Authorization: Bearer <id>.<secret>
```

**API Key Format:**

```
a1b2c3d4-e5f6-7890-abcd-ef1234567890.xK9mPqR2sT4uV6wX8yZ0aB3cD5eF7gH
└──────────── id (UUID) ──────────────┘.└──── secret (32 chars) ────────┘
```

Keys are stored **hashed** (SHA-256 + per-key salt) in the database. The raw secret is
shown **only once** at creation time and cannot be retrieved afterward.

> [!CAUTION]
> Store your API key immediately after generation. The raw secret is displayed **only once**
> in the creation response. If lost, you must revoke the key and generate a new one.

Endpoints marked **🔓 Public** do not require authentication.

---

## Response Format

### Success Response

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

### Error Response

All errors follow a consistent structure:

```json
{
  "success": false,
  "error": {
    "code": "ERROR_CODE",
    "message": "Human-readable description of what went wrong.",
    "timestamp": 1717600000000
  }
}
```

### Common Error Codes

| HTTP Status | Error Code              | Description                                       |
|:-----------:|:------------------------|:--------------------------------------------------|
| `400`       | `BAD_REQUEST`           | Malformed request body or missing required fields  |
| `401`       | `UNAUTHORIZED`          | Missing or invalid API key                         |
| `403`       | `FORBIDDEN`             | API key lacks the required scope                   |
| `404`       | `NOT_FOUND`             | Resource or endpoint not found                     |
| `429`       | `RATE_LIMITED`           | Too many requests — rate limit exceeded            |
| `500`       | `INTERNAL_SERVER_ERROR` | Unexpected server-side failure                     |

---

## Scopes Reference

Scopes control what each API key is allowed to access. Assign one or more scopes
(comma-separated) when creating a key.

| Scope            | Description                        |
|:-----------------|:-----------------------------------|
| `read:server`    | Read server stats and info         |
| `read:players`   | Read player data and statistics    |
| `read:worlds`    | Read world data and properties     |
| `read:vault`     | Read Vault economy data            |
| `read:luckperms` | Read LuckPerms permissions data    |
| `read:papi`      | Evaluate PlaceholderAPI placeholders |
| `admin`          | Access admin management endpoints  |
| `*`              | **All scopes** (super key)         |

> [!TIP]
> Follow the principle of least privilege — only grant the scopes each consumer actually
> needs. Use `*` sparingly and only for trusted internal tooling.

---

## Endpoints

---

### 1. Health Check

Check whether the plugin, database, and HTTP server are operational.

| Property       | Value               |
|:---------------|:---------------------|
| **Method**     | `GET`                |
| **Path**       | `/admin/health`      |
| **Auth**       | 🔓 **Public** — No authentication required |
| **Scopes**     | —                    |

#### Description

Returns the current health status of the plugin. Use this for uptime monitoring,
load balancer health probes, or quick connectivity checks. This endpoint is always
public so external monitoring tools can reach it without credentials.

#### curl Example

```bash
curl -s http://localhost:7890/api/v1/admin/health
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "status": "ok",
    "database": "connected",
    "httpServer": "running"
  }
}
```

#### Error Response — `500 Internal Server Error`

Returned if one or more subsystems are unhealthy:

```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_SERVER_ERROR",
    "message": "Database connection is not available.",
    "timestamp": 1717600000000
  }
}
```

---

### 2. OpenAPI Spec (YAML)

Retrieve the full OpenAPI 3.1 specification in YAML format.

| Property       | Value                   |
|:---------------|:------------------------|
| **Method**     | `GET`                   |
| **Path**       | `/admin/openapi.yaml`   |
| **Auth**       | 🔓 **Public** — No authentication required |
| **Scopes**     | —                       |
| **Content-Type** | `application/x-yaml` |

#### Description

Returns the machine-readable OpenAPI 3.1 specification for the entire NaturalAPI surface.
Import this into tools like Swagger UI, Redoc, Postman, or any OpenAPI-compatible client
to explore and test the API interactively.

#### curl Example

```bash
curl -s http://localhost:7890/api/v1/admin/openapi.yaml
```

#### Success Response — `200 OK`

```yaml
openapi: "3.1.0"
info:
  title: "NaturalAPI"
  version: "1.0.0"
  description: "REST API for Minecraft server data"
paths:
  /admin/health:
    get:
      summary: "Plugin health check"
      # ... full spec continues
```

> [!NOTE]
> This endpoint returns raw YAML, **not** the standard JSON envelope. The response
> body is the OpenAPI spec document itself.

---

### 3. OpenAPI Spec (JSON)

Retrieve the full OpenAPI 3.1 specification in JSON format.

| Property       | Value                    |
|:---------------|:-------------------------|
| **Method**     | `GET`                    |
| **Path**       | `/admin/openapi.json`    |
| **Auth**       | 🔓 **Public** — No authentication required |
| **Scopes**     | —                        |
| **Content-Type** | `application/json`    |

#### Description

Identical content to the YAML variant, serialized as JSON. Use whichever format
your tooling prefers.

#### curl Example

```bash
curl -s http://localhost:7890/api/v1/admin/openapi.json
```

#### Success Response — `200 OK`

```json
{
  "openapi": "3.1.0",
  "info": {
    "title": "NaturalAPI",
    "version": "1.0.0",
    "description": "REST API for Minecraft server data"
  },
  "paths": {
    "/admin/health": {
      "get": {
        "summary": "Plugin health check"
      }
    }
  }
}
```

> [!NOTE]
> This endpoint returns the raw OpenAPI JSON document, **not** the standard
> `{"success": true, "data": ...}` envelope.

---

### 4. List API Keys

List all registered API keys and their metadata.

| Property       | Value                |
|:---------------|:---------------------|
| **Method**     | `GET`                |
| **Path**       | `/admin/keys`        |
| **Auth**       | 🔒 Required          |
| **Scopes**     | `admin`              |

#### Description

Returns an array of all API keys currently registered in the system. Each entry
includes the key's ID, friendly name, assigned scopes, timestamps, and enabled status.
For security, the **hashed secret** and **salt** are never included in the response.

#### curl Example

```bash
curl -s http://localhost:7890/api/v1/admin/keys \
  -H "Authorization: Bearer a1b2c3d4-e5f6-7890-abcd-ef1234567890.xK9mPqR2sT4uV6wX8yZ0aB3cD5eF7gH"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": [
    {
      "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
      "name": "my-dashboard",
      "scopes": "read:server,read:players",
      "createdAt": "2026-06-01T10:30:00Z",
      "expiresAt": null,
      "lastUsed": "2026-06-05T08:12:33Z",
      "enabled": true
    },
    {
      "id": "f9e8d7c6-b5a4-3210-fedc-ba0987654321",
      "name": "admin-cli",
      "scopes": "admin",
      "createdAt": "2026-05-20T14:00:00Z",
      "expiresAt": "2027-05-20T14:00:00Z",
      "lastUsed": "2026-06-05T11:45:00Z",
      "enabled": true
    }
  ]
}
```

#### Error Response — `401 Unauthorized`

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

#### Error Response — `403 Forbidden`

```json
{
  "success": false,
  "error": {
    "code": "FORBIDDEN",
    "message": "API key does not have the required scope: admin.",
    "timestamp": 1717600000000
  }
}
```

---

### 5. Generate API Key

Create a new API key with specified scopes and optional expiration.

| Property       | Value                |
|:---------------|:---------------------|
| **Method**     | `POST`               |
| **Path**       | `/admin/keys`        |
| **Auth**       | 🔒 Required          |
| **Scopes**     | `admin`              |
| **Content-Type** | `application/json` |

#### Description

Generates a new API key and returns its full raw value. The key is composed of a UUID
identifier and a 32-character random secret joined by a dot (`<id>.<secret>`). The raw
secret is **only returned in this response** — it is hashed (SHA-256 + salt) before
being persisted and can never be retrieved again.

#### Request Body

| Field       | Type              | Required | Description                                                                                  |
|:------------|:------------------|:--------:|:---------------------------------------------------------------------------------------------|
| `name`      | `string`          | ✅       | A human-readable name to identify this key (e.g., `"my-dashboard"`, `"grafana-prod"`)        |
| `scopes`    | `string`          | ✅       | Comma-separated list of scopes to grant (e.g., `"read:server,read:players"` or `"*"`)        |
| `expiresAt` | `string \| null`  | ❌       | ISO-8601 expiration timestamp, or `null` for a non-expiring key. Default: `null`             |

```json
{
  "name": "my-dashboard",
  "scopes": "read:server,read:players",
  "expiresAt": null
}
```

#### curl Example

```bash
curl -s -X POST http://localhost:7890/api/v1/admin/keys \
  -H "Authorization: Bearer a1b2c3d4-e5f6-7890-abcd-ef1234567890.xK9mPqR2sT4uV6wX8yZ0aB3cD5eF7gH" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "my-dashboard",
    "scopes": "read:server,read:players",
    "expiresAt": null
  }'
```

#### Success Response — `201 Created`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "id": "c4d5e6f7-a8b9-0123-cdef-456789abcdef",
    "name": "my-dashboard",
    "scopes": "read:server,read:players",
    "createdAt": "2026-06-05T11:48:00Z",
    "expiresAt": null,
    "enabled": true,
    "key": "c4d5e6f7-a8b9-0123-cdef-456789abcdef.aB3dE5fG7hI9jK1lM3nO5pQ7rS9tU1vW"
  }
}
```

> [!CAUTION]
> The `key` field contains the **full raw API key** and is returned **only in this
> response**. Copy and store it securely now. It cannot be shown again.

#### Error Response — `400 Bad Request`

```json
{
  "success": false,
  "error": {
    "code": "BAD_REQUEST",
    "message": "Field 'name' is required and cannot be empty.",
    "timestamp": 1717600000000
  }
}
```

#### Error Response — `401 Unauthorized`

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

---

### 6. Revoke API Key

Permanently delete an API key by its ID.

| Property       | Value                    |
|:---------------|:-------------------------|
| **Method**     | `DELETE`                 |
| **Path**       | `/admin/keys/{keyId}`    |
| **Auth**       | 🔒 Required              |
| **Scopes**     | `admin`                  |

#### Description

Immediately and permanently revokes the specified API key. Any subsequent requests
using that key will receive `401 Unauthorized`. This action cannot be undone.

#### Path Parameters

| Parameter | Type     | Description                              |
|:----------|:---------|:-----------------------------------------|
| `keyId`   | `string` | UUID of the API key to revoke            |

#### curl Example

```bash
curl -s -X DELETE http://localhost:7890/api/v1/admin/keys/c4d5e6f7-a8b9-0123-cdef-456789abcdef \
  -H "Authorization: Bearer a1b2c3d4-e5f6-7890-abcd-ef1234567890.xK9mPqR2sT4uV6wX8yZ0aB3cD5eF7gH"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "message": "API key 'c4d5e6f7-a8b9-0123-cdef-456789abcdef' has been revoked."
  }
}
```

#### Error Response — `404 Not Found`

```json
{
  "success": false,
  "error": {
    "code": "NOT_FOUND",
    "message": "API key with id 'c4d5e6f7-a8b9-0123-cdef-456789abcdef' does not exist.",
    "timestamp": 1717600000000
  }
}
```

#### Error Response — `401 Unauthorized`

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

> [!WARNING]
> Revoking a key is **irreversible**. All clients using the revoked key will
> immediately lose access. Make sure you have a replacement key ready before
> revoking a production key.

---

### 7. View Config

Retrieve the current running plugin configuration.

| Property       | Value                |
|:---------------|:---------------------|
| **Method**     | `GET`                |
| **Path**       | `/admin/config`      |
| **Auth**       | 🔒 Required          |
| **Scopes**     | `admin`              |

#### Description

Returns the plugin's current in-memory configuration. Sensitive fields (database
passwords, key salts, etc.) are **masked** with `******` to prevent accidental exposure.

#### curl Example

```bash
curl -s http://localhost:7890/api/v1/admin/config \
  -H "Authorization: Bearer a1b2c3d4-e5f6-7890-abcd-ef1234567890.xK9mPqR2sT4uV6wX8yZ0aB3cD5eF7gH"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "server": {
      "host": "0.0.0.0",
      "port": 7890,
      "corsOrigins": ["*"]
    },
    "database": {
      "type": "sqlite",
      "url": "jdbc:sqlite:plugins/NaturalAPI/naturalapi.db",
      "username": "sa",
      "password": "******"
    },
    "rateLimit": {
      "enabled": true,
      "maxRequests": 60,
      "windowSeconds": 60
    },
    "snapshot": {
      "enabled": true,
      "intervalMinutes": 5,
      "retentionDays": 30
    }
  }
}
```

> [!NOTE]
> The response reflects the **live running configuration**, which may differ from the
> on-disk config file if a reload has not been performed since the file was edited.

#### Error Response — `401 Unauthorized`

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

---

### 8. Reload Plugin

Trigger a full plugin reload cycle.

| Property       | Value                |
|:---------------|:---------------------|
| **Method**     | `POST`               |
| **Path**       | `/admin/reload`      |
| **Auth**       | 🔒 Required          |
| **Scopes**     | `admin`              |

#### Description

Performs a full graceful reload of the NaturalAPI plugin. The reload sequence is:

1. Stop the HTTP server (in-flight requests are completed)
2. Close all database connections
3. Re-read and parse the configuration file from disk
4. Re-initialize database connections
5. Restart the HTTP server with the new configuration

This allows you to apply configuration changes without a full server restart.

#### curl Example

```bash
curl -s -X POST http://localhost:7890/api/v1/admin/reload \
  -H "Authorization: Bearer a1b2c3d4-e5f6-7890-abcd-ef1234567890.xK9mPqR2sT4uV6wX8yZ0aB3cD5eF7gH"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "message": "Plugin reload completed successfully.",
    "reloadTimeMs": 342
  }
}
```

#### Error Response — `500 Internal Server Error`

Returned if the reload process encounters a failure (e.g., invalid config, DB unreachable):

```json
{
  "success": false,
  "error": {
    "code": "INTERNAL_SERVER_ERROR",
    "message": "Reload failed: unable to connect to database with new configuration.",
    "timestamp": 1717600000000
  }
}
```

> [!WARNING]
> During reload, the API is briefly unavailable. Clients may receive connection
> errors for a few hundred milliseconds. Plan reloads during low-traffic periods
> or implement retry logic in your consumers.

---

### 9. View Rate Limits

View current rate limit counters for all tracked IP addresses.

| Property       | Value                    |
|:---------------|:-------------------------|
| **Method**     | `GET`                    |
| **Path**       | `/admin/rate-limits`     |
| **Auth**       | 🔒 Required              |
| **Scopes**     | `admin`                  |

#### Description

Returns a breakdown of current rate limit state per IP address, including the number
of requests made in the current window, the window expiry time, and whether the IP is
currently throttled. Useful for diagnosing rate limit issues or identifying abusive clients.

#### curl Example

```bash
curl -s http://localhost:7890/api/v1/admin/rate-limits \
  -H "Authorization: Bearer a1b2c3d4-e5f6-7890-abcd-ef1234567890.xK9mPqR2sT4uV6wX8yZ0aB3cD5eF7gH"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": [
    {
      "ip": "192.168.1.50",
      "requestCount": 12,
      "maxRequests": 60,
      "windowExpiresAt": 1717600060000,
      "throttled": false
    },
    {
      "ip": "10.0.0.22",
      "requestCount": 60,
      "maxRequests": 60,
      "windowExpiresAt": 1717600045000,
      "throttled": true
    }
  ]
}
```

#### Error Response — `401 Unauthorized`

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

---

### 10. Reset Rate Limit

Clear the rate limit counter for a specific IP address.

| Property       | Value                        |
|:---------------|:-----------------------------|
| **Method**     | `DELETE`                     |
| **Path**       | `/admin/rate-limits/{ip}`    |
| **Auth**       | 🔒 Required                  |
| **Scopes**     | `admin`                      |

#### Description

Immediately resets the rate limit counter for the specified IP address. The IP will
be treated as if it has made zero requests in the current window. This is useful for
unblocking a legitimate client that was temporarily throttled.

#### Path Parameters

| Parameter | Type     | Description                                   |
|:----------|:---------|:----------------------------------------------|
| `ip`      | `string` | IPv4 or IPv6 address to reset (URL-encoded if needed) |

#### curl Example

```bash
curl -s -X DELETE http://localhost:7890/api/v1/admin/rate-limits/192.168.1.50 \
  -H "Authorization: Bearer a1b2c3d4-e5f6-7890-abcd-ef1234567890.xK9mPqR2sT4uV6wX8yZ0aB3cD5eF7gH"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "message": "Rate limit for IP '192.168.1.50' has been reset."
  }
}
```

#### Error Response — `404 Not Found`

```json
{
  "success": false,
  "error": {
    "code": "NOT_FOUND",
    "message": "No rate limit entry found for IP '10.99.99.99'.",
    "timestamp": 1717600000000
  }
}
```

> [!TIP]
> For IPv6 addresses, make sure to URL-encode the colons if your HTTP client doesn't
> handle this automatically (e.g., `::1` becomes `%3A%3A1`).

---

### 11. Snapshot History

Retrieve recent snapshot records from the database.

| Property       | Value                        |
|:---------------|:-----------------------------|
| **Method**     | `GET`                        |
| **Path**       | `/admin/snapshot/history`    |
| **Auth**       | 🔒 Required                  |
| **Scopes**     | `admin`                      |

#### Description

Returns the most recent snapshot records stored in the database, ordered by timestamp
descending (newest first). Snapshots capture periodic server state for historical
analysis and trend monitoring.

#### Query Parameters

| Parameter | Type      | Default | Description                             |
|:----------|:----------|:--------|:----------------------------------------|
| `limit`   | `integer` | `50`    | Maximum number of records to return     |
| `offset`  | `integer` | `0`     | Number of records to skip (pagination)  |

#### curl Example

```bash
# Fetch the last 10 snapshots
curl -s "http://localhost:7890/api/v1/admin/snapshot/history?limit=10&offset=0" \
  -H "Authorization: Bearer a1b2c3d4-e5f6-7890-abcd-ef1234567890.xK9mPqR2sT4uV6wX8yZ0aB3cD5eF7gH"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "snapshots": [
      {
        "id": 1024,
        "takenAt": "2026-06-05T11:45:00Z",
        "playersOnline": 42,
        "tps": 19.98,
        "memoryUsedMb": 2048,
        "memoryMaxMb": 4096,
        "worldCount": 3,
        "loadedChunks": 1523
      },
      {
        "id": 1023,
        "takenAt": "2026-06-05T11:40:00Z",
        "playersOnline": 40,
        "tps": 20.0,
        "memoryUsedMb": 2012,
        "memoryMaxMb": 4096,
        "worldCount": 3,
        "loadedChunks": 1498
      }
    ],
    "total": 1024,
    "limit": 10,
    "offset": 0
  }
}
```

#### Error Response — `401 Unauthorized`

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

---

### 12. Purge Snapshots

Delete old snapshot records that exceed the configured retention period.

| Property       | Value                        |
|:---------------|:-----------------------------|
| **Method**     | `DELETE`                     |
| **Path**       | `/admin/snapshot/purge`      |
| **Auth**       | 🔒 Required                  |
| **Scopes**     | `admin`                      |

#### Description

Permanently removes all snapshot records older than the configured retention period
(see `snapshot.retentionDays` in the plugin config). This frees up database storage
and is typically called on a schedule or manually during maintenance.

#### curl Example

```bash
curl -s -X DELETE http://localhost:7890/api/v1/admin/snapshot/purge \
  -H "Authorization: Bearer a1b2c3d4-e5f6-7890-abcd-ef1234567890.xK9mPqR2sT4uV6wX8yZ0aB3cD5eF7gH"
```

#### Success Response — `200 OK`

```json
{
  "success": true,
  "timestamp": 1717600000000,
  "data": {
    "purgedCount": 288,
    "retentionDays": 30,
    "oldestRemainingSnapshot": "2026-05-06T11:45:00Z"
  }
}
```

#### Error Response — `401 Unauthorized`

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

> [!NOTE]
> If no snapshots are older than the retention period, `purgedCount` will be `0`
> and no data is deleted.

---

## Quick Reference

| Method     | Path                          | Auth     | Description                        |
|:-----------|:------------------------------|:---------|:-----------------------------------|
| `GET`      | `/admin/health`               | 🔓 Public | Plugin health check               |
| `GET`      | `/admin/openapi.yaml`         | 🔓 Public | OpenAPI 3.1 spec (YAML)           |
| `GET`      | `/admin/openapi.json`         | 🔓 Public | OpenAPI 3.1 spec (JSON)           |
| `GET`      | `/admin/keys`                 | 🔒 `admin` | List all API keys                 |
| `POST`     | `/admin/keys`                 | 🔒 `admin` | Generate a new API key            |
| `DELETE`   | `/admin/keys/{keyId}`         | 🔒 `admin` | Revoke an API key                 |
| `GET`      | `/admin/config`               | 🔒 `admin` | View running configuration        |
| `POST`     | `/admin/reload`               | 🔒 `admin` | Trigger full plugin reload        |
| `GET`      | `/admin/rate-limits`          | 🔒 `admin` | View rate limit counters          |
| `DELETE`   | `/admin/rate-limits/{ip}`     | 🔒 `admin` | Reset rate limit for an IP        |
| `GET`      | `/admin/snapshot/history`     | 🔒 `admin` | View recent snapshot records      |
| `DELETE`   | `/admin/snapshot/purge`       | 🔒 `admin` | Purge old snapshots               |
