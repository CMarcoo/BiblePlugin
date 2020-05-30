package me.thevipershow.bibleplugin;

import me.thevipershow.bibleplugin.commands.BibleCommand;
import me.thevipershow.bibleplugin.downloaders.BibleURL;
import me.thevipershow.bibleplugin.managers.BibleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BiblePlugin extends JavaPlugin {
    private BibleManager bibleManager;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        // Plugin startup logic
        bibleManager = BibleManager.getInstance(this);
        bibleManager.getBibleDownloader().createBibleFolder(Exception::printStackTrace);
        bibleManager.downloadBible(BibleURL.BASIC_EN);
        getCommand("bible").setExecutor(BibleCommand.getInstance(this));
    }
}
