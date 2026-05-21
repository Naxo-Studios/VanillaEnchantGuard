package dev.veg.commands;

import dev.veg.VanillaEnchantGuard;
import dev.veg.gui.AdminGUI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VEGCommand implements CommandExecutor {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players.");
            return true;
        }

        if (args.length == 0) {

            String message = VanillaEnchantGuard.getInstance()
                    .getConfig()
                    .getString(
                            "messages.main-command",
                            "<gradient:#00ff99:#00ffff>VanillaEnchantGuard Premium</gradient>"
                    );

            player.sendMessage(
                    miniMessage.deserialize(message)
            );

            return true;
        }

        if (args[0].equalsIgnoreCase("admin")) {
            new AdminGUI().open(player);
        }

        return true;
    }
}