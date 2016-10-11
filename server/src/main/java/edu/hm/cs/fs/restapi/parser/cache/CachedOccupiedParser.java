package edu.hm.cs.fs.restapi.parser.cache;

import com.google.gson.reflect.TypeToken;
import edu.hm.cs.fs.common.model.RoomOccupation;
import edu.hm.cs.fs.restapi.parser.OccupiedParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Fabio
 */
public class CachedOccupiedParser extends CachedParser<RoomOccupation> {
    private static final int UPDATETIME = 3;
    private static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    private static CachedOccupiedParser instance;

    /**
     * Creates a cached person parser.
     */
    private CachedOccupiedParser() {
        super(new OccupiedParser(), UPDATETIME, TIME_UNIT, UpdateType.FIXEDTIME);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ArrayList<RoomOccupation>>() {
        }.getType();
    }

    public static CachedOccupiedParser getInstance() {
        if (instance == null) {
            instance = new CachedOccupiedParser();
        }
        return instance;
    }
}
