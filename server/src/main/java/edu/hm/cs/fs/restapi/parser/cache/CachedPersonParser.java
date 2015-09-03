package edu.hm.cs.fs.restapi.parser.cache;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import edu.hm.cs.fs.restapi.parser.PersonParser;

/**
 * Created by Fabio on 08.07.2015.
 */
public class CachedPersonParser extends CachedParser<Person> {
    private static final int INTERVAL = 31;
    private static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    /**
     * Creates a cached person parser.
     */
    public CachedPersonParser() {
        super(new PersonParser(), INTERVAL, TIME_UNIT);
    }

    /**
     *
     * @param personId
     * @return
     */
    public Optional<SimplePerson> findByIdSimple(String personId) {
        return findById(personId).map(person -> {
            SimplePerson sPerson = new SimplePerson();
            sPerson.setId(person.getId());
            sPerson.setLastName(person.getLastName());
            sPerson.setFirstName(person.getFirstName());
            sPerson.setTitle(person.getTitle());
            return sPerson;
        });
    }

    /**
     *
     * @param personId
     * @return
     */
    public Optional<Person> findById(String personId) {
        return parse().parallelStream()
                .filter(person -> person.getId().equalsIgnoreCase(personId))
                .findFirst();
    }

    @Override
    public Type getType() {
        return new TypeToken<ArrayList<Person>>() {
        }.getType();
    }
}
