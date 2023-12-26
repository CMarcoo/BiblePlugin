package me.thevipershow.bibleplugin.obtainer;

import java.util.ArrayList;
import java.util.List;
import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.data.Book;
import me.thevipershow.bibleplugin.data.Chapter;
import me.thevipershow.bibleplugin.data.Verse;
import me.thevipershow.bibleplugin.exceptions.BibleException;

@Deprecated
public interface BibleObtainer {
    /**
     * Get all books that the Bible contains.
     * @param bible The Bible.
     * @return The full list of books that the Bible contains.
     */
    List<Book> getBooks(Bible bible);

    /**
     * Get all chapters of a specified Bible.
     * @param bible The Bible.
     * @return The full list of chapters that the Bible contains.
     */
    default List<Chapter> getChapters(Bible bible) {
        final List<Chapter> chapters = new ArrayList<>();
        getBooks(bible).forEach(book -> chapters.addAll(book.chapters()));
        return chapters;
    }

    /**
     * Get all verses for a specified Bible.
     * @param bible The Bible.
     * @return The full list of verses that the Bible contains.
     */
    default List<Verse> getVerses(Bible bible) {
        final List<Verse> verses = new ArrayList<>();
        getChapters(bible).forEach(chapter -> verses.addAll(chapter.verses()));
        return verses;
    }

    BibleSearch getBibleSearch(String word) throws BibleException;
}
