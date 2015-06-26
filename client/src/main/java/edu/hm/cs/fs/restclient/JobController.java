package edu.hm.cs.fs.restclient;

import java.util.List;

import edu.hm.cs.fs.common.model.Job;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * The JobController is the interface to access the REST-API. It provides only calls which belong to
 * <a href="http://fi.cs.hm.edu/fi/rest/public/job">jobs</a>.
 *
 * @author Fabio
 */
public interface JobController extends Controller {
    /**
     * Requests all jobs.
     *
     * @return a list with jobs.
     */
    @GET("/job")
    List<Job> listAll();

    /**
     * Requests all jobs asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET("/job")
    void listAll(final Callback<List<Job>> callback);

    /**
     * Requests all searched jobs.
     *
     * @param searchContent the job title and description for matching.
     * @return a list with jobs.
     */
    @GET("/job")
    List<Job> search(@Query("search") final String searchContent);

    /**
     * Requests all searched jobs asynchronous.
     *
     * @param search the job title and description for matching.
     * @param callback to retrieve the result.
     */
    @GET("/job")
    void search(@Query("search") final String search, final Callback<List<Job>> callback);
}
