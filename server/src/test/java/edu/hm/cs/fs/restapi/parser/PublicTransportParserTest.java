package edu.hm.cs.fs.restapi.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import edu.hm.cs.fs.common.constant.PublicTransportLocation;
import edu.hm.cs.fs.common.model.PublicTransport;

/**
 * Created by Fabio on 26.06.2015.
 */
public class PublicTransportParserTest {
  @Test
  public void testParsingLocationPasing() {
    try {
      List<PublicTransport> parser =
          new PublicTransportParser(PublicTransportLocation.PASING).parse();
      Assert.assertThat(true, CoreMatchers.is(CoreMatchers.not(parser.isEmpty())));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testParsingLocationLothstr() {
    try {
      List<PublicTransport> parser =
          new PublicTransportParser(PublicTransportLocation.LOTHSTR).parse();
      Assert.assertThat(true, CoreMatchers.is(CoreMatchers.not(parser.isEmpty())));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
