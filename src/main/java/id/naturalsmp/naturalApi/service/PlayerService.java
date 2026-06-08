package id.naturalsmp.naturalApi.service;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.util.ItemSerializer;
import id.naturalsmp.naturalApi.util.LocationSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import id.naturalsmp.naturalApi.database.dao.PlayerSnapshotDao;
import id.naturalsmp.naturalApi.database.dao.PlayerIpHistoryDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.OfflinePlayer;

public class PlayerService {

    private final NaturalAPI plugin;

    public PlayerService(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public List<Map<String, Object>> getOnlinePlayers(boolean includeVanished) {
        List<Map<String, Object>> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            boolean vanished = isPlayerVanished(player);
            if (vanished && !includeVanished) {
                continue;
            }
            players.add(getPlayerSummary(player));
        }
        return players;
    }

    public Map<String, Object> getAllPlayersPaged(int page, int pageSize, String search, String status, boolean includeVanished) {
        List<Map<String, Object>> allPlayers = new ArrayList<>();
        
        // 1. Get all online players
        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        java.util.Set<UUID> onlineUuids = new java.util.HashSet<>();
        
        for (Player p : onlinePlayers) {
            boolean vanished = isPlayerVanished(p);
            if (vanished && !includeVanished) {
                continue;
            }
            
            // Apply status filter
            if ("offline".equalsIgnoreCase(status)) {
                continue;
            }
            
            // Apply search filter
            if (search != null && !search.isEmpty()) {
                String term = search.toLowerCase();
                String name = p.getName() != null ? p.getName().toLowerCase() : "";
                String disp = p.getDisplayName() != null ? p.getDisplayName().toLowerCase() : "";
                if (!name.contains(term) && !disp.contains(term)) {
                    continue;
                }
            }
            
            Map<String, Object> summary = getPlayerSummary(p);
            // Add lastSeen for consistency
            summary.put("lastSeen", p.getLastPlayed());
            allPlayers.add(summary);
            onlineUuids.add(p.getUniqueId());
        }
        
        // 2. Get offline players
        if (!"online".equalsIgnoreCase(status)) {
            for (OfflinePlayer op : Bukkit.getOfflinePlayers()) {
                if (onlineUuids.contains(op.getUniqueId())) {
                    continue;
                }
                
                String username = op.getName();
                if (username == null) {
                    continue; // Skip if name is unknown
                }
                
                // Apply search filter
                if (search != null && !search.isEmpty()) {
                    String term = search.toLowerCase();
                    if (!username.toLowerCase().contains(term)) {
                        continue;
                    }
                }
                
                Map<String, Object> data = new HashMap<>();
                data.put("uuid", op.getUniqueId().toString());
                data.put("username", username);
                data.put("displayName", username);
                data.put("online", false);
                data.put("vanished", false);
                data.put("afk", false);
                data.put("staffMode", false);
                data.put("lastSeen", op.getLastPlayed());
                
                allPlayers.add(data);
            }
        }
        
        // Sort: Online first (descending), then by lastSeen (descending)
        allPlayers.sort((p1, p2) -> {
            boolean online1 = (boolean) p1.get("online");
            boolean online2 = (boolean) p2.get("online");
            if (online1 != online2) {
                return online1 ? -1 : 1; // Online first
            }
            
            long lastSeen1 = (long) p1.getOrDefault("lastSeen", 0L);
            long lastSeen2 = (long) p2.getOrDefault("lastSeen", 0L);
            return Long.compare(lastSeen2, lastSeen1); // Newest first
        });
        
        // Paginate
        int total = allPlayers.size();
        int totalPages = (int) Math.ceil((double) total / pageSize);
        if (totalPages == 0) {
            totalPages = 1;
        }
        
        // Bound page
        if (page < 1) page = 1;
        
        int fromIndex = (page - 1) * pageSize;
        List<Map<String, Object>> paginatedList = new ArrayList<>();
        if (fromIndex < total) {
            int toIndex = Math.min(fromIndex + pageSize, total);
            paginatedList = allPlayers.subList(fromIndex, toIndex);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("players", paginatedList);
        
        Map<String, Object> pagination = new HashMap<>();
        pagination.put("page", page);
        pagination.put("pageSize", pageSize);
        pagination.put("total", total);
        pagination.put("totalPages", totalPages);
        response.put("pagination", pagination);
        
        return response;
    }

    public Map<String, Object> getPlayerSummary(Player player) {
        Map<String, Object> data = new HashMap<>();
        data.put("uuid", player.getUniqueId().toString());
        data.put("username", player.getName());
        data.put("displayName", player.getDisplayName());
        data.put("online", true);
        data.put("vanished", isPlayerVanished(player));

        id.naturalsmp.naturalApi.integration.IntegrationManager im = plugin.getIntegrationManager();
        if (im != null && im.getNaturalCoreIntegration() != null && im.getNaturalCoreIntegration().isEnabled()) {
            data.put("afk", im.getNaturalCoreIntegration().isAFK(player));
            data.put("staffMode", im.getNaturalCoreIntegration().isInStaffMode(player));
        } else {
            data.put("afk", false);
            data.put("staffMode", false);
        }
        return data;
    }

    public boolean isPlayerVanished(Player player) {
        if (player == null) return false;
        id.naturalsmp.naturalApi.integration.IntegrationManager im = plugin.getIntegrationManager();
        if (im != null && im.getNaturalCoreIntegration() != null && im.getNaturalCoreIntegration().isEnabled()) {
            if (im.getNaturalCoreIntegration().isVanished(player)) {
                return true;
            }
        }
        if (player.hasMetadata("vanished")) {
            for (org.bukkit.metadata.MetadataValue value : player.getMetadata("vanished")) {
                if (value.asBoolean()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Player getPlayer(String identifier) {
        try {
            UUID uuid = UUID.fromString(identifier);
            return Bukkit.getPlayer(uuid);
        } catch (IllegalArgumentException e) {
            return Bukkit.getPlayerExact(identifier);
        }
    }

    public Map<String, Object> getPlayerFull(Player player) {
        Map<String, Object> data = getPlayerSummary(player);
        data.put("location", LocationSerializer.serialize(player.getLocation()));
        data.put("health", player.getHealth());
        data.put("maxHealth", player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue());
        data.put("foodLevel", player.getFoodLevel());
        data.put("saturation", player.getSaturation());
        data.put("expLevel", player.getLevel());
        data.put("expProgress", player.getExp());
        data.put("totalExp", player.getTotalExperience());
        data.put("gamemode", player.getGameMode().name());

        Map<String, Object> netData = getPlayerNetworkData(player);
        data.put("ping", netData.get("ping"));
        data.put("locale", netData.get("locale"));
        data.put("clientBrand", netData.get("clientBrand"));
        data.put("ipAddress", netData.get("ipAddress"));
        data.put("country", netData.get("country"));
        data.put("region", netData.get("region"));
        data.put("city", netData.get("city"));
        data.put("isp", netData.get("isp"));
        data.put("asn", netData.get("asn"));
        data.put("ipHistory", netData.get("ipHistory"));

        data.put("firstJoin", player.getFirstPlayed());
        data.put("lastSeen", player.getLastPlayed());
        data.put("totalPlaytimeMs", player.getStatistic(org.bukkit.Statistic.PLAY_ONE_MINUTE) * 50L);
        
        id.naturalsmp.naturalApi.integration.IntegrationManager im = plugin.getIntegrationManager();
        if (im != null) {
            if (im.getVaultIntegration() != null && im.getVaultIntegration().isEnabled()) {
                data.put("vault", im.getVaultIntegration().getVaultData(player));
            }
            if (im.getLuckPermsIntegration() != null && im.getLuckPermsIntegration().isEnabled()) {
                data.put("luckperms", im.getLuckPermsIntegration().getLuckPermsData(player));
            }
            if (im.getNaturalSchoolIntegration() != null && im.getNaturalSchoolIntegration().isEnabled()) {
                data.put("school", im.getNaturalSchoolIntegration().getSchoolData(player.getUniqueId()));
            }
        }
        
        return data;
    }

    public Map<String, Object> getPlayerNetworkData(Player player) {
        Map<String, Object> network = new HashMap<>();
        network.put("ping", player.getPing());
        network.put("locale", player.locale().getLanguage());
        network.put("clientBrand", player.getClientBrandName());

        if (player.getAddress() != null) {
            String ipAddress = player.getAddress().getAddress().getHostAddress();
            network.put("ipAddress", ipAddress);

            // Fetch GeoIP Info (caches internally)
            Map<String, Object> geo = plugin.getGeoIpService().getGeoIpInfo(ipAddress);
            network.put("country", geo.getOrDefault("country", "Unknown"));
            network.put("region", geo.getOrDefault("region", "Unknown"));
            network.put("city", geo.getOrDefault("city", "Unknown"));
            network.put("isp", geo.getOrDefault("isp", "Unknown"));
            network.put("asn", geo.getOrDefault("asn", "Unknown"));
        } else {
            network.put("ipAddress", null);
            network.put("country", null);
            network.put("region", null);
            network.put("city", null);
            network.put("isp", null);
            network.put("asn", null);
        }

        // Fetch IP history from database
        try {
            List<Map<String, Object>> rawHistory = plugin.getDatabaseManager().getJdbi().withHandle(h ->
                h.createQuery("SELECT * FROM napi_player_ip_history WHERE player_uuid = :uuid ORDER BY last_seen DESC")
                 .bind("uuid", player.getUniqueId().toString())
                 .mapToMap()
                 .list()
            );
            List<Map<String, Object>> history = new ArrayList<>();
            for (Map<String, Object> row : rawHistory) {
                Map<String, Object> item = new HashMap<>();
                item.put("ipAddress", getCaseInsensitive(row, "ip_address"));
                item.put("country", getCaseInsensitive(row, "country"));
                item.put("region", getCaseInsensitive(row, "region"));
                item.put("city", getCaseInsensitive(row, "city"));
                item.put("isp", getCaseInsensitive(row, "isp"));
                item.put("asn", getCaseInsensitive(row, "asn"));
                item.put("firstSeen", getCaseInsensitive(row, "first_seen"));
                item.put("lastSeen", getCaseInsensitive(row, "last_seen"));
                history.add(item);
            }
            network.put("ipHistory", history);
        } catch (Exception e) {
            network.put("ipHistory", new ArrayList<>());
        }

        return network;
    }

    public void processPlayerIpAndGeolocate(Player player) {
        if (player == null || player.getAddress() == null) {
            return;
        }
        String ipAddress = player.getAddress().getAddress().getHostAddress();
        String uuidStr = player.getUniqueId().toString();
        String name = player.getName();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                // Fetch GeoIP Info
                Map<String, Object> geo = plugin.getGeoIpService().getGeoIpInfo(ipAddress);
                String country = (String) geo.getOrDefault("country", "Unknown");
                String region = (String) geo.getOrDefault("region", "Unknown");
                String city = (String) geo.getOrDefault("city", "Unknown");
                String isp = (String) geo.getOrDefault("isp", "Unknown");
                String asn = (String) geo.getOrDefault("asn", "Unknown");

                java.util.Optional<Map<String, Object>> existing = plugin.getDatabaseManager().getJdbi().withHandle(h ->
                    h.createQuery("SELECT * FROM napi_player_ip_history WHERE player_uuid = :uuid AND ip_address = :ip LIMIT 1")
                     .bind("uuid", uuidStr)
                     .bind("ip", ipAddress)
                     .mapToMap()
                     .findOne()
                );
                PlayerIpHistoryDao dao = plugin.getDatabaseManager().getJdbi().onDemand(PlayerIpHistoryDao.class);

                if (existing.isPresent()) {
                    // Update last seen
                    String recordId = (String) existing.get().get("id");
                    if (recordId == null) {
                        recordId = (String) getCaseInsensitive(existing.get(), "id");
                    }
                    dao.updateLastSeen(recordId, name, System.currentTimeMillis());
                } else {
                    // Insert new record
                    String recordId = UUID.randomUUID().toString();
                    dao.insertRecord(
                            recordId,
                            uuidStr,
                            name,
                            ipAddress,
                            country,
                            region,
                            city,
                            isp,
                            asn,
                            System.currentTimeMillis(),
                            System.currentTimeMillis()
                    );
                }
            } catch (Exception e) {
                plugin.getLogger().log(java.util.logging.Level.SEVERE, "Failed to process IP history for " + name, e);
            }
        });
    }

    public List<Map<String, Object>> getInventory(Player player) {
        List<Map<String, Object>> inventory = new ArrayList<>();
        PlayerInventory inv = player.getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            Map<String, Object> serialized = ItemSerializer.serialize(item, i);
            if (serialized != null) {
                inventory.add(serialized);
            }
        }
        return inventory;
    }

    public List<Map<String, Object>> getHotbar(Player player) {
        List<Map<String, Object>> hotbar = new ArrayList<>();
        PlayerInventory inv = player.getInventory();
        for (int i = 0; i <= 8; i++) {
            ItemStack item = inv.getItem(i);
            Map<String, Object> serialized = ItemSerializer.serialize(item, i);
            if (serialized != null) {
                hotbar.add(serialized);
            }
        }
        return hotbar;
    }

    public Map<String, Object> getArmor(Player player) {
        Map<String, Object> armor = new HashMap<>();
        PlayerInventory inv = player.getInventory();
        armor.put("helmet", ItemSerializer.serialize(inv.getHelmet(), 39));
        armor.put("chestplate", ItemSerializer.serialize(inv.getChestplate(), 38));
        armor.put("leggings", ItemSerializer.serialize(inv.getLeggings(), 37));
        armor.put("boots", ItemSerializer.serialize(inv.getBoots(), 36));
        return armor;
    }

    public Map<String, Object> getOffhand(Player player) {
        return ItemSerializer.serialize(player.getInventory().getItemInOffHand(), 40);
    }

    public List<Map<String, Object>> getEffects(Player player) {
        List<Map<String, Object>> effects = new ArrayList<>();
        for (PotionEffect effect : player.getActivePotionEffects()) {
            Map<String, Object> eff = new HashMap<>();
            eff.put("type", effect.getType().getName());
            eff.put("amplifier", effect.getAmplifier());
            eff.put("durationTicks", effect.getDuration());
            eff.put("durationSeconds", effect.getDuration() / 20.0);
            eff.put("ambient", effect.isAmbient());
            eff.put("particles", effect.hasParticles());
            eff.put("icon", effect.hasIcon());
            effects.add(eff);
        }
        return effects;
    }

    public Map<String, Object> getOfflinePlayer(String identifier) {
        Optional<Map<String, Object>> snapshotOpt;
        try {
            UUID uuid = UUID.fromString(identifier);
            String uuidStr = uuid.toString();
            snapshotOpt = plugin.getDatabaseManager().getJdbi().withHandle(h ->
                h.createQuery("SELECT * FROM napi_player_snapshots WHERE player_uuid = :uuid ORDER BY snapshot_time DESC LIMIT 1")
                 .bind("uuid", uuidStr)
                 .mapToMap()
                 .findOne()
            );
        } catch (IllegalArgumentException e) {
            snapshotOpt = plugin.getDatabaseManager().getJdbi().withHandle(h ->
                h.createQuery("SELECT * FROM napi_player_snapshots WHERE player_name = :name ORDER BY snapshot_time DESC LIMIT 1")
                 .bind("name", identifier)
                 .mapToMap()
                 .findOne()
            );
        }

        if (snapshotOpt.isEmpty()) {
            return null;
        }

        return parseSnapshotMap(snapshotOpt.get());
    }

    private Object getCaseInsensitive(Map<String, Object> map, String key) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> parseSnapshotMap(Map<String, Object> dbMap) {
        Map<String, Object> data = new HashMap<>();
        
        String uuidStr = (String) getCaseInsensitive(dbMap, "player_uuid");
        String name = (String) getCaseInsensitive(dbMap, "player_name");
        
        data.put("uuid", uuidStr);
        data.put("username", name);
        data.put("displayName", name);
        data.put("online", false);
        data.put("vanished", false);

        // Bukkit offline player metadata
        long firstPlayed = 0;
        long lastSeen = 0;
        boolean isOp = false;
        boolean isBanned = false;
        boolean isWhitelisted = false;
        if (uuidStr != null) {
            try {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuidStr));
                firstPlayed = offlinePlayer.getFirstPlayed();
                lastSeen = offlinePlayer.getLastPlayed();
                isOp = offlinePlayer.isOp();
                isBanned = offlinePlayer.isBanned();
                isWhitelisted = offlinePlayer.isWhitelisted();
            } catch (Exception e) {
                // Ignore
            }
        }

