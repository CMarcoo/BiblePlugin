package me.thevipershow.bibleplugin.obtainer;

import me.thevipershow.bibleplugin.data.Book;
import me.thevipershow.bibleplugin.data.Verse;
import me.thevipershow.bibleplugin.exceptions.BibleException;

@Deprecated
public abstract class BibleSearch extends Search {
    private final int chapterNumber, verseNumber;
    private final Book book;

    public BibleSearch(Book book, String search) throws BibleException {
        super(search);
        this.book = book;
        if (search.matches("[0-9]:[0-9]")) {
            final String[] strings = search.split(":");
            chapterNumber = Integer.parseInt(strings[0]);
            verseNumber = Integer.parseInt(strings[1]);
            if (chapterNumber == 0 || verseNumber == 0) {
                throw new BibleException("Cannot search for 0");
            }
        } else {
            throw new BibleException("The search was invalid. It must match `[0-9]:[0-9]");
        }
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public int getVerseNumber() {
        return verseNumber;
    }
}
