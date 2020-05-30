package me.thevipershow.bibleplugin.data;

import com.google.gson.Gson;
import java.util.Arrays;
import me.thevipershow.bibleplugin.obtainer.FastBible;

public final class BibleFactory {
    public static BibleFactory instance = null;
    private final Gson gson;

    public static BibleFactory getInstance() {
        return instance != null ? instance : (instance = new BibleFactory());
    }

    private BibleFactory() {
        gson = new Gson();
    }

    /**
     * Instantiate a Bible from a JSON String of valid format.
     * @param json The JSON Bible.
     * @param bibleName The name of the bible.
     * @return A Bible object.
     */
    public Bible createBible(String json, String bibleName) {
        final Book[] books = gson.fromJson(json, Book[].class);
        return new FastBible(Arrays.asList(books), bibleName);
    }
}
