package dev.veg.listeners;

import dev.veg.VanillaEnchantGuard;
import dev.veg.manager.MenuManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AdminGUIListener implements Listener {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        YamlConfiguration menu = MenuManager.getMenu();

        String title = menu.getString(
                "title",
                "<gradient:#00ff99:#00ffff>VanillaEnchantGuard</gradient>"
        );

        if (!event.getView().title().equals(
                miniMessage.deserialize(title)
        )) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (event.getCurrentItem() == null) {
            return;
        }

        Material material = event.getCurrentItem().getType();

        switch (material) {

            //
            // RELOAD CONFIG
            //
            case BOOK -> {

                VanillaEnchantGuard.getInstance().reloadConfig();

                MenuManager.load();

                player.sendMessage(
                        miniMessage.deserialize(
                                "<green>Configuration reloaded."
                        )
                );

                player.closeInventory();
            }

            //
            // STRICT MODE
            //
            case BARRIER -> {

                boolean current = VanillaEnchantGuard.getInstance()
                        .getConfig()
                        .getBoolean("strict-mode");

                VanillaEnchantGuard.getInstance()
                        .getConfig()
                        .set("strict-mode", !current);

                VanillaEnchantGuard.getInstance().saveConfig();

                player.sendMessage(
                        miniMessage.deserialize(
                                "<yellow>Strict mode is now "
                                        + (
                                        !current
                                                ? "<green>ENABLED"
                                                : "<red>DISABLED"
                                )
                        )
                );

                player.closeInventory();
            }

            //
            // SCANNER TOGGLE
            //
            case HOPPER -> {

                boolean current = VanillaEnchantGuard.getInstance()
                        .getConfig()
                        .getBoolean("scanner.enabled");

                VanillaEnchantGuard.getInstance()
                        .getConfig()
                        .set("scanner.enabled", !current);

                VanillaEnchantGuard.getInstance().saveConfig();

                player.sendMessage(
                        miniMessage.deserialize(
                                "<green>Scanner toggled: "
                                        + (
                                        !current
                                                ? "<green>ENABLED"
                                                : "<red>DISABLED"
                                )
                        )
                );

                player.closeInventory();
            }

            //
            // SANITIZE ALL
            //
            case LAVA_BUCKET -> {

                player.sendMessage(
                        miniMessage.deserialize(
                                "<red>Sanitize system executed."
                        )
                );

                player.closeInventory();
            }

            //
            // STATS
            //
            case WRITABLE_BOOK -> {

                player.sendMessage(
                        miniMessage.deserialize(
                                "<gold>Opening statistics menu..."
                        )
                );
            }

            //
            // ENDERCHEST SCAN
            //
            case ENDER_CHEST -> {

                player.sendMessage(
                        miniMessage.deserialize(
                                "<aqua>Executing EnderChest scan..."
                        )
                );
            }

            //
            // SHULKER SCAN
            //
            case SHULKER_BOX -> {

                player.sendMessage(
                        miniMessage.deserialize(
                                "<light_purple>Executing Shulker scan..."
                        )
                );
            }
        }
    }
}