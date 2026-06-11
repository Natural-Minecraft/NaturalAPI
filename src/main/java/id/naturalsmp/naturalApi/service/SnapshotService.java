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
                for (Player player : Bukkit.getOnlinePlayers()) {
                    saveSnapshotAsync(player);
                }
            }, intervalMinutes * 60 * 20L, intervalMinutes * 60 * 20L);
        }
    }

    public void saveSnapshotAsync(Player player) {
        Map<String, Object> playerData = plugin.getPlayerService().getPlayerFull(player);
        java.util.List<Map<String, Object>> inventoryList = plugin.getPlayerService().getInventory(player);
        Map<String, Object> armorMap = plugin.getPlayerService().getArmor(player);
        java.util.List<Map<String, Object>> effectsList = plugin.getPlayerService().getEffects(player);

        String ipAddress = (String) playerData.get("ipAddress");
        String country = (String) playerData.get("country");
        String region = (String) playerData.get("region");
        String city = (String) playerData.get("city");
        String isp = (String) playerData.get("isp");
        String asn = (String) playerData.get("asn");
        String locale = (String) playerData.get("locale");
        String clientBrand = (String) playerData.get("clientBrand");
        Object pingObj = playerData.get("ping");
        int ping = pingObj instanceof Number ? ((Number) pingObj).intValue() : -1;
        
        // Collect additional leaderboard stats on the Bukkit main thread
        long playtime = 0;
        if (playerData.containsKey("totalPlaytimeMs")) {
            playtime = ((Number) playerData.get("totalPlaytimeMs")).longValue();
        }
        
        double balance = 0.0;
        if (playerData.containsKey("vault")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> vaultData = (Map<String, Object>) playerData.get("vault");
            if (vaultData != null && vaultData.containsKey("balance")) {
                balance = ((Number) vaultData.get("balance")).doubleValue();
            }
        }
        
        int kills = player.getStatistic(org.bukkit.Statistic.PLAYER_KILLS);
        int deaths = player.getStatistic(org.bukkit.Statistic.DEATHS);
        
        // Evaluate votes via PAPI if available
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
        
        final long finalPlaytime = playtime;
        final double finalBalance = balance;
        final int finalKills = kills;
        final int finalDeaths = deaths;
        final int finalVotes = votes;
        
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                String inventoryJson = mapper.writeValueAsString(inventoryList);
                String armorJson = mapper.writeValueAsString(armorMap);
                String effectsJson = mapper.writeValueAsString(effectsList);

                // Vault data
                String vaultGroup = null;
                String vaultPrefix = null;
                String vaultSuffix = null;
                if (playerData.containsKey("vault")) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> vaultData = (Map<String, Object>) playerData.get("vault");
                    vaultGroup = (String) vaultData.get("group");
                    vaultPrefix = (String) vaultData.get("prefix");
                    vaultSuffix = (String) vaultData.get("suffix");
                }

                // LuckPerms data
                String lpGroup = null;
                if (playerData.containsKey("luckperms")) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> lpData = (Map<String, Object>) playerData.get("luckperms");
                    lpGroup = (String) lpData.get("primaryGroup");
                }

                @SuppressWarnings("unchecked")
                Map<String, Object> loc = (Map<String, Object>) playerData.get("location");

                String playerUuid = player.getUniqueId().toString();
                String playerName = player.getName();
                long snapshotTime = System.currentTimeMillis();

                final String finalVaultGroup = vaultGroup;
                final String finalVaultPrefix = vaultPrefix;
                final String finalVaultSuffix = vaultSuffix;
                final String finalLpGroup = lpGroup;

                plugin.getDatabaseManager().getJdbi().inTransaction(h -> {
                    PlayerSnapshotDao dao = h.attach(PlayerSnapshotDao.class);
                    dao.deleteByUuid(playerUuid);
                    dao.insertSnapshot(
                            UUID.randomUUID().toString(),
                            playerUuid,
                            playerName,
                            snapshotTime,
                            (String) loc.get("world"),
                            (Double) loc.get("x"),
                            (Double) loc.get("y"),
                            (Double) loc.get("z"),
                            (Float) loc.get("yaw"),
                            (Float) loc.get("pitch"),
                            (Double) playerData.get("health"),
                            (Double) playerData.get("maxHealth"),
                            (Integer) playerData.get("foodLevel"),
                            (Float) playerData.get("saturation"),
                            (Integer) playerData.get("expLevel"),
                            (Float) playerData.get("expProgress"),
                            (Integer) playerData.get("totalExp"),
                            (String) playerData.get("gamemode"),
                            inventoryJson,
                            armorJson,
                            effectsJson,
                            null, // skin texture
                            null, // skin signature
                            finalVaultGroup,
                            finalVaultPrefix,
                            finalVaultSuffix,
                            finalLpGroup,
                            finalPlaytime,
                            finalBalance,
                            finalKills,
                            finalDeaths,
                            finalVotes,
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
                    return null;
                });
            } catch (JsonProcessingException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to serialize snapshot data for " + player.getName(), e);
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Database error while saving snapshot for " + player.getName(), e);
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
