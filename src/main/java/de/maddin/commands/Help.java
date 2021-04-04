package de.maddin.commands;

import de.maddin.Commands;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Help implements Command {

    private static final Help instance = new Help();

    public static Help getInstance() {
        return instance;
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {

        for (Commands cmd : Commands.values()) {
            String helpMessage = cmd.getHelp();
            if (helpMessage != null) {
                sender.sendMessage(helpMessage);
            }
        }
        return true;
    }
}
