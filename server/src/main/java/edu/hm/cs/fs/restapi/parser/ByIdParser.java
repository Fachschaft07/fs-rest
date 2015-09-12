package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.restapi.parser.Parser;

import java.util.Optional;

/**
 * @author Fabio
 */
public interface ByIdParser<T> extends Parser<T> {
    /**
     * Get an item by the id.
     *
     * @param itemId to get the item of.
     * @return the item.
     * @throws Exception
     */
    Optional<T> getById(final String itemId) throws Exception;
}
