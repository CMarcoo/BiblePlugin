package me.thevipershow.bibleplugin.commands;

import java.io.File;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import me.thevipershow.bibleplugin.data.Bible;
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
        for (final BibleURL value : BibleURL.values())
            builder.append("&a").append(value.name()).append("&7, ");

        builder.setLength(builder.length() - 2);
        sender.sendMessage(color("&8[&eBiblePlugin&8]&f: &7" + builder.toString()));
    }

    private void sendDownloaded(CommandSender sender) {
        final StringBuilder builder = new StringBuilder();
        bibleManager.getLoadedBibles().forEach(bible -> {
            builder.append("&a").append(bible.getName()).append("&7, ");
        });

        builder.setLength(builder.length() - 2);
        sender.sendMessage(color("&8[&eBiblePlugin&8]&f: &7" + builder.toString()));
    }

    private void downloadBible(CommandSender sender, String bibleName) {
        try {
            BibleURL bibleURL = BibleURL.valueOf(bibleName.toUpperCase(Locale.ROOT));
            bibleManager.downloadBible(bibleURL);
            sender.sendMessage("&8[&eBiblePlugin&8]&f: &7Finished downloading bible " + bibleURL.name()+ "`");
        } catch (final IllegalArgumentException e) {
            sender.sendMessage("&8[&eBiblePlugin&8]&f: &7No bible with that identifier exist!");
            sender.sendMessage("      &7Consider using &e/bible available &7to see which ones you can download!");
        }
    }

    private void searchForWord(String bibleName, String word) {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final int length = args.length;
        if (length == 0) {
            return false;
        } else {
            switch (length) {
                case 1:
                    switch (args[0].toLowerCase(Locale.ROOT)) {
                        case "available":
                            sendAvailable(sender);
                            break;
                        case "downloaded":
                            sendDownloaded(sender);
                            break;
                        default:
                            return false;
                    }
                    break;
                case 2: {
                    if (args[0].equalsIgnoreCase("download")) {
                        downloadBible(sender, args[1]);
                    } else {
                        return false;
                    }
                }
                break;
                case 3: {
                    switch (args[0].toLowerCase(Locale.ROOT)) {
                        case "verse": {

                        }
                        break;
                        case "occurrences": {

                        }
                        break;
                        case "find": {

                        }
                        break;
                    }
                }
                break;
                default:
                    return false;
            }
        }


        return true;
    }
}
