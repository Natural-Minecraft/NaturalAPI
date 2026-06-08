package id.naturalsmp.naturalApi.integration;

import id.naturalsmp.naturalApi.NaturalAPI;

public class IntegrationManager {

    private final NaturalAPI plugin;
    private VaultIntegration vaultIntegration;
    private LuckPermsIntegration luckPermsIntegration;
    private PapiIntegration papiIntegration;
    private NaturalSchoolIntegration naturalSchoolIntegration;
    private NaturalCoreIntegration naturalCoreIntegration;

    public IntegrationManager(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        plugin.getLogger().info("Initializing integrations...");
        
        vaultIntegration = new VaultIntegration(plugin);
        if (vaultIntegration.setup()) {
            plugin.getLogger().info("Vault detected. Integration enabled.");
        }
        
        luckPermsIntegration = new LuckPermsIntegration(plugin);
        if (luckPermsIntegration.setup()) {
            plugin.getLogger().info("LuckPerms detected. Integration enabled.");
        }
        
        papiIntegration = new PapiIntegration(plugin);
        if (papiIntegration.setup()) {
            plugin.getLogger().info("PlaceholderAPI detected. Integration enabled.");
        }

        naturalSchoolIntegration = new NaturalSchoolIntegration(plugin);
        if (naturalSchoolIntegration.setup()) {
            plugin.getLogger().info("NaturalSchool detected. Integration enabled.");
        }

        naturalCoreIntegration = new NaturalCoreIntegration(plugin);
        if (naturalCoreIntegration.setup()) {
            plugin.getLogger().info("NaturalCore detected. Integration enabled.");
        }
    }

    public VaultIntegration getVaultIntegration() {
        return vaultIntegration;
    }

    public LuckPermsIntegration getLuckPermsIntegration() {
        return luckPermsIntegration;
    }

    public PapiIntegration getPapiIntegration() {
        return papiIntegration;
    }

    public NaturalSchoolIntegration getNaturalSchoolIntegration() {
        return naturalSchoolIntegration;
    }

    public NaturalCoreIntegration getNaturalCoreIntegration() {
        return naturalCoreIntegration;
    }
}
