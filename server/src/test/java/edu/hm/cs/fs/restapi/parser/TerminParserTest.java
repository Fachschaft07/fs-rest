package edu.hm.cs.fs.restapi.parser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;

import edu.hm.cs.fs.common.model.Termin;

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
