package me.thevipershow.bibleplugin.data;

import me.thevipershow.bibleplugin.obtainer.FastBible;
import me.thevipershow.bibleplugin.obtainer.ParallelStreamBible;
import me.thevipershow.bibleplugin.obtainer.StreamBible;

import java.util.List;

public enum BibleType {
    STREAM(StreamBible.class, "stream"), PARALLEL_STREAM(ParallelStreamBible.class, "parallel-stream"), FAST(FastBible.class, "fast");

    private final Class<? extends Bible> bibleImpl;
    private final String name;

    BibleType(Class<? extends Bible> bibleImpl, String name) {
        this.bibleImpl = bibleImpl;
        this.name = name;
    }

    public final Bible build(final List<Book> books) {
        try {
            return bibleImpl.getConstructor(List.class).newInstance(books);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final Class<? extends Bible> getBibleImpl() {
        return bibleImpl;
    }

    public final String getName() {
        return name;
    }
}
