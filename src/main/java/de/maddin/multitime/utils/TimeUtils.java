package de.maddin.multitime.utils;

import de.maddin.multitime.Constants;
import org.bukkit.GameRule;
import org.bukkit.World;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This utils class handles all time related tasks.
 */
public class TimeUtils {

    private TimeUtils() {
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
        return ticks < 0 ? ticks + 24000 : ticks;
    }

    public static boolean isTimeLockedInWorld(World world) {
        return Boolean.FALSE.equals(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE));
    }

    public static List<String> getAllTimePresetsAsStringsStartingWith(String arg) {
        return Arrays.stream(Constants.TimePresets.values())
                .map(Constants.TimePresets::getCommand)
                .filter(name -> name.startsWith(arg.toLowerCase()))
                .collect(Collectors.toList());
    }
}
