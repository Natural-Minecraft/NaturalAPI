package id.naturalsmp.naturalApi.util;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class LocationSerializer {

    public static Map<String, Object> serialize(Location loc) {
        if (loc == null) return null;
        
        Map<String, Object> data = new HashMap<>();
        data.put("world", loc.getWorld() != null ? loc.getWorld().getName() : "unknown");
        data.put("x", loc.getX());
        data.put("y", loc.getY());
        data.put("z", loc.getZ());
        data.put("yaw", loc.getYaw());
        data.put("pitch", loc.getPitch());
        return data;
    }
}
