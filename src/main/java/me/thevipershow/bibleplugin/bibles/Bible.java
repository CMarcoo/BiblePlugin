package me.thevipershow.bibleplugin.bibles;

import java.util.List;
import me.thevipershow.bibleplugin.bibles.serialization.Chapter;

public interface Bible {
    int getTotalChapters();

    List<Chapter> getChapters();

    Chapter getChapter(int i);
}
