package de.maddin.commands;

import de.maddin.TimePresets;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static de.maddin.Utils.getAllWorlds;
import static de.maddin.Utils.getWorldOfSender;
import static de.maddin.Utils.isValidTickAmount;

public class CommandSet {

    public static final String NAME = "set";

    private CommandSet() {}

    public static boolean run(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length == 1) {
            sender.sendMessage("Must enter time to set.");
            return false;
        }
        String newTimeArg = args[1].toLowerCase();

        int newTime;
        if (isValidTickAmount(newTimeArg)) {
            newTime = Integer.parseInt(newTimeArg) % 24000;

        } else if (TimePresets.getAllNames().contains(newTimeArg)) {
            newTime = TimePresets.valueOf(newTimeArg).getTicks();

        } else {
            sender.sendMessage("Invalid time.");
            return false;
        }

        List<World> worldsToSet;
        if (args.length == 2) {
            worldsToSet = Collections.singletonList(getWorldOfSender(sender));

        } else {
            String worldToChangeArg = args[2];
            if (worldToChangeArg.equals("all")) {
                worldsToSet = getAllWorlds(sender);

            } else {
                World world = sender.getServer().getWorld(worldToChangeArg);
                if (world != null) {
                    worldsToSet = Collections.singletonList(world);
                } else {
                    sender.sendMessage("Invalid world.");
                    return false;
                }
            }
        }

        for (World world : worldsToSet) {
            world.setTime(newTime);
            sender.sendMessage("Set time in " + world.getName() + " to " + newTime + " ticks.");
        }

        return true;
    }
}
