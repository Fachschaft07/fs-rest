package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
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
   * Requests all Lost and Found Items from 
   * <a href="http://fi.cs.hm.edu/fi/rest/public/lostfound.xml">http://fi.cs.hm.edu/fi/rest/public/lostfound.xml</a>
   * and filters them by Search
   *
   * @param search for the words in the Lost and Found Subject.
   * @return a list with all matched items.
   */
  @RequestMapping("/rest/api/lostandfound")
  public List<LostFound> lostFound(@RequestParam(value = "search", defaultValue = "") String search) {
    return new LostFoundParser().parse().stream()
        .filter(lostFound -> lostFound.getSubject().contains(search))
        .collect(Collectors.toList());
  }
}
