package de.maddin;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import static de.maddin.Constants.PLUGIN_ID;

public class MultiTime extends JavaPlugin {

    private static MultiTime instance;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        instance = this;
        commandManager = new CommandManager();
        commandManager.setup();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        new Metrics(this, PLUGIN_ID);

        Bukkit.getLogger().info("SetWorldTime enabled");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("SetWorldTime disabled");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return commandManager.onCommand(sender, command, label, args);
    }

    public static MultiTime getInstance() {
        return MultiTime.instance;
    }
}
