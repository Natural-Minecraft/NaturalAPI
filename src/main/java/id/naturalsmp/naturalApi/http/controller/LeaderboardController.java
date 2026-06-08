package id.naturalsmp.naturalApi.http.controller;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.util.ResponseBuilder;
import io.javalin.http.Context;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardController {

    private final NaturalAPI plugin;

    public LeaderboardController(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public void getLeaderboard(Context ctx) {
        String type = ctx.queryParam("type");
        if (type == null || type.trim().isEmpty()) {
            ctx.status(400).json(ResponseBuilder.error("INVALID_REQUEST", "Query parameter 'type' is required (e.g. playtime, balance, kills, deaths, votes, exp, ajlb:<board>, or a custom PAPI placeholder)"));
            return;
        }

        int limit = ctx.queryParamAsClass("limit", Integer.class).getOrDefault(10);
        if (limit < 1 || limit > 100) {
            limit = 10;
        }

        List<Map<String, Object>> data = plugin.getLeaderboardService().getLeaderboard(type, limit);
        if (data == null) {
            ctx.status(400).json(ResponseBuilder.error("UNSUPPORTED_TYPE", "Unsupported leaderboard type. Use playtime, balance, kills, deaths, votes, exp, ajlb:<board>, or a custom PAPI placeholder like %some_placeholder%"));
            return;
        }

        // Build metadata
        Map<String, Object> metadata = new HashMap<>();
        if (type.toLowerCase().startsWith("ajlb:")) {
            String[] parts = type.split(":");
            if (parts.length >= 2) {
                String board = parts[1];
                String timeType = parts.length > 2 ? parts[2] : "alltime";
                
                String timeRemainingPlaceholder = "%ajlb_time_" + board + "_" + timeType + "%";
                Player onlinePlayer = Bukkit.getOnlinePlayers().stream().findFirst().orElse(null);
                
                String timeRemaining = null;
                if (plugin.getIntegrationManager().getPapiIntegration() != null && plugin.getIntegrationManager().getPapiIntegration().isEnabled()) {
                    timeRemaining = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(onlinePlayer, timeRemainingPlaceholder);
                }
                
                if (timeRemaining != null && !timeRemaining.equals(timeRemainingPlaceholder) && !timeRemaining.trim().isEmpty()) {
                    metadata.put("time_remaining_to_reset", timeRemaining);
                } else {
                    metadata.put("time_remaining_to_reset", "never (alltime) or not configured");
                }
            }
            metadata.put("provider", "ajLeaderboards");
            metadata.put("sync_interval_seconds", 60);
        } else if (type.startsWith("%") && type.endsWith("%")) {
            metadata.put("provider", "PlaceholderAPI (Dynamic)");
            metadata.put("note", "Evaluated in real-time for online players only");
        } else {
            metadata.put("provider", "NaturalAPI Snapshots Database");
            int intervalMinutes = plugin.getConfig().getInt("features.snapshot.interval-minutes", 10);
            metadata.put("sync_interval_minutes", intervalMinutes);
            
            Long lastSnapshot = plugin.getDatabaseManager().getJdbi().withHandle(h ->
                h.createQuery("SELECT MAX(snapshot_time) FROM napi_player_snapshots")
                 .mapTo(Long.class)
                 .findOne()
                 .orElse(null)
            );
            if (lastSnapshot != null) {
                metadata.put("last_sync_timestamp", lastSnapshot);
                metadata.put("next_sync_timestamp", lastSnapshot + (intervalMinutes * 60 * 1000L));
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("timestamp", System.currentTimeMillis());
        response.put("metadata", metadata);
        response.put("data", data);
        ctx.json(response);
    }
}
