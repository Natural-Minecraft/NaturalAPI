package id.naturalsmp.naturalApi.websocket;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.util.ResponseBuilder;
import io.javalin.websocket.WsConnectContext;

import java.util.Map;

public class WsAuthHandler {

    private final NaturalAPI plugin;

    public WsAuthHandler(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    /**
     * Authenticate WebSocket connection via "token" query parameter.
     * Format: ?token=keyId.rawSecret
     */
    public boolean authenticate(WsConnectContext ctx, String requiredScope) {
        String token = ctx.queryParam("token");

        if (token == null || token.isEmpty()) {
            ctx.session.close(4001, "Missing token parameter");
            return false;
        }

        boolean valid = plugin.getAuthService().verifyKey(token, requiredScope);
        if (!valid) {
            ctx.session.close(4003, "Invalid token or insufficient scope");
            return false;
        }

        return true;
    }
}
