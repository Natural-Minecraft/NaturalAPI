package id.naturalsmp.naturalApi.config;

import id.naturalsmp.naturalApi.NaturalAPI;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final NaturalAPI plugin;
    private FileConfiguration config;

    public ConfigManager(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    public int getHttpPort() {
        return config.getInt("server.port", 7890);
    }

    public String getHttpBindAddress() {
        return config.getString("server.bind-address", "0.0.0.0");
    }

    public String getBasePath() {
        return config.getString("server.base-path", "/api/v1");
    }

    public String getDatabaseProvider() {
        return config.getString("database.provider", "sqlite");
    }

    public boolean isServerEndpointsEnabled() {
        return config.getBoolean("features.endpoints.server", true);
    }

    public boolean isPlayerEndpointsEnabled() {
        return config.getBoolean("features.endpoints.players", true);
    }

    public boolean isWorldEndpointsEnabled() {
        return config.getBoolean("features.endpoints.worlds", true);
    }

    public boolean isOfflinePlayerEndpointsEnabled() {
        return config.getBoolean("features.endpoints.offline-players", true);
    }

    public boolean isWebSocketEnabled() {
        return config.getBoolean("features.websocket.enabled", true);
    }

    public int getWebSocketStatsInterval() {
        return config.getInt("features.websocket.server-stats-interval", 20);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
