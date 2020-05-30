package me.thevipershow.bibleplugin.downloaders;

public enum BibleURL {
    KING_JAMES_EN("https://raw.githubusercontent.com/thiagobodruk/bible/master/json/en_kjv.json", "King_James_en"),
    BASIC_EN("https://raw.githubusercontent.com/thiagobodruk/bible/master/json/en_bbe.json", "Basic_en");

    final String URL;
    final String name;

    BibleURL(String URL, String name) {
        this.URL = URL;
        this.name = name;
    }
}
