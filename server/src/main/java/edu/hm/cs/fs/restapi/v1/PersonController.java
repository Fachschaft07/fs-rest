package edu.hm.cs.fs.restapi.v1;

import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.restapi.parser.cache.CachedPersonParser;
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
     * @param personId
     * @return
     */
    @RequestMapping("/rest/api/1/person")
    public Person getPersonById(@RequestParam(value = "id") String personId) {
        return new CachedPersonParser()
                .findById(personId)
                .orElseThrow(() -> new IllegalStateException("No person found with id '" + personId + "'."));
    }
}
