package de.maddin;

import de.maddin.commands.*;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static de.maddin.Utils.colorString;

public enum Commands {
    GET(Get.getInstance()),
    SET(Set.getInstance()),
    LOCK(Lock.getInstance()),
    UNLOCK(Unlock.getInstance()),
    HELP(Help.getInstance());

    private final Command executor;

    Commands(Command executor) {
        this.executor = executor;
    }

    public static boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        for (Commands cmd : Commands.values()) {
            if (cmd.name().equalsIgnoreCase(args[0]))
                return cmd.run(sender, args);
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