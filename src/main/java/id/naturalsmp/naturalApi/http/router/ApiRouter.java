package id.naturalsmp.naturalApi.http.router;

import id.naturalsmp.naturalApi.NaturalAPI;
import io.javalin.apibuilder.ApiBuilder;

import id.naturalsmp.naturalApi.http.controller.IntegrationController;
import id.naturalsmp.naturalApi.http.controller.PlayerController;
import id.naturalsmp.naturalApi.http.controller.ServerController;
import id.naturalsmp.naturalApi.http.controller.WorldController;
import id.naturalsmp.naturalApi.http.controller.IntegrationController;
import id.naturalsmp.naturalApi.http.controller.AdminController;
import id.naturalsmp.naturalApi.http.controller.LeaderboardController;
import id.naturalsmp.naturalApi.http.middleware.AuthMiddleware;
import id.naturalsmp.naturalApi.http.middleware.CorsMiddleware;
import id.naturalsmp.naturalApi.http.middleware.IpAllowlistMiddleware;
import id.naturalsmp.naturalApi.http.middleware.RateLimiterMiddleware;

public class ApiRouter {

    public static void register(NaturalAPI plugin) {
        String basePath = plugin.getConfigManager().getBasePath();
        ServerController serverController = new ServerController(plugin);
        PlayerController playerController = new PlayerController(plugin);
        WorldController worldController = new WorldController(plugin);
        IntegrationController integrationController = new IntegrationController(plugin);
        AdminController adminController = new AdminController(plugin);
        LeaderboardController leaderboardController = new LeaderboardController(plugin);
        
        // Setup Global Middlewares
        CorsMiddleware corsMiddleware = new CorsMiddleware(plugin);
        IpAllowlistMiddleware ipAllowlistMiddleware = new IpAllowlistMiddleware(plugin);
        RateLimiterMiddleware rateLimiterMiddleware = new RateLimiterMiddleware(plugin);
        
        // Register global before handler using ApiBuilder (works inside apiBuilder context)
        ApiBuilder.before(ctx -> {
            corsMiddleware.handle(ctx);
            // Skip IP check and RateLimit if it's OPTIONS
            if (!ctx.method().toString().equals("OPTIONS")) {
                ipAllowlistMiddleware.handle(ctx);
                if (ctx.statusCode() != 403) {
                    rateLimiterMiddleware.handle(ctx);
                }
            }
        });
        
        ApiBuilder.path(basePath, () -> {
            // Server Endpoints
            if (plugin.getConfigManager().isServerEndpointsEnabled()) {
                ApiBuilder.path("/server", () -> {
                    ApiBuilder.before(new AuthMiddleware(plugin, "read:server"));
                    ApiBuilder.get("/", sync(plugin, serverController::getServerStatus));
                    ApiBuilder.get("/status", sync(plugin, serverController::getServerStatus));
                    ApiBuilder.get("/tps", sync(plugin, serverController::getTps));
                    ApiBuilder.get("/mspt", sync(plugin, serverController::getMspt));
                    ApiBuilder.get("/ram", sync(plugin, serverController::getRam));
                    ApiBuilder.get("/uptime", sync(plugin, serverController::getUptime));
                    ApiBuilder.get("/version", sync(plugin, serverController::getVersion));
                    ApiBuilder.get("/players/count", sync(plugin, serverController::getPlayerCount));
                    ApiBuilder.get("/plugins", sync(plugin, serverController::getPlugins));
                    ApiBuilder.get("/whitelist", sync(plugin, serverController::getWhitelist));
                    ApiBuilder.get("/banlist", sync(plugin, serverController::getBanlist));
                    ApiBuilder.get("/leaderboard", sync(plugin, leaderboardController::getLeaderboard));
                });
            }

            // Player Endpoints
            if (plugin.getConfigManager().isPlayerEndpointsEnabled()) {
                ApiBuilder.path("/players", () -> {
                    ApiBuilder.before(new AuthMiddleware(plugin, "read:players"));
                    ApiBuilder.get("/", sync(plugin, playerController::getPlayers));
                    ApiBuilder.get("/all", sync(plugin, playerController::getAllPlayers));
                    
                    ApiBuilder.path("/{identifier}", () -> {
                        ApiBuilder.get("/", sync(plugin, playerController::getPlayer));
                        ApiBuilder.get("/location", sync(plugin, playerController::getLocation));
                        ApiBuilder.get("/health", sync(plugin, playerController::getHealth));
                        ApiBuilder.get("/experience", sync(plugin, playerController::getExperience));
                        ApiBuilder.get("/gamemode", sync(plugin, playerController::getGamemode));
                        ApiBuilder.get("/inventory", sync(plugin, playerController::getInventory));
                        ApiBuilder.get("/inventory/hotbar", sync(plugin, playerController::getHotbar));
                        ApiBuilder.get("/inventory/armor", sync(plugin, playerController::getArmor));
                        ApiBuilder.get("/inventory/offhand", sync(plugin, playerController::getOffhand));
                        ApiBuilder.get("/effects", sync(plugin, playerController::getEffects));
                        ApiBuilder.get("/skin", sync(plugin, playerController::getSkin));
                        ApiBuilder.get("/ping", sync(plugin, playerController::getPing));
                        ApiBuilder.get("/network", sync(plugin, playerController::getNetwork));
                        ApiBuilder.get("/stats", sync(plugin, playerController::getStats));
                        ApiBuilder.get("/permissions", sync(plugin, playerController::getPermissions));
                        ApiBuilder.get("/permission/{node}", sync(plugin, playerController::checkPermission));
                        ApiBuilder.get("/snapshot", sync(plugin, playerController::getSnapshot));
                        ApiBuilder.post("/snapshot", sync(plugin, playerController::createSnapshot));
                    });
                    
                    // UUID and Name aliases (same logic because identifier resolves both)
                    ApiBuilder.path("/name/{identifier}", () -> {
                        ApiBuilder.get("/", sync(plugin, playerController::getPlayer));
                        ApiBuilder.get("/location", sync(plugin, playerController::getLocation));
                        ApiBuilder.get("/health", sync(plugin, playerController::getHealth));
                        ApiBuilder.get("/experience", sync(plugin, playerController::getExperience));
                        ApiBuilder.get("/gamemode", sync(plugin, playerController::getGamemode));
                        ApiBuilder.get("/inventory", sync(plugin, playerController::getInventory));
                        ApiBuilder.get("/inventory/hotbar", sync(plugin, playerController::getHotbar));
                        ApiBuilder.get("/inventory/armor", sync(plugin, playerController::getArmor));
                        ApiBuilder.get("/inventory/offhand", sync(plugin, playerController::getOffhand));
                        ApiBuilder.get("/effects", sync(plugin, playerController::getEffects));
                        ApiBuilder.get("/skin", sync(plugin, playerController::getSkin));
                        ApiBuilder.get("/ping", sync(plugin, playerController::getPing));
                        ApiBuilder.get("/network", sync(plugin, playerController::getNetwork));
                        ApiBuilder.get("/stats", sync(plugin, playerController::getStats));
                        ApiBuilder.get("/permissions", sync(plugin, playerController::getPermissions));
                        ApiBuilder.get("/permission/{node}", sync(plugin, playerController::checkPermission));
                        ApiBuilder.get("/snapshot", sync(plugin, playerController::getSnapshot));
                        ApiBuilder.post("/snapshot", sync(plugin, playerController::createSnapshot));
                    });

                    if (plugin.getConfigManager().isOfflinePlayerEndpointsEnabled()) {
                        ApiBuilder.path("/offline/{identifier}", () -> {
                            ApiBuilder.get("/", sync(plugin, playerController::getOfflinePlayer));
                        });
                        ApiBuilder.path("/offline/name/{identifier}", () -> {
                            ApiBuilder.get("/", sync(plugin, playerController::getOfflinePlayer));
                        });
                    }
                });
            }

            // World Endpoints
            if (plugin.getConfigManager().isWorldEndpointsEnabled()) {
                ApiBuilder.path("/worlds", () -> {
                    ApiBuilder.before(new AuthMiddleware(plugin, "read:worlds"));
                    ApiBuilder.get("/", sync(plugin, worldController::getWorlds));
                    
                    ApiBuilder.path("/{name}", () -> {
                        ApiBuilder.get("/", sync(plugin, worldController::getWorld));
                        ApiBuilder.get("/time", sync(plugin, worldController::getTime));
                        ApiBuilder.get("/weather", sync(plugin, worldController::getWeather));
                        ApiBuilder.get("/players", sync(plugin, worldController::getPlayers));
                        ApiBuilder.get("/entities", sync(plugin, worldController::getEntities));
                        ApiBuilder.get("/chunks", sync(plugin, worldController::getChunks));
                        ApiBuilder.get("/border", sync(plugin, worldController::getBorder));
                        ApiBuilder.get("/gamerules", sync(plugin, worldController::getGamerules));
                    });
                });
            }

            // Integration Endpoints
            ApiBuilder.path("/vault", () -> {
                ApiBuilder.before(new AuthMiddleware(plugin, "read:vault"));
                ApiBuilder.get("/player/{identifier}", sync(plugin, integrationController::getVaultData));
                ApiBuilder.get("/groups", sync(plugin, integrationController::getVaultGroups));
                ApiBuilder.get("/groups/{group}", sync(plugin, integrationController::getVaultGroup));
                ApiBuilder.get("/economy/status", sync(plugin, integrationController::getVaultEconomyStatus));
            });

            ApiBuilder.path("/luckperms", () -> {
                ApiBuilder.before(new AuthMiddleware(plugin, "read:luckperms"));
                ApiBuilder.get("/player/{identifier}", sync(plugin, integrationController::getLuckPermsData));
                ApiBuilder.get("/groups", sync(plugin, integrationController::getLpGroups));
                ApiBuilder.get("/groups/{group}", sync(plugin, integrationController::getLpGroup));
                ApiBuilder.get("/groups/{group}/members", sync(plugin, integrationController::getLpGroupMembers));
                ApiBuilder.get("/groups/{group}/permissions", sync(plugin, integrationController::getLpGroupPermissions));
            });

            ApiBuilder.path("/papi", () -> {
                ApiBuilder.before(new AuthMiddleware(plugin, "read:papi"));
                ApiBuilder.post("/evaluate", sync(plugin, integrationController::evaluatePapi));
                ApiBuilder.get("/plugins", sync(plugin, integrationController::getPapiPlugins));
            });
            
            // Admin Endpoints
            ApiBuilder.path("/admin", () -> {
                ApiBuilder.get("/health", adminController::getHealth); // No auth needed
                ApiBuilder.get("/openapi.yaml", adminController::getOpenApiYaml);
                ApiBuilder.get("/openapi.json", adminController::getOpenApiJson);
                
                ApiBuilder.path("/", () -> {
                    ApiBuilder.before(new AuthMiddleware(plugin, "admin"));
                    ApiBuilder.get("/keys", adminController::getKeys);
                    ApiBuilder.post("/keys", adminController::generateKey);
                    ApiBuilder.delete("/keys/{keyId}", adminController::revokeKey);
                    ApiBuilder.get("/config", adminController::getConfig);
                    ApiBuilder.post("/reload", adminController::reloadPlugin);
                    ApiBuilder.get("/rate-limits", adminController::getRateLimits);
                    ApiBuilder.delete("/rate-limits/{ip}", adminController::resetRateLimit);
                    ApiBuilder.get("/snapshot/history", adminController::getSnapshotHistory);
                    ApiBuilder.delete("/snapshot/purge", adminController::purgeSnapshots);
                });
            });
        });
    }

    private static io.javalin.http.Handler sync(NaturalAPI plugin, io.javalin.http.Handler handler) {
        return ctx -> {
            java.util.concurrent.CompletableFuture<Void> future = new java.util.concurrent.CompletableFuture<>();
            org.bukkit.Bukkit.getScheduler().runTask(plugin, () -> {
                try {
                    handler.handle(ctx);
                    future.complete(null);
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            });
            ctx.future(() -> future);
        };
    }
}

