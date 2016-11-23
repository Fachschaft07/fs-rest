package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import edu.hm.cs.fs.restapi.parser.PersonParser;
import edu.hm.cs.fs.restapi.parser.cache.CachedPersonParser;

/**
 * Created by Fabio on 03.09.2015.
 */
@RestController
public class PersonController {
    /**
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "getPersons")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/persons", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public List<SimplePerson> getPersons() throws Exception {
        return CachedPersonParser.getInstance().getAll()
                .stream()
                .map(SimplePerson::new)
                .collect(Collectors.toList());
    }

    /**
     * @param personId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "getPersonById")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/person", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID of a person", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public Person getPersonById(@RequestParam(value = "id") String personId) throws Exception {
        return CachedPersonParser.getInstance()
                .getById(personId)
                .orElseThrow(() -> new IllegalStateException("No person found with id '" + personId + "'."));
    }
}
