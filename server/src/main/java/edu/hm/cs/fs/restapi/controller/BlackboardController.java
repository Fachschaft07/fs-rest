package edu.hm.cs.fs.restapi.controller;

import edu.hm.cs.fs.common.model.BlackboardEntry;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.simple.SimpleBlackboardEntry;
import edu.hm.cs.fs.restapi.parser.BlackboardParser;
import edu.hm.cs.fs.restapi.parser.PersonParser;
import edu.hm.cs.fs.restapi.parser.cache.CachedBlackboardParser;
import edu.hm.cs.fs.restapi.parser.cache.CachedPersonParser;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The BlackboardController represents the interface to the REST-API. It has different methods to
 * access blackboard entries and filter them.
 * <p>
 * Url:
 * <a href="http://fi.cs.hm.edu/fi/rest/public/news">http://fi.cs.hm.edu/fi/rest/public/news</a>
 *
 * @author Fabio
 */
@RestController
public class BlackboardController {
    /**
     * Requests all blackboard entries from
     * <a href="http://fi.cs.hm.edu/fi/rest/public/news">http://fi.cs.hm.edu/fi/rest/public/news</a>.
     *
     * @param search for the words in the entry title and description.
     * @return a list with all matched entries.
     * @throws Exception
     */
    @ApiOperation(value = "getBlackboard")
    @RequestMapping(method = RequestMethod.GET, value = "/blackboard", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search", value = "Content to search for", required = false, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "since", value = "Timestamp", required = false, dataType = "long", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "before", value = "Timestamp", required = false, dataType = "long", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "group", value = "Group in format [A-Z]{2}[0-9]{1}[A-Z]{1}", required = false, dataType = "string", paramType = "query", defaultValue = "")
    })
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public List<SimpleBlackboardEntry> getBlackboard(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "since", defaultValue = "0") long since,
            @RequestParam(value = "before", defaultValue = "0") long before,
            @RequestParam(value = "group", defaultValue = "") Group group) throws Exception {
        return new BlackboardParser(CachedPersonParser.getInstance()).getAll()
                .parallelStream()
                .filter(entry -> {
                    boolean ret = (group.getStudy() == null);

                    if (group.getStudy() != null && entry.getGroups() != null && entry.getGroups().size() > 0) {
                        ret = entry.getGroups().parallelStream().filter(g -> g.getStudy() == group.getStudy())
                                .collect(Collectors.toList()).size() > 0;
                    }

                    return ret;
                })
                .filter(entry -> entry.getSubject().toLowerCase().contains(search.toLowerCase())
                        || entry.getText().toLowerCase().contains(search.toLowerCase()))
                .filter(entry -> since == 0 || entry.getPublish().after(new Date(since))).filter(entry -> {
                    Date date = new Date();
                    Date publish = entry.getPublish();

                    if (before > 0) {
                        date.setTime(before);
                    }

                    return publish.before(date);
                })
                .sorted((o1, o2) -> o2.getPublish().compareTo(o1.getPublish()) * -1) // we want it DESC
                .map(SimpleBlackboardEntry::new)
                .collect(Collectors.toList());
    }

    /**
     * @param blackboardEntryId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "getBlackboardEntryById")
    @RequestMapping(method = RequestMethod.GET, value = "/blackboardentry", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID of a blackboard entry", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public BlackboardEntry getBlackboardEntryById(@RequestParam(value = "id") String blackboardEntryId) throws Exception {
        return new BlackboardParser(CachedPersonParser.getInstance())
                .getById(blackboardEntryId)
                .orElseThrow(() -> new IllegalStateException("No blackboard entry found with id '" + blackboardEntryId + "'."));
    }
}
