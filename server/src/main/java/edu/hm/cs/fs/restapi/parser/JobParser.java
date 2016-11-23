package edu.hm.cs.fs.restapi.parser;

import com.google.common.base.Strings;
import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Job;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import org.apache.log4j.Logger;

import javax.xml.xpath.XPathConstants;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Url: <a href="http://fi.cs.hm.edu/fi/rest/public/job">http://fi.cs.hm.edu/fi/rest
 * /public/job</a>
 *
 * @author Fabio
 */
public class JobParser extends AbstractXmlParser<Job> implements ByIdParser<Job> {
    private final static Logger LOG = Logger.getLogger(JobParser.class);
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/job.xml";
    private static final String ROOT_NODE = "/joblist/job";
    private final ByIdParser<Person> personParser;

    /**
     * Create a parser for job entries.
     */
    public JobParser(final ByIdParser<Person> personParser) {
        super(URL, ROOT_NODE);
        this.personParser = personParser;
    }

    @Override
    public List<Job> onCreateItems(final String rootPath) {
        final List<Job> result = new ArrayList<>();

        // Parse Elements
        final Optional<String> jobId = findString(rootPath + "/id/text()");
        final Optional<String> title = findString(rootPath + "/title/text()");
        final Optional<String> provider = findString(rootPath + "/provider/text()");
        final Optional<String> description = findString(rootPath + "/description/text()");
        final Optional<String> contact = findString(rootPath + "/contact/text()");
        final Study program = findString(rootPath + "/program/text()")
                .map(Group::of).map(Group::getStudy).orElse(null);
        final Date expire = findString(rootPath + "/expire/text()").map(tmp -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(tmp);
            } catch (ParseException e) {
                LOG.error(e);
                return null;
            }
        }).orElse(new Date());
        final String url = findString(rootPath + "/url/text()").orElse("");

        if(jobId.isPresent() && title.isPresent() && provider.isPresent() && description.isPresent() && contact.isPresent()) {
            // Create object
            final Job job = new Job();
            job.setId(jobId.get());
            job.setTitle(title.get());
            job.setProvider(provider.get());
            job.setDescription(description.get());
            job.setProgram(program);
            personParser.getById(contact.get()).map(SimplePerson::new).ifPresent(job::setContact);
            job.setExpire(expire);
            job.setUrl(url);

            result.add(job);
        }

        return result;
    }

    @Override
    public Optional<Job> getById(String itemId) {
        return getAll().parallelStream()
                .filter(item -> itemId.equalsIgnoreCase(item.getId()))
                .findAny();
    }
}
