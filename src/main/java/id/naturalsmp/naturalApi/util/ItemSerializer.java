package id.naturalsmp.naturalApi.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class ItemSerializer {

    public static Map<String, Object> serialize(ItemStack item, int slot) {
        if (item == null || item.getType().isAir()) {
            return null;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("slot", slot);
        data.put("material", item.getType().name());
        data.put("amount", item.getAmount());

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
            } else {
                data.put("damage", 0);
            }

            if (meta.hasEnchants()) {
                Map<String, Integer> enchants = new HashMap<>();
                for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                    enchants.put(entry.getKey().getKey().getKey().toUpperCase(), entry.getValue());
                }
                data.put("enchantments", enchants);
            }
        } else {
            data.put("damage", 0);
        }

        return data;
    }
}
