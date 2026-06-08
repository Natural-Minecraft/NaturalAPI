package id.naturalsmp.naturalApi.integration;

import id.naturalsmp.naturalApi.NaturalAPI;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.HashMap;
import java.util.Map;

public class VaultIntegration {

    private final NaturalAPI plugin;
    private Economy econ = null;
    private Permission perms = null;
    private Chat chat = null;

    public VaultIntegration(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public boolean setup() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        setupEconomy();
        setupChat();
        setupPermissions();
        return true;
    }

    private boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = plugin.getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null) return false;
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) return false;
        perms = rsp.getProvider();
        return perms != null;
    }

    public Map<String, Object> getVaultData(Player player) {
        Map<String, Object> data = new HashMap<>();
        
        if (perms != null) {
            data.put("group", perms.getPrimaryGroup(player));
            data.put("groups", perms.getPlayerGroups(player));
        }

        if (chat != null) {
            data.put("prefix", chat.getPlayerPrefix(player));
            data.put("suffix", chat.getPlayerSuffix(player));
        }

        if (econ != null) {
            data.put("balance", econ.getBalance((OfflinePlayer) player));
            data.put("currency", econ.currencyNamePlural());
        }

        if (perms != null) data.put("permissionPlugin", perms.getName());
        if (econ != null) data.put("economyPlugin", econ.getName());

        return data;
    }

    public boolean isEnabled() {
        return econ != null || perms != null || chat != null;
    }

    public String[] getGroups() {
        if (perms != null) {
            try {
                return perms.getGroups();
            } catch (UnsupportedOperationException e) {
                // Some permission plugins might not support getGroups
            }
        }
        return new String[0];
    }

    public Map<String, Object> getGroupDetails(String group) {
        Map<String, Object> details = new HashMap<>();
        details.put("name", group);
        if (chat != null) {
            String world = Bukkit.getWorlds().isEmpty() ? "world" : Bukkit.getWorlds().get(0).getName();
            try {
                details.put("prefix", chat.getGroupPrefix(world, group));
                details.put("suffix", chat.getGroupSuffix(world, group));
            } catch (UnsupportedOperationException e) {
                // Ignore
            }
        }
        return details;
    }

    public Map<String, Object> getEconomyStatus() {
        Map<String, Object> status = new HashMap<>();
        if (econ != null) {
            status.put("enabled", econ.isEnabled());
            status.put("name", econ.getName());
            status.put("currencyPlural", econ.currencyNamePlural());
            status.put("currencySingular", econ.currencyNameSingular());
        } else {
            status.put("enabled", false);
        }
        return status;
    }

    public double getBalance(OfflinePlayer player) {
        if (econ != null) {
            try {
                return econ.getBalance(player);
            } catch (Exception e) {
                // Ignore
            }
        }
        return 0.0;
    }
}
