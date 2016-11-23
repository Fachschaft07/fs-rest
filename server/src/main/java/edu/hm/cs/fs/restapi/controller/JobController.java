package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import edu.hm.cs.fs.restapi.parser.cache.CachedPersonParser;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @ApiOperation(value = "getJobs")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/jobs", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search", value = "Content to search for", required = false, dataType = "string", paramType = "query", defaultValue = "")
    })
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public List<SimpleJob> getJobs(@RequestParam(value = "search", defaultValue = "") String search) throws Exception {
        return new JobParser(CachedPersonParser.getInstance()).getAll().stream()
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
    @ApiOperation(value = "getJobById")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/job", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID of a job", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public Job getJobById(@RequestParam(value = "id") String id) throws Exception {
        return new JobParser(CachedPersonParser.getInstance())
                .getById(id)
                .orElseThrow(() -> new IllegalStateException("No module found with id '" + id + "'."));
    }
}