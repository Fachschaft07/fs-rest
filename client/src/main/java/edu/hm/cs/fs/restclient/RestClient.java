package edu.hm.cs.fs.restclient;

import com.google.gson.GsonBuilder;
import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.PublicTransportLocation;
import edu.hm.cs.fs.common.constant.RoomType;
import edu.hm.cs.fs.common.constant.StudentWorkMunich;
import edu.hm.cs.fs.common.model.*;
import edu.hm.cs.fs.common.model.simple.SimpleJob;
import edu.hm.cs.fs.common.model.simple.SimpleModule;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import edu.hm.cs.fs.common.model.simple.SimpleRoom;
import edu.hm.cs.fs.restclient.typeadapter.DateTypeAdapter;
import edu.hm.cs.fs.restclient.typeadapter.GroupTypeAdapter;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import java.util.Date;
import java.util.List;

/**
 * The rest client api.
 *
 * @author Fabio
 */
public interface RestClient {
    String ROOT_PATH = "/rest/api/1/";

    /**
     * The builder creates the connection to the rest client.
     */
    class Builder {
        private static final String ENDPOINT_ULR = "https://fs.cs.hm.edu";
        private String endpoint = ENDPOINT_ULR;

        /**
         * Specify another endpoint url. (e.g. for testing)
         *
         * @param url to connect to.
         * @return the builder.
         */
        public Builder setEndpoint(final String url) {
            endpoint = url;
            return this;
        }

        /**
         * Create an interface to communicate with the rest api.
         *
         * @return the controller.
         */
        public RestClient build() {
            return new Retrofit.Builder()
                    .baseUrl(endpoint)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .registerTypeAdapter(Group.class, new GroupTypeAdapter())
                            .registerTypeAdapter(Date.class, new DateTypeAdapter())
                            .create()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
                    .create(RestClient.class);
        }
    }

    ////////////////////////////////////////////////////////////////////
    //
    // Blackboard
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all blackboard entries.
     *
     * @return a list with blackboard entries.
     */
    @GET(ROOT_PATH + "blackboard")
    Call<List<BlackboardEntry>> getEntries();

    /**
     * @see #getEntries()
     */
    @GET(ROOT_PATH + "blackboard")
    Observable<List<BlackboardEntry>> getEntriesRx();

    /**
     * Requests all blackboard entries that fit the search.
     *
     * @param search string to search for.
     * @return a list with blackboard entries.
     */
    @GET(ROOT_PATH + "blackboard")
    Call<List<BlackboardEntry>> getEntries(@Query("search") String search);

    /**
     * @see #getEntries(String)
     */
    @GET(ROOT_PATH + "blackboard")
    Observable<List<BlackboardEntry>> getEntriesRx(@Query("search") String search);

    /**
     * Requests all blackboard entries for an study group.
     *
     * @param group representing an study group.
     * @return a list with blackboard entries.
     */
    @GET(ROOT_PATH + "blackboard")
    Call<List<BlackboardEntry>> getEntries(@Query("group") Group group);

    /**
     * @see #getEntries(Group)
     */
    @GET(ROOT_PATH + "blackboard")
    Observable<List<BlackboardEntry>> getEntriesRx(@Query("group") Group group);

    /**
     * Requests all blackboard entries for an study group.
     *
     * @param search to search for in blackboard entry.
     * @param group  representing an study group.
     * @param since  a long representing an date.
     * @param before a long representing an date.
     * @return a list with blackboard entries.
     */
    @GET(ROOT_PATH + "blackboard")
    Call<List<BlackboardEntry>> getEntries(@Query("search") String search, @Query("group") Group group, @Query("since") long since, @Query("before") long before);

    /**
     * @see #getEntries(String, Group, long, long)
     */
    @GET(ROOT_PATH + "blackboard")
    Observable<List<BlackboardEntry>> getEntriesRx(@Query("search") String search, @Query("group") Group group, @Query("since") long since, @Query("before") long before);

    /**
     * Requests all blackboard entries that are publish after 'since'.
     *
     * @param since a long representing an date.
     * @return a list with blackboard entries.
     */
    @GET(ROOT_PATH + "blackboard")
    Call<List<BlackboardEntry>> getEntriesSince(@Query("since") long since);

