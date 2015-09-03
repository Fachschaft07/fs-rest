package edu.hm.cs.fs.restapi.v1;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.xpath.XPathExpressionException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.SimpleJob;
import edu.hm.cs.fs.common.model.util.ModelUtil;
import edu.hm.cs.fs.restapi.parser.JobParser;

/**
 * The JobController represents the interface to the REST-API. It has different methods to access
 * jobs and filter them. <p> Url: <a href="http://fi.cs.hm.edu/fi/rest/public/job">http://fi.cs.hm.edu/fi/rest/public/job</a>
 *
 * @author Fabio
 */
@RestController
public class JobController {

    /**
     * Requests all jobs from <a href="http://fi.cs.hm.edu/fi/rest/public/job">http://fi.cs.hm.edu/fi/rest/public/job</a>.
     * and filters them by ID or Search
     *
     * @param search for the words in the job title and description.
     * @param id     for the id of the job
     * @return a list with all matched jobs.
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws XPathExpressionException 
     */
    @RequestMapping("/rest/api/1/job")
    public List<SimpleJob> findJob(@RequestParam(value = "search", defaultValue = "") String search,
                             @RequestParam(value = "id", defaultValue = "") String id) throws MalformedURLException, IOException, XPathExpressionException {
        return ModelUtil.convert(new JobParser().parse().stream()
                .filter(job -> search == null || search.length() == 0 || (job.getTitle() + job.getDescription()).toLowerCase().contains(search.toLowerCase()))
                .filter(job -> id == null || id.length() == 0 || job.getId().equals(id))
                .collect(Collectors.toList()), SimpleJob.class);
    }
}