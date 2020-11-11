package me.thevipershow.bibleplugin.data;

public final class Verse implements Numbered {
    private final String verse;
    private final int number;

    public Verse(String verse, int number) {
        this.verse = verse;
        this.number = number;
    }

    public final String getVerse() {
        return verse;
    }

    @Override
    public final int getNumber() {
        return number;
    }
}
