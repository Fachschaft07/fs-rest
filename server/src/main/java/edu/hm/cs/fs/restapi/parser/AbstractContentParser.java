package edu.hm.cs.fs.restapi.parser;

import java.util.List;

/**
 * An very abstract parser for every type of web content parsing.
 *
 * @author Fabio
 */
public abstract class AbstractContentParser<T> implements Parser<T> {
    /**
     * The url to getAll.
     */
    private final String url;

    /**
     * Creates an abstract content parser.
     *
     * @param url to getAll.
     */
    public AbstractContentParser(final String url) {
        this.url = url;
    }

    /**
     * Downloads and reads the content from the web if a connection is available. Otherwise the
     * offline file will be read.
     *
     * @return a list with the content.
     */
    public abstract List<T> getAll();

    /**
     * Get the url the parser should getAll the content from.
     *
     * @return the url.
     */
    public String getUrl() {
        return url;
    }
}
