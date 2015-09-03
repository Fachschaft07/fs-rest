package edu.hm.cs.fs.restclient;

import edu.hm.cs.fs.common.constant.*;
import edu.hm.cs.fs.common.model.*;
import edu.hm.cs.fs.common.model.simple.SimpleJob;
import edu.hm.cs.fs.common.model.simple.SimpleModule;
import edu.hm.cs.fs.common.model.simple.SimpleRoom;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

/**
 * The Rest Client of Version 1.
 *
 * @author Fabio
 */
public interface RestClientV1 {
    String ROOT_PATH = "/rest/api/1/";

    ////////////////////////////////////////////////////////////////////
    //
    //          Blackboard
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all blackboard entries.
     *
     * @return a list with blackboard entries.
     */
    @GET(ROOT_PATH + "blackboard/entry")
    List<BlackboardEntry> getEntries();

    /**
     * Requests all blackboard entries asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET(ROOT_PATH + "blackboard/entry")
    void getEntries(Callback<List<BlackboardEntry>> callback);

    ////////////////////////////////////////////////////////////////////
    //
    //          Calendar
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all termins.
     *
     * @return a list with termins.
     */
    @GET(ROOT_PATH + "calendar/termin")
    List<Termin> getTermins();

    /**
     * Requests all termins asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET(ROOT_PATH + "calendar/termin")
    void getTermins(Callback<List<Termin>> callback);

    /**
     * Requests all holidays.
     *
     * @return a list with holidays.
     */
    @GET(ROOT_PATH + "calendar/holiday")
    List<Holiday> getHolidays();

    /**
     * Requests all holidays asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET(ROOT_PATH + "calendar/holiday")
    void getHolidays(Callback<List<Holiday>> callback);

    ////////////////////////////////////////////////////////////////////
    //
    //          Student Council
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all presence.
     *
     * @return a list with presence.
     */
    @GET(ROOT_PATH + "fs/presence")
    List<Presence> getPresence();

    /**
     * Requests all presence asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET(ROOT_PATH + "fs/presence")
    void getPresence(Callback<List<Presence>> callback);

    ////////////////////////////////////////////////////////////////////
    //
    //          Job
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all jobs.
     *
     * @return a list with jobs.
     */
    @GET(ROOT_PATH + "job")
    List<SimpleJob> getJobs();

    /**
     * Requests all jobs asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET(ROOT_PATH + "job")
    void getJobs(final Callback<List<SimpleJob>> callback);

    /**
     * Requests all searched jobs.
     *
     * @param searchContent the job title and description for matching.
     * @return a list with jobs.
     */
    @GET(ROOT_PATH + "job")
    List<SimpleJob> getJobsByContent(@Query("search") final String searchContent);

    /**
     * Requests all searched jobs asynchronous.
     *
     * @param search   the job title and description for matching.
     * @param callback to retrieve the result.
     */
    @GET(ROOT_PATH + "job")
    void getJobsByContent(@Query("search") final String search, final Callback<List<SimpleJob>> callback);

    ////////////////////////////////////////////////////////////////////
    //
    //          Rooms
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all free rooms.
     *
     * @param day  to search at.
     * @param time to search at.
     * @return a list with free rooms.
     */
    @GET(ROOT_PATH + "room")
    List<SimpleRoom> getRoomByDateTime(@Query("day") Day day, @Query("time") Time time);

    /**
     * Requests all free rooms asynchronous.
     *
     * @param day      to search at.
     * @param time     to search at.
     * @param callback to retrieve the result.
     */
    @GET(ROOT_PATH + "room")
    void getRoomByDateTime(@Query("day") Day day, @Query("time") Time time, Callback<List<SimpleRoom>> callback);

    ////////////////////////////////////////////////////////////////////
    //
    //          Module
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all modules in a small form for lower traffic usage.
     *
     * @return a list with modules.
     */
    @GET(ROOT_PATH + "modules")
    List<SimpleModule> getModules();

    /**
     * Requests all modules in a small form for lower traffic usage asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET(ROOT_PATH + "modules")
    void getModules(Callback<List<SimpleModule>> callback);

    /**
     * Requests a module by id with all information.
     *
     * @param moduleId to request.
     * @return the module or <code>null</code>.
     */
    @GET(ROOT_PATH + "module")
    Module getModuleById(@Query("id") String moduleId);

    /**
     * Requests a module by id with all information asynchronous.
     *
     * @param moduleId to request.
     * @param callback to retrive the result.
     */
    @GET(ROOT_PATH + "module")
    void getModuleById(@Query("id") String moduleId, Callback<Module> callback);

    ////////////////////////////////////////////////////////////////////
    //
    //          Exam
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all exams.
     *
     * @return a list with exams.
     */
    @GET(ROOT_PATH + "exam")
    List<Exam> getExams();

    /**
     * Requests all exams asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET(ROOT_PATH + "exam")
    void getExams(Callback<List<Exam>> callback);

    /**
     * Requests all lessons of the specified study group and module.
     *
     * @param studyGroup to search for.
     * @param moduleName to search for.
     * @return a list with exams.
     */
    @GET(ROOT_PATH + "exam")
    List<Exam> getExams(@Query("group") String studyGroup, @Query("module") String moduleName);

    /**
     * Requests all lessons of the specified study group and module asynchronous.
     *
     * @param studyGroup to search for.
     * @param moduleName to search for.
     * @param callback   to retrieve the result.
     */
    @GET(ROOT_PATH + "exam")
    void getExams(@Query("group") String studyGroup, @Query("module") String moduleName,
                  Callback<List<Exam>> callback);

    ////////////////////////////////////////////////////////////////////
    //
    //          Person
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Request the person by id.
     *
     * @param personId to get the person from.
     * @return a person.
     */
    @GET(ROOT_PATH + "person")
    Person getPersonById(@Query("id") final String personId);

    /**
     * Request the person by id asynchronous.
     *
     * @param personId to get the person from.
     * @param callback to retrieve the results.
     */
    @GET(ROOT_PATH + "person")
    void getPersonById(@Query("id") final String personId,
                             final Callback<List<PublicTransport>> callback);

    ////////////////////////////////////////////////////////////////////
    //
    //          Mensa
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all meals.
     *
     * @param location of the mensa or stucafe.
     * @return a list with meals.
     */
    @GET(ROOT_PATH + "meal")
    List<Meal> getMeals(@Query("location") StudentWorkMunich location);

    /**
     * Requests all meals asynchronous.
     *
     * @param location of the mensa or stucafe.
     * @param callback to retrieve the result.
     */
    @GET(ROOT_PATH + "meal")
    void getMeals(@Query("location") StudentWorkMunich location, Callback<List<Meal>> callback);

    ////////////////////////////////////////////////////////////////////
    //
    //          Public Transport
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Request all departures from a specified location of the mvv.
     *
     * @param location to get the departures from.
     * @return a list with public transport possibilities.
     */
    @GET(ROOT_PATH + "publicTransport")
    List<PublicTransport> getPublicTransports(@Query("location") final PublicTransportLocation location);

    /**
     * Request all departures from a specified location of the mvv asynchronous.
     *
     * @param location to get the departures from.
     * @param callback to retrieve the results.
     */
    @GET(ROOT_PATH + "publicTransport")
    void getPublicTransports(@Query("location") final PublicTransportLocation location,
                             final Callback<List<PublicTransport>> callback);
}
