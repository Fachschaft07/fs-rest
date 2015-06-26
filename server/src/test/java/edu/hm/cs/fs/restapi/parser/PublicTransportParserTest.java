package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.constant.PublicTransportLocation;
import edu.hm.cs.fs.common.model.PublicTransport;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Fabio on 26.06.2015.
 */
public class PublicTransportParserTest {
    @Test
    public void testParsingLocationPasing() {
        List<PublicTransport> parser = new PublicTransportParser(PublicTransportLocation.PASING).parse();
        Assert.assertThat(true, CoreMatchers.is(CoreMatchers.not(parser.isEmpty())));
    }

    @Test
    public void testParsingLocationLothstr() {
        List<PublicTransport> parser = new PublicTransportParser(PublicTransportLocation.LOTHSTR).parse();
        Assert.assertThat(true, CoreMatchers.is(CoreMatchers.not(parser.isEmpty())));
    }
}