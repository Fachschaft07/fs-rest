package edu.hm.cs.fs.restapi.v1;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.News;
import edu.hm.cs.fs.restapi.parser.NewsParser;

/**
 * The NewsController represents the interface to the REST-API. It has one methods to access
 * FS News. <p> Url: <a href="http://fs.cs.hm.edu/category/news">http://fs.cs.hm.edu/category/news</a>
 *
 * @author Luca
 */
@RestController
public class NewsController {

    /**
     * Requests all news from <a href="http://fs.cs.hm.edu/category/news">http://fs.cs.hm.edu/category/news</a>.
     *
     * @return a list with all news.
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws XPathExpressionException 
     */
    @RequestMapping("/rest/api/1/news")
    public List<News> getNews() throws MalformedURLException, XPathExpressionException, IOException {
        return new NewsParser().parse();
    }
}