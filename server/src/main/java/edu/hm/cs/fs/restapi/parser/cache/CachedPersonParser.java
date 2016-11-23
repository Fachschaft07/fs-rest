package edu.hm.cs.fs.restapi.parser.cache;

import com.google.gson.reflect.TypeToken;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.restapi.parser.PersonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Fabio
 */
public class CachedPersonParser extends ByIdCachedParser<Person> {
    private static final int UPDATETIME = 4;
    private static final TimeUnit TIME_UNIT = TimeUnit.HOURS;

    private static CachedPersonParser instance;

    /**
     * Creates a cached person parser.
     */
    private CachedPersonParser() {
        super(new PersonParser(), UPDATETIME, TIME_UNIT, UpdateType.NONE);
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

    public static CachedPersonParser getInstance() {
        if (instance == null) {
            instance = new CachedPersonParser();
        }
        return instance;
    }
}
