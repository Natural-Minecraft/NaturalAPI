# WebSocket API

NaturalAPI menyediakan WebSocket endpoints untuk data real-time. Semua koneksi WebSocket memerlukan autentikasi via query parameter `token`.

---

## Autentikasi

Semua WebSocket endpoint memerlukan token API key yang dikirim sebagai query parameter:

```
ws://<server-ip>:7890/ws/server?token=<api-key>
```

Token menggunakan format yang sama dengan HTTP API: `keyId.rawSecret`

---

## Endpoints

### `ws://<server-ip>:7890/ws/server`

**Scope:** `read:server`

Menerima broadcast real-time status server dengan interval yang dapat dikonfigurasi.

**Subscribe:** Connect ke WebSocket endpoint.
**Config toggle:** `features.websocket.endpoints.server-stats`
**Interval:** `features.websocket.server-stats-interval` (default `20` tick = 1 detik)

**Message format (diterima client):**
```json
{
  "type": "stats",
  "timestamp": 1780742286966,
  "data": {
    "online": true,
    "version": "1.21.11",
    "platform": "Paper",
    "motd": "A Minecraft Server",
    "tps": {
      "now": 20.0,
      "1m": 20.0,
      "5m": 19.95,
      "15m": 19.98
    },
    "players": {
      "online": 5,
      "visible": 3,
      "vanished": 2,
      "max": 100
    },
    "mspt": 6.82,
    "uptime": 1464,
    "ram": {
      "usedMB": 2043,
      "freeMB": 6148,
      "maxMB": 8192
    },
    "system": {
      "availableProcessors": 4,
      "systemLoadAverage": 0.45,
      "processCpuLoad": 12.5,
      "systemCpuLoad": 35.2,
      "storage": {
        "totalBytes": 1000204886016,
        "freeBytes": 500123456789,
        "usableBytes": 450123456789
      },
      "network": {
        "rxBytesTotal": 1234567890,
        "txBytesTotal": 987654321,
        "rxBytesPerSec": 51200,
        "txBytesPerSec": 25600
      }
    },
    "javaVersion": "25.0.3",
    "osName": "Linux",
    "worlds": ["world", "world_nether", "world_the_end"]
  }
}
```

**JavaScript client example:**
```javascript
const ws = new WebSocket('ws://localhost:7890/ws/server?token=napi_xxx');

ws.onmessage = (event) => {
  const msg = JSON.parse(event.data);
  console.log('TPS:', msg.data.tps.now);
  console.log('Players:', msg.data.players.online);
};
```

---

### `ws://<server-ip>:7890/ws/players`

**Scope:** `read:players`

Menerima event player join/leave secara real-time.

**Subscribe:** Connect ke WebSocket endpoint.
**Config toggle:** `features.websocket.endpoints.player-events`

**Player join:**
```json
{
  "type": "join",
  "timestamp": 1780742286966,
  "data": {
    "uuid": "eea40d5a-...",
    "username": "AdityaOkeGas4",
    "displayName": "AdityaOkeGas4",
    "online": true,
    "vanished": false,
    "afk": false,
    "staffMode": false
  }
}
```

**Player leave:**
```json
{
  "type": "leave",
  "timestamp": 1780742286966,
  "data": {
    "uuid": "eea40d5a-...",
    "username": "AdityaOkeGas4"
  }
}
```

---

### `ws://<server-ip>:7890/ws/chat`

**Scope:** `read:server`

Menerima real-time chat messages dari server.

**Subscribe:** Connect ke WebSocket endpoint.
**Config toggle:** `features.websocket.endpoints.chat`

**Message format:**
```json
{
  "type": "message",
  "timestamp": 1780742286966,
  "data": {
    "uuid": "eea40d5a-...",
    "username": "AdityaOkeGas4",
    "displayName": "AdityaOkeGas4",
    "message": "Halo semuanya!",
    "format": "<AdityaOkeGas4> Halo semuanya!"
  }
}
```

**JavaScript client example:**
```javascript
const ws = new WebSocket('ws://localhost:7890/ws/chat?token=napi_xxx');

ws.onmessage = (event) => {
  const msg = JSON.parse(event.data);
  console.log(`${msg.data.username}: ${msg.data.message}`);
};
```

---

### `ws://<server-ip>:7890/ws/player/{uuid}`

**Scope:** `read:players`

Mendapatkan data detail player spesifik. Mengirimkan full snapshot saat connect, lalu mendukung `ping`/`pong`.

**Subscribe:** Connect ke WebSocket endpoint.
**Config toggle:** `features.websocket.endpoints.player-detail`

**Initial snapshot message:**
```json
{
  "type": "snapshot",
  "timestamp": 1780742286966,
  "data": {
    "uuid": "eea40d5a-...",
    "username": "AdityaOkeGas4",
    "displayName": "AdityaOkeGas4",
    "online": true,
    "vanished": false,
    "location": {
      "world": "world",
      "x": 3525.6,
      "y": 68.0,
      "z": 4069.1,
      "yaw": 0.0,
      "pitch": 0.0
    },
    "health": 20.0,
    "maxHealth": 20.0,
    "foodLevel": 20,
    "saturation": 5.0,
    "expLevel": 0,
    "expProgress": 0.0,
    "totalExp": 0,
    "gamemode": "SURVIVAL",
    "ping": 3,
    "...": "..."
  }
}
```

**Client-to-server:**
Kirim `ping` untuk keep-alive, server akan membalas `pong`.

---

## Connection Management

### Error Codes

| Code | Deskripsi |
|---|---|
| `4001` | Token tidak ditemukan (missing `token` parameter) |
| `4003` | Token invalid atau scope tidak mencukupi |
| `1001` | Server shutting down |

### Error Handling (JavaScript)
```javascript
const ws = new WebSocket('ws://localhost:7890/ws/server?token=napi_xxx');

ws.onclose = (event) => {
  if (event.code === 4003) {
    console.error('Authentication failed!');
  } else {
    // Reconnect after 5 seconds
    setTimeout(() => connect(), 5000);
  }
};
```

---

## Konfigurasi

Semua WebSocket endpoint bisa di-enable/disable via `config.yml`:

```yaml
features:
  websocket:
    enabled: true               # Master switch
    server-stats-interval: 20   # Interval broadcast /ws/server (tick, 20 = 1 detik)
    endpoints:
      server-stats: true        # /ws/server — real-time server status (TPS, RAM, CPU, storage, network)
      player-events: true       # /ws/players — player join/leave events
      chat: true                # /ws/chat — real-time chat messages
      player-detail: true       # /ws/player/{uuid} — individual player detail stream
```

Set `enabled: false` untuk menonaktifkan seluruh WebSocket system.
Set `server-stats-interval` untuk mengubah interval broadcast (nilai dalam tick Minecraft, 20 tick = 1 detik).
