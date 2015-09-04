package edu.hm.cs.fs.restapi.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import edu.hm.cs.fs.common.model.News;

/**
 * A modul can be choosen by a student. Some moduls are mandatory. (Url: <a
 * href="http://fi.cs.hm.edu/fi/rest/public/modul">http://fi.cs.hm.edu/fi/rest/ public/modul</a>)
 *
 * @author Fabio
 */
public class NewsParser extends AbstractXmlParser<News> {
    private static final String URL = "http://fs.cs.hm.edu/category/news/feed/";
    private static final String ROOT_NODE = "/rss/channel/item";
    
    private final DateFormat DATE_PARSER = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
    
    public NewsParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<News> onCreateItems(final String rootPath) throws XPathExpressionException, MalformedURLException, IOException {
      
        String title;
        String link;
        String pubDate;
        Date date;
        String description;

        // Parse Elements...
        title = findByXPath(rootPath + "/title/text()",
                XPathConstants.STRING, String.class);
        
        link = findByXPath(rootPath + "/link/text()",
                XPathConstants.STRING, String.class);
        
        pubDate = findByXPath(rootPath + "/pubDate/text()",
            XPathConstants.STRING, String.class);
        
        try {
          date = DATE_PARSER.parse(pubDate);
        } catch (ParseException e) {
          date = null;
        }
        
        description = findByXPath(rootPath + "/description/text()",
                XPathConstants.STRING, String.class);

        News news = new News();
        news.setTitle(title);
        news.setLink(link);
        news.setDate(date);
        news.setDescription(description);

        return Collections.singletonList(news);
    }
}
