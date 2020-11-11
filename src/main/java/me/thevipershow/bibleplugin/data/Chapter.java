package me.thevipershow.bibleplugin.data;

import java.util.List;

public final class Chapter implements Numbered {
    private final List<Verse> verses;
    private final int number;

    public Chapter(List<Verse> verses, int number) {
        this.verses = verses;
        this.number = number;
    }

    @Override
    public final int getNumber() {
        return number;
    }

    public final List<Verse> getVerses() {
        return verses;
    }
}
