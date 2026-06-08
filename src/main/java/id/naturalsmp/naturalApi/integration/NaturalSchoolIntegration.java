package id.naturalsmp.naturalApi.integration;

import id.naturalsmp.naturalApi.NaturalAPI;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NaturalSchoolIntegration {

    private final NaturalAPI plugin;
    private boolean enabled = false;

    public NaturalSchoolIntegration(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public boolean setup() {
        if (Bukkit.getPluginManager().isPluginEnabled("NaturalSchool")) {
            try {
                // Verify class exists and provider is loadable
                Class.forName("id.naturalsmp.naturalSchool.api.NaturalSchoolProvider");
                enabled = true;
                return true;
            } catch (ClassNotFoundException e) {
                plugin.getLogger().warning("NaturalSchool plugin detected but API class not found!");
            }
        }
        enabled = false;
        return false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Map<String, Object> getSchoolData(UUID uuid) {
        if (!enabled || uuid == null) {
            return null;
        }
        try {
            var optProfile = id.naturalsmp.naturalSchool.api.NaturalSchoolProvider.get().getOnlineProfile(uuid);
            if (optProfile.isPresent()) {
                return serializeProfile(optProfile.get());
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Error fetching NaturalSchool profile for " + uuid + ": " + e.getMessage());
        }
        return null;
    }

    private Map<String, Object> serializeProfile(id.naturalsmp.naturalSchool.profile.StudentProfile profile) {
        Map<String, Object> map = new HashMap<>();
        map.put("nis", profile.getNis());
        map.put("academicStage", profile.getAcademicStage());
        map.put("academicClass", profile.getAcademicClass());
        map.put("currentSemester", profile.getCurrentSemester());
        
        if (profile.getRank() != null) {
            Map<String, Object> rankMap = new HashMap<>();
            rankMap.put("id", profile.getRank().getId());
            rankMap.put("displayName", profile.getRank().getDisplayName());
            rankMap.put("priority", profile.getRank().getPriority());
            rankMap.put("type", profile.getRank().getType().name());
            map.put("rank", rankMap);
        } else {
            map.put("rank", null);
        }
        
        map.put("isStaff", profile.isStaff());
        map.put("isManagement", profile.isManagement());
        return map;
    }
}
