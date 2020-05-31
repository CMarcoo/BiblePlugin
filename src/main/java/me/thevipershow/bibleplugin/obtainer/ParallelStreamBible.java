package me.thevipershow.bibleplugin.obtainer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.data.Book;
import me.thevipershow.bibleplugin.data.Chapter;
import me.thevipershow.bibleplugin.data.Verse;
import org.apache.commons.lang.StringUtils;

public final class ParallelStreamBible extends Bible {
    public ParallelStreamBible(List<Book> books) {
        super(books);
    }

    @Override
    public long findWordOccurrences(Bible bible, String word) {
        return bible.getBooks().parallelStream().mapToLong(book -> findWordOccurrences(book, word)).sum();
    }

    /**
     * Find how many times a word can be found in a book.
     *
     * @param book The book.
     * @param word The word\phrase.
     * @return The number of occurrences.
     */
    @Override
    public long findWordOccurrences(Book book, String word) {
        return book.getChapters().parallelStream().mapToLong(chapter -> findWordOccurrences(chapter, word)).sum();
    }

    /**
     * Find how many times a word can be found in a chapter.
     *
     * @param chapter The chapter.
     * @param word    The word\phrase.
     * @return The number of occurrences.
     */
    @Override
    public long findWordOccurrences(Chapter chapter, String word) {
        return chapter.getVerses().parallelStream().mapToLong(verse -> findWordOccurrences(verse, word)).sum();
    }

    /**
     * Find how many times a word can be found in a verse.
     *
     * @param verse The verse.
     * @param word  The word\phrase.
     * @return The number of occurrences.
     */
    @Override
    public long findWordOccurrences(Verse verse, String word) {
        return StringUtils.countMatches(verse.getVerse(), word);
    }

    /**
     * Find all the verses that contain a given word or phrase in a given Bible.
     *
     * @param bible The bible where the research will be performed
     * @param word  A word or phrase.
     * @return a List that will contain a list of verses if found, an empty List otherwise.
     */
    @Override
    public List<Verse> findVerseContainingWord(Bible bible, String word) {
        return bible.getBooks().stream()
                .flatMap(book -> findVerseContainingWord(book, word).parallelStream())
                .collect(Collectors.toList());
    }

    /**
     * Find all the verses that contain a given word or phrase in a given book.
     *
     * @param book The book where the research will be performed
     * @param word A word or phrase.
     * @return a List that will contain a list of verses if found, an empty List otherwise.
     */
    @Override
    public List<Verse> findVerseContainingWord(Book book, String word) {
        return book.getChapters().stream()
                .flatMap(chapter -> findVerseContainingWord(chapter, word).parallelStream())
                .collect(Collectors.toList());
    }

    /**
     * Find all the verses that contain a given word or phrase in a given chapter.
     *
     * @param chapter The chapter where the research will be performed
     * @param word    A word or phrase.
     * @return a List that will contain a list of verses if found, an empty List otherwise.
     */
    @Override
    public List<Verse> findVerseContainingWord(Chapter chapter, String word) {
        return chapter.getVerses().parallelStream().filter(verse -> verse.getVerse().contains(word)).collect(Collectors.toList());
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
