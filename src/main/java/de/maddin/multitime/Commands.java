package de.maddin.multitime;

import de.maddin.multitime.commands.Command;
import de.maddin.multitime.commands.CommandAdd;
import de.maddin.multitime.commands.CommandGet;
import de.maddin.multitime.commands.CommandHelp;
import de.maddin.multitime.commands.CommandLock;
import de.maddin.multitime.commands.CommandSet;
import de.maddin.multitime.commands.CommandUnlock;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static de.maddin.multitime.utils.StringUtils.getMessage;

/**
 * Enum containing all command executors.
 */
public enum Commands {
    GET(new CommandGet()),
    SET(new CommandSet()),
    LOCK(new CommandLock()),
    UNLOCK(new CommandUnlock()),
    HELP(new CommandHelp()),
    ADD(new CommandAdd());

    private final Command executor;

    Commands(Command executor) {
        this.executor = executor;
    }

    public static boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        for (Commands cmd : Commands.values()) {
            if (cmd.getCommand().equals(args[0].toLowerCase())) {
                return cmd.run(sender, args);
            }
        }
        sender.sendMessage(getMessage("error_invalid_command", args[0]));
        return false;
    }

    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
        return executor.run(sender, args);
    }

    public String getHelp() {
        return executor.getHelp();
    }

    public String getCommand() {
        return name().toLowerCase();
    }
}
