package de.maddin.multitime;

import de.maddin.multitime.commands.Add;
import de.maddin.multitime.commands.Command;
import de.maddin.multitime.commands.Get;
import de.maddin.multitime.commands.Help;
import de.maddin.multitime.commands.Lock;
import de.maddin.multitime.commands.Set;
import de.maddin.multitime.commands.Unlock;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static de.maddin.multitime.Utils.colorString;

/**
 * Enum containing all command executors.
 */
public enum Commands {
    GET(new Get()),
    SET(new Set()),
    LOCK(new Lock()),
    UNLOCK(new Unlock()),
    HELP(new Help()),
    ADD(new Add());

    private final Command executor;

    Commands(Command executor) {
        this.executor = executor;
    }

    public static boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        for (Commands cmd : Commands.values()) {
            if (cmd.name().equalsIgnoreCase(args[0])) {
                return cmd.run(sender, args);
            }
        }
        sender.sendMessage(colorString("&cCommand '" + args[0] + "' doesn't exist."));
        return false;
    }

    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
        return executor.run(sender, args);
    }

    public String getHelp() {
        return executor.getHelp();
    }
}
