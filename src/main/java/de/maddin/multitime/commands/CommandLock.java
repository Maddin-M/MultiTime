package de.maddin.multitime.commands;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static de.maddin.multitime.Constants.ALL_ARG;
import static de.maddin.multitime.Constants.COMMAND;
import static de.maddin.multitime.utils.StringUtils.getMessage;
import static de.maddin.multitime.utils.TimeUtils.isTimeLockedInWorld;
import static de.maddin.multitime.utils.WorldUtils.getAllWorlds;
import static de.maddin.multitime.utils.WorldUtils.getWorldOfSender;

/**
 * Locks time in a specified world or all worlds.
 */
public class CommandLock implements Command {

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length >= 3) {
            sender.sendMessage(getMessage("error_too_many_parameters"));
            return false;
        }

        List<World> affectedWorlds;
        if (args.length == 1) {
            affectedWorlds = List.of(getWorldOfSender(sender));

        } else {
            String worldParameter = args[1];
            if (worldParameter.equals(ALL_ARG)) {
                affectedWorlds = getAllWorlds(sender);

            } else {
                var world = sender.getServer().getWorld(worldParameter);
                if (world == null) {
                    sender.sendMessage(getMessage("error_invalid_world", worldParameter));
                    return false;
                }
                affectedWorlds = List.of(world);
            }
        }

        affectedWorlds.forEach(world -> {
            if (isTimeLockedInWorld(world)) {
                sender.sendMessage(getMessage("time_lock_already_locked", world.getName()));
            } else {
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                sender.sendMessage(getMessage("time_lock_success", world.getName()));
            }
        });
        return true;
    }

    @Override
    public String getHelp() {
        return getMessage("time_lock_help", COMMAND);
    }
}
