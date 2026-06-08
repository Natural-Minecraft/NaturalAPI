package id.naturalsmp.naturalApi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.naturalsmp.naturalApi.NaturalAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class GeoIpService {

    private final NaturalAPI plugin;
    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private final Map<String, Map<String, Object>> cache = new ConcurrentHashMap<>();

    public GeoIpService(NaturalAPI plugin) {
        this.plugin = plugin;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        this.mapper = new ObjectMapper();
    }

    public Map<String, Object> getGeoIpInfo(String ipAddress) {
        if (ipAddress == null || ipAddress.equals("127.0.0.1") || ipAddress.startsWith("192.168.") || ipAddress.startsWith("10.") || ipAddress.startsWith("172.16.")) {
            Map<String, Object> local = new HashMap<>();
            local.put("country", "Localhost/Private");
            local.put("region", "N/A");
            local.put("city", "Localhost");
            local.put("isp", "Local Network");
            local.put("asn", "Local Network");
            return local;
        }

        if (cache.containsKey(ipAddress)) {
            return cache.get(ipAddress);
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://ip-api.com/json/" + ipAddress))
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                @SuppressWarnings("unchecked")
                Map<String, Object> body = mapper.readValue(response.body(), Map.class);
                if ("success".equals(body.get("status"))) {
                    Map<String, Object> info = new HashMap<>();
                    info.put("country", body.getOrDefault("country", "Unknown"));
                    info.put("region", body.getOrDefault("regionName", "Unknown"));
                    info.put("city", body.getOrDefault("city", "Unknown"));
                    info.put("isp", body.getOrDefault("isp", "Unknown"));
                    info.put("asn", body.getOrDefault("as", "Unknown"));
                    cache.put(ipAddress, info);
                    return info;
                }
            }
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Failed to fetch GeoIP for IP " + ipAddress, e);
        }

        Map<String, Object> fallback = new HashMap<>();
        fallback.put("country", "Unknown");
        fallback.put("region", "Unknown");
        fallback.put("city", "Unknown");
        fallback.put("isp", "Unknown");
        fallback.put("asn", "Unknown");
        return fallback;
    }
}
