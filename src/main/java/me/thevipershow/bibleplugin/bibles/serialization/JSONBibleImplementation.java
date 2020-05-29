package me.thevipershow.bibleplugin.bibles.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class JSONBibleImplementation extends AbstractBibleImplementation {

    public static Pair<List<Chapter>, String> deserializeBible(String bibleJson) {
        final Gson gson = new Gson();
        final JsonObject jsonObject = new JsonParser().parse(bibleJson).getAsJsonObject();
        final JsonElement chapterElement = jsonObject.get("chapters");
        final Type chaptersType = new TypeToken<List<List<String>>>() {}.getType();
        final List<List<String>> chapters = gson.fromJson(chapterElement, chaptersType);
        final List<Chapter> actualChapters = new ArrayList<>();
        chapters.listIterator().forEachRemaining(c -> actualChapters.add(new Chapter(c)));
        final JsonElement abbrevElement = jsonObject.get("abbrev");
        final String actualAbbreviation = abbrevElement.getAsString();
        return new Pair<>(actualChapters, actualAbbreviation);
    }

    public JSONBibleImplementation(final String bibleJson) {
        super(deserializeBible(bibleJson));
    }


    @Override
    public String getAbbreviation() {
        return super.abbreviation;
    }

    @Override
    public List<Chapter> getChapters() {
        return super.chapters;
    }
}
