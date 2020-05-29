package me.thevipershow.bibleplugin.bibles;

import java.io.File;
import java.util.ArrayList;
import me.thevipershow.bibleplugin.bibles.serialization.Chapter;

public interface Bible {
    File getBibleFile();

    String getBibleName();

    int getTotalChapters();

    ArrayList<Chapter> getChapters();

    Chapter getChapter(int i);
}
