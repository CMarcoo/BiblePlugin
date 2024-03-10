package top.cmarco.bibleplugin.obtainer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import top.cmarco.bibleplugin.data.Bible;
import top.cmarco.bibleplugin.data.Book;
import top.cmarco.bibleplugin.data.Chapter;
import top.cmarco.bibleplugin.data.Verse;

public final class StreamBible extends Bible {
    public StreamBible(List<Book> books) {
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
        return getBooks().stream().mapToLong(book -> findWordOccurrences(book, word)).sum();
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
        return book.chapters().stream().mapToLong(chapter -> findWordOccurrences(chapter, word)).sum();
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
        return chapter.verses().stream().mapToLong(verse -> findWordOccurrences(verse, word)).sum();
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
        return this.getBooks().stream()
                .flatMap(book -> findVerseContainingWord(book, word).stream())
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
        return book.chapters().stream()
                .flatMap(chapter -> findVerseContainingWord(chapter, word).stream())
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
        return chapter.verses().stream().filter(verse -> verse.verse().contains(word)).collect(Collectors.toList());
    }

    /**
     * Search for a book using its name in a Bible.
     *
     * @param name  The name of the book.
     * @return a List that will contain the Book with the specified name if found, an empty List otherwise
     */
    @Override
    public Optional<Book> findBook(String name) {
        return this.getBooks().stream().filter(book -> book.name().equalsIgnoreCase(name)).findFirst();
    }
}
