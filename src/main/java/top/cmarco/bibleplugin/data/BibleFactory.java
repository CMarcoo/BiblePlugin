package top.cmarco.bibleplugin.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.cmarco.bibleplugin.commands.BibleGuard;
import top.cmarco.bibleplugin.exceptions.BibleException;

public final class BibleFactory {
    public static BibleFactory instance = null;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Bible.class, Bible.bibleDeserializer).create();

    public static BibleFactory getInstance() {
        return instance != null ? instance : (instance = new BibleFactory());
    }

    /**
     * Instantiate a Bible from a JSON String of valid format.
     *
     * @param json      The JSON Bible.
     * @param bibleName The name of the bible.
     * @return A Bible object.
     */
    public Bible createBible(String json, String bibleName) throws BibleException {
        Bible bible = gson.fromJson(json, Bible.class);
        bible.setName(bibleName);
        return BibleGuard.validateBible(bible);
    }
}
