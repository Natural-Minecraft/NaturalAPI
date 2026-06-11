package id.naturalsmp.naturalApi.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class ItemSerializer {

    private static final com.fasterxml.jackson.databind.ObjectMapper MAPPER = new com.fasterxml.jackson.databind.ObjectMapper();

    public static Map<String, Object> serialize(ItemStack item, int slot) {
        if (item == null || item.getType().isAir()) {
            return null;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("slot", slot);
        data.put("material", item.getType().name());
        data.put("amount", item.getAmount());

        // Initialize defaults to match expected schema exactly
        data.put("displayName", null);
        data.put("lore", new java.util.ArrayList<String>());
        data.put("damage", 0);
        data.put("enchantments", new HashMap<String, Integer>());
        data.put("customModelData", 0);
        data.put("nbtJson", "{}");

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (meta.hasDisplayName()) {
                data.put("displayName", meta.getDisplayName());
            }
            if (meta.hasLore()) {
                data.put("lore", meta.getLore());
            }
            if (meta.hasCustomModelData()) {
                data.put("customModelData", meta.getCustomModelData());
            }
            if (meta instanceof Damageable) {
                data.put("damage", ((Damageable) meta).getDamage());
            }

            if (meta.hasEnchants()) {
                Map<String, Integer> enchants = new HashMap<>();
                for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                    enchants.put(entry.getKey().getKey().getKey().toUpperCase(), entry.getValue());
                }
                data.put("enchantments", enchants);
            }

            // Serialize NBT/meta using Bukkit serialization
            try {
                Map<String, Object> serialized = item.serialize();
                if (serialized.containsKey("meta")) {
                    data.put("nbtJson", MAPPER.writeValueAsString(serialized.get("meta")));
                }
            } catch (Exception e) {
                // Ignore
            }
        }

        return data;
    }
}
