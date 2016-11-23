package edu.hm.cs.fs.restapi.parser;

import com.google.common.base.Stopwatch;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * An abstract parser for html content.
 *
 * @author Fabio
 */
public abstract class AbstractHtmlParser<T> extends AbstractContentParser<T> {
    private final static Logger LOG = Logger.getLogger(AbstractHtmlParser.class);

    /**
     * Creates an abstract parser for html content.
     *
     * @param url to getAll.
     */
    public AbstractHtmlParser(final String url) {
        super(url);
    }

    @Override
    public List<T> getAll() {
        final List<T> result = new ArrayList<>();
        try {
            final Stopwatch stopwatch = Stopwatch.createStarted();

            final Document document = Jsoup.parse(
                    new URL(getUrl()), // URL to getAll
                    (int) TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS) // Timeout
            );
            result.addAll(readDoc(document));

            stopwatch.stop();
            LOG.info("Requesting and parsing finished in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms on " + getUrl());
        } catch (IOException e) {
            LOG.error(e);
        }
        return result;
    }

    /**
     * Read out the content from the document which is necessary for the objects.
     *
     * @param document to read the content from.
     * @return a list with objects.
     */
    public abstract List<T> readDoc(final Document document);
}
