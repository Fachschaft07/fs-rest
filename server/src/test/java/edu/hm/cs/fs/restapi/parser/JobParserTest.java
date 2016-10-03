package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.model.Job;
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
public class JobParserTest {

    public static final String JOB_XML = Thread.currentThread().getContextClassLoader().getResource("job.xml").toString();

    @Test
    public void testRequestSingleSuccess() throws Exception {
        final String jobId = "masterarbeitaugmentedrealityfotoboxapppixelgmbh";

        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        JobParser parser = spy(new JobParser(personParser));
        doReturn(JOB_XML).when(parser).getUrl();
        final Optional<Job> module = parser.getById(jobId);

        assertThat(module.isPresent(), is(true));
        assertThat(module.get().getId(), is(equalTo(jobId)));
    }

    @Test
    public void testRequestSingleError() throws Exception {
        final String moduleId = "testjob";

        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        JobParser parser = spy(new JobParser(personParser));
        doReturn(JOB_XML).when(parser).getUrl();
        final Optional<Job> module = parser.getById(moduleId);

        assertThat(module.isPresent(), is(false));
    }

    @Test
    public void testParsing() throws Exception {
        PersonParser personParser = spy(new PersonParser());
        doReturn(Optional.of(new Person())).when(personParser).getById(anyString());

        JobParser parser = spy(new JobParser(personParser));
        doReturn(JOB_XML).when(parser).getUrl();

        final List<Job> jobs = parser.getAll();

        assertThat(jobs.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
        verify(parser, atLeast(jobs.size())).onCreateItems(anyString());
    }

    @Test
    public void testParsingLiveData() throws Exception {
        JobParser parser = spy(new JobParser(new PersonParser()));

        final List<Job> jobs = parser.getAll();

        assertThat(jobs.isEmpty(), is(false));

        verify(parser, atLeastOnce()).getAll();
        verify(parser, atLeastOnce()).getUrl();
        verify(parser, atLeastOnce()).getRootNode();
        verify(parser, atLeast(jobs.size())).onCreateItems(anyString());
    }
}
