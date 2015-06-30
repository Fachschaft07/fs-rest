package edu.hm.cs.fs.restapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.constant.PublicTransportLocation;
import edu.hm.cs.fs.common.model.PublicTransport;
import edu.hm.cs.fs.restapi.parser.PublicTransportParser;

/**
 * The PublicTransportController represents the interface to the REST-API. It has different methods to access mvv.
 *
 * @author Fabio
 */
@RestController
public class PublicTransportController {
    /**
     * Requests the mvv data for Pasing and Lothstraße.
     *
     * @param location of the departure (see edu.hm.cs.fs.common.constants.PublicTransportLocation in module common).
     * @return the public transport possibilities.
     */
    @RequestMapping("/rest/api/mvv")
    public List<PublicTransport> mvv(@RequestParam("location") String location) {
        return new PublicTransportParser(PublicTransportLocation.valueOf(location.toUpperCase())).parse();
    }
}
