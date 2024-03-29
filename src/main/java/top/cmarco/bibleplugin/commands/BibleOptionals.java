package top.cmarco.bibleplugin.commands;

import java.util.Optional;
import top.cmarco.bibleplugin.data.Bible;
import top.cmarco.bibleplugin.data.Book;
import top.cmarco.bibleplugin.data.Chapter;
import top.cmarco.bibleplugin.data.Verse;
import top.cmarco.bibleplugin.exceptions.BibleException;
import top.cmarco.bibleplugin.managers.BibleManager;

public class BibleOptionals {
    public static Bible optionalBibleSearch(BibleManager bibleManager, String bibleName) throws BibleException {
        final Optional<Bible> optionalBible = bibleManager.getBible(bibleName);
        if (optionalBible.isEmpty()) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7Bible &f`&e" + bibleName + "&f`&7 could not be found.");
        } else {
            return optionalBible.get();
        }
    }

    public static Book optionalBookSearch(Bible bible, String bookName) throws BibleException {
        final Optional<Book> chapterOptional = bible.getBook(bookName);
        if (chapterOptional.isEmpty()) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7Book &f`&e" + bookName + "&f`&7 could not be found.");
        } else {
            return chapterOptional.get();
        }
    }

    public static Chapter optionalChapterSearch(Book book, int chapterNumber) throws BibleException {
        int size = book.chapters().size();
        if (chapterNumber > size) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7Chapter number &f`&e" + chapterNumber + "&f`&7 is greater than maximum (" + size + ")");
        } else {
            return book.chapters().get(chapterNumber - 1);
        }
    }

    public static Verse optionalVerseSearch(Chapter chapter, int verseNumber) throws BibleException {
        int size = chapter.verses().size();
        if (verseNumber > size) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7Verse number &f`&e" + verseNumber + "&f`&7 is greater than maximum (&e" + size + "&7)");
        } else if (verseNumber - 1 < 0) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7Verse number &f`&e" + verseNumber + "&f`&7 is below 1!");
        } else {
            return chapter.verses().get(verseNumber - 1);
        }
    }
}
