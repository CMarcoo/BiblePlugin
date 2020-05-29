package me.thevipershow.bibleplugin.managers;

import java.util.HashSet;
import java.util.Optional;
import me.thevipershow.bibleplugin.bibles.AbstractBible;
import me.thevipershow.bibleplugin.bibles.FastBible;
import me.thevipershow.bibleplugin.bibles.serialization.JSONBibleImplementation;
import me.thevipershow.bibleplugin.downloaders.BibleDownloader;
import me.thevipershow.bibleplugin.downloaders.BibleURL;
import me.thevipershow.bibleplugin.exceptions.BibleException;
import org.bukkit.plugin.java.JavaPlugin;

public final class BibleManager {
    private static BibleManager instance = null;
    private final HashSet<AbstractBible> loadedBibles;
    private final JavaPlugin plugin;
    private final BibleDownloader bibleDownloader;

    private BibleManager(JavaPlugin plugin) {
        this.loadedBibles = new HashSet<>();
        this.plugin = plugin;
        this.bibleDownloader = BibleDownloader.getInstance(plugin);
    }

    public static BibleManager getInstance(JavaPlugin plugin) {
        return instance != null ? instance : (instance = new BibleManager(plugin));
    }

    public void downloadBible(BibleURL bibleURL) {
        bibleDownloader.storeBibleJSON(bibleURL,
                e -> plugin.getLogger().warning("Something went wrong when downloading Bible \"" + bibleURL.name() + "\""));
    }

    public void loadBible(BibleURL bibleURL) throws BibleException {
        final Optional<String> optional = bibleDownloader.getBibleRawContent(bibleURL,
                e -> plugin.getLogger().warning("Something went wrong when loading Bible \"" + bibleURL.name() + "\""));
        if (optional.isPresent()) {
            JSONBibleImplementation bibleImplementation = new JSONBibleImplementation(optional.get());
            AbstractBible bible = new FastBible(bibleImplementation);
            loadedBibles.add(bible);
        }
    }

    public BibleDownloader getBibleDownloader() {
        return bibleDownloader;
    }
}
