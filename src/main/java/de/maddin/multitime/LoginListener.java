package de.maddin.multitime;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static de.maddin.multitime.Utils.colorString;

/**
 * Listens to users logging in, if update is available. Notifies OPs in chat to update the plugin.
 */
public class LoginListener implements Listener {

    private final MultiTime plugin;

    public LoginListener(MultiTime plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.isUpdateAvailable() && player.isOp()) {
            player.sendMessage(colorString("&eA new version of MultiTime is available!"));
        }
    }
}
