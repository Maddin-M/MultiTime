package de.maddin.multitime.commands;

import de.maddin.multitime.Constants;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static de.maddin.multitime.Constants.ALL_ARG;
import static de.maddin.multitime.Constants.COMMAND;
import static de.maddin.multitime.utils.StringUtils.getMessage;
import static de.maddin.multitime.utils.TimeUtils.convertToRealTicks;
import static de.maddin.multitime.utils.TimeUtils.isValidTickAmount;
import static de.maddin.multitime.utils.WorldUtils.getAllWorlds;
import static de.maddin.multitime.utils.WorldUtils.getWorldOfSender;

/**
 * Sets the time in a specific world or all worlds.
 */
public class CommandSet implements Command {

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length >= 4) {
            sender.sendMessage(getMessage("error_too_many_parameters"));
            return false;
        }
        if (args.length <= 1) {
            sender.sendMessage(getMessage("time_set_no_parameter"));
            return false;
        }

        String timeParameter = args[1];
        int newTime;
        if (isValidTickAmount(timeParameter)) {
            newTime = convertToRealTicks(Integer.parseInt(timeParameter));

        } else if (Constants.TimePresets.contains(timeParameter)) {
            newTime = Constants.TimePresets.valueOf(timeParameter.toUpperCase()).getTicks();

        } else {
            sender.sendMessage(getMessage("error_invalid_time", timeParameter));
            return false;
        }

        List<World> affectedWorlds;
        if (args.length == 2) {
            affectedWorlds = List.of(getWorldOfSender(sender));

        } else {
            String worldParameter = args[2];
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
            world.setTime(newTime);
            sender.sendMessage(getMessage("time_set_success", world.getName(), newTime));
        });
        return true;
    }

    @Override
    public String getHelp() {
        return getMessage("time_set_help", COMMAND);
    }
}
