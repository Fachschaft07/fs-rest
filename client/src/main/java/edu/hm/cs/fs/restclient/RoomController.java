package edu.hm.cs.fs.restclient;

import java.util.List;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.Room;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Fabio on 21.07.2015.
 */
public interface RoomController {
    /**
     * Requests all free rooms.
     *
     * @param day to search at.
     * @param time to search at.
     * @return a list with free rooms.
     */
    @GET("/rest/api/calendar/holiday")
    List<Room> getHolidays(@Query("day") Day day, @Query("time")Time time);

    /**
     * Requests all free rooms asynchronous.
     *
     * @param day to search at.
     * @param time to search at.
     * @param callback to retrieve the result.
     */
    @GET("/rest/api/calendar/holiday")
    void getHolidays(@Query("day") Day day, @Query("time")Time time, Callback<List<Room>> callback);
}
