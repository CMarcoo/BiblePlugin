package me.thevipershow.bibleplugin.bibles.serialization;

import java.util.List;

public abstract class AbstractBibleImplementation implements BibleImplementation {
    protected final List<Chapter> chapters;
    protected final String abbreviation;

    public AbstractBibleImplementation(List<Chapter> chapters, String abbreviation) {
        this.chapters = chapters;
        this.abbreviation = abbreviation;
    }
}
