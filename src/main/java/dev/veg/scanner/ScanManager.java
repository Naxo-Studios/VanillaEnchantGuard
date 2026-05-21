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

                    String title = VanillaEnchantGuard.getInstance()
                            .getConfig()
                            .getString(
                                    "discord.embeds.illegal-block.title"
                            );

                    String description = VanillaEnchantGuard.getInstance()
                            .getConfig()
                            .getString(
                                    "discord.embeds.illegal-block.description"
                            )

                            .replace("%player%", player.getName())
                            .replace("%block%", type.name());

                    int color = VanillaEnchantGuard.getInstance()
                            .getConfig()
                            .getInt(
                                    "discord.embeds.illegal-block.color"
                            );

                    DiscordWebhook.sendEmbed(
                            title,
                            description,
                            color
                    );

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

                String title = VanillaEnchantGuard.getInstance()
                        .getConfig()
                        .getString(
                                "discord.embeds.illegal-enchant.title"
                        );

                String description = VanillaEnchantGuard.getInstance()
                        .getConfig()
                        .getString(
                                "discord.embeds.illegal-enchant.description"
                        )

                        .replace("%player%", player.getName())
                        .replace("%item%", item.getType().name());

                int color = VanillaEnchantGuard.getInstance()
                        .getConfig()
                        .getInt(
                                "discord.embeds.illegal-enchant.color"
                        );

                DiscordWebhook.sendEmbed(
                        title,
                        description,
                        color
                );

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
}
