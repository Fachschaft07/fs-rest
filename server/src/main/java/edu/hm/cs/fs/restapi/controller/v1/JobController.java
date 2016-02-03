package edu.hm.cs.fs.restapi.controller.v1;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.Job;
import edu.hm.cs.fs.common.model.simple.SimpleJob;
import edu.hm.cs.fs.restapi.parser.JobParser;
import edu.hm.cs.fs.restapi.parser.PersonParser;

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
     * and filters them by Search
     *
     * @param search for the words in the job title and description.
     * @return a list with all matched jobs.
     * @throws Exception
     */
    @RequestMapping("/rest/api/1/jobs")
    public List<SimpleJob> getJobs(@RequestParam(value = "search", defaultValue = "") String search) throws Exception {
        return new JobParser(new PersonParser()).getAll().stream()
                .filter(job -> search == null || search.length() == 0 || (job.getTitle() + job.getDescription()).toLowerCase().contains(search.toLowerCase()))
                .map(SimpleJob::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Requests all jobs from <a href="http://fi.cs.hm.edu/fi/rest/public/job">http://fi.cs.hm.edu/fi/rest/public/job</a>.
     * and filters them by ID or Search
     *
     * @param id     for the id of the job
     * @return a list with all matched jobs.
     * @throws Exception
     */
    @RequestMapping("/rest/api/1/job")
    public Job getJobById(@RequestParam(value = "id") String id) throws Exception {
        return new JobParser(new PersonParser())
                .getById(id)
                .orElseThrow(() -> new IllegalStateException("No module found with id '" + id + "'."));
    }
}