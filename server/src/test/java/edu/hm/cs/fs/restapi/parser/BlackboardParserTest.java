package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.model.BlackboardEntry;
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
 * @author Luca
 */
public class BlackboardParserTest {

    public static final String BLACKBOARD_XML = Thread.currentThread().getContextClassLoader().getResource("blackboard.xml").toString();

    @Test
    public void testRequestSingleSuccess() throws Exception {
        final String entryId = "1anmeldemoeglichkeitfuerlv";

        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        BlackboardParser parser = spy(new BlackboardParser(personParser));
        doReturn(BLACKBOARD_XML).when(parser).getUrl();
        final Optional<BlackboardEntry> entry = parser.getById(entryId);

        assertThat(entry.isPresent(), is(true));
        assertThat(entry.get().getId(), is(equalTo(entryId)));
    }

    @Test
    public void testRequestSingleError() throws Exception {
        final String entryId = "testentry";

        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        BlackboardParser parser = spy(new BlackboardParser(personParser));
        doReturn(BLACKBOARD_XML).when(parser).getUrl();
        final Optional<BlackboardEntry> entry = parser.getById(entryId);

        assertThat(entry.isPresent(), is(false));
    }

    @Test
    public void testParsing() throws Exception {
        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        BlackboardParser parser = spy(new BlackboardParser(personParser));
        doReturn(BLACKBOARD_XML).when(parser).getUrl();

        final List<BlackboardEntry> entries = parser.getAll();

        assertThat(entries.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
        verify(parser, atLeast(entries.size())).onCreateItems(anyString());
    }

    @Test
    public void testParsingLiveData() throws Exception {
        PersonParser personParser = spy(new PersonParser());
        BlackboardParser parser = spy(new BlackboardParser(personParser));

        final List<BlackboardEntry> entries = parser.getAll();

        assertThat(entries.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
        verify(parser, atLeast(entries.size())).onCreateItems(anyString());
    }
}
