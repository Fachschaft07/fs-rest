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

import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.Person;

/**
 * @author Fabio
 */
public class ModuleParserTest {

    public static final String MODULE_XML = Thread.currentThread().getContextClassLoader().getResource("modul.xml").toString();

    @Test
    public void testRequestSingleSuccess() throws Exception {
        final String moduleId = "3drekonstruktion";

        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        ModuleParser parser = spy(new ModuleParser(personParser));
        doReturn(MODULE_XML).when(parser).getUrl();
        final Optional<Module> module = parser.getById(moduleId);

        assertThat(module.isPresent(), is(true));
        assertThat(module.get().getId(), is(equalTo(moduleId)));
    }

    @Test
    public void testRequestSingleError() throws Exception {
        final String moduleId = "testmodule";

        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        ModuleParser parser = spy(new ModuleParser(personParser));
        doReturn(MODULE_XML).when(parser).getUrl();
        final Optional<Module> module = parser.getById(moduleId);

        assertThat(module.isPresent(), is(false));
    }

    @Test
    public void testParsing() throws Exception {
        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        ModuleParser parser = spy(new ModuleParser(personParser));
        doReturn(MODULE_XML).when(parser).getUrl();

        final List<Module> modules = parser.getAll();

        assertThat(modules.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
        verify(parser, atLeast(modules.size())).onCreateItems(anyString());
    }

    @Test
    public void testParsingLiveData() throws Exception {
        PersonParser personParser = new PersonParser();
        ModuleParser parser = spy(new ModuleParser(personParser));

        final List<Module> modules = parser.getAll();

        assertThat(modules.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
        verify(parser, atLeast(modules.size())).onCreateItems(anyString());
    }
}
