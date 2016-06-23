package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.model.*;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Luca
 */
public class BookingParserTest {

    public static final String BOOKING_XML = Thread.currentThread().getContextClassLoader().getResource("booking.xml").toString();

    @Test
    public void testParsing() throws Exception {
        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        ModuleParser moduleParser = spy(new ModuleParser(personParser));
        doReturn(Optional.of(new Module())).when(moduleParser).getById(anyString());

        BookingParser parser = spy(new BookingParser(personParser, moduleParser));
        doReturn(BOOKING_XML).when(parser).getUrl();

        final List<TeacherBooking> bookings = parser.getAll();

        assertThat(bookings.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
    }

    @Test
    public void testParsingLiveData() throws Exception {
        PersonParser personParser = new PersonParser();
        BookingParser parser = spy(new BookingParser(personParser, new ModuleParser(personParser)));

        final List<TeacherBooking> bookings = parser.getAll();

        assertThat(bookings.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
    }
}
