package edu.hm.cs.fs.restapi.parser;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import edu.hm.cs.fs.common.model.Job;

/**
 * Created by Fabio on 25.06.2015.
 */
public class JobParserTest {
    private Parser<Job> parser;

    @Before
    public void setUp() throws Exception {
        parser = new JobParser();
    }

    @Test
    public void testParsing() {
        try {
          List<Job> jobs = parser.parse();
          Assert.assertThat(true, CoreMatchers.is(CoreMatchers.not(jobs.isEmpty())));
        } catch (IOException | XPathExpressionException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    }
}