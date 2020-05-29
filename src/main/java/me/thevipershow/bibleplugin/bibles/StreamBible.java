package me.thevipershow.bibleplugin.bibles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.thevipershow.bibleplugin.bibles.serialization.BibleImplementation;
import me.thevipershow.bibleplugin.bibles.serialization.Chapter;
import me.thevipershow.bibleplugin.exceptions.BibleException;

public final class StreamBible extends AbstractBible {
    public StreamBible(BibleImplementation bibleImplementation) throws BibleException {
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
        return super.bibleImplementation
                .getChapters()
                .stream()
                .map(Chapter::getVerses)
                .flatMap(List::stream)
                .filter(s -> s.contains(word))
                .collect(Collectors.toList());
    }

    @Override
    long findWordOccurrences(String word) {
        return super.bibleImplementation
                .getChapters()
                .stream()
                .map(Chapter::getVerses)
                .flatMap(List::stream)
                .filter(e -> e.contains(word))
                .count();
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
