package id.naturalsmp.naturalApi.http.controller;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.service.PlayerService;
import id.naturalsmp.naturalApi.util.LocationSerializer;
import id.naturalsmp.naturalApi.util.ResponseBuilder;
import io.javalin.http.Context;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import id.naturalsmp.naturalApi.util.SkinResolver;

public class PlayerController {

    private final NaturalAPI plugin;
    private final PlayerService playerService;

    public PlayerController(NaturalAPI plugin) {
        this.plugin = plugin;
        this.playerService = plugin.getPlayerService();
    }

    public void getPlayers(Context ctx) {
        boolean includeVanished = ctx.queryParamAsClass("includeVanished", Boolean.class).getOrDefault(false);
        ctx.json(ResponseBuilder.success(playerService.getOnlinePlayers(includeVanished)));
    }

    public void getAllPlayers(Context ctx) {
        int page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        int pageSize = ctx.queryParamAsClass("pageSize", Integer.class).getOrDefault(50);
        String search = ctx.queryParam("search");
        String status = ctx.queryParam("status");
        boolean includeVanished = ctx.queryParamAsClass("includeVanished", Boolean.class).getOrDefault(false);
        
        // Enforce limits to prevent abuse
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;
        if (pageSize > 100) pageSize = 100;
        
        ctx.json(ResponseBuilder.success(playerService.getAllPlayersPaged(page, pageSize, search, status, includeVanished)));
    }

