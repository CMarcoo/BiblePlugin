package me.thevipershow.bibleplugin.data;

import java.util.List;

public record Book(String abbrev, String name, List<Chapter> chapters, int number) implements Numbered {
}
