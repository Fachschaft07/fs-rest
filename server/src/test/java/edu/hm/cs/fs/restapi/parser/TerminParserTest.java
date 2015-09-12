package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.model.Termin;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Fabio
 */
public class TerminParserTest {

    public static final String EVENT_XML = Thread.currentThread().getContextClassLoader().getResource("termin.xml").toString();

    @Test
    public void testParsing() throws Exception {
        TerminParser parser = spy(new TerminParser());
        doReturn(EVENT_XML).when(parser).getUrl();

        final List<Termin> termins = parser.getAll();

        assertThat(false, is(termins.isEmpty()));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
        verify(parser, atLeast(termins.size())).onCreateItems(anyString());
    }
}
