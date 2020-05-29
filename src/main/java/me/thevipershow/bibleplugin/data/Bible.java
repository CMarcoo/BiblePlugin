package me.thevipershow.bibleplugin.data;

import java.util.List;

public final class Bible {
    private final List<Book> books;
    private final String name;

    public Bible(List<Book> books, String name) {
        this.books = books;
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public String getName() {
        return name;
    }
}
