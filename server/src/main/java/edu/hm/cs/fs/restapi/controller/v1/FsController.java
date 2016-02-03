package edu.hm.cs.fs.restapi.controller.v1;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/rest/api/1/fs/presence")
    public List<Presence> getPresence() throws Exception {
        return new PresenceParser().getAll();
    }
    
    /**
     * Requests all news from <a href="http://fs.cs.hm.edu/category/news">http://fs.cs.hm.edu/category/news</a>.
     *
     * @return a list with all news.
     * @throws Exception
     */
    @RequestMapping("/rest/api/1/fs/news")
    public List<News> getNews() throws Exception {
        return new NewsParser().getAll().stream()
                .sorted((n1, n2) -> n1.getDate().compareTo(n2.getDate()) * -1)
                .collect(Collectors.toList());
    }
}
