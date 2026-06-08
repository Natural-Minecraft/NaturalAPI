package id.naturalsmp.naturalApi.integration;

import id.naturalsmp.naturalApi.NaturalAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PapiIntegration {

    private final NaturalAPI plugin;
    private boolean enabled = false;

    public PapiIntegration(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public boolean setup() {
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            enabled = true;
            return true;
        }
        return false;
    }

    public Map<String, String> evaluate(OfflinePlayer player, List<String> placeholders) {
        Map<String, String> results = new HashMap<>();
        if (!enabled) return results;

        for (String placeholder : placeholders) {
            String value = PlaceholderAPI.setPlaceholders(player, placeholder);
            results.put(placeholder, value);
        }
        return results;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<String> getRegisteredExpansions() {
        if (!enabled) return new java.util.ArrayList<>();
        return new java.util.ArrayList<>(PlaceholderAPI.getRegisteredIdentifiers());
    }
}
