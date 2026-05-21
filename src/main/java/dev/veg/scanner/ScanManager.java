package dev.veg.scanner;

import dev.veg.VanillaEnchantGuard;
import dev.veg.manager.StatsManager;
import dev.veg.utils.DiscordWebhook;
import dev.veg.utils.EnchantUtil;

import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.Material;

import java.util.List;

public class ScanManager {

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static void scanInventory(HumanEntity player, Inventory inventory) {

        for (ItemStack item : inventory.getContents()) {

            if (item == null) continue;

            if (VanillaEnchantGuard.getInstance()
                    .getConfig()
                    .getBoolean("illegal-blocks.enabled")) {

                List<String> illegalBlocks =
                        VanillaEnchantGuard.getInstance()
                                .getConfig()
                                .getStringList("illegal-blocks.blocks");

                Material type = item.getType();

                if (illegalBlocks.contains(type.name())) {

                    item.setAmount(0);

                    player.sendMessage(
                            miniMessage.deserialize(
                                    "<red>Illegal block removed: <white>" + type.name()
                            )
                    );
                }
            }

            if (EnchantUtil.isIllegal(item)) {

                EnchantUtil.sanitize(item);
                StatsManager.addIllegalCorrected();

                String message = VanillaEnchantGuard.getInstance()
                        .getConfig()
                        .getString(
                                "messages.illegal-enchant",
                                "<red>Illegal enchant detected."
                        );

                player.sendMessage(
                        miniMessage.deserialize(message)
                );

                DiscordWebhook.send(player.getName() + " has illegal enchants.");
            }

            if (item.hasItemMeta() && item.getItemMeta() instanceof BlockStateMeta meta) {

                if (meta.getBlockState() instanceof ShulkerBox shulker) {

                    for (ItemStack inside : shulker.getInventory().getContents()) {

                        if (inside == null) continue;

                        if (EnchantUtil.isIllegal(inside)) {

                            EnchantUtil.sanitize(inside);
                            StatsManager.addIllegalCorrected();

                            String shulkerMessage = VanillaEnchantGuard.getInstance()
                                    .getConfig()
                                    .getString(
                                            "messages.illegal-shulker-enchant",
                                            "<red>Illegal enchant inside shulker."
                                    );

                            player.sendMessage(
                                    miniMessage.deserialize(shulkerMessage)
                            );
                        }
                    }
                }
            }
        }
    }
}