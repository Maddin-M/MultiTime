package de.maddin;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Utils {

    private Utils() {}

    public static World getWorldOfSender(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            return player.getWorld();
        }
        return null;
    }

    public static List<World> getAllWorlds(CommandSender sender) {
        return sender.getServer().getWorlds();
    }

    public static boolean isValidTickAmount(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }

    public static String getTimeLockedMessage(World world) {
        if (Boolean.FALSE.equals(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE))) {
            return "Time is locked in this world.";
        }
        return "";
    }
}
