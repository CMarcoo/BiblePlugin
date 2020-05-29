package me.thevipershow.bibleplugin.downloaders;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import me.thevipershow.bibleplugin.exceptions.ExceptionHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class BibleDownloader {
    private static BibleDownloader instance = null;
    private final JavaPlugin plugin;
    private final File BIBLE_FOLDER;

    private BibleDownloader(JavaPlugin plugin) {
        this.plugin = plugin;
        this.BIBLE_FOLDER = new File(plugin.getDataFolder(), "bibles");
    }

    public static BibleDownloader getInstance(JavaPlugin javaPlugin) {
        return instance != null ? instance : (instance = new BibleDownloader(javaPlugin));
    }

    public void createBibleFolder(ExceptionHandler handler) {
        try {
            Files.createDirectories(BIBLE_FOLDER.toPath());
        } catch (final IOException e) {
            handler.handle(e);
        }
    }

    private String getBibleFileName(BibleURL bibleURL) {
        return BIBLE_FOLDER.getAbsolutePath() + File.separatorChar + bibleURL.name + ".json";
    }

    public void storeBibleJSON(BibleURL bibleURL, ExceptionHandler handler) {
        final String bibleFileName = getBibleFileName(bibleURL);
        if (Files.exists(Paths.get(bibleFileName))) {
            return;
        }
        try (final BufferedInputStream inputStream = new BufferedInputStream(new URL(bibleURL.URL).openStream());
             final FileOutputStream outputStream = new FileOutputStream(bibleFileName)) {
            final byte[] data = new byte[1024];
            int bytes;
            while ((bytes = inputStream.read(data, 0, 1024)) != -1)
                outputStream.write(data, 0, bytes);
        } catch (final IOException e) {
            handler.handle(e);
        }
    }

    public Optional<String> getBibleRawContent(BibleURL bibleURL, ExceptionHandler handler) {
        try {
            String rawContent = Files.lines(Paths.get(getBibleFileName(bibleURL))).collect(Collectors.joining());
            return Optional.of(rawContent);
        } catch (IOException e) {
            handler.handle(e);
        }
        return Optional.empty();
    }
}
