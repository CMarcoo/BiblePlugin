package me.thevipershow.bibleplugin.bibles.serialization;

import java.util.List;

public abstract class AbstractBibleImplementation implements BibleImplementation {
    protected final List<Chapter> chapters;
    protected final String abbreviation;

    public AbstractBibleImplementation(Pair<List<Chapter>, String> bible) {
        this.chapters = bible.getA();
        this.abbreviation = bible.getB();
    }
}
