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
public class LessonFk07ParserTest {

    public static final String LESSONS_IF_XML = Thread.currentThread().getContextClassLoader().getResource("lessonFk07IF.xml").toString();
    public static final String LESSONS_IC_XML = Thread.currentThread().getContextClassLoader().getResource("lessonFk07IC.xml").toString();
    public static final String LESSONS_IB_XML = Thread.currentThread().getContextClassLoader().getResource("lessonFk07IB.xml").toString();

    @Test
    public void testParsingIF() throws Exception {

        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        ModuleParser moduleParser = spy(new ModuleParser(personParser));
        doReturn(Optional.of(new Module())).when(moduleParser).getById(anyString());

        LessonFk07Parser parser = spy(new LessonFk07Parser(personParser, moduleParser, Group.of("IF")));
        doReturn(LESSONS_IF_XML).when(parser).getUrl();

        final List<Lesson> lessons = parser.getAll();

        assertThat(lessons.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
    }

    @Test
    public void testParsingIFLiveData() throws Exception {

        PersonParser personParser = new PersonParser();
        ModuleParser moduleParser = new ModuleParser(personParser);

        LessonFk07Parser parser = spy(new LessonFk07Parser(personParser, moduleParser, Group.of("IF")));

        final List<Lesson> lessons = parser.getAll();

        assertThat(lessons.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
    }

    @Test
    public void testParsingIC() throws Exception {

        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        ModuleParser moduleParser = spy(new ModuleParser(personParser));
        doReturn(Optional.of(new Module())).when(moduleParser).getById(anyString());

        LessonFk07Parser parser = spy(new LessonFk07Parser(personParser, moduleParser, Group.of("IC")));
        doReturn(LESSONS_IC_XML).when(parser).getUrl();

        final List<Lesson> lessons = parser.getAll();

        assertThat(lessons.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
    }

    @Test
    public void testParsingICLiveData() throws Exception {

        PersonParser personParser = new PersonParser();
        ModuleParser moduleParser = new ModuleParser(personParser);
        LessonFk07Parser parser = spy(new LessonFk07Parser(personParser, moduleParser, Group.of("IC")));

        final List<Lesson> lessons = parser.getAll();

        assertThat(lessons.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
    }

    @Test
    public void testParsingIB() throws Exception {

        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        ModuleParser moduleParser = spy(new ModuleParser(personParser));
        doReturn(Optional.of(new Module())).when(moduleParser).getById(anyString());

        LessonFk07Parser parser = spy(new LessonFk07Parser(personParser, moduleParser, Group.of("IB")));
        doReturn(LESSONS_IB_XML).when(parser).getUrl();

        final List<Lesson> lessons = parser.getAll();

        assertThat(lessons.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
    }

    @Test
    public void testParsingIBLiveData() throws Exception {

        PersonParser personParser = new PersonParser();
        ModuleParser moduleParser = new ModuleParser(personParser);
        LessonFk07Parser parser = spy(new LessonFk07Parser(personParser, moduleParser, Group.of("IB")));

        final List<Lesson> lessons = parser.getAll();

        assertThat(lessons.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
    }
}
