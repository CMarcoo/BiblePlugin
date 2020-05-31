package deserialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;
import me.thevipershow.bibleplugin.downloaders.BibleURL;
import me.thevipershow.bibleplugin.exceptions.ExceptionHandler;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class BibleDeserializationTest {

    final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    final static File RESOURCE_FOLDER = new File("src/test/resources");

    private String getBibleFileName(BibleURL bibleURL) {
        return RESOURCE_FOLDER.getAbsolutePath() + File.separatorChar + bibleURL.name() + ".json";
    }

    public Optional<String> getBibleRawContent(BibleURL bibleURL, ExceptionHandler handler) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(getBibleFileName(bibleURL)))) {
                String currentLine;
                while ((currentLine = bufferedReader.readLine()) != null)
                    stringBuilder.append(currentLine).append("\n");
            }
            return Optional.of(stringBuilder.toString());
        } catch (final IOException e) {
            handler.handle(e);
        }
        return Optional.empty();
    }

    @Test
    public void testReadBible() {
        final Optional<String> optional = getBibleRawContent(BibleURL.BASIC_EN, Throwable::printStackTrace);
        assertThat(optional.isPresent(), is(true));
        assertThat(optional.get().isEmpty(), is(false));
        System.out.print(gson.toJson(optional.get()));
    }


}
