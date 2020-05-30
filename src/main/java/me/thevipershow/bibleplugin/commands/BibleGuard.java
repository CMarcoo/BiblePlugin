package me.thevipershow.bibleplugin.commands;

import me.thevipershow.bibleplugin.exceptions.BibleException;

public class BibleGuard {
    public static String[] validateVerseSearch(String search) throws BibleException {
        final String[] array = search.split(":+");
        if (array.length != 3)
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7The search was invalid!");
        try {
            Integer.parseInt(array[2]);
            return array;
        } catch (final NumberFormatException e) {
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7The chapter &f`&e" + array[2] + "&f` &7was not a number!");
        }
    }

    public static String[] validateChapterSearch(String search) throws BibleException {
        final String[] array = search.split(":+");
        if (array.length != 2)
            throw new BibleException("&8[&eBiblePlugin&8]&f: &7The search was invalid!");
        return array;
    }
}
