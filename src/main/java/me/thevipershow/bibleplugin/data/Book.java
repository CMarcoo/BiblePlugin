package me.thevipershow.bibleplugin.data;

import java.util.List;

public final class Book {
    final String abbrev;
    final String name;
    final List<Chapter> chapters;

    public Book(String abbrev, String name, List<Chapter> chapters) {
        this.abbrev = abbrev;
        this.name = name;
        this.chapters = chapters;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public String getName() {
        return name;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }
}
