package id.naturalsmp.naturalApi.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {

    public static Map<String, Object> success(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("timestamp", System.currentTimeMillis());
        response.put("data", data);
        return response;
    }

    public static Map<String, Object> error(String code, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        
        Map<String, Object> error = new HashMap<>();
        error.put("code", code);
        error.put("message", message);
        error.put("timestamp", System.currentTimeMillis());
        
        response.put("error", error);
        return response;
    }
}
