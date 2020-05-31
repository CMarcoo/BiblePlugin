package me.thevipershow.bibleplugin.data;

import java.util.List;

public final class Chapter  {
    final List<Verse> verses;

    public Chapter(List<Verse> verses) {
        this.verses = verses;
    }

    public List<Verse> getVerses() {
        return verses;
    }
}
