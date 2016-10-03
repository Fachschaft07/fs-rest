package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.constant.PublicTransportLocation;
import edu.hm.cs.fs.common.model.Lesson;
import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.PublicTransport;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Luca
 */
public class PublicTransportParserTest {

    public static final String TIMES_LOTHSTRASSE_HTML = Thread.currentThread().getContextClassLoader().getResource("publictransportLothstrasse.html").getFile();
    public static final String TIMES_PASING_HTML = Thread.currentThread().getContextClassLoader().getResource("publictransportPasing.html").getFile();

    @Test
    public void testParsingLothstrasseLiveData() throws Exception {

        PublicTransportParser parser = spy(new PublicTransportParser(PublicTransportLocation.LOTHSTR));

        final List<PublicTransport> times = parser.getAll();

        assertThat(times.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getDocument();
    }

    @Test
    public void testParsingLothstrasse() throws Exception {

        PublicTransportParser parser = spy(new PublicTransportParser(PublicTransportLocation.LOTHSTR));
        doReturn(Jsoup.parse(new File(TIMES_LOTHSTRASSE_HTML), "UTF-8", "")).when(parser).getDocument();

        final List<PublicTransport> times = parser.getAll();

        assertThat(times.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getDocument();
    }

    @Test
    public void testParsingPasingLiveData() throws Exception {

        PublicTransportParser parser = spy(new PublicTransportParser(PublicTransportLocation.PASING));

        final List<PublicTransport> times = parser.getAll();

        assertThat(times.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getDocument();
    }

    @Test
    public void testParsingPasing() throws Exception {

        PublicTransportParser parser = spy(new PublicTransportParser(PublicTransportLocation.PASING));
        doReturn(Jsoup.parse(new File(TIMES_PASING_HTML), "UTF-8", "")).when(parser).getDocument();

        final List<PublicTransport> times = parser.getAll();

        assertThat(times.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getDocument();
    }
}
