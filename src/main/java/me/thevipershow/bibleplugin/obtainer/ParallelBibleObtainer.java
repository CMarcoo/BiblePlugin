package me.thevipershow.bibleplugin.obtainer;

import java.util.List;
import java.util.stream.Collectors;
import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.data.Book;
import me.thevipershow.bibleplugin.data.Chapter;
import me.thevipershow.bibleplugin.data.Verse;
import me.thevipershow.bibleplugin.exceptions.BibleException;

@Deprecated
public final class ParallelBibleObtainer implements BibleObtainer {
    private static ParallelBibleObtainer instance = null;

    private ParallelBibleObtainer() {
    }

    public static ParallelBibleObtainer getInstance() {
        return instance != null ? instance : (instance = new ParallelBibleObtainer());
    }

    /**
     * Get all books that the Bible contains.
     *
     * @param bible The Bible.
     * @return The full list of books that the Bible contains.
     */
    @Override
    public List<Book> getBooks(Bible bible) {
        return bible.getBooks();
    }

    /**
     * Get all chapters of a specified Bible.
     *
     * @param bible The Bible.
     * @return The full list of chapters that the Bible contains.
     */
    @Override
    public List<Chapter> getChapters(Bible bible) {
        return getBooks(bible).parallelStream().flatMap(book -> book.chapters().parallelStream()).collect(Collectors.toList());
    }

    /**
     * Get all verses for a specified Bible.
     *
     * @param bible The Bible.
     * @return The full list of verses that the Bible contains.
     */
    @Override
    public List<Verse> getVerses(Bible bible) {
        return getChapters(bible).parallelStream().flatMap(chapter -> chapter.verses().parallelStream()).collect(Collectors.toList());
    }

    @Override
    public BibleSearch getBibleSearch(String word) throws BibleException {
        return null;
    }
}
