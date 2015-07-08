package edu.hm.cs.fs.restclient;

import java.util.List;

import edu.hm.cs.fs.common.model.BlackboardEntry;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * The BlackboardController is the interface to access the REST-API. It provides calls to the blackboard.
 *
 * @author Fabio
 */
public interface BlackboardController {
    /**
     * Requests all blackboard entries.
     *
     * @return a list with blackboard entries.
     */
    @GET("/rest/api/blackboard/entry")
    List<BlackboardEntry> getEntries();

    /**
     * Requests all blackboard entries asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET("/rest/api/blackboard/entry")
    void getEntries(Callback<List<BlackboardEntry>> callback);
}
