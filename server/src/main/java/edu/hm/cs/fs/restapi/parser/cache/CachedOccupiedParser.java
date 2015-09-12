package edu.hm.cs.fs.restapi.parser.cache;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import edu.hm.cs.fs.common.model.RoomOccupation;
import edu.hm.cs.fs.restapi.parser.OccupiedParser;

/**
 * @author Fabio
 */
public class CachedOccupiedParser extends CachedParser<RoomOccupation> {
    private static final int INTERVAL = 31;
    private static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    /**
     * Creates a cached person parser.
     */
    public CachedOccupiedParser() {
        super(new OccupiedParser(), INTERVAL, TIME_UNIT);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ArrayList<RoomOccupation>>() {
        }.getType();
    }
}
