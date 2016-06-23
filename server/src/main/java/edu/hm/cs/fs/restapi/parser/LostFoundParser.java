package edu.hm.cs.fs.restapi.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import edu.hm.cs.fs.common.model.LostFound;
import edu.hm.cs.fs.restapi.UrlHandler;
import edu.hm.cs.fs.restapi.UrlInfo;

/**
 * The things which gone lost and found. (Url: <a href="http://fi.cs.hm.edu/fi/rest/public/lostfound"
 * >http://fi.cs.hm.edu/fi/rest/public/lostfound</a>)
 *
 * @author Fabio
 */
public class LostFoundParser extends AbstractXmlParser<LostFound> {
    private static final UrlInfo INFO = UrlHandler.getUrlInfo(UrlHandler.Url.LOSTFOUND);

    private final DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public LostFoundParser() {
        super(INFO.getRequestUrl(), INFO.getRoot());
    }

    @Override
    public List<LostFound> onCreateItems(final String rootPath) throws Exception {
        //String id;
        String subject;
        Date date = null;

        // Parse Elements...
        //id = findByXPath(rootPath + "/id/text()", XPathConstants.STRING, String.class);
        subject = findByXPath(rootPath + "/subject/text()", XPathConstants.STRING, String.class);
        try {
            synchronized (DATE_FORMATTER) {
                date = DATE_FORMATTER.parse((String) findByXPath(rootPath + "/date/text()", XPathConstants.STRING, String.class));
            }
        } catch (Exception e) {
            date = new Date(); // Just if the parsing failed...
        }

        LostFound lostFound = new LostFound();
        //lostFound.setId(id);
        lostFound.setSubject(subject);
        lostFound.setDate(date);

        return Collections.singletonList(lostFound);
    }
}
