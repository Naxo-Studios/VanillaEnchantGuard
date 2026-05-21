package dev.veg.gui;

import dev.veg.VanillaEnchantGuard;
import dev.veg.manager.MenuManager;
import dev.veg.manager.StatsManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AdminGUI {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public void open(Player player) {

        YamlConfiguration menu = MenuManager.getMenu();

        String title = menu.getString(
                "title",
                "<gradient:#00ff99:#00ffff>VanillaEnchantGuard</gradient>"
        );

        int size = menu.getInt("size", 54);

        Inventory inv = Bukkit.createInventory(
                null,
                size,
                miniMessage.deserialize(title)
        );

        //
        // FILLER
        //
        if (menu.getBoolean("filler.enabled")) {

            Material fillerMaterial = Material.valueOf(
                    menu.getString(
                            "filler.material",
                            "BLACK_STAINED_GLASS_PANE"
                    )
            );

            String fillerName = menu.getString(
                    "filler.name",
                    " "
            );

            ItemStack filler = new ItemStack(fillerMaterial);

            ItemMeta meta = filler.getItemMeta();

            meta.displayName(
                    miniMessage.deserialize(fillerName)
            );

            filler.setItemMeta(meta);

            for (int i = 0; i < size; i++) {
                inv.setItem(i, filler);
            }
        }

        //
        // ITEMS
        //
        ConfigurationSection itemsSection =
                menu.getConfigurationSection("items");

        if (itemsSection != null) {

            for (String key : itemsSection.getKeys(false)) {

                String path = "items." + key;

                Material material = Material.valueOf(
                        menu.getString(path + ".material")
                );

                int slot = menu.getInt(path + ".slot");

                String name = replacePlaceholders(
                        menu.getString(path + ".name")
                );

                List<String> loreLines =
                        menu.getStringList(path + ".lore");

                List<net.kyori.adventure.text.Component> lore =
                        new ArrayList<>();

                for (String line : loreLines) {

                    lore.add(
                            miniMessage.deserialize(
                                    replacePlaceholders(line)
                            )
                    );
                }

                ItemStack item = new ItemStack(material);

                ItemMeta meta = item.getItemMeta();

                meta.displayName(
                        miniMessage.deserialize(name)
                );

                meta.lore(lore);

                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                item.setItemMeta(meta);

                inv.setItem(slot, item);
            }
        }

        player.openInventory(inv);
    }

    private String replacePlaceholders(String text) {

        if (text == null) return "";

        return text

                .replace(
                        "%scanner_enabled%",
                        VanillaEnchantGuard.getInstance()
                                .getConfig()
                                .getBoolean("scanner.enabled")
                                ? "<green>TRUE"
                                : "<red>FALSE"
                )

                .replace(
                        "%scanner_interval%",
                        String.valueOf(
                                VanillaEnchantGuard.getInstance()
                                        .getConfig()
                                        .getInt("scanner.interval-seconds")
                        )
                )

                .replace(
                        "%strict_mode%",
                        VanillaEnchantGuard.getInstance()
                                .getConfig()
                                .getBoolean("strict-mode")
                                ? "<green>TRUE"
                                : "<red>FALSE"
                )

                .replace(
                        "%illegal_corrected%",
                        String.valueOf(
                                StatsManager.getIllegalCorrected()
                        )
                )

                .replace(
                        "%players_supervised%",
                        String.valueOf(
                                Bukkit.getOnlinePlayers().size()
                        )
                )

                .replace(
                        "%shulkers_scanned%",
                        String.valueOf(
                                StatsManager.getShulkersScanned()
                        )
                )

                .replace(
                        "%enderchests_scanned%",
                        String.valueOf(
                                StatsManager.getEnderChestsScanned()
                        )
                );
    }
}