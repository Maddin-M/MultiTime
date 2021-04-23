package de.maddin.multitime;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

import static de.maddin.multitime.Constants.COMMAND;
import static de.maddin.multitime.utils.StringUtils.getAllCommandsAsStringsStartingWith;
import static de.maddin.multitime.utils.StringUtils.getMessage;
import static de.maddin.multitime.utils.TimeUtils.getAllTimePresetsAsStringsStartingWith;
import static de.maddin.multitime.utils.WorldUtils.getAllWorldsAsStringsStartingWith;

/**
 * This class handles showing available commands while writing them, making it easier to use the plugin.
 */
public class TabCompleteManager implements TabCompleter {

    private final MultiTime plugin;

    public TabCompleteManager(MultiTime plugin) {
        this.plugin = plugin;
        setup();
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                @NotNull String alias, @NotNull String[] args) {

        if (args.length == 1) {
            return getAllCommandsAsStringsStartingWith(args[0]);
        }

        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "set":
                    return getAllTimePresetsAsStringsStartingWith(args[1]);
                case "get":
                case "lock":
                case "unlock":
                    return getAllWorldsAsStringsStartingWith(sender, args[1]);
                default:
                    return Collections.emptyList();
            }
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            return getAllWorldsAsStringsStartingWith(sender, args[2]);
        }

        return Collections.emptyList();
    }

    private void setup() {
        var pluginCommand = plugin.getCommand(COMMAND);
        if (pluginCommand != null) {
            pluginCommand.setTabCompleter(this);
        } else {
            Bukkit.getLogger().warning(getMessage("console_command_null"));
        }
    }
}
