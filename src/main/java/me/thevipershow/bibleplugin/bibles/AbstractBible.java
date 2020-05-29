package me.thevipershow.bibleplugin.bibles;

import java.util.List;
import java.util.Optional;
import me.thevipershow.bibleplugin.bibles.serialization.BibleImplementation;
import me.thevipershow.bibleplugin.bibles.serialization.Chapter;
import me.thevipershow.bibleplugin.exceptions.BibleException;

public abstract class AbstractBible implements Bible {
    protected final BibleImplementation bibleImplementation;

    protected AbstractBible(BibleImplementation bibleImplementation) throws BibleException {
        if (bibleImplementation == null) {
            throw new BibleException("Bible cannot be null.");
        } else {
            this.bibleImplementation = bibleImplementation;
        }
    }

    abstract Optional<String> findVerse(VerseMatcher verseMatcher);

    abstract Chapter findChapter(int index);

    abstract List<String> findVersesContainingWord(String word);

    abstract long findWordOccurrences(String word);
}
