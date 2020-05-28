package me.thevipershow.bibleplugin.managers;

import java.util.HashSet;
import me.thevipershow.bibleplugin.bibles.AbstractBible;

public final class BibleManager {
    private static BibleManager instance = null;
    private final HashSet<AbstractBible> loadedBibles;

    private BibleManager() {
        this.loadedBibles = new HashSet<>();
    }

    public static BibleManager getInstance() {
        return instance != null ? instance : (instance = new BibleManager());
    }

}
