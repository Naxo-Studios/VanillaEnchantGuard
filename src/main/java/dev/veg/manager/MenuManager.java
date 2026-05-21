package dev.veg.manager;

import dev.veg.VanillaEnchantGuard;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MenuManager {

    private static YamlConfiguration menu;

    public static void load() {

        File file = new File(
                VanillaEnchantGuard.getInstance()
                        .getDataFolder(),
                "menu.yml"
        );

        if (!file.exists()) {

            VanillaEnchantGuard.getInstance()
                    .saveResource("menu.yml", false);
        }

        menu = YamlConfiguration.loadConfiguration(file);
    }

    public static YamlConfiguration getMenu() {
        return menu;
    }
}