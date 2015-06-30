package edu.hm.cs.fs.restapi.parser;

import java.io.IOException;
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
     * @param url to parse.
     */
	public AbstractHtmlParser(final String url) {
		this(url, 0);
	}
	
	public AbstractHtmlParser(final String url, final Integer interval) {
		super(url, interval);
	}

    @Override
    public List<T> read(final String url) {
        final List<T> result = new ArrayList<>();
        try {
            final Document document = Jsoup.parse(
                    new URL(url), // URL to parse
                    (int) TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS) // Timeout
            );
            result.addAll(readDoc(document));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO Log.e(getClass().getSimpleName(), "", e);
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
