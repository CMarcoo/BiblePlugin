package top.cmarco.bibleplugin.data;

import java.util.List;

public record Chapter(List<Verse> verses, int number) implements Numbered {
}
