package edu.hm.cs.fs.restapi.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * An abstract parser for html content.
 *
 * @author Fabio
 */
public abstract class AbstractHtmlParser<T> extends AbstractContentParser<T> {
    /**
     * Creates an abstract parser for html content.
     *
     * @param url to getAll.
     */
    public AbstractHtmlParser(final String url) {
        super(url);
    }

    @Override
    public List<T> getAll() throws Exception {
        final List<T> result = new ArrayList<>();
        result.addAll(readDoc(getDocument()));
        return result;
    }

    /**
     * Read out the content from the document which is necessary for the objects.
     *
     * @param document to read the content from.
     * @return a list with objects.
     */
    public abstract List<T> readDoc(final Document document);

    /**
     * Read the document.
     *
     * @return an document
     */
    public Document getDocument() throws MalformedURLException, IOException{
        return Jsoup.parse(
                new URL(getUrl()), // URL to getAll
                (int) TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS) // Timeout
        );
    }
}
