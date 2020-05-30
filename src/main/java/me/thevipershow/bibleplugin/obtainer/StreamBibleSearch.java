package me.thevipershow.bibleplugin.obtainer;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.data.Book;
import me.thevipershow.bibleplugin.data.Chapter;
import me.thevipershow.bibleplugin.data.Verse;
import me.thevipershow.bibleplugin.exceptions.BibleException;
import org.apache.commons.lang.StringUtils;

public final class StreamBibleSearch extends BibleSearch {
    public StreamBibleSearch(String search) throws BibleException {
        super(search);
    }

    @Override
    public long findWordOccurrences(Bible bible, String word) {
        return bible.getBooks().stream().mapToLong(book -> findWordOccurrences(book, word)).sum();
    }

    @Override
    public long findWordOccurrences(Book book, String word) {
        return book.getChapters().stream().mapToLong(chapter -> findWordOccurrences(chapter, word)).sum();
    }

    @Override
    public long findWordOccurrences(Chapter chapter, String word) {
        return chapter.getVerses().stream().mapToLong(verse -> findWordOccurrences(verse, word)).sum();
    }

    @Override
    public long findWordOccurrences(Verse verse, String word) {
        return StringUtils.countMatches(verse.getVerse(), word);
    }

    @Override
    public List<Verse> findVerseContainingWord(Bible bible, String word) {
        return bible.getBooks().stream()
                .flatMap(book -> book.getChapters().stream().flatMap(chapter -> chapter.getVerses().stream()))
                .filter(verse -> verse.getVerse().contains(word))
                .collect(Collectors.toList());
    }

    /**
     * Find all the verses that contain a given word or phrase in a given book.
     *
     * @param book The book were the research will be performed
     * @param word A word or phrase.
     * @return a List that will contain a list of verses if found, an empty List otherwise.
     */
    @Override
    public List<Verse> findVerseContainingWord(Book book, String word) {
        return null;
        // TODO: 30/05/2020 finish this method 
    }

    /**
     * Find all the verses that contain a given word or phrase in a given chapter.
     *
     * @param chapter The chapter were the research will be performed
     * @param word    A word or phrase.
     * @return a List that will contain a list of verses if found, an empty List otherwise.
     */
    @Override
    public List<Verse> findVerseContainingWord(Chapter chapter, String word) {
        return chapter.getVerses().stream().filter(verse -> verse.getVerse().contains(word)).collect(Collectors.toList());
    }

    /**
     * Search for a book using its name in a Bible.
     *
     * @param bible The Bible.
     * @param name  The name of the book.
     * @return a List that will contain the Book with the specified name if found, an empty List otherwise
     */
    @Override
    public Optional<Book> findBook(Bible bible, String name) {
        return bible.getBooks().stream().filter(book -> book.getName().equalsIgnoreCase(name)).findFirst();
    }
}
