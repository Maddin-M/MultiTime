package de.maddin.commands;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static de.maddin.Constants.COMMAND;
import static de.maddin.Utils.*;
import static java.lang.String.format;

public class Lock implements Command {

    private static final Lock instance = new Lock();

    public static Lock getInstance() {
        return instance;
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {

        List<World> worldsToLock;
        if (args.length == 1) {
            worldsToLock = Collections.singletonList(getWorldOfSender(sender));

        } else {
            String worldArg = args[1];
            if (worldArg.equals("all")) {
                worldsToLock = getAllWorlds(sender);

            } else {
                World world = sender.getServer().getWorld(worldArg);
                if (world != null) {
                    worldsToLock = Collections.singletonList(world);
                } else {
                    sender.sendMessage(colorString(format("&cWorld '%s' doesn't exist!", worldArg)));
                    return false;
                }
            }
        }

        for (World world : worldsToLock) {
            if (isTimeLockedInWorld(world)) {
                sender.sendMessage(colorString(format("Time in world &b%s&f is already locked.", world.getName())));
            } else {
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                sender.sendMessage(colorString(format("Locked time in world &b%s&f.", world.getName())));
            }
        }
        return true;
    }

    @Override
    public String getHelp() {
        return colorString(format("&b/%s lock &8[&eworld&8|&eall&8]", COMMAND));
    }
}
