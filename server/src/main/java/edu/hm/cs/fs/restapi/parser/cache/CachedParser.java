package edu.hm.cs.fs.restapi.parser.cache;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.hm.cs.fs.restapi.parser.Parser;

/**
 * The CachedParser is a Delegator for every Parser to handle the caching.
 */
public abstract class CachedParser<T> implements Parser<T> {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Parser<T> parser;
    private final long intervalInMin;
    private final File cacheFile;
    private Parser<T> parserAll;

    /**
     * Creates a cached parser with a specified interval.
     *
     * @param parser
     *         to handle the cache for.
     * @param interval
     *         of the new content fetching.
     */
    public CachedParser(final Parser<T> parser, final long interval, final TimeUnit timeUnit) {
        this.parser = parser;
        this.intervalInMin = TimeUnit.MINUTES.convert(interval, timeUnit);
        // Create a cache file in temp directory
        final File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        cacheFile = new File(tmpDir, "fs_rest_" + parser.getClass().getSimpleName() + "_cache.json");
        if (!cacheFile.exists()) {
            writeToCache(parser.parse());
        }
    }

    @Override
    public List<T> parse() {
        final List<T> cache;
        if (isUpToDate()) {
            cache = readFromCache();
        } else {
            cache = updateCache();
        }
        return cache;
    }

    public abstract Type getType();

    private boolean isUpToDate() {
        final Calendar lastModified = Calendar.getInstance();
        lastModified.setTimeInMillis(cacheFile.lastModified());
        lastModified.add(Calendar.MINUTE, (int) intervalInMin);

        return Calendar.getInstance().before(lastModified);
    }

    private List<T> readFromCache() {
        try {
            // Read the cache file and reproduce the output
            return gson.fromJson(Files.toString(cacheFile, Charsets.UTF_8), getType());
        } catch (IOException e) {
            // Something went wrong? --> Reproduce the cache file and go on!
            return updateCache();
        }
    }

    private List<T> updateCache() {
        writeToCache(parserAll.parse());
        return parser.parse();
    }

    private void writeToCache(List<T> list) {
        // Write the data into a file in json format
        try {
            Files.write(gson.toJson(list, getType()), cacheFile, Charsets.UTF_8);
        } catch (IOException ignored) {
        }
    }
}
