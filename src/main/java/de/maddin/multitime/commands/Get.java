package de.maddin.multitime.commands;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static de.maddin.multitime.Constants.COMMAND;
import static de.maddin.multitime.Utils.colorString;
import static de.maddin.multitime.Utils.getAllWorlds;
import static de.maddin.multitime.Utils.getTimeLockedMessage;
import static de.maddin.multitime.Utils.getWorldOfSender;
import static java.lang.String.format;

/**
 * Command to get the time from a certain world or all worlds.
 */
public class Get implements Command {

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
        List<World> worldsToGet;

        if (args.length == 1) {
            worldsToGet = Collections.singletonList(getWorldOfSender(sender));

        } else {
            String worldArg = args[1];
            if (worldArg.equals("all")) {
                worldsToGet = getAllWorlds(sender);

            } else {
                World world = sender.getServer().getWorld(worldArg);
                if (world != null) {
                    worldsToGet = Collections.singletonList(world);
                } else {
                    sender.sendMessage(colorString(format("&cWorld '%s' doesn't exist!", worldArg)));
                    return false;
                }
            }
        }

        for (World world : worldsToGet) {
            sender.sendMessage(colorString(format("The time in &b%s&f is &b%d&f ticks.%s",
                    world.getName(), world.getTime(), getTimeLockedMessage(world))));
        }

        return true;
    }

    @Override
    public String getHelp() {
        return colorString(format("&b/%s get &8[&eworld&8|&eall&8]", COMMAND));
    }
}
