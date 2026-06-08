package id.naturalsmp.naturalApi.http.middleware;

import id.naturalsmp.naturalApi.NaturalAPI;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;

public class CorsMiddleware implements Handler {

    private final boolean enabled;
    private final List<String> allowedOrigins;
    private final String allowedMethods;
    private final String allowedHeaders;

    public CorsMiddleware(NaturalAPI plugin) {
        this.enabled = plugin.getConfig().getBoolean("security.cors.enabled", true);
        this.allowedOrigins = plugin.getConfig().getStringList("security.cors.allowed-origins");
        this.allowedMethods = String.join(", ", plugin.getConfig().getStringList("security.cors.allowed-methods"));
        this.allowedHeaders = String.join(", ", plugin.getConfig().getStringList("security.cors.allowed-headers"));
    }

    @Override
    public void handle(Context ctx) throws Exception {
        if (!enabled) return;

        String origin = ctx.header("Origin");
        if (origin != null && (allowedOrigins.contains("*") || allowedOrigins.contains(origin))) {
            ctx.header("Access-Control-Allow-Origin", origin);
            ctx.header("Access-Control-Allow-Methods", allowedMethods);
            ctx.header("Access-Control-Allow-Headers", allowedHeaders);
        } else if (allowedOrigins.contains("*")) {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Methods", allowedMethods);
            ctx.header("Access-Control-Allow-Headers", allowedHeaders);
        }

        if (ctx.method().toString().equals("OPTIONS")) {
            ctx.status(204);
            return;
        }
    }
}
