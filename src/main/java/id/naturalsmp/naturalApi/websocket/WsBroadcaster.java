package id.naturalsmp.naturalApi.websocket;

import id.naturalsmp.naturalApi.NaturalAPI;
import io.javalin.websocket.WsContext;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class WsBroadcaster {

    private final NaturalAPI plugin;
    private final Map<String, Set<WsContext>> channels = new ConcurrentHashMap<>();

    public WsBroadcaster(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public void register(String channel, WsContext ctx) {
        channels.computeIfAbsent(channel, k -> new CopyOnWriteArraySet<>()).add(ctx);
        plugin.getLogger().fine("WebSocket session " + ctx.sessionId() + " subscribed to " + channel);
    }

    public void unregister(String channel, WsContext ctx) {
        Set<WsContext> sessions = channels.get(channel);
        if (sessions != null) {
            sessions.remove(ctx);
            if (sessions.isEmpty()) {
                channels.remove(channel);
            }
        }
    }

    public void unregisterAll(WsContext ctx) {
        for (Map.Entry<String, Set<WsContext>> entry : channels.entrySet()) {
            entry.getValue().remove(ctx);
        }
        channels.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }

    public void broadcast(String channel, String json) {
        Set<WsContext> sessions = channels.get(channel);
        if (sessions == null || sessions.isEmpty()) return;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (WsContext session : sessions) {
                try {
                    if (session.session.isOpen()) {
                        session.send(json);
                    }
                } catch (Exception e) {
                    // Suppress and continue to other sessions
                    plugin.getLogger().fine("Failed to broadcast message to session " + session.sessionId() + ": " + e.getMessage());
                }
            }
        });
    }

    private static final com.fasterxml.jackson.databind.ObjectMapper MAPPER = new com.fasterxml.jackson.databind.ObjectMapper();

    public void broadcast(String channel, Object data) {
        try {
            broadcast(channel, MAPPER.writeValueAsString(data));
        } catch (Exception e) {
            plugin.getLogger().warning("WebSocket broadcast JSON error: " + e.getMessage());
        }
    }

    public int getSessionCount(String channel) {
        Set<WsContext> sessions = channels.get(channel);
        return sessions != null ? sessions.size() : 0;
    }

    public Map<String, Integer> getAllChannelStats() {
        Map<String, Integer> stats = new ConcurrentHashMap<>();
        for (Map.Entry<String, Set<WsContext>> entry : channels.entrySet()) {
            stats.put(entry.getKey(), entry.getValue().size());
        }
        return stats;
    }

    public void shutdown() {
        for (Set<WsContext> sessions : channels.values()) {
            for (WsContext session : sessions) {
                if (session.session.isOpen()) {
                    session.closeSession(1001, "Server shutting down");
                }
            }
        }
        channels.clear();
    }
}
