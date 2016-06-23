package edu.hm.cs.fs.restapi.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import edu.hm.cs.fs.common.model.Person;

/**
 * @author Fabio
 */
public class PersonParserTest {
    public static final String PERSON_XML = Thread.currentThread().getContextClassLoader().getResource("person.xml").toString();

    @Test
    public void testRequestSingleSuccess() throws Exception {
        final String personId = "abmayrthomas";

        PersonParser parser = spy(new PersonParser());
        doReturn(PERSON_XML).when(parser).getUrl();
        final Optional<Person> person = parser.getById(personId);

        assertThat(person.isPresent(), is(true));
        assertThat(person.get().getId(), is(equalTo(personId)));
    }

    @Test
    public void testRequestSingleError() throws Exception {
        final String personId = "maxmustermann";

        PersonParser parser = spy(new PersonParser());
        doReturn(PERSON_XML).when(parser).getUrl();

        final Optional<Person> person = parser.getById(personId);

        assertThat(person.isPresent(), is(false));
    }

    @Test
    public void testParsing() throws Exception {
        PersonParser parser = spy(new PersonParser());
        doReturn(PERSON_XML).when(parser).getUrl();

        final List<Person> modules = parser.getAll();

        assertThat(modules.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
        verify(parser, atLeast(modules.size())).onCreateItems(anyString());
    }

    @Test
    public void testParsingLiveData() throws Exception {
        PersonParser parser = spy(new PersonParser());

        final List<Person> modules = parser.getAll();

        assertThat(modules.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
        verify(parser, atLeast(modules.size())).onCreateItems(anyString());
    }
}
