package id.naturalsmp.naturalApi.http.controller;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.util.ResponseBuilder;
import io.javalin.http.Context;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class IntegrationController {

    private final NaturalAPI plugin;

    public IntegrationController(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public void getVaultData(Context ctx) {
        Player player = plugin.getPlayerService().getPlayer(ctx.pathParam("identifier"));
        if (player == null) {
            ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found"));
            return;
        }

        if (plugin.getIntegrationManager().getVaultIntegration() == null || !plugin.getIntegrationManager().getVaultIntegration().isEnabled()) {
            ctx.status(400).json(ResponseBuilder.error("VAULT_DISABLED", "Vault integration is not enabled"));
            return;
        }

        ctx.json(ResponseBuilder.success(plugin.getIntegrationManager().getVaultIntegration().getVaultData(player)));
    }

    public void getLuckPermsData(Context ctx) {
        Player player = plugin.getPlayerService().getPlayer(ctx.pathParam("identifier"));
        if (player == null) {
            ctx.status(404).json(ResponseBuilder.error("PLAYER_NOT_FOUND", "Player not found"));
            return;
        }

        if (plugin.getIntegrationManager().getLuckPermsIntegration() == null || !plugin.getIntegrationManager().getLuckPermsIntegration().isEnabled()) {
            ctx.status(400).json(ResponseBuilder.error("LUCKPERMS_DISABLED", "LuckPerms integration is not enabled"));
            return;
        }

        ctx.json(ResponseBuilder.success(plugin.getIntegrationManager().getLuckPermsIntegration().getLuckPermsData(player)));
    }

    public void evaluatePapi(Context ctx) {
        if (plugin.getIntegrationManager().getPapiIntegration() == null || !plugin.getIntegrationManager().getPapiIntegration().isEnabled()) {
            ctx.status(400).json(ResponseBuilder.error("PAPI_DISABLED", "PlaceholderAPI integration is not enabled"));
            return;
        }

        try {
            PapiEvaluateRequest req = ctx.bodyAsClass(PapiEvaluateRequest.class);
            Player player = plugin.getPlayerService().getPlayer(req.player);
            // Even if player is null, some placeholders might still work without a player, but usually they need an OfflinePlayer
            OfflinePlayer target = player != null ? player : plugin.getServer().getOfflinePlayer(req.player);
            
            Map<String, String> results = plugin.getIntegrationManager().getPapiIntegration().evaluate(target, req.placeholders);
            ctx.json(ResponseBuilder.success(results));
        } catch (Exception e) {
            ctx.status(400).json(ResponseBuilder.error("INVALID_REQUEST", "Invalid body format"));
        }
    }

    public void getVaultGroups(Context ctx) {
        if (plugin.getIntegrationManager().getVaultIntegration() == null || !plugin.getIntegrationManager().getVaultIntegration().isEnabled()) {
            ctx.status(400).json(ResponseBuilder.error("VAULT_DISABLED", "Vault integration is not enabled"));
            return;
        }
        ctx.json(ResponseBuilder.success(plugin.getIntegrationManager().getVaultIntegration().getGroups()));
    }

    public void getVaultGroup(Context ctx) {
        if (plugin.getIntegrationManager().getVaultIntegration() == null || !plugin.getIntegrationManager().getVaultIntegration().isEnabled()) {
            ctx.status(400).json(ResponseBuilder.error("VAULT_DISABLED", "Vault integration is not enabled"));
            return;
        }
        String group = ctx.pathParam("group");
        ctx.json(ResponseBuilder.success(plugin.getIntegrationManager().getVaultIntegration().getGroupDetails(group)));
    }

    public void getVaultEconomyStatus(Context ctx) {
        if (plugin.getIntegrationManager().getVaultIntegration() == null || !plugin.getIntegrationManager().getVaultIntegration().isEnabled()) {
            ctx.status(400).json(ResponseBuilder.error("VAULT_DISABLED", "Vault integration is not enabled"));
            return;
        }
        ctx.json(ResponseBuilder.success(plugin.getIntegrationManager().getVaultIntegration().getEconomyStatus()));
    }

    public void getLpGroups(Context ctx) {
        if (plugin.getIntegrationManager().getLuckPermsIntegration() == null || !plugin.getIntegrationManager().getLuckPermsIntegration().isEnabled()) {
            ctx.status(400).json(ResponseBuilder.error("LUCKPERMS_DISABLED", "LuckPerms integration is not enabled"));
            return;
        }
        ctx.json(ResponseBuilder.success(plugin.getIntegrationManager().getLuckPermsIntegration().getGroups()));
    }

    public void getLpGroup(Context ctx) {
        if (plugin.getIntegrationManager().getLuckPermsIntegration() == null || !plugin.getIntegrationManager().getLuckPermsIntegration().isEnabled()) {
            ctx.status(400).json(ResponseBuilder.error("LUCKPERMS_DISABLED", "LuckPerms integration is not enabled"));
            return;
        }
        String group = ctx.pathParam("group");
        ctx.json(ResponseBuilder.success(plugin.getIntegrationManager().getLuckPermsIntegration().getGroupDetails(group)));
    }

    public void getLpGroupMembers(Context ctx) {
        if (plugin.getIntegrationManager().getLuckPermsIntegration() == null || !plugin.getIntegrationManager().getLuckPermsIntegration().isEnabled()) {
            ctx.status(400).json(ResponseBuilder.error("LUCKPERMS_DISABLED", "LuckPerms integration is not enabled"));
            return;
        }
        String group = ctx.pathParam("group");
        try {
            List<Map<String, Object>> members = plugin.getIntegrationManager().getLuckPermsIntegration().getGroupMembers(group).join();
            ctx.json(ResponseBuilder.success(members));
        } catch (Exception e) {
            ctx.status(500).json(ResponseBuilder.error("INTERNAL_ERROR", "Error fetching LuckPerms group members: " + e.getMessage()));
        }
    }

    public void getLpGroupPermissions(Context ctx) {
        if (plugin.getIntegrationManager().getLuckPermsIntegration() == null || !plugin.getIntegrationManager().getLuckPermsIntegration().isEnabled()) {
            ctx.status(400).json(ResponseBuilder.error("LUCKPERMS_DISABLED", "LuckPerms integration is not enabled"));
            return;
        }
        String group = ctx.pathParam("group");
        ctx.json(ResponseBuilder.success(plugin.getIntegrationManager().getLuckPermsIntegration().getGroupPermissions(group)));
    }

    public void getPapiPlugins(Context ctx) {
        if (plugin.getIntegrationManager().getPapiIntegration() == null || !plugin.getIntegrationManager().getPapiIntegration().isEnabled()) {
            ctx.status(400).json(ResponseBuilder.error("PAPI_DISABLED", "PlaceholderAPI integration is not enabled"));
            return;
        }
        ctx.json(ResponseBuilder.success(plugin.getIntegrationManager().getPapiIntegration().getRegisteredExpansions()));
    }

    public static class PapiEvaluateRequest {
        public String player;
        public List<String> placeholders;
    }
}
