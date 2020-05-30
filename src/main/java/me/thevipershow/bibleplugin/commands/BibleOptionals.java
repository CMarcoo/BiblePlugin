package me.thevipershow.bibleplugin.commands;

import java.util.Optional;
import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.data.Book;
import me.thevipershow.bibleplugin.data.Chapter;
import me.thevipershow.bibleplugin.exceptions.BibleException;
import me.thevipershow.bibleplugin.managers.BibleManager;

public class BibleOptionals {
    public static Bible optionalBibleSearch(BibleManager bibleManager, String bibleName) throws BibleException {
        final Optional<Bible> optionalBible = bibleManager.getBible(bibleName);
        if (optionalBible.isPresent())
            return optionalBible.get();
        throw new BibleException("&8[&eBiblePlugin&8]&f: &7Bible &f`&e" + bibleName + "&f`&7 could not be found.");
    }

    public static Book optionalBookSearch(Bible bible, String bookName) throws BibleException {
        final Optional<Book> chapterOptional = bible.getBook(bookName);
        if (chapterOptional.isPresent())
            return chapterOptional.get();
        throw new BibleException("&8[&eBiblePlugin&8]&f: &7Book &f`&e" + bookName + "&f`&7 could not be found.");
    }

    public static Chapter optionalChapterSearch(Book book, int chapterNumber) throws BibleException {
        if (book.getChapters().size() <= chapterNumber)
            return book.getChapters().get(chapterNumber - 1);
        throw new BibleException("&8[&eBiblePlugin&8]&f: &7Chapter number &f`&e" + chapterNumber + "&f`&7 could not be found.");
    }
}
