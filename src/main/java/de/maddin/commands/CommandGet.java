package de.maddin.commands;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static de.maddin.Utils.*;

public class CommandGet {

    public static final String NAME = "get";

    private CommandGet() {}

    public static boolean run(@NotNull CommandSender sender, @NotNull String[] args) {

        List<World> worldsToGet;

        if (args.length == 1) {
            worldsToGet = Collections.singletonList(getWorldOfSender(sender));

        } else {
            String worldToGetArg = args[1];
            if (worldToGetArg.equals("all")) {
                worldsToGet = getAllWorlds(sender);

            } else {
                World world = sender.getServer().getWorld(worldToGetArg);
                if (world != null) {
                    worldsToGet = Collections.singletonList(world);
                } else {
                    sender.sendMessage("World " + worldToGetArg + " doesn't exist.");
                    return false;
                }
            }
        }

        for (World world : worldsToGet) {
            sender.sendMessage("The time in " + world.getName() + " is " +
                    world.getTime() + " ticks. " + getTimeLockedMessage(world));
        }

        return true;
    }
}
