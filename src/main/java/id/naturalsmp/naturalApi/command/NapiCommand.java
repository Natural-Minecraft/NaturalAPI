package id.naturalsmp.naturalApi.command;

import id.naturalsmp.naturalApi.NaturalAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NapiCommand implements CommandExecutor {

    private final NaturalAPI plugin;

    public NapiCommand(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("naturalapi.use")) {
            sender.sendMessage("§cYou don't have permission to use this command.");
            return true;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("§aNaturalAPI v" + plugin.getDescription().getVersion());
            sender.sendMessage("§7/napi help §f- Show this help menu");
            sender.sendMessage("§7/napi status §f- Show HTTP server status");
            if (sender.hasPermission("naturalapi.admin")) {
                sender.sendMessage("§7/napi reload §f- Reload the plugin");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("status")) {
            sender.sendMessage("§aNaturalAPI Status:");
            sender.sendMessage("§7HTTP Server: §a" + (plugin.getHttpServer().getApp() != null ? "Running" : "Stopped"));
            sender.sendMessage("§7Database: §a" + (plugin.getDatabaseManager().getJdbi() != null ? "Connected" : "Disconnected"));
            return true;
        }

        if (args[0].equalsIgnoreCase("snapshot")) {
            if (!sender.hasPermission("naturalapi.admin")) {
                sender.sendMessage("§cYou don't have permission to use this command.");
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage("§cUsage: /napi snapshot <player>");
                return true;
            }
            org.bukkit.entity.Player target = org.bukkit.Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cPlayer not found.");
                return true;
            }
            plugin.getSnapshotService().saveSnapshotAsync(target);
            sender.sendMessage("§aSnapshot triggered for " + target.getName());
            return true;
        }

        if (args[0].equalsIgnoreCase("key")) {
            if (!sender.hasPermission("naturalapi.admin")) {
                sender.sendMessage("§cYou don't have permission to use this command.");
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage("§cUsage: /napi key <generate|list|revoke|info>");
                return true;
            }

            id.naturalsmp.naturalApi.database.dao.ApiKeyDao dao = plugin.getDatabaseManager().getJdbi().onDemand(id.naturalsmp.naturalApi.database.dao.ApiKeyDao.class);

            if (args[1].equalsIgnoreCase("list")) {
                sender.sendMessage("§eAPI Keys:");
                java.util.List<java.util.Map<String, Object>> keys = plugin.getDatabaseManager().getJdbi().withHandle(h -> 
                    h.createQuery("SELECT * FROM napi_api_keys").mapToMap().list()
                );
                for (java.util.Map<String, Object> key : keys) {
                    sender.sendMessage("§7- ID: §f" + key.get("id") + " §7(Name: " + key.get("name") + ")");
                }
                return true;
            }

            if (args[1].equalsIgnoreCase("generate")) {
                if (args.length < 3) {
                    sender.sendMessage("§cUsage: /napi key generate <name> [scopes]");
                    return true;
                }
                String name = args[2];
                String scopes = args.length > 3 ? args[3] : "*";
                String[] generated = plugin.getAuthService().generateKey(name, scopes, null);
                sender.sendMessage("§aAPI Key generated successfully.");
                sender.sendMessage("§7Name: §f" + name);
                sender.sendMessage("§7Scopes: §f" + scopes);
                sender.sendMessage("§cKeep this key safe! It won't be shown again:");
                sender.sendMessage("§e" + generated[1]);
                return true;
            }

            if (args[1].equalsIgnoreCase("revoke")) {
                if (args.length < 3) {
                    sender.sendMessage("§cUsage: /napi key revoke <id>");
                    return true;
                }
                dao.deleteKey(args[2]);
                sender.sendMessage("§aKey revoked: " + args[2]);
                return true;
            }

            sender.sendMessage("§cUnknown key action.");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("naturalapi.admin")) {
                sender.sendMessage("§cYou don't have permission to use this command.");
                return true;
            }
            
            sender.sendMessage("§eReloading NaturalAPI...");
            plugin.onDisable();
            plugin.onEnable();
            sender.sendMessage("§aNaturalAPI reloaded successfully.");
            return true;
        }

        sender.sendMessage("§cUnknown subcommand. Type /napi help");
        return true;
    }
}
