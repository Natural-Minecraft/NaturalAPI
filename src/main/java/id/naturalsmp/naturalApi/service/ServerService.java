package id.naturalsmp.naturalApi.service;

import id.naturalsmp.naturalApi.NaturalAPI;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;

public class ServerService implements Runnable {

    private final NaturalAPI plugin;
    private final long startTime;
    
    // TPS tracker variables (rolling window of 100 ticks / 5 seconds)
    private static final int WINDOW_SIZE = 100;
    private final long[] tickTimes = new long[WINDOW_SIZE];
    private int tickCount = 0;

    public ServerService(NaturalAPI plugin) {
        this.plugin = plugin;
        this.startTime = System.currentTimeMillis();
        
        long now = System.nanoTime();
        for (int i = 0; i < WINDOW_SIZE; i++) {
            tickTimes[i] = now - (WINDOW_SIZE - i) * 50_000_000L;
        }
    }

    @Override
    public void run() {
        tickTimes[tickCount % WINDOW_SIZE] = System.nanoTime();
        tickCount++;
    }

    public double getRecentTps() {
        int head = (tickCount - 1) % WINDOW_SIZE;
        int tail = tickCount % WINDOW_SIZE;
        long elapsedNano = tickTimes[head] - tickTimes[tail];
        if (elapsedNano <= 0) return 20.0;
        double elapsedSeconds = elapsedNano / 1_000_000_000.0;
        double tps = (WINDOW_SIZE - 1) / elapsedSeconds;
        return Math.min(20.0, Math.max(0.0, tps));
    }

