package me.thevipershow.bibleplugin.obtainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.data.Book;
import me.thevipershow.bibleplugin.data.Chapter;
import me.thevipershow.bibleplugin.data.Verse;
import org.apache.commons.lang.StringUtils;

public final class FastBible extends Bible {
    public FastBible(List<Book> books) {
        super(books);
    }

    /**
     * Find how many times a word can be found in a Bible.
     *
     * @param bible The Bible.
     * @param word  The word\phrase.
     * @return The number of occurrences.
     */
    @Override
    public long findWordOccurrences(Bible bible, String word) {
        long count = 0L;
        for (final Book book : bible.getBooks())
            count += findWordOccurrences(book, word);

        return count;
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
        long count = 0L;
        for (final Chapter chapter : book.getChapters())
            count += findWordOccurrences(chapter, word);

        return count;
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
        long count = 0L;
        for (final Verse verse : chapter.getVerses())
            count += findWordOccurrences(verse, word);

        return count;
    }

    /**
     * Find how many times a word can be found in a verse.
     *
     * @param verse The chapter.
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
     * @param bible The bible were the research will be performed
     * @param word  A word or phrase.
     * @return a List that will contain a list of verses if found, an empty List otherwise.
     */
    @Override
    public List<Verse> findVerseContainingWord(Bible bible, String word) {
        final List<Verse> verses = new ArrayList<>();
        for (final Book book : bible.getBooks())
            verses.addAll(findVerseContainingWord(book, word));

        return verses;
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
        final List<Verse> verses = new ArrayList<>();
        for (final Chapter chapter : book.getChapters())
            verses.addAll(findVerseContainingWord(chapter, word));

        return verses;
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
        final List<Verse> verses = new ArrayList<>();
        for (final Verse verse : chapter.getVerses())
            if (verse.getVerse().contains(word))
                verses.add(verse);

        return verses;
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
        for (final Book book : bible.getBooks())
            if (book.getName().equalsIgnoreCase(name))
                return Optional.of(book);

        return Optional.empty();
    }
}
