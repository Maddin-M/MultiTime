package de.maddin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

import static de.maddin.Constants.COMMAND;
import static de.maddin.Utils.getVersion;
import static java.lang.String.format;

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
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase(COMMAND)) {

            if (args.length == 0) {
                sender.sendMessage(format("%s %s", plugin.getName(), getVersion(plugin)));
                return true;
            }

            return Commands.execute(sender, args);
        }
        return false;
    }
}