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
        // Simple caching via DB could be implemented here
        // For brevity in this v1, we fetch direct if needed or return empty if rate limited
        Map<String, String> result = new HashMap<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.replace("-", "") + "?unsigned=false"))
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
                        
                        break;
                    }
                }
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to fetch skin for " + uuid + ": " + e.getMessage());
        }
        return result;
    }
}
