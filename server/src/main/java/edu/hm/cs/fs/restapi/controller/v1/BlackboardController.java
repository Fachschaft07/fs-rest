package edu.hm.cs.fs.restapi.controller.v1;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.BlackboardEntry;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.restapi.parser.cache.CachedBlackboardParser;

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
  @RequestMapping("/rest/api/1/blackboard")
  public List<BlackboardEntry> getBlackboard(
      @RequestParam(value = "search", defaultValue = "") String search,
      @RequestParam(value = "since", defaultValue = "0") long since,
      @RequestParam(value = "before", defaultValue = "0") long before,
      @RequestParam(value = "group", defaultValue = "") Group group) throws Exception {

    return CachedBlackboardParser.getInstance().getAll().parallelStream().filter(entry -> {
      boolean ret = (group.getStudy()==null);

      if (group.getStudy() != null && entry.getGroups() != null && entry.getGroups().size() > 0) {
        ret = entry.getGroups().parallelStream().filter(g -> g.getStudy() == group.getStudy())
            .collect(Collectors.toList()).size() > 0;
      }

      return ret;
    }).filter(entry -> entry.getSubject().toLowerCase().contains(search.toLowerCase())
        || entry.getText().toLowerCase().contains(search.toLowerCase()))
        .filter(entry -> since == 0 || entry.getPublish().after(new Date(since))).filter(entry -> {
          Date date = new Date();
          Date publish = entry.getPublish();

          if (before > 0) {
            date.setTime(before);
          }

          return publish.before(date);
        }).sorted((o1, o2) ->
          o2.getPublish().compareTo(o1.getPublish()) * -1 // we want it DESC
        ).collect(Collectors.toList());
  }
}
