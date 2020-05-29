package me.thevipershow.bibleplugin.commands;

import me.thevipershow.bibleplugin.managers.BibleManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class BibleCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final BibleManager bibleManager;
    private static BibleCommand instance = null;

    private BibleCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.bibleManager = BibleManager.getInstance(plugin);
    }

    private static BibleCommand getInstance(JavaPlugin plugin) {
        return instance != null ? instance : (instance = new BibleCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
}
