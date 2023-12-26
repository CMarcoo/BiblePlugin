package me.thevipershow.bibleplugin.commands;

import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.exceptions.BibleException;

public final class BibleGuard {
    public static String[] validateGetVerse(String search) throws BibleException {
        try {
            String[] array = search.split(":+");
            String bookName = array[0];
            int chapter = Integer.parseInt(array[1]);
            int verse = Integer.parseInt(array[2]);
            return array;
        } catch (NumberFormatException e) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7The chapter or verse was not a number!");
        } catch (IndexOutOfBoundsException e) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7The search had an invalid format!");
        }
    }

    public static String[] validateVerseSearch(String search) throws BibleException {
        try {
            String[] array = parseSearch(search);
            return array;
        } catch (NumberFormatException e) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7The chapter or verse was not a number!");
        } catch (IndexOutOfBoundsException e) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7The search had an invalid format!");
        }
    }

    public static String[] parseSearch(String search) throws NumberFormatException, IndexOutOfBoundsException {
        String[] array = search.split(":+");
        String bibleName = array[0];
        String bookName = array[1];
        int chapter = Integer.parseInt(array[2]);
        return array;
    }

    public static String[] validateChapterSearch(String search) throws BibleException {
        String[] array = search.split(":+");
        if (array.length != 2)
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7The search was invalid!");
        return array;
    }

    public static Bible validateBible(Bible bible) throws BibleException {
        if (bible == null)
            throw new BibleException("bible is null");
        if (bible.getBooks() == null)
            throw new BibleException("books were null");
        if (bible.getBooks().isEmpty())
            throw new BibleException("no books were found");
        if (bible.getBooks().stream().mapToLong(book -> book.chapters().size()).sum() == 0)
            throw new BibleException("no chapters were found");
        return bible;
    }
}