    /**
     * @see #getEntriesSince(long)
     */
    @GET(ROOT_PATH + "blackboard")
    Observable<List<BlackboardEntry>> getEntriesSinceRx(@Query("since") long since);

    /**
     * Requests all blackboard entries for an study group.
     *
     * @param search to search for in blackboard entry.
     * @param group  representing an study group.
     * @param since  a long representing an date.
     * @return a list with blackboard entries.
     */
    @GET(ROOT_PATH + "blackboard")
    Call<List<BlackboardEntry>> getEntriesSince(@Query("search") String search, @Query("group") Group group, @Query("since") long since);

    /**
     * @see #getEntriesSince(String, Group, long)
     */
    @GET(ROOT_PATH + "blackboard")
    Observable<List<BlackboardEntry>> getEntriesSinceRx(@Query("search") String search, @Query("group") Group group, @Query("since") long since);

    /**
     * Requests all blackboard entries that are publish before 'before'.
     *
     * @param before a long representing an date.
     * @return a list with blackboard entries.
     */
    @GET(ROOT_PATH + "blackboard")
    Call<List<BlackboardEntry>> getEntriesBefore(@Query("before") long before);

    /**
     * @see #getEntriesBefore(long)
     */
    @GET(ROOT_PATH + "blackboard")
    Observable<List<BlackboardEntry>> getEntriesBeforeRx(@Query("before") long before);

    /**
     * Requests all blackboard entries for an study group.
     *
     * @param search to search for in blackboard entry.
     * @param group  representing an study group.
     * @param before a long representing an date.
     * @return a list with blackboard entries.
     */
    @GET(ROOT_PATH + "blackboard")
    Call<List<BlackboardEntry>> getEntriesBefore(@Query("search") String search, @Query("group") Group group, @Query("before") long before);

    /**
     * @see #getEntriesBefore(String, Group, long)
     */
    @GET(ROOT_PATH + "blackboard")
    Observable<List<BlackboardEntry>> getEntriesBeforeRx(@Query("search") String search, @Query("group") Group group, @Query("before") long before);

    /**
     * Requests one blackboard entry by id.
     *
     * @return a list with one or none blackboard entry.
     */
    @GET(ROOT_PATH + "blackboard")
    Call<BlackboardEntry> getEntryById(@Query("id") String id);

    /**
     * @see #getEntryById(String)
     */
    @GET(ROOT_PATH + "blackboard")
    Observable<BlackboardEntry> getEntryByIdRx(@Query("id") String id);

    ////////////////////////////////////////////////////////////////////
    //
    // Calendar
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all termins.
     *
     * @return a list with termins.
     */
    @GET(ROOT_PATH + "calendar/termin")
    Call<List<Event>> getTermins();

    /**
     * @see #getTermins()
     */
    @GET(ROOT_PATH + "calendar/termin")
    Observable<List<Event>> getTerminsRx();

    /**
     * Requests all holidays.
     *
     * @return a list with holidays.
     */
    @GET(ROOT_PATH + "calendar/holiday")
    Call<List<Holiday>> getHolidays();

    /**
     * @see #getHolidays()
     */
    @GET(ROOT_PATH + "calendar/holiday")
    Observable<List<Holiday>> getHolidaysRx();

    ////////////////////////////////////////////////////////////////////
    //
    // Student Council
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all presence.
     *
     * @return a list with presence.
     */
    @GET(ROOT_PATH + "fs/presence")
    Call<List<Presence>> getPresence();

    /**
     * @see #getPresence()
     */
    @GET(ROOT_PATH + "fs/presence")
    Observable<List<Presence>> getPresenceRx();

    /**
     * Request all News from the FS Website.
     *
     * @return a list with all News.
     */
    @GET(ROOT_PATH + "fs/news")
    Call<List<News>> getNews();

    /**
     * @see #getNews()
     */
    @GET(ROOT_PATH + "fs/news")
    Observable<List<News>> getNewsRx();

    ////////////////////////////////////////////////////////////////////
    //
    // Job
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all jobs.
     *
     * @return a list with jobs.
     */
    @GET(ROOT_PATH + "jobs")
    Call<List<SimpleJob>> getJobs();

    /**
     * @see #getJobs()
     */
    @GET(ROOT_PATH + "jobs")
    Observable<List<SimpleJob>> getJobsRx();

