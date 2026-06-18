package id.naturalsmp.naturalApi.http;

import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.config.ConfigManager;
import id.naturalsmp.naturalApi.http.router.ApiRouter;
import id.naturalsmp.naturalApi.websocket.WsServer;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class HttpServer {

    private final NaturalAPI plugin;
    private final ConfigManager configManager;
    private Javalin app;
    private WsServer wsServer;

    public HttpServer(NaturalAPI plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    public void start() {
        int port = configManager.getHttpPort();
        String bindAddress = configManager.getHttpBindAddress();
        
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            boolean swaggerEnabled = plugin.getConfig().getBoolean("server.swagger-enabled", true);
            String swaggerPath = plugin.getConfig().getString("server.swagger-path", "/swagger");

            app = Javalin.create(config -> {
                config.showJavalinBanner = false;
                config.bundledPlugins.enableCors(cors -> {
                    cors.addRule(it -> it.anyHost());
                });
                
                if (swaggerEnabled) {
                    config.staticFiles.add(staticFiles -> {
                        staticFiles.hostedPath = swaggerPath;
                        staticFiles.directory = "/swagger-ui";
                        staticFiles.location = Location.CLASSPATH;
                    });
                }
                
                // Register HTTP + WebSocket routes
                config.router.apiBuilder(() -> {
                    ApiRouter.register(plugin);
                    wsServer = new WsServer(plugin);
                    wsServer.registerRoutes();
                });
            });

            app.exception(Exception.class, (e, ctx) -> {
                plugin.getLogger().log(java.util.logging.Level.SEVERE, "API Error: " + e.getMessage(), e);
                ctx.status(500).json(id.naturalsmp.naturalApi.util.ResponseBuilder.error("INTERNAL_ERROR", "An unexpected error occurred: " + e.getMessage()));
            });

            if (bindAddress != null && !bindAddress.isEmpty()) {
                app.start(bindAddress, port);
            } else {
                app.start(port);
            }

            plugin.getLogger().info("HTTP Server started on port " + port);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to start HTTP Server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
    }

    public void stop() {
        if (wsServer != null) {
            wsServer.shutdown();
        }
        if (app != null) {
            app.stop();
            plugin.getLogger().info("HTTP Server stopped.");
        }
    }

    public Javalin getApp() {
        return app;
    }

    public WsServer getWsServer() {
        return wsServer;
    }
}
