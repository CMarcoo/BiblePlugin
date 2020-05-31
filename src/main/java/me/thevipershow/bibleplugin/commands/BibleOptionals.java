package me.thevipershow.bibleplugin.commands;

import java.util.Optional;
import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.data.Book;
import me.thevipershow.bibleplugin.data.Chapter;
import me.thevipershow.bibleplugin.data.Verse;
import me.thevipershow.bibleplugin.exceptions.BibleException;
import me.thevipershow.bibleplugin.managers.BibleManager;

public class BibleOptionals {
    public static Bible optionalBibleSearch(BibleManager bibleManager, String bibleName) throws BibleException {
        final Optional<Bible> optionalBible = bibleManager.getBible(bibleName);
        if (!optionalBible.isPresent()) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7Bible &f`&e" + bibleName + "&f`&7 could not be found.");
        } else {
            return optionalBible.get();
        }
    }

    public static Book optionalBookSearch(Bible bible, String bookName) throws BibleException {
        final Optional<Book> chapterOptional = bible.getBook(bookName);
        if (!chapterOptional.isPresent()) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7Book &f`&e" + bookName + "&f`&7 could not be found.");
        } else {
            return chapterOptional.get();
        }
    }

    public static Chapter optionalChapterSearch(Book book, int chapterNumber) throws BibleException {
        int size = book.getChapters().size();
        if (chapterNumber > size) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7Chapter number &f`&e" + chapterNumber + "&f`&7 is greater than maximum (" + size + ")");
        } else {
            return book.getChapters().get(chapterNumber - 1);
        }
    }

    public static Verse optionalVerseSearch(Chapter chapter, int verseNumber) throws BibleException {
        int size = chapter.getVerses().size();
        if (verseNumber > size) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7Verse number &f`&e" + verseNumber + "&f`&7 is greater than maximum (&e" + size + "&7)");
        } else {
            return chapter.getVerses().get(verseNumber - 1);
        }
    }
}
