package edu.hm.cs.fs.restapi.parser;

import com.google.common.base.Strings;
import edu.hm.cs.fs.common.model.Termin;
import org.jsoup.helper.StringUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import edu.hm.cs.fs.common.model.Event;

/**
 * The appointments at the faculty 07. (Url: <a href="http://fi.cs.hm.edu/fi/rest/public/termin.xml"
 * >http://fi.cs.hm.edu/fi/rest/public/termin.xml</a>)
 *
 * @author Fabio
 */
public class TerminParser extends AbstractXmlParser<Termin> {
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/termin.xml";
    private static final String ROOT_NODE = "/terminlist/termin";

    public TerminParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<Termin> onCreateItems(final String rootPath) throws Exception {
        String id;
        String subject;
        String scope;
        Date date = null;

        // Parse Elements...
        id = findByXPath(rootPath + "/id/text()",
                XPathConstants.STRING, String.class);
        subject = findByXPath(rootPath + "/subject/text()",
                XPathConstants.STRING, String.class);
        scope = findByXPath(rootPath + "/scope/text()", XPathConstants.STRING, String.class);
        final String dateStr = findByXPath(rootPath + "/date/text()",
                XPathConstants.STRING, String.class);
        if (!Strings.isNullOrEmpty(dateStr)) {
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            } catch (ParseException e) {
                throw new Exception(e);
            }
        }

        Termin event = new Termin();
        event.setId(id);
        event.setTitle(subject);
        event.setScope(scope);
        event.setDate(date);

        return Collections.singletonList(event);
    }
}
