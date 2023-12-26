package me.thevipershow.bibleplugin;

import java.util.Locale;
import me.thevipershow.bibleplugin.commands.BibleCommand;
import me.thevipershow.bibleplugin.data.BibleType;
import me.thevipershow.bibleplugin.downloaders.BibleURL;
import me.thevipershow.bibleplugin.exceptions.BibleException;
import me.thevipershow.bibleplugin.listeners.BibleJoinMessage;
import me.thevipershow.bibleplugin.managers.BibleManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class BiblePlugin extends JavaPlugin {
    private BibleManager bibleManager;

    private static Plugin plugin = null;
    private static BibleType preferred = BibleType.FAST;

    private void assignBible() {
        String choosenType = getConfig().getString("bible.implementation");
        if (choosenType != null) {
            for (final BibleType value : BibleType.values()) {
                if (value.getName().equalsIgnoreCase(choosenType)) {
                    preferred = value;
                    break;
                }
            }
        }
    }

    private void loadDefaults() {
        getConfig().getStringList("bible.keep-loaded")
                .forEach(s -> {
                    try {
                        BibleURL bibleURL = BibleURL.valueOf(s.toUpperCase(Locale.ROOT));
                        bibleManager.downloadBible(bibleURL);
                        bibleManager.loadBible(bibleURL);
                    } catch (BibleException | IllegalArgumentException b) {
                        b.printStackTrace();
                    }
                });

    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        bibleManager = BibleManager.getInstance(this);
        bibleManager.getBibleDownloader().createBibleFolder(Exception::printStackTrace);

        loadDefaults();
        assignBible();

        BibleCommand bibleCommand = BibleCommand.getInstance(this);
        getCommand("bible").setExecutor(bibleCommand);
        getServer().getPluginManager().registerEvents(new BibleJoinMessage(bibleCommand), this);
    }

    /**
     * Expose this plugin's instance if the plugin has been correctly loaded!
     * @return null if this plugin was not loaded properly, otherwise its instance.
     */
    public static Plugin getPlugin() {
        return plugin;
    }

    /**
     * Expose the preferred bible implementation type.
     * @return BibleType. never null.
     */
    public static BibleType getPreferredBibleType() {
        return preferred;
    }
}
