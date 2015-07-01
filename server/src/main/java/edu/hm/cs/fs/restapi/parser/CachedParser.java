package edu.hm.cs.fs.restapi.parser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The CachedParser is a Delegator for every Parser to handle the caching.
 */
public class CachedParser<T> implements Parser<T> {
    private Parser<T> parser;
    private final long intervalInMilli;
    private Date nextPoll;
    private List<T> cache;

    /**
     * Creates a cached parser with a specified interval.
     *
     * @param parser to handle the cache for.
     * @param intervalInMilli of the new content fetching.
     */
    public CachedParser(Parser<T> parser, long intervalInMilli) {
        this.parser = parser;
        this.intervalInMilli = intervalInMilli;
    }

    @Override
    public List<T> parse() {
        final Calendar now = Calendar.getInstance();
        if (cache == null || now.after(nextPoll)) {
            cache = parser.parse();
            now.add(Calendar.MILLISECOND, (int) intervalInMilli);
            nextPoll = now.getTime();
        }
        return cache;
    }
}
