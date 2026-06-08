package id.naturalsmp.naturalApi.http.controller;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.service.WorldService;
import id.naturalsmp.naturalApi.util.ResponseBuilder;
import io.javalin.http.Context;
import org.bukkit.World;

import java.util.stream.Collectors;

public class WorldController {

    private final NaturalAPI plugin;
    private final WorldService worldService;

    public WorldController(NaturalAPI plugin) {
        this.plugin = plugin;
        this.worldService = plugin.getWorldService();
    }

    public void getWorlds(Context ctx) {
        ctx.json(ResponseBuilder.success(worldService.getWorlds()));
    }

    private World resolveWorld(Context ctx) {
        String name = ctx.pathParam("name");
        World world = worldService.getWorld(name);
        if (world == null) {
            ctx.status(404).json(ResponseBuilder.error("WORLD_NOT_FOUND", "World not found: " + name));
        }
        return world;
    }

    public void getWorld(Context ctx) {
        World world = resolveWorld(ctx);
        if (world != null) {
            ctx.json(ResponseBuilder.success(worldService.getWorldOverview(world)));
        }
    }

    public void getTime(Context ctx) {
        World world = resolveWorld(ctx);
        if (world != null) {
            ctx.json(ResponseBuilder.success(worldService.getTime(world)));
        }
    }

    public void getWeather(Context ctx) {
        World world = resolveWorld(ctx);
        if (world != null) {
            ctx.json(ResponseBuilder.success(worldService.getWeather(world)));
        }
    }

    public void getPlayers(Context ctx) {
        World world = resolveWorld(ctx);
        if (world != null) {
            ctx.json(ResponseBuilder.success(world.getPlayers().stream().map(p -> plugin.getPlayerService().getPlayerSummary(p)).collect(Collectors.toList())));
        }
    }

    public void getEntities(Context ctx) {
        World world = resolveWorld(ctx);
        if (world != null) {
            ctx.json(ResponseBuilder.success(worldService.getEntities(world)));
        }
    }

    public void getChunks(Context ctx) {
        World world = resolveWorld(ctx);
        if (world != null) {
            ctx.json(ResponseBuilder.success(world.getLoadedChunks().length));
        }
    }

    public void getBorder(Context ctx) {
        World world = resolveWorld(ctx);
        if (world != null) {
            ctx.json(ResponseBuilder.success(worldService.getBorder(world)));
        }
    }

    public void getGamerules(Context ctx) {
        World world = resolveWorld(ctx);
        if (world != null) {
            ctx.json(ResponseBuilder.success(worldService.getGamerules(world)));
        }
    }
}
