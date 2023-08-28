package me.thevipershow.bibleplugin.listeners;

import me.thevipershow.bibleplugin.commands.BibleCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Random;

public class BibleJoinMessage implements Listener {

    private final BibleCommand bibleCommand;

    public BibleJoinMessage(BibleCommand bibleCommand) {
        this.bibleCommand = bibleCommand;
    }

    public final static String[] godFavouriteVerses = {
            "John:3:16", "Jeremiah:29:11", "Psalm:23",
            "Romans:8:28", "Romans:12:2", "Philippians:4:6",
            "Philippians:4:13", "Isaiah:41:10", "Matthew:6:33",
            "John:14:6", "Ephesians:6:12", "Joshua:1:9",
            "John:16:33", "Isaiah:40:31", "2_Timothy:1:7",
            "2_Corinthians:5:17", "John:10:10", "Proverbs:3:5",
            "Galatians:5:22", "1_Peter:5:7", "2_Chronicles:7:14",
            "Psalm:91:11", "John:14:27", "Matthew:14:27",
            "Matthew:28:19", "1_Corinthians:10:13", "Psalm:91",
            "2_Timothy:3:16", "Ephesians:3:20", "Ephesians:2:8",
            "2_Corinthians:12:9", "1_Thessalonians:5:18", "1_John:1:9",
            "Isaiah:53:5", "Hebrews:11:1", "1_Peter:5:8", "Genesis:1:27",
            "Romans:12:1", "Isaiah:9:6", "2_Corinthians:10:5",
            "Psalm:46:10","Romans:3:23", "James:5:16"
    };

    public String getRandomGodVerse() {
        return godFavouriteVerses[new Random().nextInt(0, godFavouriteVerses.length-1)];
    }
    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        bibleCommand.searchForVerse(player, "ENGLISH", getRandomGodVerse());
    }
}
