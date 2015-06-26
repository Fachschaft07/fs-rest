package edu.hm.cs.fs.restclient;

import edu.hm.cs.fs.common.constant.PublicTransportLocation;
import edu.hm.cs.fs.common.model.PublicTransport;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

/**
 * The PublicTransportController is the interface to access the REST-API. It provides calls to the mvv.
 *
 * @author Fabio
 */
public interface PublicTransportController extends Controller {
    /**
     * Request all departures from a specified location of the mvv.
     *
     * @param location to get the departures from.
     * @return a list with public transport possibilities.
     */
    @GET("/mvv")
    List<PublicTransport> listAll(@Query("location") final PublicTransportLocation location);

    /**
     * Request all departures from a specified location of the mvv asynchronous.
     *
     * @param location to get the departures from.
     * @param callback to retrieve the results.
     */
    @GET("/mvv")
    void listAll(@Query("location") final PublicTransportLocation location, final Callback<List<PublicTransport>> callback);
}