        // If lastSeen from offlinePlayer is 0 or less, fallback to snapshot time
        if (lastSeen <= 0) {
            Number snapshotTime = (Number) getCaseInsensitive(dbMap, "snapshot_time");
            if (snapshotTime != null) {
                lastSeen = snapshotTime.longValue();
            }
        }

        data.put("firstPlayed", firstPlayed);
        data.put("lastSeen", lastSeen);
        data.put("isOp", isOp);
        data.put("isBanned", isBanned);
        data.put("isWhitelisted", isWhitelisted);

        // Location
        Map<String, Object> location = new HashMap<>();
        location.put("world", getCaseInsensitive(dbMap, "world"));
        location.put("x", getCaseInsensitive(dbMap, "x"));
        location.put("y", getCaseInsensitive(dbMap, "y"));
        location.put("z", getCaseInsensitive(dbMap, "z"));
        location.put("yaw", getCaseInsensitive(dbMap, "yaw"));
        location.put("pitch", getCaseInsensitive(dbMap, "pitch"));
        data.put("location", location);

        // Health & Food
        data.put("health", getCaseInsensitive(dbMap, "health"));
        data.put("maxHealth", getCaseInsensitive(dbMap, "max_health"));
        data.put("foodLevel", getCaseInsensitive(dbMap, "food_level"));
        data.put("saturation", getCaseInsensitive(dbMap, "saturation"));