    /**
     * Requests all searched jobs.
     *
     * @param searchContent the job title and description for matching.
     * @return a list with jobs.
     */
    @GET(ROOT_PATH + "jobs")
    Call<List<SimpleJob>> getJobs(@Query("search") final String searchContent);

    /**
     * @see #getJobs(String)
     */
    @GET(ROOT_PATH + "jobs")
    Observable<List<SimpleJob>> getJobsRx(@Query("search") final String searchContent);

    /**
     * Requests an job by id.
     *
     * @param id the job id.
     * @return a job.
     */
    @GET(ROOT_PATH + "job")
    Call<Job> getJobById(@Query("id") final String id);

    /**
     * @see #getJobById(String)
     */
    @GET(ROOT_PATH + "job")
    Observable<Job> getJobByIdRx(@Query("id") final String id);

    ////////////////////////////////////////////////////////////////////
    //
    // Rooms
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all free rooms.
     *
     * @param day    to search at.
     * @param hour   to search at.
     * @param minute to search at.
     * @return a list with free rooms.
     */
    @GET(ROOT_PATH + "room")
    Call<List<SimpleRoom>> getRoomByDateTime(@Query("type") RoomType type, @Query("day") Day day, @Query("hour") int hour,
                                             @Query("minute") int minute);

    /**
     * @see #getRoomByDateTime(RoomType, Day, int, int)
     */
    @GET(ROOT_PATH + "room")
    Observable<List<SimpleRoom>> getRoomByDateTimeRx(@Query("type") RoomType type, @Query("day") Day day, @Query("hour") int hour,
                                             @Query("minute") int minute);

    ////////////////////////////////////////////////////////////////////
    //
    // Timetable
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all lessons from a specified group.
     *
     * @param group to get the lessons from.
     * @return a list with lessons.
     */
    @GET(ROOT_PATH + "timetable/modules")
    Call<List<LessonGroup>> getLessonGroups(@Query("group") Group group);

    /**
     * @see #getLessonGroups(Group)
     */
    @GET(ROOT_PATH + "timetable/modules")
    Observable<List<LessonGroup>> getLessonGroupsRx(@Query("group") Group group);

    /**
     * Requests all lessons by the specified parameters.
     *
     * @param group    to get the correct lessons.
     * @param moduleId of the module.
     * @return a list with lessons.
     */
    @GET(ROOT_PATH + "timetable/lessons")
    Call<List<Lesson>> getLessons(@Query("group") Group group, @Query("module") String moduleId);

    /**
     * @see #getLessons(Group, String)
     */
    @GET(ROOT_PATH + "timetable/lessons")
    Observable<List<Lesson>> getLessonsRx(@Query("group") Group group, @Query("module") String moduleId);

    /**
     * Requests all lessons by the specified parameters.
     *
     * @param group     to get the correct lessons.
     * @param moduleId  of the module.
     * @param teacherId of the prof.
     * @return a list with lessons.
     */
    @GET(ROOT_PATH + "timetable/lessons")
    Call<List<Lesson>> getLessons(@Query("group") Group group, @Query("module") String moduleId,
                                  @Query("teacher") String teacherId);

    /**
     * @see #getLessons(Group, String, String)
     */
    @GET(ROOT_PATH + "timetable/lessons")
    Observable<List<Lesson>> getLessonsRx(@Query("group") Group group, @Query("module") String moduleId,
                                  @Query("teacher") String teacherId);

    /**
     * Requests all lessons by the specified parameters.
     *
     * @param group     to get the correct lessons.
     * @param moduleId  of the module.
     * @param teacherId of the prof.
     * @param pk        if it is a practical lecture.
     * @return a list with lessons.
     */
    @GET(ROOT_PATH + "timetable/lessons")
    Call<List<Lesson>> getLessons(@Query("group") Group group, @Query("module") String moduleId,
                                  @Query("teacher") String teacherId, @Query("pk") int pk);

    /**
     * @see #getLessons(Group, String, String, int)
     */
    @GET(ROOT_PATH + "timetable/lessons")
    Observable<List<Lesson>> getLessonsRx(@Query("group") Group group, @Query("module") String moduleId,
                                  @Query("teacher") String teacherId, @Query("pk") int pk);

    ////////////////////////////////////////////////////////////////////
    //
    // Module
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all modules in a small form for lower traffic usage.
     *
     * @return a list with modules.
     */
    @GET(ROOT_PATH + "modules")
    Call<List<SimpleModule>> getModules();

