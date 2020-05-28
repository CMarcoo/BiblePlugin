package me.thevipershow.bibleplugin.bibles.serialization;

import java.util.List;

public interface BibleImplementation {
    String getAbbreviation();

    List<Chapter> getChapters();
}
