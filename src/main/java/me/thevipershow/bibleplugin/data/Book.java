package me.thevipershow.bibleplugin.data;

import java.util.List;

public final class Book implements Numbered {
    private final String abbrev;
    private final String name;
    private final List<Chapter> chapters;
    private final int number;

    public Book(String abbrev, String name, List<Chapter> chapters, int number) {
        this.abbrev = abbrev;
        this.name = name;
        this.chapters = chapters;
        this.number = number;
    }

    @Override
    public final int getNumber() {
        return this.number;
    }

    public final String getAbbrev() {
        return abbrev;
    }

    public final String getName() {
        return name;
    }

    public final List<Chapter> getChapters() {
        return chapters;
    }
}
