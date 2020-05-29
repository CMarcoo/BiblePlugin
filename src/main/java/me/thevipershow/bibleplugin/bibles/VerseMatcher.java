package me.thevipershow.bibleplugin.bibles;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.thevipershow.bibleplugin.bibles.serialization.Pair;
import me.thevipershow.bibleplugin.exceptions.BibleException;

public final class VerseMatcher {
    private final String searchInput;

    public VerseMatcher(String searchInput) throws BibleException {
        final Pattern pattern = Pattern.compile("[0-9]+:[0-9]+");
        final Matcher matcher = pattern.matcher(searchInput);
        if (matcher.matches())
            this.searchInput = searchInput;
        else
            throw new BibleException("The verse has an invalid format");
    }

    public String getSearchInput() {
        return searchInput;
    }

    public Pair<Integer, Integer> getChapterVersePair() {
        final String[] strings = searchInput.split(":");
        final int[] ints = {Integer.parseUnsignedInt(strings[0]), Integer.parseUnsignedInt(strings[1])};
        return new Pair<>(ints[0], ints[1]);
    }

    /**
     * This method is used to know if a Bible can contain the specified verse and chapter
     * that have been specified
     *
     * @param bible the bible we should search into
     * @return true if the bible has that verse & chapter, false otherwise.
     */
    public boolean isValidInputInBible(Bible bible) {
        final Pair<Integer, Integer> chapterVersePair = getChapterVersePair();
        return bible.getTotalChapters() <= chapterVersePair.getA()
                && bible.getChapter(chapterVersePair.getA()).getTotalVerses() <= chapterVersePair.getB();
    }

    /**
     * Search for a verse in a bible using the current verse matcher data
     *
     * @param bible the bible we should search into
     * @return the verse if obtainable, otherwise an empty Optional.
     */
    public Optional<String> getValidVerse(Bible bible) {
        if (isValidInputInBible(bible)) {
            final Pair<Integer, Integer> pair = getChapterVersePair();
            return Optional.of(bible.getChapter(pair.getA()).getVerse(pair.getB()));
        }
        return Optional.empty();
    }
}
