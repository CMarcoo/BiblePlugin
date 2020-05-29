package me.thevipershow.bibleplugin.bibles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.thevipershow.bibleplugin.bibles.serialization.BibleImplementation;
import me.thevipershow.bibleplugin.bibles.serialization.Chapter;
import me.thevipershow.bibleplugin.exceptions.BibleException;

public final class FastBible extends AbstractBible {

    public FastBible(BibleImplementation bibleImplementation) throws BibleException {
        super(bibleImplementation);
    }

    @Override
    Optional<String> findVerse(VerseMatcher verseMatcher) {
        return verseMatcher.getValidVerse(this);
    }

    @Override
    Chapter findChapter(int index) {
        return super.bibleImplementation.getChapters().get(index);
    }

    @Override
    List<String> findVersesContainingWord(String word) {
        final List<String> foundVerses = new ArrayList<>();
        for (final Chapter chapter : bibleImplementation.getChapters())
            for (final String s : chapter.getVerses())
                if (s.contains(word))
                    foundVerses.add(s);

        return foundVerses;
    }

    @Override
    long findWordOccurrences(String word) {
        int occurrences = 0;
        for (Chapter chapter : super.bibleImplementation.getChapters()) {
            for (String s : chapter.getVerses()) {
                if (s.contains(word))
                    occurrences++;
            }
        }
        return occurrences;
    }

    @Override
    public int getTotalChapters() {
        return super.bibleImplementation.getChapters().size();
    }

    @Override
    public List<Chapter> getChapters() {
        return super.bibleImplementation.getChapters();
    }

    @Override
    public Chapter getChapter(int i) {
        return super.bibleImplementation.getChapters().get(i);
    }
}
