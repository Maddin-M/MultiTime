package de.maddin.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface Command {

    boolean run(@NotNull CommandSender sender, @NotNull String[] args);

    default String getHelp() {
        return null;
    }
}
