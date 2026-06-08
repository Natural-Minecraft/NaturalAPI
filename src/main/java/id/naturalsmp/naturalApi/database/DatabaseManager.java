package id.naturalsmp.naturalApi.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import id.naturalsmp.naturalApi.NaturalAPI;
import id.naturalsmp.naturalApi.config.ConfigManager;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.io.File;

public class DatabaseManager {

    private final NaturalAPI plugin;
    private final ConfigManager configManager;
    private HikariDataSource dataSource;
    private Jdbi jdbi;

    public DatabaseManager(NaturalAPI plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    public void connect() {
        HikariConfig hikariConfig = new HikariConfig();
        String provider = configManager.getDatabaseProvider().toLowerCase();

        switch (provider) {
            case "mysql":
            case "mariadb":
                String host = configManager.getConfig().getString("database." + provider + ".host", "localhost");
                int port = configManager.getConfig().getInt("database." + provider + ".port", 3306);
                String database = configManager.getConfig().getString("database." + provider + ".database", "naturalapi");
                String username = configManager.getConfig().getString("database." + provider + ".username", "root");
                String password = configManager.getConfig().getString("database." + provider + ".password", "change_me");
                
                String jdbcUrl = "jdbc:" + provider + "://" + host + ":" + port + "/" + database;
                hikariConfig.setJdbcUrl(jdbcUrl);
                if (provider.equals("mysql")) {
                    hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
                } else if (provider.equals("mariadb")) {
                    hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
                }
                hikariConfig.setUsername(username);
                hikariConfig.setPassword(password);
                hikariConfig.setMaximumPoolSize(configManager.getConfig().getInt("database." + provider + ".pool-size", 10));
                hikariConfig.setConnectionTimeout(configManager.getConfig().getInt("database." + provider + ".connection-timeout", 30000));
                break;

            case "sqlite":
            default:
                File dbFile = new File(plugin.getDataFolder(), configManager.getConfig().getString("database.sqlite.file", "data.db"));
                // Create parent directories if they don't exist
                if (!dbFile.getParentFile().exists()) {
                    dbFile.getParentFile().mkdirs();
                }
                hikariConfig.setJdbcUrl("jdbc:sqlite:" + dbFile.getAbsolutePath());
                hikariConfig.setDriverClassName("org.sqlite.JDBC");
                break;
        }

        dataSource = new HikariDataSource(hikariConfig);
        
        // Run Migrations
        runMigrations();

        // Setup JDBI
        jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
        
        plugin.getLogger().info("Connected to database using " + provider);
    }

    private void runMigrations() {
        plugin.getLogger().info("Running database migrations...");
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            Flyway flyway = Flyway.configure(getClass().getClassLoader())
                    .dataSource(dataSource)
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .load();
            flyway.migrate();
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
        plugin.getLogger().info("Database migrations completed.");
    }

    public void disconnect() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    public Jdbi getJdbi() {
        return jdbi;
    }
}
