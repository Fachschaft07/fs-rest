package edu.hm.cs.fs.restapi.controller.v1;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.BlackboardEntry;
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
      @RequestParam(value = "before", defaultValue = "0") long before
      ) throws Exception {
    return CachedBlackboardParser.getInstance().getAll().parallelStream()
        .filter(entry -> entry.getSubject().toLowerCase().contains(search.toLowerCase())
            || entry.getText().toLowerCase().contains(search.toLowerCase()))
        .filter(entry -> since==0||entry.getPublish().after(new Date(since)))
        .filter(entry -> {
          Date date = new Date();
          Date publish = entry.getPublish();
          
          if(before > 0){
            date.setTime(before);
          }
          
          Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          cal.add(Calendar.DAY_OF_MONTH, -7);
          
          return publish.before(date) && publish.after(cal.getTime());
        })
        .sorted(new Comparator<BlackboardEntry>() {
          @Override
          public int compare(BlackboardEntry o1, BlackboardEntry o2) {
            return (int)(o2.getPublish().getTime()-o1.getPublish().getTime());
          }
        })
        .collect(Collectors.toList());
  }
}
