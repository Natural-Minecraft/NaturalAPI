package id.naturalsmp.naturalApi.service;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.database.dao.ApiKeyDao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AuthService {

    private final NaturalAPI plugin;
    private final SecureRandom secureRandom = new SecureRandom();

    public AuthService(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public String[] generateKey(String name, String scopes, Long expiresAt) {
        String id = UUID.randomUUID().toString();
        String rawKey = UUID.randomUUID().toString().replace("-", ""); // The secret part
        
        byte[] saltBytes = new byte[16];
        secureRandom.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);
        
        String hash = hashKey(rawKey, salt);
        
        ApiKeyDao dao = plugin.getDatabaseManager().getJdbi().onDemand(ApiKeyDao.class);
        dao.insertKey(id, name, hash, salt, scopes, System.currentTimeMillis(), expiresAt, true);
        
        // Return full key in format: id.rawKey
        return new String[]{id, id + "." + rawKey};
    }

    public boolean verifyKey(String fullKey, String requiredScope) {
        if (fullKey == null || !fullKey.contains(".")) return false;
        
        String[] parts = fullKey.split("\\.", 2);
        String id = parts[0];
        String rawKey = parts[1];
        
        ApiKeyDao dao = plugin.getDatabaseManager().getJdbi().onDemand(ApiKeyDao.class);
        Optional<Map<String, Object>> keyOpt = plugin.getDatabaseManager().getJdbi().withHandle(h -> 
            h.createQuery("SELECT * FROM napi_api_keys WHERE id = :id")
             .bind("id", id)
             .mapToMap()
             .findOne()
        );
        
        if (keyOpt.isEmpty()) return false;
        
        Map<String, Object> keyData = keyOpt.get();
        
        boolean enabled = false;
        if (keyData.get("enabled") instanceof Boolean) {
             enabled = (Boolean) keyData.get("enabled");
        } else if (keyData.get("enabled") instanceof Integer) {
             enabled = ((Integer) keyData.get("enabled")) == 1;
        }

        if (!enabled) return false;
        
        Object expiresAtObj = keyData.get("expires_at");
        if (expiresAtObj != null) {
            long expiresAt = ((Number) expiresAtObj).longValue();
            if (expiresAt > 0 && System.currentTimeMillis() > expiresAt) {
                return false;
            }
        }
        
        String savedHash = (String) keyData.get("key_hash");
        String salt = (String) keyData.get("key_salt");
        
        if (!MessageDigest.isEqual(hashKey(rawKey, salt).getBytes(StandardCharsets.UTF_8), savedHash.getBytes(StandardCharsets.UTF_8))) {
            return false;
        }
        
        String scopes = (String) keyData.get("scopes");
        if (!scopes.contains("*") && !scopes.contains(requiredScope)) {
            return false;
        }
        
        // Update last used asynchronously
        org.bukkit.Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            dao.updateLastUsed(id, System.currentTimeMillis());
        });
        
        return true;
    }

    private String hashKey(String rawKey, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hashedBytes = md.digest(rawKey.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported", e);
        }
    }
}
