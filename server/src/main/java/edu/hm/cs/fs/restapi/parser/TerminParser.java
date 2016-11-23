package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.model.Termin;
import org.apache.log4j.Logger;

import javax.xml.xpath.XPathConstants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The appointments at the faculty 07. (Url: <a href="http://fi.cs.hm.edu/fi/rest/public/termin.xml"
 * >http://fi.cs.hm.edu/fi/rest/public/termin.xml</a>)
 *
 * @author Fabio
 */
public class TerminParser extends AbstractXmlParser<Termin> {
    private final static Logger LOG = Logger.getLogger(TerminParser.class);
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/termin.xml";
    private static final String ROOT_NODE = "/terminlist/termin";

    public TerminParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<Termin> onCreateItems(final String rootPath) {
        final List<Termin> result = new ArrayList<>();

        // Parse Elements...
        final Optional<String> id = findString(rootPath + "/id/text()");
        final Optional<String> subject = findString(rootPath + "/subject/text()");
        final String scope = findString(rootPath + "/scope/text()").orElse("");
        final Optional<Date> date = findString(rootPath + "/date/text()")
                .map(tmp -> {
                    try {
                        return new SimpleDateFormat("yyyy-MM-dd").parse(tmp);
                    } catch (ParseException e) {
                        LOG.error(e);
                        return null;
                    }
                });

        if (id.isPresent() && subject.isPresent() && date.isPresent()) {
            Termin event = new Termin();
            event.setId(id.get());
            event.setTitle(subject.get());
            event.setScope(scope);
            event.setDate(date.get());

            result.add(event);
        }

        return result;
    }
}
