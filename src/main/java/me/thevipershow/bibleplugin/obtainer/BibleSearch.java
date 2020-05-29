package me.thevipershow.bibleplugin.obtainer;

import java.util.List;
import java.util.Optional;
import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.data.Book;
import me.thevipershow.bibleplugin.data.Chapter;
import me.thevipershow.bibleplugin.data.Verse;
import me.thevipershow.bibleplugin.exceptions.BibleException;

public abstract class BibleSearch extends Search {
    private final int chapter, verse;

    public BibleSearch(String search) throws BibleException {
        super(search);
        if (search.matches("[0-9]:[0-9]")) {
            final String[] strings = search.split(":");
            chapter = Integer.parseInt(strings[0]);
            verse = Integer.parseInt(strings[1]);
            if (chapter == 0 || verse == 0) {
                throw new BibleException("Cannot search for 0");
            }
        } else {
            throw new BibleException("The search was invalid. It must match `[0-9]:[0-9]");
        }
    }

    /**
     * Find how many times a word can be found in a Bible.
     *
     * @param bible The Bible.
     * @param word  The word\phrase.
     * @return The number of occurrences.
     */
    public abstract long findWordOccurrences(Bible bible, String word);

    /**
     * Find how many times a word can be found in a book.
     *
     * @param book The book.
     * @param word The word\phrase.
     * @return The number of occurrences.
     */
    public abstract long findWordOccurrences(Book book, String word);

    /**
     * Find how many times a word can be found in a chapter.
     *
     * @param chapter The chapter.
     * @param word    The word\phrase.
     * @return The number of occurrences.
     */
    public abstract long findWordOccurrences(Chapter chapter, String word);

    /**
     * Find how many times a word can be found in a verse.
     *
     * @param verse The chapter.
     * @param word  The word\phrase.
     * @return The number of occurrences.
     */
    public abstract long findWordOccurrences(Verse verse, String word);

    /**
     * Find all the verses that contain a given word or phrase in a given Bible.
     *
     * @param word  A word or phrase.
     * @param bible The bible were the research will be performed
     * @return an Optional that will contain a list of verses if found, an empty Optional otherwise.
     */
    public abstract Optional<List<Verse>> findVerseContainingWord(Bible bible, String word);

    /**
     * Find all the verses that contain a given word or phrase in a given book.
     *
     * @param word A word or phrase.
     * @param book The book were the research will be performed
     * @return an Optional that will contain a list of verses if found, an empty Optional otherwise.
     */
    public abstract Optional<List<Verse>> findVerseContainingWord(Book book, String word);

    /**
     * Find all the verses that contain a given word or phrase in a given chapter.
     *
     * @param word    A word or phrase.
     * @param chapter The chapter were the research will be performed
     * @return an Optional that will contain a list of verses if found, an empty Optional otherwise.
     */
    public abstract Optional<List<Verse>> findVerseContainingWord(Chapter chapter, String word);

    /**
     * Search for a book using its name in a Bible.
     *
     * @param bible The Bible.
     * @param name  The name of the book.
     * @return an Optional that will contain the Book with the specified name if found, an empty Optional otherwise
     */
    public abstract Optional<Book> findBook(Bible bible, String name);

    public int getChapter() {
        return chapter;
    }

    public int getVerse() {
        return verse;
    }
}
