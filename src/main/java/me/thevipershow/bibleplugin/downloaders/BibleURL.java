package me.thevipershow.bibleplugin.downloaders;

public enum BibleURL {
    ENGLISH("https://paste.gg/p/thevipershow/680381eb106b497b827f9b8f4b0aeda0/files/5e640ad469b943fca13c9bb7d77a7f58/raw", "English"),
    FRENCH("https://raw.githubusercontent.com/thiagobodruk/bible/master/json/fr_apee.json","French"),
    GERMAN("https://raw.githubusercontent.com/thiagobodruk/bible/master/json/de_schlachter.json","German"),
    FINNISH("https://raw.githubusercontent.com/thiagobodruk/bible/master/json/fi_finnish.json","Finnish"),
    GREEK("https://raw.githubusercontent.com/thiagobodruk/bible/master/json/el_greek.json","Greek"),
    RUSSIAN("https://raw.githubusercontent.com/thiagobodruk/bible/master/json/ru_synodal.json", "Russian"),
    SPANISH("https://raw.githubusercontent.com/thiagobodruk/bible/master/json/es_rvr.json","Spanish");

    final String URL;
    final String name;

    BibleURL(final String URL, final String name) {
        this.URL = URL;
        this.name = name;
    }
}
