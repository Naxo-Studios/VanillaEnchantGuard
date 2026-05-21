package dev.veg.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EnchantUtil {

    public static boolean isIllegal(ItemStack item) {

        if (item == null) return false;

        for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {

            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();

            if (level > enchantment.getMaxLevel()) {
                return true;
            }
        }

        return false;
    }

    public static void sanitize(ItemStack item) {

        if (item == null) return;

        Map<Enchantment, Integer> enchants = new HashMap<>(item.getEnchantments());

        for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {

            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();

            int maxLevel = enchantment.getMaxLevel();

            if (level > maxLevel) {

                item.removeEnchantment(enchantment);

                item.addUnsafeEnchantment(enchantment, maxLevel);
            }
        }
    }
}