package id.naturalsmp.naturalApi.integration;

import id.naturalsmp.naturalApi.NaturalAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import net.luckperms.api.node.matcher.NodeMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LuckPermsIntegration {

    private final NaturalAPI plugin;
    private LuckPerms luckPerms;
    private boolean enabled = false;

    public LuckPermsIntegration(NaturalAPI plugin) {
        this.plugin = plugin;
    }

    public boolean setup() {
        if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null) {
            this.luckPerms = LuckPermsProvider.get();
            this.enabled = true;
            return true;
        }
        return false;
    }

    public Map<String, Object> getLuckPermsData(Player player) {
        Map<String, Object> data = new HashMap<>();
        if (!enabled) return data;

        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null) return data;

        data.put("primaryGroup", user.getPrimaryGroup());
        
        List<String> inheritedGroups = user.getNodes().stream()
                .filter(net.luckperms.api.node.NodeType.INHERITANCE::matches)
                .map(net.luckperms.api.node.NodeType.INHERITANCE::cast)
                .map(net.luckperms.api.node.types.InheritanceNode::getGroupName)
                .collect(Collectors.toList());
        
        data.put("inheritedGroups", inheritedGroups);

        List<Map<String, Object>> nodes = new ArrayList<>();
        user.getNodes().forEach(node -> {
            Map<String, Object> n = new HashMap<>();
            n.put("node", node.getKey());
            n.put("value", node.getValue());
            nodes.add(n);
        });
        data.put("permissionNodes", nodes);

        return data;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<String> getGroups() {
        if (!enabled) return new ArrayList<>();
        return luckPerms.getGroupManager().getLoadedGroups().stream()
                .map(net.luckperms.api.model.group.Group::getName)
                .collect(Collectors.toList());
    }

    public Map<String, Object> getGroupDetails(String name) {
        Map<String, Object> details = new HashMap<>();
        if (!enabled) return details;
        net.luckperms.api.model.group.Group group = luckPerms.getGroupManager().getGroup(name);
        if (group != null) {
            details.put("name", group.getName());
            details.put("displayName", group.getDisplayName());
            details.put("weight", group.getWeight().orElse(0));
        }
        return details;
    }

    public java.util.concurrent.CompletableFuture<List<Map<String, Object>>> getGroupMembers(String groupName) {
        if (!enabled) return java.util.concurrent.CompletableFuture.completedFuture(new ArrayList<>());
        net.luckperms.api.node.types.InheritanceNode node = net.luckperms.api.node.types.InheritanceNode.builder(groupName).build();
        return luckPerms.getUserManager().searchAll(NodeMatcher.key(node)).thenApply(map -> {
            List<Map<String, Object>> members = new ArrayList<>();
            map.forEach((uuid, name) -> {
                Map<String, Object> member = new HashMap<>();
                member.put("uuid", uuid.toString());
                member.put("username", name);
                members.add(member);
            });
            return members;
        });
    }

    public List<Map<String, Object>> getGroupPermissions(String name) {
        List<Map<String, Object>> permissions = new ArrayList<>();
        if (!enabled) return permissions;
        net.luckperms.api.model.group.Group group = luckPerms.getGroupManager().getGroup(name);
        if (group != null) {
            group.getNodes().forEach(node -> {
                Map<String, Object> p = new HashMap<>();
                p.put("node", node.getKey());
                p.put("value", node.getValue());
                permissions.add(p);
            });
        }
        return permissions;
    }
}
