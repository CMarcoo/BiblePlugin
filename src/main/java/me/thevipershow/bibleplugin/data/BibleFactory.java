package me.thevipershow.bibleplugin.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
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
        Type booksArrayType = new TypeToken<List<Book>>(){}.getType();
        List<Book> books = (List<Book>) gson.fromJson(json, booksArrayType);
// TODO: 31/05/2020 fix this :
        return new FastBible(books, bibleName);
    }
}
