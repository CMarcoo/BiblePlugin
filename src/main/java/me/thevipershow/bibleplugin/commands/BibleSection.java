package me.thevipershow.bibleplugin.commands;

import me.thevipershow.bibleplugin.data.Bible;
import me.thevipershow.bibleplugin.data.Book;
import me.thevipershow.bibleplugin.data.Chapter;
import me.thevipershow.bibleplugin.data.Verse;

public enum BibleSection {
    BIBLE(Bible.class), BOOK(Book.class), CHAPTER(Chapter.class), VERSE(Verse.class);

    private final Class<?> requiredType;

    BibleSection(final Class<?> requiredType) {
        this.requiredType = requiredType;
    }

    public final Class<?> getRequiredType() {
        return requiredType;
    }

    public final boolean isValidObject(Object o) {
        return this.requiredType.isAssignableFrom(o.getClass());
    }
}
