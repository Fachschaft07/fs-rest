package edu.hm.cs.fs.restapi.parser;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.RoomOccupation;

/**
 * All the rooms with their occupancy. (Url: <a href="http://fi.cs.hm.edu/fi/rest/public/timetable/room"
 * >http://fi.cs.hm.edu/fi/rest/public/timetable/room</a>)
 *
 * @author Fabio
 */
public class OccupiedParser extends AbstractXmlParser<RoomOccupation> {
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/timetable/room.xml";
    private static final String ROOT_NODE = "/list/timetable";

    /**
     *
     */
    public OccupiedParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<RoomOccupation> onCreateItems(final String rootPath) throws XPathExpressionException {
        // Parse Elements...
        final String name = findByXPath(rootPath + "/value/text()",
                XPathConstants.STRING, String.class);
        final int capacity = findByXPath(rootPath + "/capacity/text()",
                XPathConstants.NUMBER, Double.class).intValue();

        Map<Day, List<Time>> map = new HashMap<>();

        final int dayCount = getCountByXPath(rootPath + "/day");
        for (int dayIndex = 1; dayIndex <= dayCount; dayIndex++) {
            final String dayKey = findByXPath(rootPath + "/day[" + dayIndex + "]/weekday/text()",
                    XPathConstants.STRING, String.class);
            if (Strings.isNullOrEmpty(dayKey)) {
                continue;
            }
            final Day day = Day.of(dayKey);
            if (!map.containsKey(day)) {
                map.put(day, new ArrayList<>());
            }

            final int timeCount = getCountByXPath(rootPath + "/day[" + dayIndex + "]/time");
            for (int timeIndex = 1; timeIndex <= timeCount; timeIndex++) {
                final Optional<Time> time = Optional.ofNullable(Time.of(findByXPath(rootPath +
                                "/day[" + dayIndex + "]/time[" + timeIndex + "]/starttime/text()",
                        XPathConstants.STRING, String.class)));
                time.ifPresent(timeAvailable -> map.get(day).add(timeAvailable));
            }
        }

        RoomOccupation room = new RoomOccupation();
        room.setName(name);
        room.setCapacity(capacity);
        room.setOccupied(map);

        return Collections.singletonList(room);
    }
}
