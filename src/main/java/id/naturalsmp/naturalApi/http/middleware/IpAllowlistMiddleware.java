package id.naturalsmp.naturalApi.http.middleware;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.util.ResponseBuilder;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;

public class IpAllowlistMiddleware implements Handler {

    private final boolean enabled;
    private final List<String> allowedIps;

    public IpAllowlistMiddleware(NaturalAPI plugin) {
        this.enabled = plugin.getConfig().getBoolean("security.ip-allowlist.enabled", false);
        this.allowedIps = plugin.getConfig().getStringList("security.ip-allowlist.ips");
    }

    @Override
    public void handle(Context ctx) throws Exception {
        if (!enabled) return;

        String ip = ctx.ip();
        if (!allowedIps.contains(ip)) {
            // Note: Does not yet support CIDR properly without external library, simple exact match for now
            ctx.status(403).json(ResponseBuilder.error("IP_BLOCKED", "Your IP address is not allowed."));
            ctx.skipRemainingHandlers();
        }
    }
}
