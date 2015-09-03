package edu.hm.cs.fs.restapi.parser.cache;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
     * @throws IOException 
     * @throws MalformedURLException 
     */
    public Optional<RoomOccupation> findByName(String roomName) throws MalformedURLException, IOException {
        return parse().parallelStream()
                .filter(room -> room.getName().equalsIgnoreCase(roomName))
                .findFirst();
    }

    @Override
    public Type getType() {
        return new TypeToken<ArrayList<RoomOccupation>>() {
        }.getType();
    }
}
