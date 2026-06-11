package id.naturalsmp.naturalApi.listener;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.websocket.WsServer;
import id.naturalsmp.naturalApi.websocket.WsBroadcaster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerListener implements Listener {

    private final NaturalAPI plugin;

    public PlayerListener(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getPlayerService().processPlayerIpAndGeolocate(event.getPlayer());

        if (!plugin.getConfigManager().isWebSocketEnabled()) return;
        if (!plugin.getConfig().getBoolean("features.websocket.endpoints.player-events", true)) return;
        
        // Prevent vanished staff member join event leak
        if (plugin.getPlayerService().isPlayerVanished(event.getPlayer())) return;

        WsServer wsServer = plugin.getHttpServer().getWsServer();
        if (wsServer == null) return;

        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "join");
        payload.put("timestamp", System.currentTimeMillis());
        payload.put("data", plugin.getPlayerService().getPlayerSummary(event.getPlayer()));

        wsServer.getBroadcaster().broadcast(WsServer.CHANNEL_PLAYER_EVENTS, payload);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!plugin.getConfigManager().isWebSocketEnabled()) return;
        if (!plugin.getConfig().getBoolean("features.websocket.endpoints.player-events", true)) return;
        
        // Prevent vanished staff member quit event leak
        if (plugin.getPlayerService().isPlayerVanished(event.getPlayer())) return;

        WsServer wsServer = plugin.getHttpServer().getWsServer();
        if (wsServer == null) return;

        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "leave");
        payload.put("timestamp", System.currentTimeMillis());
        Map<String, Object> playerData = new HashMap<>();
        playerData.put("uuid", event.getPlayer().getUniqueId().toString());
        playerData.put("username", event.getPlayer().getName());
        payload.put("data", playerData);

        wsServer.getBroadcaster().broadcast(WsServer.CHANNEL_PLAYER_EVENTS, payload);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;
        if (!plugin.getConfigManager().isWebSocketEnabled()) return;
        if (!plugin.getConfig().getBoolean("features.websocket.endpoints.chat", true)) return;

        WsServer wsServer = plugin.getHttpServer().getWsServer();
        if (wsServer == null) return;

        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "message");
        payload.put("timestamp", System.currentTimeMillis());

        Map<String, Object> msgData = new HashMap<>();
        msgData.put("uuid", event.getPlayer().getUniqueId().toString());
        msgData.put("username", event.getPlayer().getName());
        msgData.put("displayName", event.getPlayer().getDisplayName());
        msgData.put("message", event.getMessage());
        msgData.put("format", event.getFormat());
        payload.put("data", msgData);

        wsServer.getBroadcaster().broadcast(WsServer.CHANNEL_CHAT, payload);
    }
}
