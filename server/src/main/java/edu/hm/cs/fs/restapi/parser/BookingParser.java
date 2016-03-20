package edu.hm.cs.fs.restapi.parser;

import com.google.common.base.Strings;
import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.RoomType;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.Booking;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.RoomOccupation;
import edu.hm.cs.fs.common.model.TeacherBooking;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import edu.hm.cs.fs.restapi.parser.cache.CachedModuleParser;
import edu.hm.cs.fs.restapi.parser.cache.CachedPersonParser;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * All the rooms with their occupancy. (Url: <a href="http://fi.cs.hm.edu/fi/rest/public/timetable/room"
 * >http://fi.cs.hm.edu/fi/rest/public/timetable/room</a>)
 *
 * @author Fabio
 */
public class BookingParser extends AbstractXmlParser<TeacherBooking> {
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/timetable/room.xml";
    private static final String ROOT_NODE = "/list/timetable";

    /**
     *
     */
    public BookingParser() {
        super(URL, ROOT_NODE);  
    }

    @Override
    public List<TeacherBooking> onCreateItems(final String rootPath) throws Exception {
        // Parse Elements...

        final CachedPersonParser personParser = CachedPersonParser.getInstance();
        final CachedModuleParser moduleParser = CachedModuleParser.getInstance();


        List<TeacherBooking> bookings = new ArrayList<>();

        getXPathStream(rootPath + "/day")
                .forEach(dayPath -> {
                    try {
                        getXPathStream(dayPath + "/time")
                                .forEach(timePath -> {
                                    String room = "";
                                    String teacherId = "";
                                    String moduleId = "";
                                    String day = "";
                                    String starttime = "";

                                    try {

                                        day = findByXPath(timePath + "/booking/weekday/text()", XPathConstants.STRING, String.class);

                                        room = findByXPath(timePath + "/booking/room/text()", XPathConstants.STRING, String.class);
                                        starttime = findByXPath(timePath + "/booking/starttime/text()", XPathConstants.STRING, String.class);

                                        moduleId = findByXPath(timePath + "/booking/courses/course[1]/modul/text()", XPathConstants.STRING, String.class);
                                        teacherId = findByXPath(timePath + "/booking/teacher/text()", XPathConstants.STRING, String.class);

                                        TeacherBooking booking = new TeacherBooking();

                                        // Convert name from r0009 to R0.009
                                        StringBuilder roomNameBuilder = new StringBuilder(room.toUpperCase());
                                        if(!room.isEmpty()) {
                                            roomNameBuilder.insert(2, '.');
                                        }
                                        booking.setRoom(roomNameBuilder.toString());

                                        booking.setDay(day.isEmpty()?null:Day.of(day));
                                        booking.setTime(starttime.isEmpty()?null:Time.of(starttime));

                                        moduleParser.getById(moduleId).ifPresent(booking::setModule);
                                        personParser.getById(teacherId).ifPresent(booking::setTeacher);

                                        bookings.add(booking);
                                    } catch(Exception e){
                                        throw new RuntimeException(e);
                                    }
                                });
                    } catch(Exception e){
                        throw new RuntimeException(e);
                    }
                });

        return bookings;
    }

}
