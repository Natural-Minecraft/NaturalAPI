package id.naturalsmp.naturalApi.service;

import id.naturalsmp.naturalApi.NaturalAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardService {

    private final NaturalAPI plugin;

    public LeaderboardService(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public List<Map<String, Object>> getLeaderboard(String type, int limit) {
        String resolvedType = type.toLowerCase();
        String provider = plugin.getConfig().getString("features.leaderboard.providers." + resolvedType, "database");

        if (provider.equalsIgnoreCase("ajlb") || resolvedType.startsWith("ajlb:")) {
            String ajlbType = resolvedType.startsWith("ajlb:") ? type : "ajlb:" + type;
            return getAjLeaderboard(ajlbType, limit);
        }

        String column;
        switch (resolvedType) {
            case "playtime":
                column = "playtime";
                break;
            case "balance":
                column = "balance";
                break;
            case "kills":
            case "kill":
                column = "kills";
                break;
            case "deaths":
            case "death":
                column = "deaths";
                break;
            case "votes":
            case "vote":
                column = "votes";
                break;
            case "exp":
            case "experience":
                column = "total_exp";
                break;
            default:
                // If it is a PAPI placeholder, evaluate it dynamically for online players
                if (type.startsWith("%") && type.endsWith("%")) {
                    return getOnlineLeaderboard(type, limit);
                }
                return null;
        }

        // Query the database to get the latest snapshot of every player, sorted by the specified column descending
        String sql = "SELECT s1.player_uuid AS uuid, s1.player_name AS username, s1." + column + " AS value " +
                     "FROM napi_player_snapshots s1 " +
                     "INNER JOIN (" +
                     "    SELECT player_uuid, MAX(snapshot_time) as max_time " +
                     "    FROM napi_player_snapshots " +
                     "    GROUP BY player_uuid" +
                     ") s2 ON s1.player_uuid = s2.player_uuid AND s1.snapshot_time = s2.max_time " +
                     "ORDER BY value DESC " +
                     "LIMIT :limit";

        return plugin.getDatabaseManager().getJdbi().withHandle(h ->
            h.createQuery(sql)
             .bind("limit", limit)
             .mapToMap()
             .list()
        );
    }

    private List<Map<String, Object>> getOnlineLeaderboard(String placeholder, int limit) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (plugin.getIntegrationManager().getPapiIntegration() == null || !plugin.getIntegrationManager().getPapiIntegration().isEnabled()) {
            return list;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            String valStr = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(p, placeholder);
            double val = 0;
            try {
                // Remove formatting or symbols before parsing
                String cleanVal = valStr.replaceAll("[^0-9.-]", "");
                val = Double.parseDouble(cleanVal.trim());
            } catch (Exception e) {
                // Ignore parsing errors, default value is 0
            }
            Map<String, Object> map = new HashMap<>();
            map.put("uuid", p.getUniqueId().toString());
            map.put("username", p.getName());
            map.put("value", valStr);
            map.put("numericValue", val);
            list.add(map);
        }

        // Sort descending by numericValue
        list.sort((m1, m2) -> Double.compare((Double) m2.get("numericValue"), (Double) m1.get("numericValue")));

        // Clean up internal numericValue field before returning
        for (Map<String, Object> m : list) {
            m.remove("numericValue");
        }

        if (list.size() > limit) {
            return new ArrayList<>(list.subList(0, limit));
        }
        return list;
    }

    private List<Map<String, Object>> getAjLeaderboard(String type, int limit) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (plugin.getIntegrationManager().getPapiIntegration() == null || !plugin.getIntegrationManager().getPapiIntegration().isEnabled()) {
            return list;
        }

        // Format: ajlb:<board_name>[:time_type]
        String[] parts = type.split(":");
        if (parts.length < 2) {
            return list;
        }
        String board = parts[1];
        String timeType = parts.length > 2 ? parts[2] : "alltime";

        Player onlinePlayer = Bukkit.getOnlinePlayers().stream().findFirst().orElse(null);

        for (int rank = 1; rank <= limit; rank++) {
            String uuidPlaceholder = "%ajlb_lb_" + board + "_" + rank + "_" + timeType + "_uuid%";
            String namePlaceholder = "%ajlb_lb_" + board + "_" + rank + "_" + timeType + "_name%";
            String valuePlaceholder = "%ajlb_lb_" + board + "_" + rank + "_" + timeType + "_rawvalue%";

            String uuidStr = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(onlinePlayer, uuidPlaceholder);
            // If the placeholder is returned raw or is empty/invalid, it means there's no entry at this rank
            if (uuidStr.equals(uuidPlaceholder) || uuidStr.trim().isEmpty() || uuidStr.equals("UNKNOWN") || uuidStr.contains("%")) {
                break; // No more ranks populated
            }

            String nameStr = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(onlinePlayer, namePlaceholder);
            String valueStr = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(onlinePlayer, valuePlaceholder);

            Map<String, Object> map = new HashMap<>();
            map.put("uuid", uuidStr);
            map.put("username", nameStr);

            // Try parsing value
            try {
                if (valueStr.contains(".")) {
                    map.put("value", Double.parseDouble(valueStr.replaceAll("[^0-9.-]", "")));
                } else {
                    map.put("value", Long.parseLong(valueStr.replaceAll("[^0-9-]", "")));
                }
            } catch (NumberFormatException e) {
                map.put("value", valueStr);
            }

            list.add(map);
        }
        return list;
    }
}
