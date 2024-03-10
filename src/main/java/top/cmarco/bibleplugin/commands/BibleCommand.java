package top.cmarco.bibleplugin.commands;

import java.util.HashSet;
import java.util.Locale;
import top.cmarco.bibleplugin.data.Bible;
import top.cmarco.bibleplugin.data.Book;
import top.cmarco.bibleplugin.data.Chapter;
import top.cmarco.bibleplugin.data.Verse;
import top.cmarco.bibleplugin.downloaders.BibleURL;
import top.cmarco.bibleplugin.exceptions.BibleException;
import top.cmarco.bibleplugin.managers.BibleManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class BibleCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final BibleManager bibleManager;
    private final PlayerBibleDataManager bibleDataManager;
    private static BibleCommand instance = null;

    private BibleCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.bibleManager = BibleManager.getInstance(plugin);
        this.bibleDataManager = PlayerBibleDataManager.getInstance(plugin);
    }

    public static BibleCommand getInstance(JavaPlugin plugin) {
        return instance != null ? instance : (instance = new BibleCommand(plugin));
    }

    private String colour(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private void permissionCheck(CommandSender sender, Permission permission, PermissionCheck check) {
        if (sender.hasPermission(permission.getPermission())) {
            check.runWithPermission(sender);
        } else {
            sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7You are missing permissions!"));
        }
    }

    private void sendAvailable(CommandSender sender) {
        final StringBuilder builder = new StringBuilder();
        for (final BibleURL value : BibleURL.values())
            builder.append("&a").append(value.name()).append("&7, ");

        builder.setLength(builder.length() - 2);
        sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7" + builder));
    }

    private void sendDownloaded(CommandSender sender) {
        HashSet<Bible> bibleSet = bibleManager.getLoadedBibles();
        if (bibleSet.isEmpty()) {
            sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7You don't have any bible downloaded."));
            return;
        }
        final StringBuilder builder = new StringBuilder();
        bibleSet.forEach(bible -> builder.append("&a").append(bible.getName()).append("&7, "));
        builder.setLength(builder.length() - 2);
        sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7" + builder.toString()));
    }

    public void downloadBible(CommandSender sender, String bibleName) {
        try {
            BibleURL bibleURL = BibleURL.valueOf(bibleName.toUpperCase(Locale.ROOT));
            final long operationStartTime = System.currentTimeMillis();
            sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7Starting downloading bible &f`&e" + bibleURL.name() + "&f`"));
            bibleManager.downloadBible(bibleURL);
            final long timeTaken = (System.currentTimeMillis() - operationStartTime) / 1000000;
            sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7Finished downloading bible &f`&e" + bibleURL.name() + "&f`&7 in &e" + timeTaken + " &7ms"));
        } catch (final IllegalArgumentException e) {
            sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7No bible with that identifier exist!"));
            sender.sendMessage(colour("      &7Consider using &e/bible available &7to see which ones you can download!"));
        }
    }

    public void printNavigator(Player player) {
        PlayerBibleData playerBibleData = bibleDataManager.getPlayerBibleDataMap().get(player.getUniqueId());
        Bible currentBible = playerBibleData.getCurrentBible();
        Book currentBook = playerBibleData.getCurrentBook();
        Chapter chapter = playerBibleData.getCurrentChapter();
        Verse currentVerse = playerBibleData.getCurrentVerse();
        final BaseComponent result = new TextComponent();

        if (currentVerse != null && currentVerse.number() - 1 >= 0) {
            BaseComponent component = new TextComponent(colour("&7[&aPrev. Verse&7]"));
            component.setClickEvent(new ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    String.format("/bible verse %s %s:%d:%d", currentBible.getName(), currentBook.name(), chapter.number(), currentVerse.number())));

            result.addExtra(component);
        }

        if (currentVerse != null && chapter.verses().size() >= currentVerse.number() + 1) {
            BaseComponent component = new TextComponent(colour("    &7[&aNext Verse&7]"));
            component.setClickEvent(new ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    String.format("/bible verse %s %s:%d:%d", currentBible.getName(), currentBook.name(), chapter.number(), currentVerse.number() + 2)));

            result.addExtra(component);
        }

        if (currentVerse != null && chapter.verses().size() == 1+currentVerse.number()) {
            BaseComponent component;
            if (currentBook.chapters().size() == chapter.number()) {
                component = new TextComponent(colour("    &7[&6Next Book&7]"));
                component.setClickEvent(new ClickEvent(
                        ClickEvent.Action.RUN_COMMAND,
                        String.format("/bible verse %s %s:%d:%d", currentBible.getName(), currentBible.getBooks().get(currentBook.number()+1).name(), 1, 1)));
            } else {

                component = new TextComponent(colour("    &7[&eNext Chapter&7]"));
                component.setClickEvent(new ClickEvent(
                        ClickEvent.Action.RUN_COMMAND,
                        String.format("/bible verse %s %s:%d:%d", currentBible.getName(), currentBook.name(), chapter.number() + 1, 1)));
            }

            result.addExtra(component);
        }

        player.spigot().sendMessage(result);
    }

    public void searchForVerse(CommandSender sender, String bibleName, String verseSearch) {
        try {
            String[] array = BibleGuard.validateGetVerse(verseSearch);
            String bookName = array[0];
            int chapterNumber = Integer.parseInt(array[1]), verseNumber = Integer.parseInt(array[2]);
            Bible bible = BibleOptionals.optionalBibleSearch(this.bibleManager, bibleName);
            Book book = BibleOptionals.optionalBookSearch(bible, bookName);
            Chapter chapter = BibleOptionals.optionalChapterSearch(book, chapterNumber);
            Verse verse = BibleOptionals.optionalVerseSearch(chapter, verseNumber);
            //sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7" + verse.getVerse()));
            sender.sendMessage(colour("&7  " + verse.verse()));
            if (sender instanceof Player player) { // Checking for Player identity
                bibleDataManager.update(player, BibleSection.BIBLE, bible);     // Updating the newly found values
                bibleDataManager.update(player, BibleSection.BOOK, book);       // into his data map.
                bibleDataManager.update(player, BibleSection.CHAPTER, chapter); //
                bibleDataManager.update(player, BibleSection.VERSE, verse);
                printNavigator(player);
            }
        } catch (BibleException e) {
            sender.sendMessage(colour(e.getMessage()));
        }
    }

    private void getVerses(CommandSender sender, String search) {
        try {
            String[] array = BibleGuard.validateVerseSearch(search);
            final String bibleName = array[0], bookName = array[1];
            final int chapterNumber = Integer.parseInt(array[2]);
            Chapter chapter = BibleOptionals.optionalChapterSearch(BibleOptionals.optionalBookSearch(BibleOptionals.optionalBibleSearch(bibleManager, bibleName), bookName), chapterNumber);
            sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7Chapter &f`&e" + chapterNumber
                    + "&f`&7 from book &f`&e" + bookName
                    + "&f`&7 from Bible &f`&e" + bibleName + "&f`" + "&7 has &f`&e" + chapter.verses().size() + "&f`&7 verses."));
        } catch (BibleException bibleException) {
            sender.sendMessage(colour(bibleException.getMessage()));
        }
    }

    private void getChapters(CommandSender sender, String search) {
        try {
            final String[] array = BibleGuard.validateChapterSearch(search);
            final String bibleName = array[0], bookName = array[1];
            Book book = BibleOptionals.optionalBookSearch(BibleOptionals.optionalBibleSearch(bibleManager, bibleName), bookName);
            sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7Book &f`&e" + bookName + "&f`&7 from Bible &f`&e" + bibleName + "&f`" + "&7 has &f`&e" + book.chapters().size() + "&f`&7 chapters."));
        } catch (BibleException bibleException) {
            sender.sendMessage(colour(bibleException.getMessage()));
        }
    }

    private void printBooks(CommandSender sender, Bible bible) {
        final StringBuilder builder = new StringBuilder();
        for (final Book book : bible.getBooks())
            builder.append("&e").append(book.name()).append("&7, ");
        builder.setLength(builder.length() - 2);
        sender.sendMessage(colour("&8[&eList&8]&7: " + builder.toString()));
    }

    private void getBooks(CommandSender sender, String bibleName) {
        try {
            Bible bible = BibleOptionals.optionalBibleSearch(bibleManager, bibleName);
            sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7Bible &f`&e" + bibleName + "&f`" + "&7 has &f`&e" + bible.getBooks().size() + "&f`&7 books."));
            printBooks(sender, bible);
        } catch (BibleException bibleException) {
            sender.sendMessage(colour(bibleException.getMessage()));
        }
    }

    private void sendOccurrences(long occurrences, CommandSender sender, String word) {
        if (occurrences == 0)
            sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7There are no occurrences for &f`&e" + word + "&f`"));
        else
            sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7There are occurrences &e" + occurrences + "&7 for &f`&e" + word + "&f`"));
    }

    private void wordOccurrencesBible(CommandSender sender, String bibleSearch, String word) {
        try {
            Bible bible = BibleOptionals.optionalBibleSearch(bibleManager, bibleSearch);
            long occurrences = bible.findWordOccurrences(word);
            sendOccurrences(occurrences, sender, word);
        } catch (BibleException bibleException) {
            sender.sendMessage(colour(bibleException.getMessage()));
        }
    }

    private void wordOccurrencesBook(CommandSender sender, String bookSearch, String word) {
        try {
            String[] array = BibleGuard.validateChapterSearch(bookSearch);
            String bibleName = array[0], bookName = array[1];
            Bible bible = BibleOptionals.optionalBibleSearch(bibleManager, bibleName);
            Book book = BibleOptionals.optionalBookSearch(bible, bookName);
            long occurrences = bible.findWordOccurrences(book, word);
            sendOccurrences(occurrences, sender, word);
        } catch (BibleException bibleException) {
            sender.sendMessage(colour(bibleException.getMessage()));
        }
    }

    private void wordOccurrencesChapter(CommandSender sender, String chapterSearch, String word) {
        try {
            String[] array = BibleGuard.validateVerseSearch(chapterSearch);
            String bibleName = array[0], bookName = array[1];
            int chapterNumber = Integer.parseInt(array[2]);
            Bible bible = BibleOptionals.optionalBibleSearch(bibleManager, bibleName);
            Chapter chapter = BibleOptionals.optionalChapterSearch(BibleOptionals.optionalBookSearch(bible, bookName), chapterNumber);
            long occurrences = bible.findWordOccurrences(chapter, word);
            sendOccurrences(occurrences, sender, word);
        } catch (BibleException bibleException) {
            sender.sendMessage(colour(bibleException.getMessage()));
        }
    }

    private void loadBible(String bibleName, CommandSender sender) {
        try {
            sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7Starting to load the bible."));
            bibleManager.loadBible(BibleURL.valueOf(bibleName.toUpperCase(Locale.ROOT)));
            sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7Successfully loaded the bible."));
        } catch (BibleException bibleException) {
            sender.sendMessage(colour("&8[&eBiblePlugin&8]&f: &7Could not load that Bible."));
        }
    }

    private void disableLoginMessage(CommandSender sender) {
        if (sender instanceof Player player) {
            boolean newvalue = bibleDataManager.updateLoginVerse(player);
            player.sendMessage(colour("&aYour messages have been successfully " + (newvalue ? "enabled" : "disabled")));
        } else {
            sender.sendMessage(colour("&cThis cannot be executed from a console!"));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final int length = args.length;
        if (length == 0) {
            return false;
        } else {
            switch (length) {
                case 1 -> {
                    switch (args[0].toLowerCase(Locale.ROOT)) {
                        case "available" -> permissionCheck(sender, Permission.AVAILABLE, this::sendAvailable);
                        case "downloaded" -> permissionCheck(sender, Permission.DOWNLOADED, this::sendDownloaded);
                        case "loginverse" -> permissionCheck(sender, Permission.LOGIN_VERSE, this::disableLoginMessage);
                        default -> {
                            return false;
                        }
                    }
                }
                case 2 -> {
                    switch (args[0].toLowerCase(Locale.ROOT)) {
                        case "download" -> permissionCheck(sender, Permission.DOWNLOAD, s -> downloadBible(s, args[1]));
                        case "books" -> permissionCheck(sender, Permission.BOOKS, s -> getBooks(sender, args[1]));
                        case "chapters" -> permissionCheck(sender, Permission.CHAPTERS, s -> getChapters(s, args[1]));
                        case "verses" -> permissionCheck(sender, Permission.VERSES, s -> getVerses(s, args[1]));
                        case "load" -> permissionCheck(sender, Permission.LOAD, s -> loadBible(args[1], s));
                        default -> {
                            return false;
                        }
                    }
                }
                case 3 -> {
                    switch (args[0].toLowerCase(Locale.ROOT)) {
                        case "verse": {
                            permissionCheck(sender, Permission.VERSE, s -> searchForVerse(s, args[1], args[2]));
                        }
                        break;
                        case "occurrences": {
                            int search = args[1].split(":+").length;
                            switch (search) {
                                case 1:
                                    permissionCheck(sender, Permission.OCCURRENCES, s -> wordOccurrencesBible(s, args[1], args[2]));
                                    break;
                                case 2:
                                    permissionCheck(sender, Permission.OCCURRENCES, s -> wordOccurrencesBook(s, args[1], args[2]));
                                    break;
                                case 3:
                                    permissionCheck(sender, Permission.OCCURRENCES, s -> wordOccurrencesChapter(s, args[1], args[2]));
                                    break;
                                default:
                                    return false;
                            }
                        }
                        break;
                        case "find": {
                            // TODO: 31/05/2020 implement!
                            sender.sendMessage(colour("&7command not available yet."));
                        }
                        break;
                    }
                }
                default -> {
                    return false;
                }
            }
        }
        return true;
    }
}
