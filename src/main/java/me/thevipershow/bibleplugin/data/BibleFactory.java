package me.thevipershow.bibleplugin.data;

import com.google.gson.Gson;
import java.util.Arrays;

public final class BibleFactory {
    public static BibleFactory instance = null;
    private final Gson gson;

    public static BibleFactory getInstance() {
        return instance != null ? instance : (instance = new BibleFactory());
    }

    private BibleFactory() {
        gson = new Gson();
    }

    public Bible createBible(String json, String bibleName) {
        final Book[] books = gson.fromJson(json, Book[].class);
        return new Bible(Arrays.asList(books), bibleName);
    }
}
