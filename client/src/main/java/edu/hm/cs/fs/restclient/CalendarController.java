package edu.hm.cs.fs.restclient;

import java.util.List;

import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.Exam;
import edu.hm.cs.fs.common.model.Holiday;
import edu.hm.cs.fs.common.model.Termin;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * The CalendarController is the interface to access the REST-API. It provides calls to the termins, holidays, exams
 * and timetables.
 *
 * @author Fabio
 */
public interface CalendarController {
    /**
     * Requests all termins.
     *
     * @return a list with termins.
     */
    @GET("/rest/api/calendar/termin")
    List<Termin> getTermins();

    /**
     * Requests all termins asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET("/rest/api/calendar/termin")
    void getTermins(Callback<List<Termin>> callback);

    /**
     * Requests all holidays.
     *
     * @return a list with holidays.
     */
    @GET("/rest/api/calendar/holiday")
    List<Holiday> getHolidays();

    /**
     * Requests all holidays asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET("/rest/api/calendar/holiday")
    void getHolidays(Callback<List<Holiday>> callback);

    /**
     * Requests all exams.
     *
     * @return a list with exams.
     */
    @GET("/rest/api/calendar/exam")
    List<Exam> getExams();

    /**
     * Requests all exams asynchronous.
     *
     * @param callback to retrieve the result.
     */
    @GET("/rest/api/calendar/exam")
    void getExams(Callback<List<Exam>> callback);

    /**
     * Requests all exams which match the study and the module code.
     *
     * @param study of the exam (e.g. IF, IC).
     * @param moduleCodeId of the module (e.g. compiler).
     * @return a list with exams.
     */
    @GET("/rest/api/calendar/exam")
    List<Exam> getExams(@Query("study") Study study, @Query("module") String moduleCodeId);

    /**
     * Requests all exams which match the study and the module code asynchronous.
     *
     * @param study of the exam (e.g. IF, IC).
     * @param moduleCodeId of the module (e.g. compiler).
     * @param callback to retrieve the result.
     */
    @GET("/rest/api/calendar/exam")
    void getExams(@Query("study") Study study, @Query("module") String moduleCodeId, Callback<List<Exam>> callback);
}
