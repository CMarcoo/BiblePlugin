package top.cmarco.bibleplugin.commands;

import java.util.UUID;
import top.cmarco.bibleplugin.data.Bible;
import top.cmarco.bibleplugin.data.Book;
import top.cmarco.bibleplugin.data.Chapter;
import top.cmarco.bibleplugin.data.Verse;
import top.cmarco.bibleplugin.exceptions.BibleException;

public class PlayerBibleData {
    private Bible currentBible;
    private Book currentBook;
    private Chapter currentChapter;
    private Verse currentVerse;

    private boolean loginverse = true;

    public PlayerBibleData() {}

    public PlayerBibleData(UUID uuid, Bible currentBible, Book currentBook, Chapter currentChapter, Verse currentVerse) {
        this.currentBible = currentBible;
        this.currentBook = currentBook;
        this.currentChapter = currentChapter;
        this.currentVerse = currentVerse;
    }

    /**
     * Updates a section of the PlayerBibleData.
     * If the Object doesn't respect the field's type
     * a BibleException will be thrown.
     * @param bibleSection The BibleSection to update.
     * @param o The Object.
     */
    public final void setSection(BibleSection bibleSection, Object o) throws BibleException {
        if (!bibleSection.isValidObject(o)) {
            throw new BibleException("An illegal object has been tried to be inserted into this data" +
                    "\nThe Object passed was of type " + o.getClass().getSimpleName() + " , but the required was " + bibleSection.getRequiredType().getName());
        }
        switch (bibleSection) {
            case BIBLE:
                setCurrentBible((Bible) o);
                break;
            case BOOK:
                setCurrentBook((Book) o);
                break;
            case CHAPTER:
                setCurrentChapter((Chapter) o);
                break;
            case VERSE:
                setCurrentVerse((Verse) o);
                break;
            default:
                throw new IllegalArgumentException("Unknown BibleSection type has been passed.");
        }
    }

    /**
     * Get the current Bible selected in this data.
     * @return The Bible. Null if none is selected.
     */
    public final Bible getCurrentBible() {
        return currentBible;
    }

    /**
     * Sets the current selected Bible.
     * @param currentBible A Bible.
     */
    public final void setCurrentBible(Bible currentBible) {
        this.currentBible = currentBible;
    }

    /**
     * Get the current Book selected in this data.
     * @return The book. Null if none is selected.
     */
    public final Book getCurrentBook() {
        return currentBook;
    }

    /**
     * Set the current selected book.
     * @param currentBook A book.
     */
    public final void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;
    }

    /**
     * Get the current chapter selected in this data.
     * @return The chapter. Null if none is selected.
     */
    public final Chapter getCurrentChapter() {
        return currentChapter;
    }

    /**
     * Set the current selected chapter.
     * @param currentChapter A chapter.
     */
    public final void setCurrentChapter(Chapter currentChapter) {
        this.currentChapter = currentChapter;
    }

    /**
     * Get the current verse selected in this data.
     * @return The chapter. Null if none is selected.
     */
    public final Verse getCurrentVerse() {
        return currentVerse;
    }

    /**
     * Set the current selected verse.
     * @param currentVerse A verse.
     */
    public final void setCurrentVerse(Verse currentVerse) {
        this.currentVerse = currentVerse;
    }

    public boolean isLoginverse() {
        return loginverse;
    }

    public void setLoginverse(boolean loginverse) {
        this.loginverse = loginverse;
    }
}
