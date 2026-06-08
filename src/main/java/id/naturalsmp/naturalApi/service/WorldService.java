package id.naturalsmp.naturalApi.service;

import id.naturalsmp.naturalApi.NaturalAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorldService {

    private final NaturalAPI plugin;

    public WorldService(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public List<Map<String, Object>> getWorlds() {
        return Bukkit.getWorlds().stream().map(this::getWorldOverview).collect(Collectors.toList());
    }

    public World getWorld(String name) {
        return Bukkit.getWorld(name);
    }

    public Map<String, Object> getWorldOverview(World world) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", world.getName());
        data.put("environment", world.getEnvironment().name());
        data.put("seed", world.getSeed());
        data.put("time", world.getTime());
        data.put("fullTime", world.getFullTime());
        
        String weather = "CLEAR";
        if (world.hasStorm()) weather = "RAIN";
        if (world.isThundering()) weather = "THUNDER";
        data.put("weather", weather);
        
        data.put("playerCount", world.getPlayers().size());
        data.put("entityCount", world.getEntityCount());
        data.put("loadedChunks", world.getLoadedChunks().length);
        data.put("pvp", world.getPVP());
        data.put("difficulty", world.getDifficulty().name());
        
        return data;
    }

    public Map<String, Object> getTime(World world) {
        Map<String, Object> data = new HashMap<>();
        data.put("time", world.getTime());
        data.put("fullTime", world.getFullTime());
        data.put("dayCount", world.getFullTime() / 24000);
        return data;
    }

    public String getWeather(World world) {
        if (world.isThundering()) return "THUNDER";
        if (world.hasStorm()) return "RAIN";
        return "CLEAR";
    }

    public Map<String, Integer> getEntities(World world) {
        Map<String, Integer> counts = new HashMap<>();
        for (Entity entity : world.getEntities()) {
            String type = entity.getType().name();
            counts.put(type, counts.getOrDefault(type, 0) + 1);
        }
        return counts;
    }

    public Map<String, Object> getBorder(World world) {
        Map<String, Object> border = new HashMap<>();
        border.put("size", world.getWorldBorder().getSize());
        border.put("center", Map.of("x", world.getWorldBorder().getCenter().getX(), "z", world.getWorldBorder().getCenter().getZ()));
        border.put("damageBuffer", world.getWorldBorder().getDamageBuffer());
        border.put("damageAmount", world.getWorldBorder().getDamageAmount());
        return border;
    }

    public Map<String, Object> getGamerules(World world) {
        Map<String, Object> rules = new HashMap<>();
        for (String rule : world.getGameRules()) {
            GameRule<?> gameRule = GameRule.getByName(rule);
            if (gameRule != null) {
                rules.put(rule, world.getGameRuleValue(gameRule));
            }
        }
        return rules;
    }
}
