package id.naturalsmp.naturalApi.integration;

import id.naturalsmp.naturalApi.NaturalAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NaturalCoreIntegration {

    private final NaturalAPI plugin;
    private boolean enabled = false;

    public NaturalCoreIntegration(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public boolean setup() {
        if (Bukkit.getPluginManager().isPluginEnabled("NaturalCore")) {
            try {
                // Verify class exists and main plugin is loadable
                Class.forName("id.naturalsmp.naturalcore.NaturalCore");
                enabled = true;
                return true;
            } catch (ClassNotFoundException e) {
                plugin.getLogger().warning("NaturalCore plugin detected but API class not found!");
            }
        }
        enabled = false;
        return false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isVanished(Player player) {
        if (!enabled || player == null) {
            return false;
        }
        try {
            id.naturalsmp.naturalcore.NaturalCore core = id.naturalsmp.naturalcore.NaturalCore.getInstance();
            if (core != null && core.getVanishManager() != null) {
                return core.getVanishManager().isVanished(player);
            }
        } catch (Throwable e) {
            // Ignore
        }
        return false;
    }

    public boolean isAFK(Player player) {
        if (!enabled || player == null) {
            return false;
        }
        try {
            id.naturalsmp.naturalcore.NaturalCore core = id.naturalsmp.naturalcore.NaturalCore.getInstance();
            if (core != null && core.getAFKManager() != null) {
                return core.getAFKManager().isAFK(player);
            }
        } catch (Throwable e) {
            // Ignore
        }
        return false;
    }

    public boolean isInStaffMode(Player player) {
        if (!enabled || player == null) {
            return false;
        }
        try {
            id.naturalsmp.naturalcore.NaturalCore core = id.naturalsmp.naturalcore.NaturalCore.getInstance();
            if (core != null && core.getStaffManager() != null) {
                return core.getStaffManager().isInStaffMode(player);
            }
        } catch (Throwable e) {
            // Ignore
        }
        return false;
    }
}
