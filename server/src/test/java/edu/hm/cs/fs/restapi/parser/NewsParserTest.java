package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.News;
import edu.hm.cs.fs.common.model.Person;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Fabio
 */
public class NewsParserTest {

    public static final String NEWS_XML = Thread.currentThread().getContextClassLoader().getResource("news.xml").toString();

    @Test
    public void testParsing() throws Exception {

        NewsParser parser = spy(new NewsParser());
        doReturn(NEWS_XML).when(parser).getUrl();

        final List<News> news = parser.getAll();

        assertThat(news.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
        verify(parser, atLeast(news.size())).onCreateItems(anyString());
    }

    @Test
    public void testParsingLiveData() throws Exception {

        NewsParser parser = spy(new NewsParser());

        final List<News> news = parser.getAll();

        assertThat(news.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
        verify(parser, atLeast(news.size())).onCreateItems(anyString());
    }
}
