package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.model.LostFound;
import edu.hm.cs.fs.common.model.Module;
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
public class LostFoundParserTest {

    public static final String LOSTFOUND_XML = Thread.currentThread().getContextClassLoader().getResource("lostfound.xml").toString();

    @Test
    public void testParsing() throws Exception {
        LostFoundParser parser = spy(new LostFoundParser());
        doReturn(LOSTFOUND_XML).when(parser).getUrl();

        final List<LostFound> items = parser.getAll();

        assertThat(items.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
        verify(parser, atLeast(items.size())).onCreateItems(anyString());
    }

    @Test
    public void testParsingLiveData() throws Exception {
        LostFoundParser parser = spy(new LostFoundParser());
        final List<LostFound> items = parser.getAll();

        assertThat(items.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
        verify(parser, atLeast(items.size())).onCreateItems(anyString());
    }
}
