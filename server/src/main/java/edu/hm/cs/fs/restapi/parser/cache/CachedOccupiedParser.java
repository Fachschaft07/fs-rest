package edu.hm.cs.fs.restapi.parser.cache;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.google.gson.reflect.TypeToken;

import edu.hm.cs.fs.common.model.Occupied;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.RoomOccupation;
import edu.hm.cs.fs.restapi.parser.OccupiedParser;

/**
 * Created by Fabio on 08.07.2015.
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

    /**
     *
     * @param roomName
     * @return
     */
    public Optional<RoomOccupation> findByName(String roomName) {
        return parse().parallelStream()
                .filter(room -> room.getName().equalsIgnoreCase(roomName))
                .findFirst();
    }

    @Override
    public Type getType() {
        return new TypeToken<ArrayList<Person>>() {
        }.getType();
    }
}