    /**
     * @see #getModules()
     */
    @GET(ROOT_PATH + "modules")
    Observable<List<SimpleModule>> getModulesRx();

    /**
     * Requests a module by id with all information.
     *
     * @param moduleId to request.
     * @return the module.
     */
    @GET(ROOT_PATH + "module")
    Call<Module> getModuleById(@Query("id") String moduleId);

    /**
     * @see #getModuleById(String)
     */
    @GET(ROOT_PATH + "module")
    Observable<Module> getModuleByIdRx(@Query("id") String moduleId);

    ////////////////////////////////////////////////////////////////////
    //
    // Exam
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all exams.
     *
     * @return a list with exams.
     */
    @GET(ROOT_PATH + "exam")
    Call<List<Exam>> getExams();

    /**
     * @see #getExams()
     */
    @GET(ROOT_PATH + "exam")
    Observable<List<Exam>> getExamsRx();

    /**
     * Requests all lessons of the specified study group and module.
     *
     * @param studyGroup to search for.
     * @param moduleId   to search for.
     * @return a list with exams.
     */
    @GET(ROOT_PATH + "exam")
    Call<List<Exam>> getExams(@Query("group") String studyGroup, @Query("module") String moduleId);

    /**
     * @see #getExams(String, String)
     */
    @GET(ROOT_PATH + "exam")
    Observable<List<Exam>> getExamsRx(@Query("group") String studyGroup, @Query("module") String moduleId);

    ////////////////////////////////////////////////////////////////////
    //
    // Person
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Request all the persons.
     *
     * @return a list with persons.
     */
    @GET(ROOT_PATH + "persons")
    Call<List<SimplePerson>> getPersons();

    /**
     * @see #getPersons()
     */
    @GET(ROOT_PATH + "persons")
    Observable<List<SimplePerson>> getPersonsRx();

    /**
     * Request the person by id.
     *
     * @param personId to get the person from.
     * @return a person.
     */
    @GET(ROOT_PATH + "person")
    Call<Person> getPersonById(@Query("id") final String personId);

    /**
     * @see #getPersonById(String)
     */
    @GET(ROOT_PATH + "person")
    Observable<Person> getPersonByIdRx(@Query("id") final String personId);

    ////////////////////////////////////////////////////////////////////
    //
    // Lost & Found
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Request the lost and founds.
     *
     * @return a person.
     */
    @GET(ROOT_PATH + "lostandfound")
    Call<List<LostFound>> getLostAndFound();

    /**
     * @see #getLostAndFound()
     */
    @GET(ROOT_PATH + "lostandfound")
    Observable<List<LostFound>> getLostAndFoundRx();

    /**
     * Request the lost and founds.
     *
     * @param content to search for.
     * @return a person.
     */
    @GET(ROOT_PATH + "lostandfound")
    Call<List<LostFound>> getLostAndFound(@Query("search") final String content);

    /**
     * @see #getLostAndFound(String)
     */
    @GET(ROOT_PATH + "lostandfound")
    Observable<List<LostFound>> getLostAndFoundRx(@Query("search") final String content);

    ////////////////////////////////////////////////////////////////////
    //
    // Mensa
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Requests all meals.
     *
     * @param location of the mensa or stucafe.
     * @return a list with meals.
     */
    @GET(ROOT_PATH + "meal")
    Call<List<Meal>> getMeals(@Query("location") StudentWorkMunich location);

    /**
     * @see #getMeals(StudentWorkMunich)
     */
    @GET(ROOT_PATH + "meal")
    Observable<List<Meal>> getMealsRx(@Query("location") StudentWorkMunich location);

    ////////////////////////////////////////////////////////////////////
    //
    // Public Transport
    //
    ///////////////////////////////////////////////////////////////////

    /**
     * Request all departures from a specified location of the mvv.
     *
     * @param location to get the departures from.
     * @return a list with public transport possibilities.
     */
    @GET(ROOT_PATH + "publicTransport")
    Call<List<PublicTransport>> getPublicTransports(
            @Query("location") final PublicTransportLocation location);

    /**
     * @see #getPublicTransports(PublicTransportLocation)
     */
    @GET(ROOT_PATH + "publicTransport")
    Observable<List<PublicTransport>> getPublicTransportsRx(
            @Query("location") final PublicTransportLocation location);

}
