package edu.hm.cs.fs.restapi.parser;

import java.io.IOException;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.hm.cs.fs.common.model.News;

/**
 * Created by Fabio on 25.06.2015.
 */
public class NewsParserTest {
    private Parser<News> parser;

    @Before
    public void setUp() throws Exception {
        parser = new NewsParser();
    }

    @Test
    public void testParsing() {
        try {
          List<News> news = parser.parse();
          Assert.assertThat(true, CoreMatchers.is(CoreMatchers.not(news.isEmpty())));
        } catch (IOException | XPathExpressionException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    }
}