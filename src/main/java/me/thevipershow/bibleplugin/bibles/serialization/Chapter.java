package me.thevipershow.bibleplugin.bibles.serialization;

import java.util.List;

public final class Chapter {
    final List<String> verses;

    public Chapter(List<String> verses) {
        this.verses = verses;
    }

    public List<String> getVerses() {
        return verses;
    }

    public int getTotalVerses() {return verses.size();}
}
