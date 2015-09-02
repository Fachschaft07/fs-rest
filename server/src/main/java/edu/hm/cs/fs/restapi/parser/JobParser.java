package edu.hm.cs.fs.restapi.parser;

import com.google.common.base.Strings;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.constant.StudyGroup;
import edu.hm.cs.fs.common.model.Job;
import edu.hm.cs.fs.restapi.parser.cache.CachedPersonParser;

/**
 * Url: <a href="http://fi.cs.hm.edu/fi/rest/public/job">http://fi.cs.hm.edu/fi/rest
 * /public/job</a>
 *
 * @author Fabio
 */
public class JobParser extends AbstractXmlParser<Job> {
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/job.xml";
    private static final String ROOT_NODE = "/joblist/job";
    private final DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Create a parser for job entries.
     */
    public JobParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<Job> onCreateItems(final String rootPath) throws XPathExpressionException {
        // Parse Elements
        final String jobId = findByXPath(rootPath + "/id/text()", XPathConstants.STRING, String.class);
        final String title = findByXPath(rootPath + "/title/text()", XPathConstants.STRING, String.class);
        final String provider = findByXPath(rootPath + "/provider/text()", XPathConstants.STRING, String.class);
        final String description = findByXPath(rootPath + "/description/text()", XPathConstants.STRING, String.class);
        final String contact = findByXPath(rootPath + "/contact/text()", XPathConstants.STRING, String.class);
        final String programStr = findByXPath(rootPath + "/program/text()", XPathConstants.STRING, String.class);
        Study program = null;
        if (!Strings.isNullOrEmpty(programStr)) {
            StudyGroup studyGroup = StudyGroup.of(programStr);
            if (studyGroup != null) {
                program = studyGroup.getStudy();
            }
        }
        Date expire;
        try {
            expire = dateParser.parse(findByXPath(rootPath + "/expire/text()", XPathConstants.STRING, String.class));
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
        new CachedPersonParser().findByIdSimple(contact).ifPresent(job::setContact);
        job.setExpire(expire);
        job.setUrl(url);

        return Collections.singletonList(job);
    }
}
