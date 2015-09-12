package edu.hm.cs.fs.restapi.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.xpath.XPathExpressionException;

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
    @RequestMapping("/rest/api/1/lostandfound")
    public List<LostFound> lostFound(@RequestParam(value = "search", defaultValue = "") String search) throws Exception {
        return new LostFoundParser().getAll().stream()
                .filter(lostFound -> lostFound.getSubject().contains(search))
                .collect(Collectors.toList());
    }
}
