package top.cmarco.bibleplugin.managers;

import java.util.HashSet;
import java.util.Optional;
import top.cmarco.bibleplugin.commands.BibleGuard;
import top.cmarco.bibleplugin.data.Bible;
import top.cmarco.bibleplugin.data.BibleFactory;
import top.cmarco.bibleplugin.downloaders.BibleDownloader;
import top.cmarco.bibleplugin.downloaders.BibleURL;
import top.cmarco.bibleplugin.exceptions.BibleException;
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

    /**
     * Return the instance of the BibleManager singleton
     *
     * @return A valid instance if the plugin has been correctly loaded and is present on the server,
     * otherwise returns a null instance.
     */
    public static BibleManager getInstance() {
        return instance;
    }

    public void downloadBible(BibleURL bibleURL) {
        bibleDownloader.storeBibleJSON(bibleURL,
                e -> plugin.getLogger().warning("Something went wrong when downloading Bible \"" + bibleURL.name() + "\""));
    }

    public void loadBible(BibleURL bibleURL) throws BibleException {
        if (loadedBibles.stream().noneMatch(bible -> bible.getName().equalsIgnoreCase(bibleURL.name()))) {
            final Optional<String> optional = bibleDownloader.getBibleRawContent(bibleURL,
                    e -> plugin.getLogger().warning("Something went wrong when loading Bible \"" + bibleURL.name() + "\""));
            if (optional.isPresent()) {
                final String fullText = optional.get();
                final Bible bible = bibleFactory.createBible(fullText, bibleURL.name());
                loadedBibles.add(BibleGuard.validateBible(bible));
            } else {
                plugin.getLogger().warning("Text from bible " + bibleURL.name() + " could not be read!");
            }
        } else {
            plugin.getLogger().info("That Bible was already loaded!");
        }
    }

    public BibleDownloader getBibleDownloader() {
        return bibleDownloader;
    }

    /**
     * Get all the currently loaded bibles
     *
     * @return a HashSet of Bibles
     */
    public HashSet<Bible> getLoadedBibles() {
        return loadedBibles;
    }

    /**
     * Try to retrieve a Bible from the currently loaded ones by its name.
     *
     * @param bibleName The name of the bible to search for.
     * @return An Optional with the Bible if found, an empty Optional otherwise.
     */
    public Optional<Bible> getBible(String bibleName) {
        for (final Bible bible : loadedBibles) {
            if (bible.getName().equalsIgnoreCase(bibleName))
                return Optional.of(bible);
        }
        return Optional.empty();
    }
}
