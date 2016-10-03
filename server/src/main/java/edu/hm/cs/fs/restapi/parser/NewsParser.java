package edu.hm.cs.fs.restapi.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.xpath.XPathConstants;

import edu.hm.cs.fs.common.model.News;
import edu.hm.cs.fs.restapi.UrlHandler;
import edu.hm.cs.fs.restapi.UrlInfo;

/**
 * A modul can be choosen by a student. Some moduls are mandatory. (Url: <a
 * href="http://fi.cs.hm.edu/fi/rest/public/modul">http://fi.cs.hm.edu/fi/rest/ public/modul</a>)
 *
 * @author Fabio
 */
public class NewsParser extends AbstractXmlParser<News> {
    private static final UrlInfo INFO = UrlHandler.getUrlInfo(UrlHandler.Url.NEWS);

    private final DateFormat DATE_PARSER = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
    
    public NewsParser() {
        super(INFO.getRequestUrl(), INFO.getRoot());
    }

    @Override
    public List<News> onCreateItems(final String rootPath) throws Exception {
      
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
            synchronized (DATE_PARSER) {
                date = DATE_PARSER.parse(pubDate);
            }
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
