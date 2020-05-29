package me.thevipershow.bibleplugin.data;

import java.util.List;

public final class Book {
    final String abbreviation;
    final String name;
    final List<Chapter> chapters;

    public Book(String abbreviation, String name, List<Chapter> chapters) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.chapters = chapters;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }
}
