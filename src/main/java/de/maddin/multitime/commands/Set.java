package de.maddin.multitime.commands;

import de.maddin.multitime.Constants;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static de.maddin.multitime.Constants.COMMAND;
import static de.maddin.multitime.Utils.colorString;
import static de.maddin.multitime.Utils.convertToRealTicks;
import static de.maddin.multitime.Utils.getAllWorlds;
import static de.maddin.multitime.Utils.getWorldOfSender;
import static de.maddin.multitime.Utils.isValidTickAmount;
import static java.lang.String.format;

/**
 * Sets the time in a specific world or all worlds.
 */
public class Set implements Command {

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length == 1) {
            sender.sendMessage(colorString("&cMust enter time to set."));
            return false;
        }
        String newTimeArg = args[1];

        int newTime;
        if (isValidTickAmount(newTimeArg)) {
            newTime = convertToRealTicks(Integer.parseInt(newTimeArg));

        } else if (Constants.TimePresets.contains(newTimeArg)) {
            newTime = Constants.TimePresets.valueOf(newTimeArg.toUpperCase()).getTicks();

        } else {
            sender.sendMessage(colorString(format("&c'%s' is not a valid time.", newTimeArg)));
            return false;
        }

        List<World> worldsToSet;
        if (args.length == 2) {
            worldsToSet = Collections.singletonList(getWorldOfSender(sender));

        } else {
            String worldArg = args[2];
            if (worldArg.equals("all")) {
                worldsToSet = getAllWorlds(sender);

            } else {
                World world = sender.getServer().getWorld(worldArg);
                if (world != null) {
                    worldsToSet = Collections.singletonList(world);
                } else {
                    sender.sendMessage(colorString(format("&cWorld '%s' doesn't exist!", worldArg)));
                    return false;
                }
            }
        }

        for (World world : worldsToSet) {
            world.setTime(newTime);
            sender.sendMessage(colorString(format("Set time in &b%s&f to &b%d&f ticks.", world.getName(), newTime)));
        }

        return true;
    }

    @Override
    public String getHelp() {
        return colorString(
                format("&b/%s set &8[&eticks&8|&eday&8|&enoon&8|&enight&8|&emidnight&8] [&eworld&8|&eall&8]",
                        COMMAND));
    }
}
