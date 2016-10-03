package edu.hm.cs.fs.restapi.parser;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.xml.xpath.XPathConstants;

import com.google.common.base.Strings;

import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Job;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import edu.hm.cs.fs.restapi.UrlHandler;
import edu.hm.cs.fs.restapi.UrlInfo;

/**
 * Url: <a href="http://fi.cs.hm.edu/fi/rest/public/job">http://fi.cs.hm.edu/fi/rest
 * /public/job</a>
 *
 * @author Fabio
 */
public class JobParser extends AbstractXmlParser<Job> implements ByIdParser<Job> {
    private static final UrlInfo INFO = UrlHandler.getUrlInfo(UrlHandler.Url.JOB);

    private final DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
    private final ByIdParser<Person> personParser;

    /**
     * Create a parser for job entries.
     */
    public JobParser(final ByIdParser<Person> personParser) {
        super(INFO.getRequestUrl(), INFO.getRoot());
        this.personParser = personParser;
    }

    @Override
    public List<Job> onCreateItems(final String rootPath) throws Exception {
        // Parse Elements
        final String jobId = findByXPath(rootPath + "/id/text()", XPathConstants.STRING, String.class);
        final String title = findByXPath(rootPath + "/title/text()", XPathConstants.STRING, String.class);
        final String provider = findByXPath(rootPath + "/provider/text()", XPathConstants.STRING, String.class);
        final String description = findByXPath(rootPath + "/description/text()", XPathConstants.STRING, String.class);
        final String contact = findByXPath(rootPath + "/contact/text()", XPathConstants.STRING, String.class);
        final String programStr = findByXPath(rootPath + "/program/text()", XPathConstants.STRING, String.class);
        Study program = null;
        if (!Strings.isNullOrEmpty(programStr)) {
            Group studyGroup = Group.of(programStr);
            if (studyGroup != null) {
                program = studyGroup.getStudy();
            }
        }
        Date expire;
        try {
            synchronized (dateParser){
                expire = dateParser.parse(findByXPath(rootPath + "/expire/text()", XPathConstants.STRING, String.class));
            }
        } catch (ParseException e) {
            expire = new Date();
        }
        final String url = findByXPath(rootPath + "/url/text()", XPathConstants.STRING, String.class);
        
        // Create object
        final Job job = new Job();
        job.setId(jobId);
        job.setTitle(title);
        job.setProvider(provider);
        job.setDescription(description);
        job.setProgram(program);
        personParser.getById(contact).map(SimplePerson::new).ifPresent(job::setContact);
        job.setExpire(expire);
        job.setUrl(url);

        return Collections.singletonList(job);
    }

    @Override
    public Optional<Job> getById(String itemId) throws Exception {
        try {
            return getAll().parallelStream()
                    .filter(item -> itemId.equalsIgnoreCase(item.getId()))
                    .findAny();
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
