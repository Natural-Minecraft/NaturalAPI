package id.naturalsmp.naturalApi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.database.dao.PlayerSnapshotDao;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class SnapshotService {

    private final NaturalAPI plugin;
    private final ObjectMapper mapper;
    private int taskId = -1;

    public SnapshotService(NaturalAPI plugin) {
        this.plugin = plugin;
        this.mapper = new ObjectMapper();
    }

    public void start() {
        plugin.getLogger().info("SnapshotService started.");
        
        boolean autoSave = plugin.getConfig().getBoolean("features.snapshot.auto-save", true);
        if (autoSave) {
            int intervalMinutes = plugin.getConfig().getInt("features.snapshot.interval-minutes", 10);
            taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                java.util.List<RawSnapshotData> batch = new java.util.ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    try {
                        batch.add(gatherRawSnapshotData(player));
                    } catch (Exception e) {
                        plugin.getLogger().log(Level.SEVERE, "Failed to gather snapshot data for " + player.getName(), e);
                    }
                }
                saveSnapshotBatchAsync(batch);
            }, intervalMinutes * 60 * 20L, intervalMinutes * 60 * 20L);
        }
    }

    private static class RawSnapshotData {
        String playerUuid;
        String playerName;
        long snapshotTime;
        Map<String, Object> playerData;
        java.util.List<Map<String, Object>> inventoryList;
        Map<String, Object> armorMap;
        java.util.List<Map<String, Object>> effectsList;
        long playtime;
        double balance;
        int kills;
        int deaths;
        int votes;
    }

    private RawSnapshotData gatherRawSnapshotData(Player player) {
        RawSnapshotData raw = new RawSnapshotData();
        raw.playerUuid = player.getUniqueId().toString();
        raw.playerName = player.getName();
        raw.snapshotTime = System.currentTimeMillis();
        
        raw.playerData = plugin.getPlayerService().getPlayerFull(player);
        raw.inventoryList = plugin.getPlayerService().getInventory(player);
        raw.armorMap = plugin.getPlayerService().getArmor(player);
        raw.effectsList = plugin.getPlayerService().getEffects(player);

        long playtime = 0;
        if (raw.playerData.containsKey("totalPlaytimeMs")) {
            playtime = ((Number) raw.playerData.get("totalPlaytimeMs")).longValue();
        }
        raw.playtime = playtime;

        double balance = 0.0;
        if (raw.playerData.containsKey("vault")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> vaultData = (Map<String, Object>) raw.playerData.get("vault");
            if (vaultData != null && vaultData.containsKey("balance")) {
                balance = ((Number) vaultData.get("balance")).doubleValue();
            }
        }
        raw.balance = balance;

        raw.kills = player.getStatistic(org.bukkit.Statistic.PLAYER_KILLS);
        raw.deaths = player.getStatistic(org.bukkit.Statistic.DEATHS);

        int votes = 0;
        if (plugin.getIntegrationManager().getPapiIntegration() != null && plugin.getIntegrationManager().getPapiIntegration().isEnabled()) {
            try {
                String vStr = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, "%votingplugin_votes%");
                if (vStr == null || vStr.equals("%votingplugin_votes%")) {
                    vStr = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, "%votingplugin_totalvotes%");
                }
                if (vStr != null && !vStr.contains("%")) {
                    votes = Integer.parseInt(vStr.trim());
                }
            } catch (Exception e) {
                // Ignore
            }
        }
        raw.votes = votes;
        
        return raw;
    }

    public void saveSnapshotAsync(Player player) {
        try {
            RawSnapshotData raw = gatherRawSnapshotData(player);
            saveSnapshotBatchAsync(java.util.Collections.singletonList(raw));
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to trigger single snapshot for " + player.getName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public void saveSnapshotBatchAsync(java.util.List<RawSnapshotData> batch) {
        if (batch == null || batch.isEmpty()) return;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                plugin.getDatabaseManager().getJdbi().inTransaction(h -> {
                    PlayerSnapshotDao dao = h.attach(PlayerSnapshotDao.class);
                    for (RawSnapshotData raw : batch) {
                        try {
                            String inventoryJson = mapper.writeValueAsString(raw.inventoryList);
                            String armorJson = mapper.writeValueAsString(raw.armorMap);
                            String effectsJson = mapper.writeValueAsString(raw.effectsList);

                            String vaultGroup = null;
                            String vaultPrefix = null;
                            String vaultSuffix = null;
                            if (raw.playerData.containsKey("vault")) {
                                Map<String, Object> vaultData = (Map<String, Object>) raw.playerData.get("vault");
                                if (vaultData != null) {
                                    vaultGroup = (String) vaultData.get("group");
                                    vaultPrefix = (String) vaultData.get("prefix");
                                    vaultSuffix = (String) vaultData.get("suffix");
                                }
                            }

                            String lpGroup = null;
                            if (raw.playerData.containsKey("luckperms")) {
                                Map<String, Object> lpData = (Map<String, Object>) raw.playerData.get("luckperms");
                                if (lpData != null) {
                                    lpGroup = (String) lpData.get("primaryGroup");
                                }
                            }

                            Map<String, Object> loc = (Map<String, Object>) raw.playerData.get("location");
                            String ipAddress = (String) raw.playerData.get("ipAddress");
                            String country = (String) raw.playerData.get("country");
                            String region = (String) raw.playerData.get("region");
                            String city = (String) raw.playerData.get("city");
                            String isp = (String) raw.playerData.get("isp");
                            String asn = (String) raw.playerData.get("asn");
                            String locale = (String) raw.playerData.get("locale");
                            String clientBrand = (String) raw.playerData.get("clientBrand");
                            Object pingObj = raw.playerData.get("ping");
                            int ping = pingObj instanceof Number ? ((Number) pingObj).intValue() : -1;

                            dao.deleteByUuid(raw.playerUuid);
                            dao.insertSnapshot(
                                    UUID.randomUUID().toString(),
                                    raw.playerUuid,
                                    raw.playerName,
                                    raw.snapshotTime,
                                    (String) loc.get("world"),
                                    (Double) loc.get("x"),
                                    (Double) loc.get("y"),
                                    (Double) loc.get("z"),
                                    (Float) loc.get("yaw"),
                                    (Float) loc.get("pitch"),
                                    (Double) raw.playerData.get("health"),
                                    (Double) raw.playerData.get("maxHealth"),
                                    (Integer) raw.playerData.get("foodLevel"),
                                    (Float) raw.playerData.get("saturation"),
                                    (Integer) raw.playerData.get("expLevel"),
                                    (Float) raw.playerData.get("expProgress"),
                                    (Integer) raw.playerData.get("totalExp"),
                                    (String) raw.playerData.get("gamemode"),
                                    inventoryJson,
                                    armorJson,
                                    effectsJson,
                                    null,
                                    null,
                                    vaultGroup,
                                    vaultPrefix,
                                    vaultSuffix,
                                    lpGroup,
                                    raw.playtime,
                                    raw.balance,
                                    raw.kills,
                                    raw.deaths,
                                    raw.votes,
                                    ipAddress,
                                    country,
                                    region,
                                    city,
                                    isp,
                                    asn,
                                    locale,
                                    clientBrand,
                                    ping
                            );
                        } catch (JsonProcessingException e) {
                            plugin.getLogger().log(Level.SEVERE, "Failed to serialize snapshot data for " + raw.playerName, e);
                        } catch (Exception e) {
                            plugin.getLogger().log(Level.SEVERE, "Error saving snapshot record for " + raw.playerName, e);
                        }
                    }
                    return null;
                });
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Database transaction error during snapshot batch save", e);
            }
        });
    }

    public void stop() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
        plugin.getLogger().info("SnapshotService stopped.");
    }
}
