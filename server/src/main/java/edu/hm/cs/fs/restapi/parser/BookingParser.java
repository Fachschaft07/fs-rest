package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.TeacherBooking;
import edu.hm.cs.fs.restapi.parser.cache.CachedModuleParser;
import edu.hm.cs.fs.restapi.parser.cache.CachedPersonParser;

import javax.xml.xpath.XPathConstants;
import java.util.List;
import java.util.Optional;
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

    public BookingParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<TeacherBooking> onCreateItems(final String rootPath) {
        // Parse Elements...
        final CachedPersonParser personParser = CachedPersonParser.getInstance();
        final CachedModuleParser moduleParser = CachedModuleParser.getInstance();

        return getXPathStream(rootPath + "/day")
                .flatMap(dayPath -> getXPathStream(dayPath + "/time"))
                .map(timePath -> {
                    final Optional<String> day = findString(timePath + "/booking/weekday/text()");

                    final Optional<String> room = findString(timePath + "/booking/room/text()");
                    final Optional<String> starttime = findString(timePath + "/booking/starttime/text()");

                    final Optional<String> moduleId = findString(timePath + "/booking/courses/course[1]/modul/text()");
                    final Optional<String> teacherId = findString(timePath + "/booking/teacher/text()");

                    if (day.isPresent() && room.isPresent() && starttime.isPresent() && moduleId.isPresent() && teacherId.isPresent()) {
                        TeacherBooking booking = new TeacherBooking();

                        // Convert name from r0009 to R0.009
                        booking.setRoom(room.map(name -> new StringBuilder(name.toUpperCase()))
                                .filter(name -> name.length() > 0)
                                .map(name -> name.insert(2, "."))
                                .map(StringBuilder::toString)
                                .orElse(""));

                        booking.setDay(day.map(Day::of).orElse(null));
                        booking.setTime(starttime.map(Time::of).orElse(null));

                        moduleParser.getById(moduleId.get()).ifPresent(booking::setModule);
                        personParser.getById(teacherId.get()).ifPresent(booking::setTeacher);

                        return booking;
                    }

                    return null;
                })
                .filter(booking -> booking != null)
                .collect(Collectors.toList());
    }
}
