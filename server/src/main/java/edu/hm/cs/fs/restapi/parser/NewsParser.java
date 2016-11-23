package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.model.News;
import org.apache.log4j.Logger;

import javax.xml.xpath.XPathConstants;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A modul can be choosen by a student. Some moduls are mandatory. (Url: <a
 * href="http://fi.cs.hm.edu/fi/rest/public/modul">http://fi.cs.hm.edu/fi/rest/ public/modul</a>)
 *
 * @author Fabio
 */
public class NewsParser extends AbstractXmlParser<News> {
    private final static Logger LOG = Logger.getLogger(NewsParser.class);
    private static final String URL = "http://fs.cs.hm.edu/category/news/feed/";
    private static final String ROOT_NODE = "/rss/channel/item";

    private final DateFormat DATE_PARSER = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);

    public NewsParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<News> onCreateItems(final String rootPath) {
        final List<News> result = new ArrayList<>();

        // Parse Elements...
        final Optional<String> title = findString(rootPath + "/title/text()");
        final String link = findString(rootPath + "/link/text()").orElse("");
        final Date pubDate = findString(rootPath + "/pubDate/text()").map(tmp -> {
            try {
                return DATE_PARSER.parse(tmp);
            } catch (ParseException e) {
                LOG.warn(e);
                return null;
            }
        }).orElse(new Date());
        final String description = findString(rootPath + "/description/text()").orElse("");

        if(title.isPresent()) {
            News news = new News();
            news.setTitle(title.get());
            news.setLink(link);
            news.setDate(pubDate);
            news.setDescription(description);
            result.add(news);
        }

        return result;
    }
}
