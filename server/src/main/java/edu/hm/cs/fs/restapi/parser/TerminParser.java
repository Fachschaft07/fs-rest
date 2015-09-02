package edu.hm.cs.fs.restapi.parser;

import org.jsoup.helper.StringUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import edu.hm.cs.fs.common.model.Termin;

/**
 * The appointments at the faculty 07. (Url: <a href="http://fi.cs.hm.edu/fi/rest/public/termin.xml"
 * >http://fi.cs.hm.edu/fi/rest/public/termin.xml</a>)
 *
 * @author Fabio
 */
public class TerminParser extends AbstractXmlParser<Termin> {
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/termin.xml";
    private static final String ROOT_NODE = "/terminlist/termin";

    private static final DateFormat DATE_PARSER = new SimpleDateFormat(
            "yyyy-MM-dd");

    public TerminParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<Termin> onCreateItems(final String rootPath) throws XPathExpressionException {
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
        if (!StringUtil.isBlank(dateStr)) {
            try {
                date = DATE_PARSER.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Termin termin = new Termin();
        termin.setId(id);
        termin.setSubject(subject);
        termin.setScope(scope);
        termin.setDate(date);

        return Collections.singletonList(termin);
    }
}
