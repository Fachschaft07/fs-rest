package edu.hm.cs.fs.restapi.parser.cache;

import com.google.gson.reflect.TypeToken;
import edu.hm.cs.fs.common.model.TeacherBooking;
import edu.hm.cs.fs.restapi.parser.BookingParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Fabio
 */
public class CachedBookingParser extends CachedParser<TeacherBooking> {
    private static final int UPDATETIME = 3;
    private static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    private static CachedBookingParser instance;

    /**
     * Creates a cached person parser.
     */
    private CachedBookingParser() {
        super(new BookingParser(), UPDATETIME, TIME_UNIT, UpdateType.NONE);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ArrayList<TeacherBooking>>() {
        }.getType();
    }

    public static CachedBookingParser getInstance() {
        if (instance == null) {
            instance = new CachedBookingParser();
        }
        return instance;
    }
}
