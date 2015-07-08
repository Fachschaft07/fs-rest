package edu.hm.cs.fs.restapi.parser;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import com.google.common.base.Strings;
import edu.hm.cs.fs.common.model.Room;
import org.jsoup.helper.StringUtil;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.Occupied;

import java.util.*;

/**
 * All the rooms with their occupancy. (Url: <a href="http://fi.cs.hm.edu/fi/rest/public/timetable/room"
 * >http://fi.cs.hm.edu/fi/rest/public/timetable/room</a>)
 *
 * @author Fabio
 */
public class OccupiedParser extends AbstractXmlParser<Room> {
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/timetable/room.xml";
    private static final String ROOT_NODE = "/list/timetable";

    /**
     *
     */
    public OccupiedParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<Room> onCreateItems(final String rootPath) throws XPathExpressionException {
        // Parse Elements...
        final String name = findByXPath(rootPath + "/value/text()",
                XPathConstants.STRING, String.class);
        final int capacity = findByXPath(rootPath + "/capacity/text()",
                XPathConstants.NUMBER, Double.class).intValue();

        Map<Day, List<Time>> map = new HashMap<>();

        final int dayCount = getCountByXPath(rootPath + "/day");
        for (int dayIndex = 0; dayIndex < dayCount; dayIndex++) {
            final String dayKey = findByXPath(rootPath + "/day[" + dayIndex + "]/weekday/text()",
                    XPathConstants.STRING, String.class);
            if(Strings.isNullOrEmpty(dayKey)) {
                continue;
            }
            try {
                final Day day = Day.of(dayKey);
                if (!map.containsKey(day)) {
                    map.put(day, new ArrayList<>());
                }

                final Time time = Time.of(findByXPath(rootPath + "/day[" + dayIndex + "]/time/starttime/text()",
                        XPathConstants.STRING, String.class));
                map.get(day).add(time);
            } catch (IllegalArgumentException ignored) {
                System.err.println("Error: " + ignored.getMessage());
            }
        }

        Room room = new Room();
        room.setName(name);
        room.setCapacity(capacity);
        room.setOccupied(map);

        return Collections.singletonList(room);
    }
}
