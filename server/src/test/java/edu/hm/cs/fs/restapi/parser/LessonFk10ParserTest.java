package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Lesson;
import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.Person;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
public class LessonFk10ParserTest {

    public static final String LESSON_HTML = Thread.currentThread().getContextClassLoader().getResource("lessonFk10.html").getFile();

    @Test
    public void testParsingLiveData() throws Exception {

        LessonFk10Parser parser = new LessonFk10Parser(null);

        final List<Lesson> lessons = parser.getAll();

        assertThat(lessons.isEmpty(), is(false));
    }

    @Test
    public void testParsing() throws Exception {

        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        ModuleParser moduleParser = spy(new ModuleParser(personParser));
        doReturn(Optional.of(new Module())).when(moduleParser).getById(anyString());

        LessonFk10Parser parser = spy(new LessonFk10Parser(null));
        doReturn(Jsoup.parse(new File(LESSON_HTML), "UTF-8", "")).when(parser).getDocument();

        final List<Lesson> lessons = parser.getAll();

        assertThat(lessons.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getDocument();
    }
}
