package id.naturalsmp.naturalApi.http.middleware;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.util.ResponseBuilder;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AuthMiddleware implements Handler {

    private final NaturalAPI plugin;
    private final String requiredScope;

    public AuthMiddleware(NaturalAPI plugin, String requiredScope) {
        this.plugin = plugin;
        this.requiredScope = requiredScope;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        String path = ctx.path();
        if (path.endsWith("/health") || path.endsWith("/openapi.yaml") || path.endsWith("/openapi.json")) {
            return; // Skip authentication for public endpoints
        }

        String authHeader = ctx.header("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ctx.status(401).json(ResponseBuilder.error("INVALID_AUTH", "Missing or malformed Authorization header"));
            ctx.skipRemainingHandlers();
            return;
        }

        String token = authHeader.substring(7);
        boolean isValid = plugin.getAuthService().verifyKey(token, requiredScope);

        if (!isValid) {
            ctx.status(403).json(ResponseBuilder.error("INSUFFICIENT_SCOPE", "Invalid token or insufficient scope"));
            ctx.skipRemainingHandlers();
            return;
        }
    }
}
