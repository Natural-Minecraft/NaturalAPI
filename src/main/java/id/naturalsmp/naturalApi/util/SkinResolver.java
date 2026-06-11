package id.naturalsmp.naturalApi.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.naturalsmp.naturalApi.NaturalAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SkinResolver {

    private final NaturalAPI plugin;
    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    public SkinResolver(NaturalAPI plugin) {
        this.plugin = plugin;
        this.httpClient = HttpClient.newBuilder().build();
        this.mapper = new ObjectMapper();
    }

    public Map<String, String> getSkin(String uuid) {
        Map<String, String> result = new java.util.HashMap<>();
        String normalizedUuid = uuid.replace("-", "");
        
        // Ensure standard UUID format with dashes for database storage
        String formattedUuid = uuid;
        if (!uuid.contains("-") && uuid.length() == 32) {
            formattedUuid = uuid.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{12})", "$1-$2-$3-$4-$5");
        }
        final String dbUuid = formattedUuid;

        // 1. Check DB cache first
        try {
            java.util.Optional<Map<String, Object>> cached = plugin.getDatabaseManager().getJdbi().withHandle(h ->
                h.createQuery("SELECT * FROM napi_skin_cache WHERE player_uuid = :uuid")
                 .bind("uuid", dbUuid)
                 .mapToMap()
                 .findOne()
            );
            if (cached.isPresent()) {
                Map<String, Object> data = cached.get();
                Object expiresAtObj = data.get("expires_at");
                if (expiresAtObj == null) {
                    expiresAtObj = getCaseInsensitive(data, "expires_at");
                }
                long expiresAt = expiresAtObj instanceof Number ? ((Number) expiresAtObj).longValue() : 0L;
                if (System.currentTimeMillis() < expiresAt) {
                    Object texVal = data.get("texture_value");
                    if (texVal == null) texVal = getCaseInsensitive(data, "texture_value");
                    Object sigVal = data.get("signature");
                    if (sigVal == null) sigVal = getCaseInsensitive(data, "signature");
                    Object urlVal = data.get("texture_url");
                    if (urlVal == null) urlVal = getCaseInsensitive(data, "texture_url");

                    result.put("value", (String) texVal);
                    result.put("signature", (String) sigVal);
                    result.put("url", (String) urlVal);
                    return result;
                }
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Error reading skin cache from database: " + e.getMessage());
        }

        // 2. Fetch from Mojang if cache expired or missing
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://sessionserver.mojang.com/session/minecraft/profile/" + normalizedUuid + "?unsigned=false"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JsonNode root = mapper.readTree(response.body());
                JsonNode properties = root.path("properties");
                for (JsonNode prop : properties) {
                    if ("textures".equals(prop.path("name").asText())) {
                        String value = prop.path("value").asText();
                        String signature = prop.path("signature").asText();
                        
                        result.put("value", value);
                        result.put("signature", signature);
                        
                        // Decode value to get URL
                        String decoded = new String(Base64.getDecoder().decode(value));
                        JsonNode decodedNode = mapper.readTree(decoded);
                        String url = decodedNode.path("textures").path("SKIN").path("url").asText();
                        result.put("url", url);
                        
                        // Save/Update DB cache (cache duration: features.skin.cache-ttl-hours from config, default 6)
                        int ttlHours = plugin.getConfig().getInt("features.skin.cache-ttl-hours", 6);
                        long cachedAt = System.currentTimeMillis();
                        long expiresAt = cachedAt + (ttlHours * 3600 * 1000L);
                        
                        plugin.getDatabaseManager().getJdbi().useHandle(h -> {
                            h.createUpdate("DELETE FROM napi_skin_cache WHERE player_uuid = :uuid")
                             .bind("uuid", dbUuid)
                             .execute();
                            h.createUpdate("INSERT INTO napi_skin_cache (player_uuid, texture_url, texture_value, signature, cached_at, expires_at) " +
                                           "VALUES (:uuid, :url, :value, :sig, :cat, :eat)")
                             .bind("uuid", dbUuid)
                             .bind("url", url)
                             .bind("value", value)
                             .bind("sig", signature)
                             .bind("cat", cachedAt)
                             .bind("eat", expiresAt)
                             .execute();
                        });
                        break;
                    }
                }
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to fetch skin for " + uuid + ": " + e.getMessage());
        }
        return result;
    }

    private Object getCaseInsensitive(Map<String, Object> map, String key) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(key)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
