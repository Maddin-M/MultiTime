package de.maddin.multitime.commands;

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
import static java.lang.Math.abs;
import static java.lang.String.format;

/**
 * This command adds (or removes) ticks from a given world or all worlds.
 */
public class Add implements Command {

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 1) {
            sender.sendMessage(colorString("&cMust enter time to add."));
            return false;
        }

        String timeToAddArg = args[1];
        int timeToAdd;
        if (isValidTickAmount(timeToAddArg)) {
            timeToAdd = Integer.parseInt(timeToAddArg);

        } else {
            sender.sendMessage(colorString(format("&c'%s' is not a valid time.", timeToAddArg)));
            return false;
        }

        List<World> worldsToAddTime;
        if (args.length == 2) {
            worldsToAddTime = Collections.singletonList(getWorldOfSender(sender));

        } else {
            String worldArg = args[2];
            if (worldArg.equals("all")) {
                worldsToAddTime = getAllWorlds(sender);

            } else {
                World world = sender.getServer().getWorld(worldArg);
                if (world != null) {
                    worldsToAddTime = Collections.singletonList(world);
                } else {
                    sender.sendMessage(colorString(format("&cWorld '%s' doesn't exist!", worldArg)));
                    return false;
                }
            }
        }

        for (World world : worldsToAddTime) {
            addTimeToWorld(world, sender, timeToAdd);
        }

        return true;
    }

    @Override
    public String getHelp() {
        return colorString(format("&b/%s add &8[&eticks&8] [&eworld&8|&eall&8]", COMMAND));
    }

    private void addTimeToWorld(World world, CommandSender sender, int timeToAdd) {

        world.setTime(convertToRealTicks((int) world.getTime() + timeToAdd));
        if (timeToAdd >= 0) {
            sender.sendMessage(colorString(format("Added &b%s&f ticks to &b%s&f.", timeToAdd, world.getName())));
        } else {
            sender.sendMessage(colorString(format("Subtracted &b%s&f ticks from &b%s&f.",
                    abs(timeToAdd), world.getName())));
        }
    }
}
