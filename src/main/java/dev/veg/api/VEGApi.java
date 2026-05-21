package dev.veg.api;

import dev.veg.utils.EnchantUtil;
import org.bukkit.inventory.ItemStack;

public class VEGApi {

    public static boolean isIllegal(ItemStack item) {
        return EnchantUtil.isIllegal(item);
    }
}