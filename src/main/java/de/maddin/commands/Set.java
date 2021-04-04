package de.maddin.commands;

import de.maddin.TimePresets;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static de.maddin.Constants.COMMAND;
import static de.maddin.Utils.*;
import static java.lang.String.format;

public class Set implements Command {

    private static final Set instance = new Set();

    public static Set getInstance() {
        return instance;
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length == 1) {
            sender.sendMessage(colorString("&cMust enter time to set."));
            return false;
        }
        String newTimeArg = args[1];

        int newTime;
        if (isValidTickAmount(newTimeArg)) {
            newTime = Integer.parseInt(newTimeArg) % 24000;

        } else if (TimePresets.contains(newTimeArg)) {
            newTime = TimePresets.valueOf(newTimeArg.toUpperCase()).getTicks();

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
                format("&b/%s set &8[&e0-24000&8|&eday&8|&enoon&8|&enight&8|&emidnight&8] [&eworld&8|&eall&8]",
                        COMMAND));
    }
}
