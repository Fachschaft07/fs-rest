package edu.hm.cs.fs.restapi.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

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
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws XPathExpressionException 
     */
    @RequestMapping("/rest/api/1/fs/presence")
    public List<Presence> getPresence() throws MalformedURLException, IOException, XPathExpressionException {
        return new PresenceParser().parse();
    }
    
    /**
     * Requests all news from <a href="http://fs.cs.hm.edu/category/news">http://fs.cs.hm.edu/category/news</a>.
     *
     * @return a list with all news.
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws XPathExpressionException 
     */
    @RequestMapping("/rest/api/1/fs/news")
    public List<News> getNews() throws MalformedURLException, XPathExpressionException, IOException {
        return new NewsParser().parse();
    }
}