package de.maddin.multitime;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

import static de.maddin.multitime.Constants.COMMAND;
import static de.maddin.multitime.Utils.getVersion;
import static java.lang.String.format;

/**
 * This class sends the command with its args to the correct executor class.
 */
public class CommandManager implements CommandExecutor {

    private final MultiTime plugin;

    public CommandManager(MultiTime plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        PluginCommand pluginCommand = plugin.getCommand(COMMAND);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
        } else {
            Bukkit.getLogger().info("PluginCommand is null");
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase(COMMAND)) {

            if (args.length == 0) {
                sender.sendMessage(format("%s %s", plugin.getName(), getVersion(plugin)));
                return false;
            }

            return Commands.execute(sender, args);
        }
        return false;
    }
}
