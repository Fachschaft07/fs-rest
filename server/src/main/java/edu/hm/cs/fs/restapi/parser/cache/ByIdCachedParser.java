package edu.hm.cs.fs.restapi.parser.cache;

import edu.hm.cs.fs.restapi.parser.ByIdParser;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Fabio
 */
public abstract class ByIdCachedParser<T> extends CachedParser<T> implements ByIdParser<T> {
    /**
     * Creates a cached parser with a specified interval.
     *
     * @param parser   to handle the cache for.
     * @param interval of the new content fetching.
     * @param timeUnit of the interval.
     */
    protected ByIdCachedParser(final ByIdParser<T> parser, final long interval, final TimeUnit timeUnit, final UpdateType updateType) {
        super(parser, interval, timeUnit, updateType);
    }

    @Override
    public Optional<T> getById(String itemId) {
        return getAll().parallelStream()
                .filter(item -> itemId.equalsIgnoreCase(getId(item)))
                .findAny();
    }

    /**
     * @param item
     * @return
     */
    protected abstract String getId(final T item);
}
