package dev.veg.scanner;

import dev.veg.VanillaEnchantGuard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerScanner {

    private final VanillaEnchantGuard plugin;

    public PlayerScanner(VanillaEnchantGuard plugin) {
        this.plugin = plugin;
    }

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {

                    ScanManager.scanInventory(player, player.getInventory());
                    ScanManager.scanInventory(player, player.getEnderChest());

                }
            }
        }.runTaskTimer(plugin, 20L, plugin.getConfig().getInt("scanner.interval-seconds") * 20L);
    }
}