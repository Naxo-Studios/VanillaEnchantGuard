package dev.veg.listeners;

import dev.veg.scanner.ScanManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        ScanManager.scanInventory(event.getPlayer(), event.getInventory());
    }
}