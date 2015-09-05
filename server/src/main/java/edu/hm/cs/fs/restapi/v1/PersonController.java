package edu.hm.cs.fs.restapi.v1;

import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import edu.hm.cs.fs.restapi.parser.cache.CachedPersonParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.xpath.XPathExpressionException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Fabio on 03.09.2015.
 */
@RestController
public class PersonController {
    /**
     *
     * @return
     * @throws IOException
     * @throws XPathExpressionException
     * @throws MalformedURLException
     * @throws IllegalStateException
     */
    @RequestMapping("/rest/api/1/persons")
    public List<SimplePerson> getPersons() throws IllegalStateException, MalformedURLException, XPathExpressionException, IOException {
        return new CachedPersonParser().parse()
                .stream()
                .map(SimplePerson::new)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param personId
     * @return
     * @throws IOException 
     * @throws XPathExpressionException 
     * @throws MalformedURLException 
     * @throws IllegalStateException 
     */
    @RequestMapping("/rest/api/1/person")
    public Person getPersonById(@RequestParam(value = "id") String personId) throws IllegalStateException, MalformedURLException, XPathExpressionException, IOException {
        return new CachedPersonParser()
                .findById(personId)
                .orElseThrow(() -> new IllegalStateException("No person found with id '" + personId + "'."));
    }
}