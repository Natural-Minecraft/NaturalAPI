package id.naturalsmp.naturalApi.http.controller;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.database.dao.ApiKeyDao;
import id.naturalsmp.naturalApi.util.ResponseBuilder;
import io.javalin.http.Context;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.nio.charset.StandardCharsets;

public class AdminController {

    private final NaturalAPI plugin;

    public AdminController(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public void getHealth(Context ctx) {
        ctx.json(ResponseBuilder.success(Map.of("status", "ok")));
    }

    public void getKeys(Context ctx) {
        List<Map<String, Object>> keys = plugin.getDatabaseManager().getJdbi().withHandle(h -> 
            h.createQuery("SELECT * FROM napi_api_keys").mapToMap().list()
        );
        
        // Remove sensitive hash and salt before returning
        List<Map<String, Object>> safeKeys = new java.util.ArrayList<>();
        for (Map<String, Object> key : keys) {
            Map<String, Object> safeKey = new java.util.HashMap<>(key);
            safeKey.remove("key_hash");
            safeKey.remove("key_salt");
            safeKeys.add(safeKey);
        }
        
        ctx.json(ResponseBuilder.success(safeKeys));
    }

    public void generateKey(Context ctx) {
        GenerateKeyRequest req = ctx.bodyAsClass(GenerateKeyRequest.class);
        if (req.name == null || req.name.isEmpty()) {
            ctx.status(400).json(ResponseBuilder.error("INVALID_REQUEST", "Name is required"));
            return;
        }
        
        String scopes = req.scopes != null ? req.scopes : "*";
        String[] generated = plugin.getAuthService().generateKey(req.name, scopes, req.expiresAt);
        
        ctx.status(201).json(ResponseBuilder.success(Map.of(
            "id", generated[0],
            "key", generated[1],
            "name", req.name,
            "scopes", scopes,
            "expiresAt", req.expiresAt
        )));
    }

    public void revokeKey(Context ctx) {
        String id = ctx.pathParam("keyId");
        ApiKeyDao dao = plugin.getDatabaseManager().getJdbi().onDemand(ApiKeyDao.class);
        dao.deleteKey(id);
        ctx.json(ResponseBuilder.success(Map.of("revoked", id)));
    }

    public void getConfig(Context ctx) {
        Map<String, Object> configValues = cleanConfigSection(plugin.getConfig());
        removeSensitiveKeys(configValues);
        ctx.json(ResponseBuilder.success(configValues));
    }

    private Map<String, Object> cleanConfigSection(org.bukkit.configuration.ConfigurationSection section) {
        Map<String, Object> cleaned = new HashMap<>();
        for (String key : section.getKeys(false)) {
            Object val = section.get(key);
            if (val instanceof org.bukkit.configuration.ConfigurationSection) {
                cleaned.put(key, cleanConfigSection((org.bukkit.configuration.ConfigurationSection) val));
            } else {
                cleaned.put(key, val);
            }
        }
        return cleaned;
    }

    @SuppressWarnings("unchecked")
    private void removeSensitiveKeys(Map<String, Object> map) {
        map.remove("password");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                removeSensitiveKeys((Map<String, Object>) entry.getValue());
            }
        }
    }

    public void reloadPlugin(Context ctx) {
        org.bukkit.Bukkit.getScheduler().runTask(plugin, () -> {
            plugin.onDisable();
            plugin.onEnable();
        });
        ctx.json(ResponseBuilder.success(Map.of("status", "reloading")));
    }

    public void getOpenApiYaml(Context ctx) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("openapi.yaml")) {
            if (is == null) {
                plugin.getLogger().warning("openapi.yaml not found in classpath!");
                ctx.status(404).json(ResponseBuilder.error("NOT_FOUND", "openapi.yaml not found"));
                return;
            }
            String yaml = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            String publicUrl = plugin.getConfig().getString("server.public-url", "http://localhost:" + plugin.getConfig().getInt("server.port", 7890));
            // Hapus trailing slash jika ada
            if (publicUrl.endsWith("/")) {
                publicUrl = publicUrl.substring(0, publicUrl.length() - 1);
            }
            
            yaml = yaml.replaceAll("(?s)servers:.*?(?=\\ntags:)", 
                "servers:\n  - url: " + publicUrl + "/api/v1\n    description: NaturalAPI Server");
                
            ctx.contentType("text/yaml").result(yaml);
        } catch (Exception e) {
            plugin.getLogger().severe("Error reading openapi.yaml: " + e.getMessage());
            ctx.status(500).json(ResponseBuilder.error("INTERNAL_ERROR", "Error reading openapi.yaml"));
        }
    }

    public void getOpenApiJson(Context ctx) {
        // Dummy conversion or just tell user to use yaml in this v1
        ctx.status(501).json(ResponseBuilder.error("NOT_IMPLEMENTED", "Please use /admin/openapi.yaml for the OpenAPI spec."));
    }

    public void getSnapshotHistory(Context ctx) {
        // Assuming we add a method to dao to get all recent snapshots
        ctx.status(501).json(ResponseBuilder.error("NOT_IMPLEMENTED", "Snapshot history not implemented yet."));
    }

    public void purgeSnapshots(Context ctx) {
        // Assume purge logic
        ctx.json(ResponseBuilder.success(Map.of("purged", true)));
    }

    public void getRateLimits(Context ctx) {
        ctx.status(501).json(ResponseBuilder.error("NOT_IMPLEMENTED", "Rate limit viewing not implemented yet."));
    }

    public void resetRateLimit(Context ctx) {
        ctx.json(ResponseBuilder.success(Map.of("reset", true)));
    }

    public static class GenerateKeyRequest {
        public String name;
        public String scopes;
        public Long expiresAt;
    }
}
