package edu.hm.cs.fs.restapi.parser.cache;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.hm.cs.fs.restapi.parser.Parser;

/**
 * The CachedParser is a Delegator for every Parser to handle the caching.
 */
public abstract class CachedParser<T> implements Parser<T>, Runnable {

  public enum UpdateType {
    FIXEDTIME, INTERVAL
  }

  private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private final Parser<T> parser;
  private final File cacheFile;

  private Logger logger = Logger.getLogger(this.getClass());

  /**
   * Creates a cached parser with a specified interval.
   *
   * @param parser to handle the cache for.
   * @param interval of the new content fetching.
   * @param timeUnit of the interval.
   */
  protected CachedParser(final Parser<T> parser, final long interval, final TimeUnit timeUnit, final UpdateType updateType) {
    this(parser, parser.getClass().getSimpleName(), interval, timeUnit, updateType);
  }

  /**
   * Creates a cached parser with a specified interval.
   *
   * @param parser to handle the cache for.
   * @param cacheFileName name of the cache file.
   * @param interval of the new content fetching.
   * @param timeUnit of the interval.
   */
  protected CachedParser(final Parser<T> parser, String cacheFileName, final long interval,
      final TimeUnit timeUnit, final UpdateType updateType) {
    this.parser = parser;
    this.cacheFile = new File(new File(System.getProperty("java.io.tmpdir")), "fs_rest_" + cacheFileName + "_cache.json"); // Create a cache file in temp directory

    // register with updater
    switch (updateType) {
      case FIXEDTIME:
        CacheUpdater.scheduleAtFixedTime(this, interval);
        break;
      case INTERVAL:
        CacheUpdater.scheduleAtFixedInterval(this, interval, timeUnit);
        break;
    }
  }

  @Override
  public List<T> getAll() throws Exception {
    return readFromCache();
  }

  protected abstract Type getType();

  public void updateCache() throws Exception {
    logger.info("Start updating cache file for "+this.getClass().getSimpleName());
    List<T> result = parser.getAll();
    synchronized (cacheFile) {
      Files.write(gson.toJson(result, getType()), cacheFile, Charsets.UTF_8);
    }
    logger.info("Finished updating cache file for "+this.getClass().getSimpleName());
  }

  public void cleanUp() {
    logger.info("Cleaning up cache file for "+this.getClass().getSimpleName());
    if (cacheFile.exists()) {
      cacheFile.delete();
    }
  }

  @Override
  public void run() {
    try {
      updateCache();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  private List<T> readFromCache() throws Exception {
    if(!cacheFile.exists()) {
      updateCache();
    }
    synchronized (cacheFile) {
      try {
        // Read the cache file and reproduce the output
        return gson.fromJson(Files.toString(cacheFile, Charsets.UTF_8), getType());
      } catch(IOException e){
        return Collections.emptyList();
      }
    }
  }
}
