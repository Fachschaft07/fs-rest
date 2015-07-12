package edu.hm.cs.fs.restclient;

import java.util.List;

import edu.hm.cs.fs.common.model.Presence;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * The FsController is the interface to access the REST-API. It provides calls to the fs.
 *
 * @author Fabio
 */
public interface FsController {
    /**
     * Requests all presence.
     *
     * @return a list with presence.
     */
    @GET("/rest/api/fs/presence")
    List<Presence> getPresence();

    /**
     * Requests all presence asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET("/rest/api/fs/presence")
    void getPresence(Callback<List<Presence>> callback);
}
