package me.thevipershow.bibleplugin;

import java.util.Locale;
import me.thevipershow.bibleplugin.commands.BibleCommand;
import me.thevipershow.bibleplugin.downloaders.BibleURL;
import me.thevipershow.bibleplugin.exceptions.BibleException;
import me.thevipershow.bibleplugin.managers.BibleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BiblePlugin extends JavaPlugin {
    private BibleManager bibleManager;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        saveDefaultConfig();
        bibleManager = BibleManager.getInstance(this);
        bibleManager.getBibleDownloader().createBibleFolder(Exception::printStackTrace);

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
        getCommand("bible").setExecutor(BibleCommand.getInstance(this));
    }
}
