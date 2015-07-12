package edu.hm.cs.fs.restclient;

import java.util.List;

import edu.hm.cs.fs.common.model.Meal;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * The MealController is the interface to access the REST-API. It provides calls to the mensa and stucafe's in
 * whole munich.
 *
 * @author Fabio
 */
public interface MealController extends Controller {
    /**
     * Requests all meals.
     *
     * @return a list with meals.
     */
    @GET("/rest/api/meal")
    List<Meal> getMeals();

    /**
     * Requests all meals asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET("/rest/api/meal")
    void getMeals(Callback<List<Meal>> callback);
}
