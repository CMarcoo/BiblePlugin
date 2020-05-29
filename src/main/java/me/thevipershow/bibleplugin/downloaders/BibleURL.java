package me.thevipershow.bibleplugin.downloaders;

public enum BibleURL {
    ENGLISH_KING_JAMES("https://raw.githubusercontent.com/thiagobodruk/bible/master/json/en_kjv.json", "en_kjv"),
    BASIC_ENGLISH("https://raw.githubusercontent.com/thiagobodruk/bible/master/json/en_bbe.json", "en_bbe");

    final String URL;
    final String name;

    BibleURL(String URL, String name) {
        this.URL = URL;
        this.name = name;
    }
}
