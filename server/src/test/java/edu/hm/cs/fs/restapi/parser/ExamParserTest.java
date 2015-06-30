package edu.hm.cs.fs.restapi.parser;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.hm.cs.fs.common.model.Exam;

/**
 * Created by Fabio on 25.06.2015.
 */
public class ExamParserTest {
    private Parser<Exam> parser;

    @Before
    public void setUp() throws Exception {
        parser = new ExamParser();
    }

    @Test
    public void testParsing() {
        final List<Exam> jobs = parser.parse();
        Assert.assertThat(true, CoreMatchers.is(CoreMatchers.not(jobs.isEmpty())));
    }
}