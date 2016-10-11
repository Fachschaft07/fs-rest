package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.model.LostFound;
import org.apache.log4j.Logger;

import javax.xml.xpath.XPathConstants;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The things which gone lost and found. (Url: <a href="http://fi.cs.hm.edu/fi/rest/public/lostfound"
 * >http://fi.cs.hm.edu/fi/rest/public/lostfound</a>)
 *
 * @author Fabio
 */
public class LostFoundParser extends AbstractXmlParser<LostFound> {
    private final static Logger LOG = Logger.getLogger(LostFoundParser.class);
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/lostfound.xml";
    private static final String ROOT_NODE = "/lostfoundlist/lostfound";

    public LostFoundParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<LostFound> onCreateItems(final String rootPath) {
        final List<LostFound> result = new ArrayList<>();

        // Parse Elements...
        //id = findString(rootPath + "/id/text()");
        final Optional<String> subject = findString(rootPath + "/subject/text()");
        final Date date = findString(rootPath + "/date/text()").map(tmp -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(tmp);
            } catch (ParseException e) {
                LOG.warn(e);
                return null;
            }
        }).orElse(new Date());

        if(subject.isPresent()) {
            LostFound lostFound = new LostFound();
            //lostFound.setId(id);
            lostFound.setSubject(subject.get());
            lostFound.setDate(date);
            result.add(lostFound);
        }

        return result;
    }
}
