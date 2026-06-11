package id.naturalsmp.naturalApi;

import id.naturalsmp.naturalApi.command.NapiCommand;
import id.naturalsmp.naturalApi.config.ConfigManager;
import id.naturalsmp.naturalApi.database.DatabaseManager;
import id.naturalsmp.naturalApi.http.HttpServer;
import id.naturalsmp.naturalApi.integration.IntegrationManager;
import id.naturalsmp.naturalApi.service.PlayerService;
import id.naturalsmp.naturalApi.service.ServerService;
import id.naturalsmp.naturalApi.service.SnapshotService;
import id.naturalsmp.naturalApi.service.WorldService;
import id.naturalsmp.naturalApi.service.AuthService;
import id.naturalsmp.naturalApi.service.LeaderboardService;
import org.bukkit.plugin.java.JavaPlugin;

public class NaturalAPI extends JavaPlugin {

    private static NaturalAPI instance;
    private ConfigManager configManager;
    private DatabaseManager databaseManager;
    private HttpServer httpServer;
    private IntegrationManager integrationManager;
    private SnapshotService snapshotService;
    private ServerService serverService;
    private PlayerService playerService;
    private WorldService worldService;
    private AuthService authService;
    private LeaderboardService leaderboardService;
    private id.naturalsmp.naturalApi.service.GeoIpService geoIpService;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Starting NaturalAPI v" + getDescription().getVersion());

        // Load config
        configManager = new ConfigManager(this);
        configManager.loadConfig();

        // Initialize Database
        databaseManager = new DatabaseManager(this, configManager);
        databaseManager.connect();

        // Initialize Integrations
        integrationManager = new IntegrationManager(this);
        integrationManager.initialize();

        // Start Services
        authService = new AuthService(this);
        serverService = new ServerService(this);
        getServer().getScheduler().runTaskTimer(this, serverService, 1L, 1L);
        playerService = new PlayerService(this);
        worldService = new WorldService(this);
        geoIpService = new id.naturalsmp.naturalApi.service.GeoIpService(this);
        snapshotService = new SnapshotService(this);
        leaderboardService = new LeaderboardService(this);
        
        // Start HTTP Server
        httpServer = new HttpServer(this, configManager);
        httpServer.start();
        snapshotService.start();

        // Register Commands
        getCommand("napi").setExecutor(new NapiCommand(this));

        // Register Listeners
        getServer().getPluginManager().registerEvents(new id.naturalsmp.naturalApi.listener.PlayerListener(this), this);

        // Start WebSocket server stats broadcaster (interval dari config)
        if (configManager.isWebSocketEnabled() && getConfig().getBoolean("features.websocket.endpoints.server-stats", true)) {
            int intervalTicks = configManager.getWebSocketStatsInterval();
            // runTaskTimer is synchronous, ensuring thread-safe access to Bukkit APIs.
            // WsBroadcaster.broadcast handles the asynchronous network writes itself.
            getServer().getScheduler().runTaskTimer(this, () -> {
                try {
                    id.naturalsmp.naturalApi.websocket.WsServer wsServer = httpServer.getWsServer();
                    if (wsServer != null) {
                        java.util.Map<String, Object> payload = new java.util.HashMap<>();
                        payload.put("type", "stats");
                        payload.put("timestamp", System.currentTimeMillis());
                        payload.put("data", serverService.getServerStatus());
                        wsServer.getBroadcaster().broadcast(
                            id.naturalsmp.naturalApi.websocket.WsServer.CHANNEL_SERVER_STATS,
                            payload
                        );
                    }
                } catch (Exception e) {
                    // silently ignore broadcast errors
                }
            }, 20L, (long) intervalTicks);
        }

        getLogger().info("NaturalAPI enabled successfully.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling NaturalAPI...");

        // Cancel all tasks registered by this plugin (prevents repeating task leaks)
        getServer().getScheduler().cancelTasks(this);

        // Unregister all listeners (prevents event listener duplicate leaks)
        org.bukkit.event.HandlerList.unregisterAll(this);

        if (snapshotService != null) {
            snapshotService.stop();
        }

        if (httpServer != null) {
            httpServer.stop();
        }

        if (databaseManager != null) {
            databaseManager.disconnect();
        }

        getLogger().info("NaturalAPI disabled.");
    }

    public static NaturalAPI getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public HttpServer getHttpServer() {
        return httpServer;
    }

    public IntegrationManager getIntegrationManager() {
        return integrationManager;
    }

    public ServerService getServerService() {
        return serverService;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public WorldService getWorldService() {
        return worldService;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public SnapshotService getSnapshotService() {
        return snapshotService;
    }

    public LeaderboardService getLeaderboardService() {
        return leaderboardService;
    }

    public id.naturalsmp.naturalApi.service.GeoIpService getGeoIpService() {
        return geoIpService;
    }
}
