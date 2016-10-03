package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.model.Occupied;
import edu.hm.cs.fs.common.model.RoomOccupation;
import edu.hm.cs.fs.common.model.TeacherBooking;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Luca
 */
public class OccupiedParserTest {

    public static final String OCCUPIED_XML = Thread.currentThread().getContextClassLoader().getResource("occupied.xml").toString();

    @Test
    public void testParsing() throws Exception {
        OccupiedParser parser = spy(new OccupiedParser());
        doReturn(OCCUPIED_XML).when(parser).getUrl();

        final List<RoomOccupation> occupations = parser.getAll();

        assertThat(occupations.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
    }

    @Test
    public void testParsingLiveData() throws Exception {
        OccupiedParser parser = spy(new OccupiedParser());

        final List<RoomOccupation> occupations = parser.getAll();

        assertThat(occupations.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
    }
}
