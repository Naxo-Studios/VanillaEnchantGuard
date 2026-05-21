package dev.veg.utils;

import dev.veg.VanillaEnchantGuard;

public class DiscordWebhook {

    public static void send(String message) {

        if (!VanillaEnchantGuard.getInstance().getConfig().getBoolean("discord.enabled")) {
            return;
        }

        VanillaEnchantGuard.getInstance().getLogger().info("[DiscordWebhook] " + message);
    }
}