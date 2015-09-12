package edu.hm.cs.fs.restapi.parser.cache;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.restapi.parser.PersonParser;

/**
 * @author Fabio
 */
public class CachedPersonParser extends ByIdCachedParser<Person> {
    private static final int INTERVAL = 31;
    private static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    /**
     * Creates a cached person parser.
     */
    public CachedPersonParser() {
        super(new PersonParser(), INTERVAL, TIME_UNIT);
    }

    @Override
    protected String getId(Person item) {
        return item.getId();
    }

    @Override
    protected Type getType() {
        return new TypeToken<ArrayList<Person>>() {
        }.getType();
    }
}
