package me.thevipershow.bibleplugin.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.thevipershow.bibleplugin.obtainer.FastBible;

public abstract class Bible {

    final static JsonDeserializer<Bible> bibleDeserializer = (json, typeOfT, context) -> {
        JsonArray booksArray = json.getAsJsonArray();
        List<Book> booksList = new ArrayList<>();

        for (JsonElement jsonElement : booksArray) {
            List<Chapter> chapterList = new ArrayList<>();

            JsonObject bookObj = jsonElement.getAsJsonObject();
            String bookName = bookObj.get("name").getAsString();
            String bookAbbreviation = bookObj.get("abbrev").getAsString();
            JsonArray chaptersArray = bookObj.get("chapters").getAsJsonArray();
            for (JsonElement element : chaptersArray) {
                List<Verse> verseList = new ArrayList<>();

                JsonArray pagesArray = element.getAsJsonArray();
                for (JsonElement verseElement : pagesArray) {
                    verseList.add(new Verse(verseElement.getAsString()));
                }
                chapterList.add(new Chapter(verseList));
            }
            booksList.add(new Book(bookAbbreviation, bookName, chapterList));
        }
        return new FastBible(booksList);
    };

    private final List<Book> books;
    private transient String name;

    public Bible(List<Book> books, String name) {
        this.books = books;
        this.name = name;
    }

    public Bible(List<Book> books) {
        this.books = books;
    }

    public void setName(String name) {
        this.name = name;
    }

    public final List<Book> getBooks() {
        return books;
    }

    public final String getName() {
        return name;
    }

    /**
     * Try to get a book in this bible by its name.
     *
     * @param bookName The name of the book.
     * @return An Optional with the book if it was found, an empty Optional otherwise.
     */
    public Optional<Book> getBook(String bookName) {
        for (final Book book : books)
            if (book.name.equalsIgnoreCase(bookName))
                return Optional.of(book);
        return Optional.empty();
    }

    /**
     * Try to get a chapter of this Bible from the book,
     * and the number of the chapter.
     *
     * @param book          The book.
     * @param chapterNumber The number of the chapter.
     * @return An Optional with the chapter if it was found, an empty Optional otherwise.
     */
    public Optional<Chapter> getChapter(Book book, int chapterNumber) {
        if (chapterNumber > 0 && chapterNumber <= book.getChapters().size())
            return Optional.of(book.getChapters().get(chapterNumber - 1));
        return Optional.empty();
    }

    /**
     * Try to get a chapter of this Bible from the name of the book,
     * and the number of the chapter.
     *
     * @param bookName      The name of the book.
     * @param chapterNumber The number of the chapter
     * @return An Optional with the chapter if it was found, an empty Optional otherwise.
     */
    public Optional<Chapter> getChapter(String bookName, int chapterNumber) {
        final Optional<Book> book = getBook(bookName);
        if (book.isPresent())
            return getChapter(book.get(), chapterNumber);
        return Optional.empty();
    }

    /**
     * Try to get a verse of this Bible from a chapter and the number of the verse.
     *
     * @param chapter     The chapter were the verse will be searched.
     * @param verseNumber The number of the verse.
     * @return An Optional with the verse if it was found, an empty Optional otherwise.
     */
    public Optional<Verse> getVerse(Chapter chapter, int verseNumber) {
        if (chapter.getVerses().size() <= verseNumber)
            return Optional.of(chapter.getVerses().get(verseNumber - 1));
        return Optional.empty();
    }

    /**
     * Try to get a verse of this Bible from the name of the book,
     * the number of the chapter and the number of the verse.
     *
     * @param bookName      The name of the book.
     * @param chapterNumber The number of the chapter the verse should be in.
     * @param verseNumber   The number of the verse.
     * @return An Optional with the verse if it was found, an empty Optional otherwise.
     */
    public Optional<Verse> getVerse(String bookName, int chapterNumber, int verseNumber) {
        final Optional<Book> book = getBook(bookName);
        if (book.isPresent()) {
            final Optional<Chapter> chapter = getChapter(book.get(), chapterNumber);
            if (chapter.isPresent())
                return getVerse(chapter.get(), verseNumber);
        }
        return Optional.empty();
    }

    /**
     * Find how many times a word can be found in a Bible.
     *
     * @param bible The Bible.
     * @param word  The word\phrase.
     * @return The number of occurrences.
     */
    public abstract long findWordOccurrences(Bible bible, String word);

    /**
     * Find how many times a word can be found in a book.
     *
     * @param book The book.
     * @param word The word\phrase.
     * @return The number of occurrences.
     */
    public abstract long findWordOccurrences(Book book, String word);

    /**
     * Find how many times a word can be found in a chapter.
     *
     * @param chapter The chapter.
     * @param word    The word\phrase.
     * @return The number of occurrences.
     */
    public abstract long findWordOccurrences(Chapter chapter, String word);

    /**
     * Find how many times a word can be found in a verse.
     *
     * @param verse The chapter.
     * @param word  The word\phrase.
     * @return The number of occurrences.
     */
    public abstract long findWordOccurrences(Verse verse, String word);

    /**
     * Find all the verses that contain a given word or phrase in a given Bible.
     *
     * @param word  A word or phrase.
     * @param bible The bible were the research will be performed
     * @return a List that will contain a list of verses if found, an empty List otherwise.
     */
    public abstract List<Verse> findVerseContainingWord(Bible bible, String word);

    /**
     * Find all the verses that contain a given word or phrase in a given book.
     *
     * @param word A word or phrase.
     * @param book The book were the research will be performed
     * @return a List that will contain a list of verses if found, an empty List otherwise.
     */
    public abstract List<Verse> findVerseContainingWord(Book book, String word);

    /**
     * Find all the verses that contain a given word or phrase in a given chapter.
     *
     * @param word    A word or phrase.
     * @param chapter The chapter were the research will be performed
     * @return a List that will contain a list of verses if found, an empty List otherwise.
     */
    public abstract List<Verse> findVerseContainingWord(Chapter chapter, String word);

    /**
     * Search for a book using its name in a Bible.
     *
     * @param bible The Bible.
     * @param name  The name of the book.
     * @return a List that will contain the Book with the specified name if found, an empty List otherwise
     */
    public abstract Optional<Book> findBook(Bible bible, String name);
}
