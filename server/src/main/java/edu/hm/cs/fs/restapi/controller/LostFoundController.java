package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.LostFound;
import edu.hm.cs.fs.restapi.parser.LostFoundParser;

/**
 * The JobController represents the interface to the REST-API. It has different methods to access
 * jobs and filter them.
 *
 * Url: <a href="http://fi.cs.hm.edu/fi/rest/public/lostfound.xml">http://fi.cs.hm.edu/fi/rest/public/lostfound.xml</a>
 *
 * @author Luca
 */
@RestController
public class LostFoundController {

    /**
     * Requests all Lost and Found Items from <a href="http://fi.cs.hm.edu/fi/rest/public/lostfound.xml">http://fi.cs.hm.edu/fi/rest/public/lostfound.xml</a>
     * and filters them by Search
     *
     * @param search for the words in the Lost and Found Subject.
     * @return a list with all matched items.
     * @throws Exception
     */
    @ApiOperation(value = "getLostAndFound")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/lostandfound", produces = "application/json")
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
    public List<LostFound> lostFound(@RequestParam(value = "search", defaultValue = "") String search) throws Exception {
        return new LostFoundParser().getAll().stream()
                .filter(lostFound -> lostFound.getSubject().toLowerCase().contains(search.toLowerCase()))
                .sorted((lf1, lf2) -> lf1.getDate().compareTo(lf2.getDate()) * -1)
                .collect(Collectors.toList());
    }
}
