package me.thevipershow.bibleplugin.managers;

import java.util.HashSet;
import java.util.Optional;
import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.data.BibleFactory;
import me.thevipershow.bibleplugin.downloaders.BibleDownloader;
import me.thevipershow.bibleplugin.downloaders.BibleURL;
import me.thevipershow.bibleplugin.exceptions.BibleException;
import org.bukkit.plugin.java.JavaPlugin;

public final class BibleManager {
    private static BibleManager instance = null;
    private final HashSet<Bible> loadedBibles;
    private final JavaPlugin plugin;
    private final BibleDownloader bibleDownloader;
    private final BibleFactory bibleFactory;

    private BibleManager(JavaPlugin plugin) {
        this.loadedBibles = new HashSet<>();
        this.plugin = plugin;
        this.bibleDownloader = BibleDownloader.getInstance(plugin);
        this.bibleFactory = BibleFactory.getInstance();
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
            final Bible bible = bibleFactory.createBible(optional.get(), bibleURL.name());
            loadedBibles.add(bible);
        }
    }

    public BibleDownloader getBibleDownloader() {
        return bibleDownloader;
    }

    public HashSet<Bible> getLoadedBibles() {
        return loadedBibles;
    }
}
