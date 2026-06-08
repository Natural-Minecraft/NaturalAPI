package id.naturalsmp.naturalApi.http.controller;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.service.ServerService;
import id.naturalsmp.naturalApi.util.ResponseBuilder;
import io.javalin.http.Context;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerController {

    private final NaturalAPI plugin;
    private final ServerService serverService;

    public ServerController(NaturalAPI plugin) {
        this.plugin = plugin;
        this.serverService = plugin.getServerService();
    }

    public void getServerStatus(Context ctx) {
        ctx.json(ResponseBuilder.success(serverService.getServerStatus()));
    }

    public void getTps(Context ctx) {
        ctx.json(ResponseBuilder.success(serverService.getTps()));
    }

    public void getMspt(Context ctx) {
        ctx.json(ResponseBuilder.success(serverService.getMspt()));
    }

    public void getRam(Context ctx) {
        ctx.json(ResponseBuilder.success(serverService.getRam()));
    }

    public void getUptime(Context ctx) {
        ctx.json(ResponseBuilder.success(serverService.getUptimeSeconds()));
    }
    
    public void getVersion(Context ctx) {
        Map<String, String> data = new HashMap<>();
        data.put("version", Bukkit.getMinecraftVersion());
        data.put("platform", Bukkit.getName());
        ctx.json(ResponseBuilder.success(data));
    }
    
    public void getPlayerCount(Context ctx) {
        Map<String, Integer> players = new HashMap<>();
        players.put("online", Bukkit.getOnlinePlayers().size());
        players.put("visible", Bukkit.getOnlinePlayers().size());
        players.put("vanished", 0);
        players.put("max", Bukkit.getMaxPlayers());
        ctx.json(ResponseBuilder.success(players));
    }
    
    public void getPlugins(Context ctx) {
        List<Map<String, String>> plugins = new ArrayList<>();
        for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
            Map<String, String> info = new HashMap<>();
            info.put("name", p.getName());
            info.put("version", p.getDescription().getVersion());
            plugins.add(info);
        }
        ctx.json(ResponseBuilder.success(plugins));
    }
    
    public void getWhitelist(Context ctx) {
        List<Map<String, String>> whitelist = Bukkit.getWhitelistedPlayers().stream().map(op -> {
            Map<String, String> data = new HashMap<>();
            data.put("uuid", op.getUniqueId().toString());
            data.put("name", op.getName());
            return data;
        }).collect(Collectors.toList());
        ctx.json(ResponseBuilder.success(whitelist));
    }
    
    public void getBanlist(Context ctx) {
        // Simple implementation for default banlist
        List<Map<String, Object>> banlist = Bukkit.getBannedPlayers().stream().map(op -> {
            Map<String, Object> data = new HashMap<>();
            data.put("uuid", op.getUniqueId().toString());
            data.put("name", op.getName());
            // More detailed ban info requires BanList API
            return data;
        }).collect(Collectors.toList());
        ctx.json(ResponseBuilder.success(banlist));
    }
}
