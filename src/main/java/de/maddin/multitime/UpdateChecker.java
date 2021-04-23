package de.maddin.multitime;

import org.bukkit.Bukkit;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static de.maddin.multitime.Constants.SPIGOT_API_LINK;
import static de.maddin.multitime.Constants.SPIGOT_PLUGIN_ID;
import static de.maddin.multitime.utils.StringUtils.getMessage;

/**
 * This class checks for updates once the plugin is started.
 */
public class UpdateChecker {

    private final MultiTime plugin;

    public UpdateChecker(MultiTime plugin) {
        this.plugin = plugin;
    }

    public void checkForUpdate() {
        getVersion(version -> {
            if (!plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                Bukkit.getLogger().warning(getMessage("console_update_available"));
                plugin.setUpdateAvailable(true);
            }
        });
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (InputStream inputStream = new URL(SPIGOT_API_LINK + SPIGOT_PLUGIN_ID).openStream();
                 Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                Bukkit.getLogger().warning(getMessage("console_update_fetch_error", exception.getMessage()));
            }
        });
    }
}
