package edu.hm.cs.fs.restapi.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import edu.hm.cs.fs.common.constant.PublicTransportLocation;
import edu.hm.cs.fs.common.model.PublicTransport;
import edu.hm.cs.fs.restapi.parser.PublicTransportParser;

/**
 * The PublicTransportController represents the interface to the REST-API. It has different methods
 * to access publicTransport.
 *
 * @author Fabio
 */
@RestController
public class PublicTransportController {
    /**
     * Requests the publicTransport data for Pasing and Lothstraße.
     *
     * @param location of the departure (see edu.hm.cs.fs.common.constants.PublicTransportLocation
     *                 in module common).
     * @return the public transport possibilities.
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws XPathExpressionException 
     */
    @RequestMapping("/rest/api/1/publicTransport")
    public List<PublicTransport> getPublicTransports(@RequestParam("location") PublicTransportLocation location) throws MalformedURLException, XPathExpressionException, IOException {
        return new PublicTransportParser(location).parse();
    }
}
