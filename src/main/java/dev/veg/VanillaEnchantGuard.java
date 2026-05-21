package dev.veg;

import dev.veg.commands.VEGCommand;
import dev.veg.listeners.InventoryListener;
import dev.veg.scanner.PlayerScanner;
import dev.veg.listeners.AdminGUIListener;
import dev.veg.manager.MenuManager;

import org.bukkit.plugin.java.JavaPlugin;

public class VanillaEnchantGuard extends JavaPlugin {

    private static VanillaEnchantGuard instance;
    private PlayerScanner scanner;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        MenuManager.load();

        getCommand("veg").setExecutor(new VEGCommand());

        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(
                new AdminGUIListener(),
                this
        );

        scanner = new PlayerScanner(this);
        scanner.start();

        getLogger().info("VanillaEnchantGuard Enabled");
    }

    public static VanillaEnchantGuard getInstance() {
        return instance;
    }

    public PlayerScanner getScanner() {
        return scanner;
    }
}