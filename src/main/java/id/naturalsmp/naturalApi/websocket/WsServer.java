package id.naturalsmp.naturalApi.websocket;

import id.naturalsmp.naturalApi.NaturalAPI;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsErrorContext;

import java.util.Map;

public class WsServer {

    private final NaturalAPI plugin;
    private final WsBroadcaster broadcaster;
    private final WsAuthHandler authHandler;

    public static final String CHANNEL_SERVER_STATS = "server:stats";
    public static final String CHANNEL_PLAYER_EVENTS = "player:events";
    public static final String CHANNEL_CHAT = "chat:messages";

    public WsServer(NaturalAPI plugin) {
        this.plugin = plugin;
        this.broadcaster = new WsBroadcaster(plugin);
        this.authHandler = new WsAuthHandler(plugin);
    }

    public WsBroadcaster getBroadcaster() {
        return broadcaster;
    }

    public void registerRoutes() {
        if (!plugin.getConfigManager().isWebSocketEnabled()) {
            return;
        }

        boolean statsEnabled = plugin.getConfigManager().getConfig().getBoolean("features.websocket.endpoints.server-stats", true);
        boolean playerEventsEnabled = plugin.getConfigManager().getConfig().getBoolean("features.websocket.endpoints.player-events", true);
        boolean chatEnabled = plugin.getConfigManager().getConfig().getBoolean("features.websocket.endpoints.chat", true);
        boolean playerDetailEnabled = plugin.getConfigManager().getConfig().getBoolean("features.websocket.endpoints.player-detail", true);

        if (statsEnabled) {
            io.javalin.apibuilder.ApiBuilder.ws("/ws/server", ws -> {
                ws.onConnect(ctx -> handleServerStatsConnect(ctx));
                ws.onClose(ctx -> handleClose(ctx, CHANNEL_SERVER_STATS));
                ws.onError(ctx -> handleError(ctx));
            });
        }

        if (playerEventsEnabled) {
            io.javalin.apibuilder.ApiBuilder.ws("/ws/players", ws -> {
                ws.onConnect(ctx -> handlePlayerEventsConnect(ctx));
                ws.onClose(ctx -> handleClose(ctx, CHANNEL_PLAYER_EVENTS));
                ws.onError(ctx -> handleError(ctx));
            });
        }

        if (chatEnabled) {
            io.javalin.apibuilder.ApiBuilder.ws("/ws/chat", ws -> {
                ws.onConnect(ctx -> handleChatConnect(ctx));
                ws.onMessage(ctx -> handleChatMessage(ctx));
                ws.onClose(ctx -> handleClose(ctx, CHANNEL_CHAT));
                ws.onError(ctx -> handleError(ctx));
            });
        }

        if (playerDetailEnabled) {
            io.javalin.apibuilder.ApiBuilder.ws("/ws/player/{uuid}", ws -> {
                ws.onConnect(ctx -> handlePlayerDetailConnect(ctx));
                ws.onMessage(ctx -> handlePlayerDetailMessage(ctx));
                ws.onClose(ctx -> {
                    String uuid = ctx.pathParam("uuid");
                    broadcaster.unregister("player:" + uuid, ctx);
                });
                ws.onError(ctx -> handleError(ctx));
            });
        }

        plugin.getLogger().info("WebSocket routes registered");
    }

    private void handleServerStatsConnect(WsConnectContext ctx) {
        if (!authHandler.authenticate(ctx, "read:server")) return;
        broadcaster.register(CHANNEL_SERVER_STATS, ctx);
    }

    private void handlePlayerEventsConnect(WsConnectContext ctx) {
        if (!authHandler.authenticate(ctx, "read:players")) return;
        broadcaster.register(CHANNEL_PLAYER_EVENTS, ctx);
    }

    private void handleChatConnect(WsConnectContext ctx) {
        if (!authHandler.authenticate(ctx, "read:server")) return;
        broadcaster.register(CHANNEL_CHAT, ctx);
    }

    private void handleChatMessage(io.javalin.websocket.WsMessageContext ctx) {
        // Chat messages are broadcast from the server-side chat listener
        // Client messages are ignored to prevent abuse
    }

    private void handlePlayerDetailConnect(WsConnectContext ctx) {
        if (!authHandler.authenticate(ctx, "read:players")) return;
        String uuid = ctx.pathParam("uuid");
        String channel = "player:" + uuid;

        // Send initial snapshot
        org.bukkit.Bukkit.getScheduler().runTask(plugin, () -> {
            org.bukkit.entity.Player player = org.bukkit.Bukkit.getPlayer(java.util.UUID.fromString(uuid));
            if (player != null && player.isOnline()) {
                Map<String, Object> full = plugin.getPlayerService().getPlayerFull(player);
                Map<String, Object> msg = new java.util.HashMap<>();
                msg.put("type", "snapshot");
                msg.put("timestamp", System.currentTimeMillis());
                msg.put("data", full);
                ctx.send(msg);
            }
        });

        broadcaster.register(channel, ctx);
    }

    private void handlePlayerDetailMessage(io.javalin.websocket.WsMessageContext ctx) {
        String msg = ctx.message();
        if (msg == null || !msg.equals("ping")) return;
        ctx.send("pong");
    }

    private void handleClose(WsCloseContext ctx, String channel) {
        broadcaster.unregister(channel, ctx);
    }

    private void handleError(WsErrorContext ctx) {
        String errorMsg = ctx.error() != null ? ctx.error().getMessage() : "Unknown error";
        if (errorMsg != null && (errorMsg.contains("Idle Timeout") || errorMsg.contains("Timeout") || errorMsg.contains("Connection reset by peer") || errorMsg.contains("Connection closed"))) {
            // Suppress normal network disconnection and timeout warnings to prevent log spam
            plugin.getLogger().fine("WebSocket connection closed/timed out on session " + ctx.sessionId() + ": " + errorMsg);
        } else {
            plugin.getLogger().warning("WebSocket error on session " + ctx.sessionId() + ": " + errorMsg);
            if (ctx.error() != null) {
                plugin.getLogger().fine(ctx.error().toString()); // Log full trace to fine/debug
            }
        }
        broadcaster.unregisterAll(ctx);
    }

    public void shutdown() {
        broadcaster.shutdown();
    }
}
