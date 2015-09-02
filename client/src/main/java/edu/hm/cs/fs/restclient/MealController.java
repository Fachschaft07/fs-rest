package edu.hm.cs.fs.restclient;

import java.util.List;

import edu.hm.cs.fs.common.constant.StudentWorkMunich;
import edu.hm.cs.fs.common.model.Meal;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * The MealController is the interface to access the REST-API. It provides calls to the mensa and
 * stucafe's in whole munich.
 *
 * @author Fabio
 */
public interface MealController {
    /**
     * Requests all meals.
     *
     * @param location of the mensa or stucafe.
     * @return a list with meals.
     */
    @GET("/rest/api/meal")
    List<Meal> getMeals(@Query("location") StudentWorkMunich location);

    /**
     * Requests all meals asynchronous.
     *
     * @param location of the mensa or stucafe.
     * @param callback to retrieve the result.
     */
    @GET("/rest/api/meal")
    void getMeals(@Query("location") StudentWorkMunich location, Callback<List<Meal>> callback);
}