        // Level & Experience
        data.put("level", getCaseInsensitive(dbMap, "exp_level"));
        data.put("exp", getCaseInsensitive(dbMap, "exp_progress"));
        data.put("totalExperience", getCaseInsensitive(dbMap, "total_exp"));

        // Gamemode
        data.put("gamemode", getCaseInsensitive(dbMap, "gamemode"));

        // Network data
        data.put("ipAddress", getCaseInsensitive(dbMap, "ip_address"));
        data.put("country", getCaseInsensitive(dbMap, "country"));
        data.put("region", getCaseInsensitive(dbMap, "region"));
        data.put("city", getCaseInsensitive(dbMap, "city"));
        data.put("isp", getCaseInsensitive(dbMap, "isp"));
        data.put("asn", getCaseInsensitive(dbMap, "asn"));
        data.put("locale", getCaseInsensitive(dbMap, "locale"));
        data.put("clientBrand", getCaseInsensitive(dbMap, "client_brand"));
        data.put("ping", getCaseInsensitive(dbMap, "ping"));

        // IP history — always attempt from dedicated table
        List<Map<String, Object>> ipHistory = new ArrayList<>();
        if (uuidStr != null) {
            try {
                List<Map<String, Object>> rawHistory = plugin.getDatabaseManager().getJdbi().withHandle(h ->
                    h.createQuery("SELECT * FROM napi_player_ip_history WHERE player_uuid = :uuid ORDER BY last_seen DESC")
                     .bind("uuid", uuidStr)
                     .mapToMap()
                     .list()
                );
                for (Map<String, Object> row : rawHistory) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("ipAddress", getCaseInsensitive(row, "ip_address"));
                    item.put("country", getCaseInsensitive(row, "country"));
                    item.put("region", getCaseInsensitive(row, "region"));
                    item.put("city", getCaseInsensitive(row, "city"));
                    item.put("isp", getCaseInsensitive(row, "isp"));
                    item.put("asn", getCaseInsensitive(row, "asn"));
                    item.put("firstSeen", getCaseInsensitive(row, "first_seen"));
                    item.put("lastSeen", getCaseInsensitive(row, "last_seen"));
                    ipHistory.add(item);
                }
            } catch (Exception e) {
                // Ignore
            }
        }
        data.put("ipHistory", ipHistory);

        // Jackson parsing
        ObjectMapper mapper = new ObjectMapper();
        
        // Inventory
        Object inventory = new ArrayList<>();
        String invJson = (String) getCaseInsensitive(dbMap, "inventory_json");
        if (invJson != null && !invJson.isEmpty()) {
            try {
                inventory = mapper.readValue(invJson, List.class);
            } catch (Exception e) {
                // Ignore
            }
        }
        data.put("inventory", inventory);

        // Armor
        Object armor = new HashMap<>();
        String armorJson = (String) getCaseInsensitive(dbMap, "armor_json");
        if (armorJson != null && !armorJson.isEmpty()) {
            try {
                armor = mapper.readValue(armorJson, Map.class);
            } catch (Exception e) {
                // Ignore
            }
        }
        data.put("armor", armor);

        // Effects
        Object effects = new ArrayList<>();
        String effJson = (String) getCaseInsensitive(dbMap, "effects_json");
        if (effJson != null && !effJson.isEmpty()) {
            try {
                effects = mapper.readValue(effJson, List.class);
            } catch (Exception e) {
                // Ignore
            }
        }
        data.put("effects", effects);

        // Integrations
        Map<String, Object> vault = new HashMap<>();
        vault.put("group", getCaseInsensitive(dbMap, "vault_group"));
        vault.put("prefix", getCaseInsensitive(dbMap, "vault_prefix"));
        vault.put("suffix", getCaseInsensitive(dbMap, "vault_suffix"));
        data.put("vault", vault);

        Map<String, Object> luckperms = new HashMap<>();
        luckperms.put("primaryGroup", getCaseInsensitive(dbMap, "lp_group"));
        data.put("luckperms", luckperms);

        id.naturalsmp.naturalApi.integration.IntegrationManager im = plugin.getIntegrationManager();
        if (im != null && im.getNaturalSchoolIntegration() != null && im.getNaturalSchoolIntegration().isEnabled() && uuidStr != null) {
            try {
                data.put("school", im.getNaturalSchoolIntegration().getSchoolData(UUID.fromString(uuidStr)));
            } catch (Exception e) {
                // Ignore
            }
        }

        return data;
    }
}