    public void getPlayer(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            ctx.json(ResponseBuilder.success(playerService.getPlayerFull(player)));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                ctx.json(ResponseBuilder.success(offlineData));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    public void getLocation(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            ctx.json(ResponseBuilder.success(LocationSerializer.serialize(player.getLocation())));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                ctx.json(ResponseBuilder.success(offlineData.get("location")));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    public void getHealth(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("health", player.getHealth());
            data.put("maxHealth", player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue());
            data.put("foodLevel", player.getFoodLevel());
            data.put("saturation", player.getSaturation());
            ctx.json(ResponseBuilder.success(data));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("health", offlineData.get("health"));
                data.put("maxHealth", offlineData.get("maxHealth"));
                data.put("foodLevel", offlineData.get("foodLevel"));
                data.put("saturation", offlineData.get("saturation"));
                ctx.json(ResponseBuilder.success(data));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    public void getExperience(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("expLevel", player.getLevel());
            data.put("expProgress", player.getExp());
            data.put("totalExp", player.getTotalExperience());
            ctx.json(ResponseBuilder.success(data));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("expLevel", offlineData.get("level"));
                data.put("expProgress", offlineData.get("exp"));
                data.put("totalExp", offlineData.get("totalExperience"));
                ctx.json(ResponseBuilder.success(data));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    public void getGamemode(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            ctx.json(ResponseBuilder.success(player.getGameMode().name()));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                ctx.json(ResponseBuilder.success(offlineData.get("gamemode")));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    public void getInventory(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            ctx.json(ResponseBuilder.success(playerService.getInventory(player)));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                ctx.json(ResponseBuilder.success(offlineData.get("inventory")));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void getHotbar(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            ctx.json(ResponseBuilder.success(playerService.getHotbar(player)));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                List<Map<String, Object>> inventory = (List<Map<String, Object>>) offlineData.get("inventory");
                List<Map<String, Object>> hotbar = new ArrayList<>();
                if (inventory != null) {
                    for (Map<String, Object> item : inventory) {
                        if (item != null) {
                            Object slotObj = item.get("slot");
                            if (slotObj instanceof Number) {
                                int slot = ((Number) slotObj).intValue();
                                if (slot >= 0 && slot <= 8) {
                                    hotbar.add(item);
                                }
                            }
                        }
                    }
                }
                ctx.json(ResponseBuilder.success(hotbar));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    public void getArmor(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            ctx.json(ResponseBuilder.success(playerService.getArmor(player)));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                ctx.json(ResponseBuilder.success(offlineData.get("armor")));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void getOffhand(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            ctx.json(ResponseBuilder.success(playerService.getOffhand(player)));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                List<Map<String, Object>> inventory = (List<Map<String, Object>>) offlineData.get("inventory");
                Map<String, Object> offhand = null;
                if (inventory != null) {
                    for (Map<String, Object> item : inventory) {
                        if (item != null) {
                            Object slotObj = item.get("slot");
                            if (slotObj instanceof Number && ((Number) slotObj).intValue() == 40) {
                                offhand = item;
                                break;
                            }
                        }
                    }
                }
                ctx.json(ResponseBuilder.success(offhand));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    public void getEffects(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            ctx.json(ResponseBuilder.success(playerService.getEffects(player)));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                ctx.json(ResponseBuilder.success(offlineData.get("effects")));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    public void getPing(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            ctx.json(ResponseBuilder.success(player.getPing()));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                ctx.json(ResponseBuilder.success(null));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void getNetwork(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        
        java.util.concurrent.CompletableFuture<Map<String, Object>> networkFuture = java.util.concurrent.CompletableFuture.supplyAsync(() -> {
            Player player = playerService.getPlayer(identifier);
            if (player != null) {
                return playerService.getPlayerNetworkData(player);
            } else {
                return playerService.getOfflinePlayer(identifier);
            }
        });

        ctx.future(() -> networkFuture.thenAccept(data -> {
            if (data != null) {
                if (data.containsKey("ipAddress")) {
                    Map<String, Object> netData = new HashMap<>();
                    netData.put("ping", null); // Offline = no ping
                    netData.put("locale", data.get("locale"));
                    netData.put("clientBrand", data.get("clientBrand"));
                    netData.put("ipAddress", data.get("ipAddress"));
                    netData.put("country", data.get("country"));
                    netData.put("region", data.get("region"));
                    netData.put("city", data.get("city"));
                    netData.put("isp", data.get("isp"));
                    netData.put("asn", data.get("asn"));
                    netData.put("ipHistory", data.getOrDefault("ipHistory", new ArrayList<>()));
                    ctx.json(ResponseBuilder.success(netData));
                } else {
                    ctx.json(ResponseBuilder.success(data));
                }
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }));
    }

    public void getStats(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("firstJoin", player.getFirstPlayed());
            data.put("lastSeen", player.getLastPlayed());
            data.put("totalPlaytimeMs", player.getStatistic(org.bukkit.Statistic.PLAY_ONE_MINUTE) * 50L);
            ctx.json(ResponseBuilder.success(data));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("firstJoin", offlineData.get("firstPlayed"));
                data.put("lastSeen", offlineData.get("lastSeen"));
                data.put("totalPlaytimeMs", null);
                ctx.json(ResponseBuilder.success(data));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    public void getSkin(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        
        java.util.concurrent.CompletableFuture<String> uuidFuture = new java.util.concurrent.CompletableFuture<>();
        org.bukkit.Bukkit.getScheduler().runTask(plugin, () -> {
            Player player = playerService.getPlayer(identifier);
            if (player != null) {
                uuidFuture.complete(player.getUniqueId().toString());
            } else {
                uuidFuture.complete(null);
            }
        });

        java.util.concurrent.CompletableFuture<Map<String, String>> skinFuture = uuidFuture.thenCompose(uuid -> {
            if (uuid != null) {
                return java.util.concurrent.CompletableFuture.supplyAsync(() -> {
                    SkinResolver resolver = new SkinResolver(plugin);
                    return resolver.getSkin(uuid);
                });
            } else {
                return java.util.concurrent.CompletableFuture.supplyAsync(() -> {
                    Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
                    if (offlineData != null) {
                        String offUuid = (String) offlineData.get("uuid");
                        SkinResolver resolver = new SkinResolver(plugin);
                        return resolver.getSkin(offUuid);
                    }
                    return null;
                });
            }
        });

        ctx.future(() -> skinFuture.thenAccept(skin -> {
            if (skin != null && !skin.isEmpty()) {
                ctx.json(ResponseBuilder.success(skin));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player or skin not found."));
            }
        }));
    }

    public void getOfflinePlayer(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            ctx.json(ResponseBuilder.success(playerService.getPlayerFull(player)));
        } else {
            Map<String, Object> data = playerService.getOfflinePlayer(identifier);
            if (data == null) {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player snapshot not found."));
            } else {
                ctx.json(ResponseBuilder.success(data));
            }
        }
    }

    public void getPermissions(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            List<String> perms = new ArrayList<>();
            player.getEffectivePermissions().forEach(attachment -> {
                if (attachment.getValue()) {
                    perms.add(attachment.getPermission());
                }
            });
            ctx.json(ResponseBuilder.success(perms));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                ctx.json(ResponseBuilder.success(new ArrayList<>()));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    public void checkPermission(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        String node = ctx.pathParam("node");
        Player player = playerService.getPlayer(identifier);
        if (player != null) {
            boolean has = player.hasPermission(node);
            ctx.json(ResponseBuilder.success(Map.of("permission", node, "hasPermission", has)));
        } else {
            Map<String, Object> offlineData = playerService.getOfflinePlayer(identifier);
            if (offlineData != null) {
                ctx.json(ResponseBuilder.success(Map.of("permission", node, "hasPermission", false)));
            } else {
                ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found."));
            }
        }
    }

    public void getSnapshot(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Map<String, Object> data = playerService.getOfflinePlayer(identifier);
        if (data == null) {
            ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player snapshot not found."));
        } else {
            ctx.json(ResponseBuilder.success(data));
        }
    }

    public void createSnapshot(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Player player = playerService.getPlayer(identifier);
        if (player == null) {
            ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found or offline."));
            return;
        }
        
        plugin.getSnapshotService().saveSnapshotAsync(player);
        ctx.json(ResponseBuilder.success(Map.of("message", "Snapshot triggered successfully.", "player", player.getName())));
    }
}
