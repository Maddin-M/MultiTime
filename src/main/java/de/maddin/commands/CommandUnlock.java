package de.maddin.commands;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static de.maddin.Utils.getAllWorlds;
import static de.maddin.Utils.getWorldOfSender;

public class CommandUnlock {

    public static final String NAME = "unlock";

    private CommandUnlock() {}

    public static boolean run(@NotNull CommandSender sender, @NotNull String[] args) {

        List<World> worldsToUnlock;
        if (args.length == 1) {
            worldsToUnlock = Collections.singletonList(getWorldOfSender(sender));

        } else {
            String worldToChangeArg = args[1];
            if (worldToChangeArg.equals("all")) {
                worldsToUnlock = getAllWorlds(sender);

            } else {
                World world = sender.getServer().getWorld(worldToChangeArg);
                if (world != null) {
                    worldsToUnlock = Collections.singletonList(world);
                } else {
                    sender.sendMessage("Invalid world.");
                    return false;
                }
            }
        }

        for (World world : worldsToUnlock) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
            sender.sendMessage("Unlocked time in world " + world.getName() + ".");
        }
        return true;
    }
}
