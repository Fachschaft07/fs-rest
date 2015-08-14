package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.BlackboardEntry;
import edu.hm.cs.fs.restapi.parser.BlackboardParser;

/**
 * The BlackboardController represents the interface to the REST-API. It has different methods to access blackboard
 * entries and filter them.
 * <p>
 * Url: <a href="http://fi.cs.hm.edu/fi/rest/public/news">http://fi.cs.hm.edu/fi/rest/public/news</a>
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
     */
    @RequestMapping("/rest/api/blackboard/entry")
    public List<BlackboardEntry> entry(@RequestParam(value = "search", defaultValue = "") String search) {
        return new BlackboardParser().parse().stream()
                .filter(entry -> entry.getSubject().contains(search) || entry.getText().contains(search))
                .collect(Collectors.toList());
    }
}
