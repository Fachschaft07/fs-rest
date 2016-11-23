package edu.hm.cs.fs.restapi.parser;

import com.google.common.base.Stopwatch;
import com.google.common.io.CharStreams;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * An abstract parser for json content.
 *
 * @author Fabio
 */
public abstract class AbstractJsonParser<T> extends AbstractContentParser<T> {
    private final static Logger LOG = Logger.getLogger(AbstractJsonParser.class);

    /**
     * Creates an abstract parser for json content.
     *
     * @param url to getAll.
     */
    public AbstractJsonParser(final String url) {
        super(url);
    }

    @Override
    public List<T> getAll() {
        final List<T> result = new ArrayList<>();
        try {
            final Stopwatch stopwatch = Stopwatch.createStarted();

            final URL source = new URL(getUrl());
            final HttpURLConnection conn = (HttpURLConnection) source.openConnection();
            conn.setReadTimeout(3000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            final String content = CharStreams.readLines(new InputStreamReader(conn.getInputStream()))
                    .stream()
                    .collect(Collectors.joining("\n"))
                    .trim();

            if(content.length() > 0) {
                if (content.charAt(0) == '[') {
                    result.addAll(convert(new JSONArray(content)));
                } else {
                    result.addAll(convert(new JSONObject(content)));
                }
            }

            stopwatch.stop();
            LOG.info("Requesting and parsing finished in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms on " + getUrl());
        } catch (Exception e) {
            LOG.error(e);
        }
        return result;
    }

    /**
     * Convert an json array to a list with objects.
     *
     * @param data to convert.
     * @return a list with objects.
     */
    public List<T> convert(final JSONArray data) {
        return new ArrayList<>();
    }

    /**
     * Convert an json object to a list with objects.
     *
     * @param data to convert.
     * @return a list with objects.
     */
    public List<T> convert(final JSONObject data) {
        return new ArrayList<>();
    }
}
