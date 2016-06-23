package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.constant.StudentWorkMunich;
import edu.hm.cs.fs.common.model.*;
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
public class MealParserTest {

    public static final String MENSA_LOTHSTRASSE_HTML = Thread.currentThread().getContextClassLoader().getResource("mealMensaLothstrasse.html").getFile();

    @Test
    public void testParsingMensaLothstrasse() throws Exception {

        MealParser parser = spy(new MealParser(StudentWorkMunich.MENSA_LOTHSTRASSE));
        doReturn(Jsoup.parse(new File(MENSA_LOTHSTRASSE_HTML), "UTF-8", "")).when(parser).getDocument();

        final List<Meal> meals = parser.getAll();

        assertThat(meals.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getDocument();
    }

    @Test
    public void testParsingMensaLothstrasseLiveData() throws Exception {

        MealParser parser = spy(new MealParser(StudentWorkMunich.MENSA_LOTHSTRASSE));

        final List<Meal> meals = parser.getAll();

        assertThat(meals.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getDocument();
    }
}
