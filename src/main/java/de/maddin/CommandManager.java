package de.maddin;

import de.maddin.commands.CommandGet;
import de.maddin.commands.CommandLock;
import de.maddin.commands.CommandSet;
import de.maddin.commands.CommandUnlock;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

import static de.maddin.Constants.COMMAND;
import static de.maddin.Constants.VERSION;

public class CommandManager implements CommandExecutor {

    private final MultiTime plugin;

    public CommandManager() {
        plugin = MultiTime.getInstance();
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
                sender.sendMessage(plugin.getName() + " " + VERSION);
                return true;

            } else if (args[0].equals(CommandSet.NAME)) {
                return CommandSet.run(sender, args);

            } else if (args[0].equals(CommandGet.NAME)) {
                return CommandGet.run(sender, args);

            } else if (args[0].equals(CommandLock.NAME)) {
                return CommandLock.run(sender, args);

            } else if (args[0].equals(CommandUnlock.NAME)) {
                return CommandUnlock.run(sender, args);
            }
        }
        return false;
    }
}