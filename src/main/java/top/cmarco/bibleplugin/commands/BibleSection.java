package top.cmarco.bibleplugin.commands;

import top.cmarco.bibleplugin.data.Bible;
import top.cmarco.bibleplugin.data.Book;
import top.cmarco.bibleplugin.data.Chapter;
import top.cmarco.bibleplugin.data.Verse;

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
