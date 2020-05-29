package me.thevipershow.bibleplugin.obtainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.data.Book;
import me.thevipershow.bibleplugin.data.Chapter;
import me.thevipershow.bibleplugin.data.Verse;
import me.thevipershow.bibleplugin.exceptions.BibleException;
import org.apache.commons.lang.StringUtils;
import sun.jvm.hotspot.opto.CompilerPhaseType;

public final class FastBibleSearch extends BibleSearch {
    public FastBibleSearch(String search) throws BibleException {
        super(search);
    }

    @Override
    public long findWordOccurrences(Bible bible, String word) {
        long count = 0;
        for (final Book book : bible.getBooks())
            count += findWordOccurrences(book, word);

        return count;
    }

    @Override
    public long findWordOccurrences(Book book, String word) {
        long count = 0;
        for (final Chapter chapter : book.getChapters())
            count += findWordOccurrences(chapter, word);

        return count;
    }

    @Override
    public long findWordOccurrences(Chapter chapter, String word) {
        long count = 0;
        for (final Verse verse : chapter.getVerses())
            count += findWordOccurrences(verse, word);

        return count;
    }

    @Override
    public long findWordOccurrences(Verse verse, String word) {
        return StringUtils.countMatches(verse.getVerse(), word);
    }

    @Override
    public Optional<List<Verse>> findVerseContainingWord(Bible bible, String word) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Verse>> findVerseContainingWord(Book book, String word) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Verse>> findVerseContainingWord(Chapter chapter, String word) {
        final List<Verse> verses = new ArrayList<>();
        for (final Verse verse : chapter.getVerses()) {
            if (verse.getVerse().contains(word))
                verses.add(verse);
        }
        if (!verses.isEmpty())
            return Optional.of(verses);
        return Optional.empty();
    }

    @Override
    public Optional<Book> findBook(Bible bible, String name) {
        for (final Book book : bible.getBooks()) {
            if (book.getName().equalsIgnoreCase(name))
                return Optional.of(book);
        }
        return Optional.empty();
    }
}
