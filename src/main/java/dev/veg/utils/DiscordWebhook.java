package dev.veg.utils;

import dev.veg.VanillaEnchantGuard;
import org.bukkit.Bukkit;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiscordWebhook {

    public static void sendEmbed(
            String title,
            String description,
            int color
    ) {

        if (!VanillaEnchantGuard.getInstance()
                .getConfig()
                .getBoolean("discord.enabled")) {
            return;
        }

        String webhookUrl = VanillaEnchantGuard.getInstance()
                .getConfig()
                .getString("discord.webhook-url");

        if (webhookUrl == null || webhookUrl.isEmpty()) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(
                VanillaEnchantGuard.getInstance(),
                () -> {

                    try {

                        URL url = new URL(webhookUrl);

                        HttpURLConnection connection =
                                (HttpURLConnection)
                                        url.openConnection();

                        connection.setRequestMethod("POST");

                        connection.setRequestProperty(
                                "Content-Type",
                                "application/json"
                        );

                        connection.setDoOutput(true);

                        String json = """
                                {
                                  "embeds": [
                                    {
                                      "title": "%s",
                                      "description": "%s",
                                      "color": %d
                                    }
                                  ]
                                }
                                """.formatted(
                                escape(title),
                                escape(description),
                                color
                        );

                        try (OutputStream os =
                                     connection.getOutputStream()) {

                            byte[] input = json.getBytes();

                            os.write(input, 0, input.length);
                        }

                        connection.getInputStream().close();

                    } catch (Exception exception) {

                        Bukkit.getLogger().warning(
                                "[VEG] Failed to send Discord webhook."
                        );

                        exception.printStackTrace();
                    }
                }
        );
    }

    private static String escape(String text) {

        return text
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
    }
}
