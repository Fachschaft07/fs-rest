package edu.hm.cs.fs.restapi.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * A parser for everything.
 *
 * @author Fabio
 */
public interface Parser<T> {
    /**
     * Parse the content and convert it to objects.
     *
     * @return a list with objects.
     * @throws IOException 
     * @throws MalformedURLException 
     */
    List<T> parse() throws MalformedURLException, IOException;
}
