package me.thevipershow.bibleplugin.bibles;

import java.io.File;
import java.util.ArrayList;

public interface Bible {
    File getBibleFile();

    String getBibleName();

    int getTotalVerses();

    ArrayList<String> getAllVerses();
}
