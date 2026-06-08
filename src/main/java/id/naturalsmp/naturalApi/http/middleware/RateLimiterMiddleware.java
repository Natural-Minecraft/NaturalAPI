package id.naturalsmp.naturalApi.http.middleware;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.util.ResponseBuilder;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiterMiddleware implements Handler {

    private final NaturalAPI plugin;
    private final boolean enabled;
    private final int requestsPerMinute;
    private final int burst;
    private final Map<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    public RateLimiterMiddleware(NaturalAPI plugin) {
        this.plugin = plugin;
        this.enabled = plugin.getConfig().getBoolean("security.rate-limit.enabled", true);
        this.requestsPerMinute = plugin.getConfig().getInt("security.rate-limit.requests-per-minute", 120);
        this.burst = plugin.getConfig().getInt("security.rate-limit.burst", 30);
    }

    @Override
    public void handle(Context ctx) throws Exception {
        if (!enabled) return;

        String ip = ctx.ip();
        TokenBucket bucket = buckets.computeIfAbsent(ip, k -> new TokenBucket(burst, requestsPerMinute));

        if (!bucket.tryConsume()) {
            ctx.header("X-RateLimit-Limit", String.valueOf(requestsPerMinute));
            ctx.header("X-RateLimit-Remaining", "0");
            ctx.status(429).json(ResponseBuilder.error("RATE_LIMITED", "Too many requests."));
            ctx.skipRemainingHandlers();
            return;
        }

        ctx.header("X-RateLimit-Limit", String.valueOf(requestsPerMinute));
        ctx.header("X-RateLimit-Remaining", String.valueOf(bucket.getTokens()));
    }

    private static class TokenBucket {
        private final int capacity;
        private final double tokensPerMs;
        private double tokens;
        private long lastRefill;

        public TokenBucket(int capacity, int requestsPerMinute) {
            this.capacity = capacity;
            this.tokensPerMs = requestsPerMinute / 60000.0;
            this.tokens = capacity;
            this.lastRefill = System.currentTimeMillis();
        }

        public synchronized boolean tryConsume() {
            refill();
            if (tokens >= 1) {
                tokens -= 1;
                return true;
            }
            return false;
        }

        private void refill() {
            long now = System.currentTimeMillis();
            long timePassed = now - lastRefill;
            if (timePassed > 0) {
                tokens = Math.min(capacity, tokens + timePassed * tokensPerMs);
                lastRefill = now;
            }
        }

        public synchronized int getTokens() {
            refill();
            return (int) tokens;
        }
    }
}
