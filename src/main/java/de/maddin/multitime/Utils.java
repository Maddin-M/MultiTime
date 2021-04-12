package de.maddin.multitime;

import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Utils class to hold all the ugly logic, i don't want to see in the command classes.
 */
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

    public static int convertToRealTicks(int ticks) {
        ticks %= 24000;
        if (ticks < 0) {
            ticks += 24000;
        }
        return ticks;
    }

    public static String getTimeLockedMessage(World world) {
        if (isTimeLockedInWorld(world)) {
            return " &eTime is locked in this world.";
        }
        return "";
    }

    public static boolean isTimeLockedInWorld(World world) {
        return Boolean.FALSE.equals(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE));
    }

    public static String colorString(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getVersion(MultiTime plugin) {
        return plugin.getDescription().getVersion();
    }
}
