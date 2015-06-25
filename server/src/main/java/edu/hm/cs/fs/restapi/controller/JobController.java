package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import edu.hm.cs.fs.common.model.Job;
import edu.hm.cs.fs.restapi.parser.JobParser;
import edu.hm.cs.fs.restapi.parser.Parser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The JobController represents the interface to the REST-API. It has different methods to access jobs and filter them.
 *
 * Url: <a href="http://fi.cs.hm.edu/fi/rest/public/job">http://fi.cs.hm.edu/fi/rest/public/job</a>
 *
 * @author Fabio
 */
@RestController
public class JobController {
    private final Parser<Job> parser;

    /**
     * Creates a JobController.
     */
    public JobController() {
        parser = new JobParser();
    }

    /**
     * Requests all jobs from
     * <a href="http://fi.cs.hm.edu/fi/rest/public/job">http://fi.cs.hm.edu/fi/rest/public/job</a>.
     *
     * @param search for the words in the job title and description.
     * @return a list with all matched jobs.
     */
    @RequestMapping("/rest/api/job")
    public List<Job> job(@RequestParam(value="search", defaultValue = "") String search) {
        return parser.parse().stream()
                .filter(job -> (job.getTitle() + job.getDescription()).toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
    }
}
