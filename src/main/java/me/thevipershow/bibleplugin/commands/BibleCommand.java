package me.thevipershow.bibleplugin.commands;

import java.io.File;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.data.Book;
import me.thevipershow.bibleplugin.data.Chapter;
import me.thevipershow.bibleplugin.data.Verse;
import me.thevipershow.bibleplugin.downloaders.BibleURL;
import me.thevipershow.bibleplugin.exceptions.BibleException;
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

    public static BibleCommand getInstance(JavaPlugin plugin) {
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
            sender.sendMessage(color("&8[&eBiblePlugin&8]&f: &7Finished downloading bible &f`&e" + bibleURL.name() + "&f`"));
        } catch (final IllegalArgumentException e) {
            sender.sendMessage(color("&8[&eBiblePlugin&8]&f: &7No bible with that identifier exist!"));
            sender.sendMessage(color("      &7Consider using &e/bible available &7to see which ones you can download!"));
        }
    }

    private void searchForVerse(CommandSender sender, String bibleName, String verseSearch) {
        final Optional<Bible> optionalBible = bibleManager.getBible(bibleName);
        optionalBible.ifPresent(bible -> {
            final String[] array = verseSearch.split(":+");
            if (array.length == 3) {
                final String bookName = array[0];
                try {
                    final int chapterNumber = Integer.parseInt(array[1]);
                    final int verseNumber = Integer.parseInt(array[2]);
                    final Optional<Verse> verseOptional = bible.getVerse(bookName, chapterNumber, verseNumber);
                    if (verseOptional.isPresent()) {
                        sender.sendMessage(color("&8[&eBiblePlugin&8]&f: &7Book &f`&e" + bookName + "&f`&7, Verse&f: &8[&e" + chapterNumber + "&f:&e" + verseNumber + "&8]"));
                        sender.sendMessage(color("&f- &7" + verseOptional.get().getVerse()));
                    } else {
                        sender.sendMessage(color("&8[&eBiblePlugin&8]&f: &7That verse could not be found into the given book."));
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(color("&8[&eBiblePlugin&8]&f: &7The chapter\\verse was not a number!"));
                }
            } else {
                sender.sendMessage(color("&8[&eBiblePlugin&8]&f: &7The search was invalid!"));
            }
        });
    }

    private void getVerses(CommandSender sender, String search) {
        try {
            String[] array = BibleGuard.validateVerseSearch(search);
            final String bibleName = array[0], bookName = array[1];
            final int chapterNumber = Integer.parseInt(array[2]);
            final Chapter chapter = BibleOptionals.optionalChapterSearch(
                    BibleOptionals.optionalBookSearch(
                            BibleOptionals.optionalBibleSearch(bibleManager, bibleName), bookName), chapterNumber);
            sender.sendMessage(color("&8[&eBiblePlugin&8]&f: &7Chapter &f`&e" + chapterNumber
                    + "&f`&7 from book &f`&e" + bookName
                    + "&f`&7 from Bible &f`&e" + bibleName + "&f`"));
            sender.sendMessage(color("&7  has &f`&e" + chapter.getVerses().size() + "&f`&7 verses."));
        } catch (BibleException bibleException) {
            sender.sendMessage(color(bibleException.getMessage()));
        }
    }

    private void getChapters(CommandSender sender, String search) {
        try {
            final String[] array = BibleGuard.validateChapterSearch(search);
            final String bibleName = array[0], bookName = array[1];
            final Book book = BibleOptionals.optionalBookSearch(BibleOptionals.optionalBibleSearch(bibleManager, bibleName), bookName);
            sender.sendMessage(color("&8[&eBiblePlugin&8]&f: Book &f`&e" + bookName + "&f`&7 from Bible &f`&e" + bibleName + "&f`"));
            sender.sendMessage(color("&7  has &f`&e" + book.getChapters().size() + "&f`&7 chapters."));
        } catch (BibleException bibleException) {
            sender.sendMessage(color(bibleException.getMessage()));
        }
    }

    private void printBooks(CommandSender sender, Bible bible) {
        final StringBuilder builder = new StringBuilder();
        for (final Book book : bible.getBooks())
            builder.append("&e").append(book.getName()).append("&7, ");
        builder.setLength(builder.length() - 2);
        sender.sendMessage(color("&8[&eList&8]&7: " + builder.toString()));
    }

    private void getBooks(CommandSender sender, String search) {
        try {
            final Bible bible = BibleOptionals.optionalBibleSearch(bibleManager, search);
            sender.sendMessage(color("&8[&eBiblePlugin&8]&f: Bible &f`&e" + search + "&f`"));
            sender.sendMessage(color("&7  has &f`&e" + bible.getBooks().size() + "&f`&7 books."));
            printBooks(sender, bible);
        } catch (BibleException bibleException) {
            sender.sendMessage(color(bibleException.getMessage()));
        }
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
                    switch (args[0].toLowerCase(Locale.ROOT)) {
                        case "download":
                            downloadBible(sender, args[1]);
                            break;
                        case "books":
                            getBooks(sender, args[1]);
                            break;
                        case "chapters":
                            getChapters(sender, args[1]);
                            break;
                        case "verses":
                            getVerses(sender, args[1]);
                            break;
                        default:
                            return false;
                    }
                }
                break;
                case 3: {
                    switch (args[0].toLowerCase(Locale.ROOT)) {
                        case "verse": {
                            searchForVerse(sender, args[1], args[2]);
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