    public Map<String, Object> getServerStatus() {
        Server server = Bukkit.getServer();
        Map<String, Object> status = new HashMap<>();
        
        status.put("online", true);
        status.put("version", server.getMinecraftVersion());
        status.put("platform", server.getName());
        status.put("motd", server.getMotd());
        
        Map<String, Double> tps = new HashMap<>();
        double[] tpsVals = server.getTPS();
        tps.put("now", getRecentTps());
        tps.put("1m", tpsVals.length > 0 ? Math.min(20.0, tpsVals[0]) : 20.0);
        tps.put("5m", tpsVals.length > 1 ? Math.min(20.0, tpsVals[1]) : 20.0);
        tps.put("15m", tpsVals.length > 2 ? Math.min(20.0, tpsVals[2]) : 20.0);
        status.put("tps", tps);
        
        int totalOnline = server.getOnlinePlayers().size();
        int vanishedCount = 0;
        for (Player p : server.getOnlinePlayers()) {
            if (plugin.getPlayerService().isPlayerVanished(p)) {
                vanishedCount++;
            }
        }
        int visibleCount = totalOnline - vanishedCount;

        Map<String, Integer> players = new HashMap<>();
        players.put("online", totalOnline);
        players.put("visible", visibleCount);
        players.put("vanished", vanishedCount);
        players.put("max", server.getMaxPlayers());
        status.put("players", players);
        
        status.put("mspt", server.getAverageTickTime());
        status.put("uptime", getUptimeSeconds());

        // RAM details
        Runtime runtime = Runtime.getRuntime();
        Map<String, Long> ram = new HashMap<>();
        ram.put("usedMB", (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024);
        ram.put("freeMB", runtime.freeMemory() / 1024 / 1024);
        ram.put("maxMB", runtime.maxMemory() / 1024 / 1024);
        status.put("ram", ram);

        // System details
        status.put("javaVersion", System.getProperty("java.version"));
        status.put("osName", System.getProperty("os.name"));

        // System stats
        status.put("system", getSystemStats());
        
        // Worlds list
        java.util.List<String> worlds = new java.util.ArrayList<>();
        for (org.bukkit.World w : server.getWorlds()) {
            worlds.add(w.getName());
        }
        status.put("worlds", worlds);
        
        return status;
    }

    public Map<String, Object> getSystemStats() {
        Map<String, Object> system = new HashMap<>();
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        // CPU cores
        system.put("availableProcessors", Runtime.getRuntime().availableProcessors());

        // System load average (1min)
        double loadAvg = osBean.getSystemLoadAverage();
        system.put("systemLoadAverage", loadAvg >= 0 ? Math.round(loadAvg * 100.0) / 100.0 : -1);

        // Process CPU load (com.sun.management)
        if (osBean instanceof com.sun.management.OperatingSystemMXBean sunBean) {
            double cpuLoad = sunBean.getProcessCpuLoad();
            system.put("processCpuLoad", cpuLoad >= 0 ? Math.round(cpuLoad * 10000.0) / 100.0 : -1);
            double systemCpuLoad = sunBean.getCpuLoad();
            system.put("systemCpuLoad", systemCpuLoad >= 0 ? Math.round(systemCpuLoad * 10000.0) / 100.0 : -1);
        } else {
            system.put("processCpuLoad", -1);
            system.put("systemCpuLoad", -1);
        }

        // Storage (current working directory)
        try {
            File cwd = new File(".").getAbsoluteFile().getParentFile();
            Map<String, Object> storage = new HashMap<>();
            storage.put("totalBytes", cwd.getTotalSpace());
            storage.put("freeBytes", cwd.getFreeSpace());
            storage.put("usableBytes", cwd.getUsableSpace());
            system.put("storage", storage);
        } catch (Exception e) {
            Map<String, Object> storage = new HashMap<>();
            storage.put("error", "unavailable");
            system.put("storage", storage);
        }

        // Network (parse /proc/net/dev on Linux)
        Map<String, Object> network = getNetworkStats();
        if (network != null) {
            system.put("network", network);
        }

        return system;
    }

    // Track previous network counters for delta calculation
    private long prevRxBytes = -1;
    private long prevTxBytes = -1;
    private long prevNetPollMs = -1;

    public Map<String, Object> getNetworkStats() {
        if (!System.getProperty("os.name").toLowerCase().contains("linux")) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader("/proc/net/dev"))) {
            long totalRx = 0;
            long totalTx = 0;
            String line;
            // Skip first two header lines
            reader.readLine();
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length < 10) continue;
                // Skip loopback
                if (parts[0].startsWith("lo:")) continue;
                totalRx += Long.parseLong(parts[1]);
                totalTx += Long.parseLong(parts[9]);
            }

            Map<String, Object> network = new HashMap<>();
            network.put("rxBytesTotal", totalRx);
            network.put("txBytesTotal", totalTx);

            long now = System.currentTimeMillis();
            if (prevNetPollMs > 0 && now > prevNetPollMs) {
                double elapsedSec = (now - prevNetPollMs) / 1000.0;
                long rxDelta = totalRx - prevRxBytes;
                long txDelta = totalTx - prevTxBytes;
                if (rxDelta >= 0) {
                    network.put("rxBytesPerSec", Math.round(rxDelta / elapsedSec));
                }
                if (txDelta >= 0) {
                    network.put("txBytesPerSec", Math.round(txDelta / elapsedSec));
                }
            }

            prevRxBytes = totalRx;
            prevTxBytes = totalTx;
            prevNetPollMs = now;

            return network;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Double> getTps() {
        Server server = Bukkit.getServer();
        Map<String, Double> tps = new HashMap<>();
        double[] tpsVals = server.getTPS();
        tps.put("now", getRecentTps());
        tps.put("1m", tpsVals.length > 0 ? Math.min(20.0, tpsVals[0]) : 20.0);
        tps.put("5m", tpsVals.length > 1 ? Math.min(20.0, tpsVals[1]) : 20.0);
        tps.put("15m", tpsVals.length > 2 ? Math.min(20.0, tpsVals[2]) : 20.0);
        return tps;
    }

    public double getMspt() {
        return Bukkit.getServer().getAverageTickTime();
    }

    public Map<String, Long> getRam() {
        Runtime runtime = Runtime.getRuntime();
        Map<String, Long> ram = new HashMap<>();
        ram.put("used", runtime.totalMemory() - runtime.freeMemory());
        ram.put("free", runtime.freeMemory());
        ram.put("max", runtime.maxMemory());
        return ram;
    }

    public long getUptimeSeconds() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }
}
