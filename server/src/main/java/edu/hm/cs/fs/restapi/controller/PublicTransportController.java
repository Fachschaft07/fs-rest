package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @throws Exception
     */
    @ApiOperation(value = "getPublicTransport")
    @RequestMapping(method = RequestMethod.GET, value = "/publicTransport", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "location", value = "Location of departure", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public List<PublicTransport> getPublicTransports(@RequestParam("location") PublicTransportLocation location)
            throws Exception {
        return new PublicTransportParser(location).getAll().stream()
                .filter(mvv -> mvv.getDepartureIn(TimeUnit.MINUTES) >= 0)
                .collect(Collectors.toList());
    }
}
