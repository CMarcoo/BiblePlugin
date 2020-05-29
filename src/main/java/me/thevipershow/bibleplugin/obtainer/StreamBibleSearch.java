package me.thevipershow.bibleplugin.obtainer;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.data.Book;
import me.thevipershow.bibleplugin.data.Chapter;
import me.thevipershow.bibleplugin.data.Verse;
import me.thevipershow.bibleplugin.exceptions.BibleException;

public final class StreamBibleSearch extends BibleSearch {
    public StreamBibleSearch(String search) throws BibleException {
        super(search);
    }

    @Override
    public long findWordOccurrences(Bible bible, String word) {
        return bible.getBooks().stream()
                .map(Book::getChapters)
                .flatMap(Collection::stream)
                .map(Chapter::getVerses)
                .flatMap(Collection::stream)
                .map(Verse::getVerse)
                .filter(s -> s.contains(word))
                .count();
    }

    @Override
    public long findWordOccurrences(Book book, String word) {
        return 0;
    }

    @Override
    public long findWordOccurrences(Chapter chapter, String word) {
        return 0;
    }

    @Override
    public long findWordOccurrences(Verse verse, String word) {
        return 0;
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
        return Optional.empty();
    }

    @Override
    public Optional<Book> findBook(Bible bible, String name) {
        return Optional.empty();
    }
}
