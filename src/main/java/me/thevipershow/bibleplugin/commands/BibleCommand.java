package me.thevipershow.bibleplugin.commands;

import java.util.Locale;
import me.thevipershow.bibleplugin.downloaders.BibleURL;
import me.thevipershow.bibleplugin.managers.BibleManager;
import org.bukkit.ChatColor;
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

    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private void permissionCheck(CommandSender sender, Permission permission, PermissionCheck check) {
        if (sender.hasPermission(permission.getPermission())) {
            check.runWithPermission();
        } else {
            sender.sendMessage(color("&8[&eBiblePlugin&8]&f: &7You are missing permissions!"));
        }
    }

    private void sendAvailable(CommandSender sender) {
        final StringBuilder builder = new StringBuilder();
        for (final BibleURL value : BibleURL.values()) {
            builder.append(value).append(", ");
        }
        builder.setLength(builder.length() - 2);
        sender.sendMessage(color("&8[&eBiblePlugin&8]&f: &7"+builder.toString()));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final int length = args.length;
        if (length == 0) {
            return false;
        } else if (length <= 3) {
            switch (length) {
                case 1:
                    switch (args[0].toLowerCase(Locale.ROOT)) {
                        case "available":
                            sendAvailable(sender);
                            break;
                        case "downloaded":

                            break;
                    }
            }
        }


        return true;
    }
}
