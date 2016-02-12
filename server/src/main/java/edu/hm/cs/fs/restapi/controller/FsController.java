package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.News;
import edu.hm.cs.fs.common.model.Presence;
import edu.hm.cs.fs.restapi.parser.NewsParser;
import edu.hm.cs.fs.restapi.parser.PresenceParser;

/**
 * @author Fabio
 */
@RestController
public class FsController {
    /**
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "getPresence")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/fs/presence", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public List<Presence> getPresence() throws Exception {
        return new PresenceParser().getAll();
    }
    
    /**
     * Requests all news from <a href="http://fs.cs.hm.edu/category/news">http://fs.cs.hm.edu/category/news</a>.
     *
     * @return a list with all news.
     * @throws Exception
     */
    @ApiOperation(value = "getNews")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/fs/news", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public List<News> getNews() throws Exception {
        return new NewsParser().getAll().stream()
                .sorted((n1, n2) -> n1.getDate().compareTo(n2.getDate()) * -1)
                .collect(Collectors.toList());
    }
}
