package me.thevipershow.bibleplugin.bibles;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import me.thevipershow.bibleplugin.bibles.serialization.BibleImplementation;
import me.thevipershow.bibleplugin.exceptions.BibleException;

public abstract class AbstractBible implements Bible {
    private final ArrayList<String> verses;

    protected AbstractBible(BibleImplementation implementation) throws IOException, BibleException {
        if (implementation == null) {
            throw new BibleException("Bible cannot be null.");
        } else {

        }
    }
}
