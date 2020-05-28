package me.thevipershow.bibleplugin.downloaders;

public enum BibleURL {
    ENGLISH_KING_JAMES("https://raw.githubusercontent.com/thiagobodruk/bible/master/json/en_kjv.json", "en_kjv");

    final String URL;
    final String name;

    BibleURL(String URL, String name) {
        this.URL = URL;
        this.name = name;
    }
}
