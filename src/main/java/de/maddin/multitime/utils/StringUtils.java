package de.maddin.multitime.utils;

import de.maddin.multitime.Commands;
import org.bukkit.World;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static de.maddin.multitime.utils.TimeUtils.isTimeLockedInWorld;

/**
 * This utils class handles all String related tasks.
 */
public class StringUtils {

    private static final ResourceBundle resource = ResourceBundle.getBundle("messages");

    private StringUtils() {
    }

    public static String getTimeLockedMessage(World world) {
        if (isTimeLockedInWorld(world)) {
            return getMessage("time_get_locked");
        }
        return "";
    }

    public static List<String> getAllCommandsAsStringsStartingWith(String arg) {
        return Arrays.stream(Commands.values())
                .map(Commands::getCommand)
                .filter(cmd -> cmd.startsWith(arg.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static String getMessage(String s, Object... args) {
        var msg = resource.getString(s);
        return args == null ? msg : MessageFormat.format(msg, args);
    }
}
