package me.thevipershow.bibleplugin.bibles;

import me.thevipershow.bibleplugin.bibles.serialization.BibleImplementation;
import me.thevipershow.bibleplugin.exceptions.BibleException;

public abstract class AbstractBible implements Bible {
    private final BibleImplementation bibleImplementation;

    protected AbstractBible(BibleImplementation bibleImplementation) throws BibleException {
        if (bibleImplementation == null) {
            throw new BibleException("Bible cannot be null.");
        } else {
            this.bibleImplementation = bibleImplementation;
        }
    }

    abstract String findVerse(String input);

    
}
