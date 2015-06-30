package edu.hm.cs.fs.restapi.parser;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.jsoup.helper.StringUtil;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.Occupied;

/**
 * All the rooms with their occupancy. (Url: <a href="http://fi.cs.hm.edu/fi/rest/public/timetable/room"
 * >http://fi.cs.hm.edu/fi/rest/public/timetable/room</a>)
 *
 * @author Fabio
 */
public class OccupiedParser extends AbstractXmlParser<Occupied> {
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/timetable/room.xml";
    private static final String ROOT_NODE = "/list/timetable/day/time/booking";

    /**
     * @param context
     */
    public OccupiedParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public Occupied onCreateItem(final String rootPath) throws XPathExpressionException {
        String room;
        Day day = null;
        Time time = null;

        // Parse Elements...
        room = findByXPath(rootPath + "/value/text()",
                XPathConstants.STRING, String.class);
        final String weekDay = findByXPath(rootPath + "/weekday/text()", XPathConstants.STRING, String.class);
        if (!StringUtil.isBlank(weekDay)) {
            day = Day.of(weekDay);
        }
        final String timeStr = findByXPath(rootPath + "/starttime/text()", XPathConstants.STRING, String.class);
        if (!StringUtil.isBlank(timeStr)) {
            time = Time.of(timeStr);
        }

        Occupied occupied = new Occupied();
        occupied.setRoom(room);
        occupied.setDay(day.toString());
        occupied.setTime(time.toString());

        return occupied;
    }
}
