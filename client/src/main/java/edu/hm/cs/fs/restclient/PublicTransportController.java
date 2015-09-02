package edu.hm.cs.fs.restclient;

import java.util.List;

import edu.hm.cs.fs.common.constant.PublicTransportLocation;
import edu.hm.cs.fs.common.model.PublicTransport;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * The PublicTransportController is the interface to access the REST-API. It provides calls to the
 * mvv.
 *
 * @author Fabio
 */
public interface PublicTransportController {
    /**
     * Request all departures from a specified location of the mvv.
     *
     * @param location to get the departures from.
     * @return a list with public transport possibilities.
     */
    @GET("/rest/api/publicTransport")
    List<PublicTransport> getPublicTransports(@Query("location") final PublicTransportLocation location);

    /**
     * Request all departures from a specified location of the mvv asynchronous.
     *
     * @param location to get the departures from.
     * @param callback to retrieve the results.
     */
    @GET("/rest/api/publicTransport")
    void getPublicTransports(@Query("location") final PublicTransportLocation location,
                             final Callback<List<PublicTransport>> callback);
}
