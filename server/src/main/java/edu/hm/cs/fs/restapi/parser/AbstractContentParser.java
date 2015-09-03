package edu.hm.cs.fs.restapi.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

/**
 * An very abstract parser for every type of web content parsing.
 *
 * @author Fabio
 */
public abstract class AbstractContentParser<T> implements Parser<T> {
    /**
     * The url to parse.
     */
    private final String mUrl;

    /**
     * Creates an abstract content parser.
     *
     * @param url to parse.
     */
    public AbstractContentParser(final String url) {
        this.mUrl = url;
    }

    /**
     * Downloads and reads the content from the web if a connection is available. Otherwise the
     * offline file will be read.
     *
     * @return a list with the content.
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws XPathExpressionException 
     */
    public final List<T> parse() throws MalformedURLException, IOException, XPathExpressionException {
        return read(mUrl);
    }

    /**
     * Reads the content from the specified url and parse it to objects.
     *
     * @param url to parse.
     * @return the objects in a list.
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws XPathExpressionException 
     */
    public abstract List<T> read(String url) throws MalformedURLException, IOException, XPathExpressionException;
}
