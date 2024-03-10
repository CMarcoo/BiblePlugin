package top.cmarco.bibleplugin.obtainer;

import top.cmarco.bibleplugin.data.Bible;
import top.cmarco.bibleplugin.data.Book;
import top.cmarco.bibleplugin.data.Chapter;
import top.cmarco.bibleplugin.data.Verse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class FastBible extends Bible {
    public FastBible(List<Book> books) {
        super(books);
    }

    /**
     * Find how many times a word can be found in a Bible.
     *
     * @param word  The word\phrase.
     * @return The number of occurrences.
     */
    @Override
    public long findWordOccurrences(String word) {
        long count = 0L;
        for (final Book book : getBooks())
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
        for (final Chapter chapter : book.chapters())
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
        for (final Verse verse : chapter.verses())
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
        long k = 0;
        for (final String s : verse.verse().split("\\s+"))
            if (s.equalsIgnoreCase(word))
                k++;
        return k;
       // for some reason apache lang was deprecated and removed from Spigot 1.19
       // return StringUtils.countMatches(verse.getVerse(), word);
    }

    /**
     * Find all the verses that contain a given word or phrase in a given Bible.
     *
     * @param word  A word or phrase.
     * @return a List that will contain a list of verses if found, an empty List otherwise.
     */
    @Override
    public List<Verse> findVerseContainingWord(String word) {
        final List<Verse> verses = new ArrayList<>();
        for (final Book book : this.getBooks())
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
        for (final Chapter chapter : book.chapters())
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
        for (final Verse verse : chapter.verses())
            if (verse.verse().contains(word))
                verses.add(verse);

        return verses;
    }

    /**
     * Search for a book using its name in a Bible.
     *
     * @param name  The name of the book.
     * @return a List that will contain the Book with the specified name if found, an empty List otherwise
     */
    @Override
    public Optional<Book> findBook(String name) {
        for (final Book book : this.getBooks())
            if (book.name().equalsIgnoreCase(name))
                return Optional.of(book);

        return Optional.empty();
    }
}
