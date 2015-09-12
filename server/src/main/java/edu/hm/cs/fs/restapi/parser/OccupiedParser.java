package edu.hm.cs.fs.restapi.parser;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<RoomOccupation> onCreateItems(final String rootPath) throws Exception {
        // Parse Elements...
        final String name = findByXPath(rootPath + "/value/text()",
                XPathConstants.STRING, String.class);
        final int capacity = findByXPath(rootPath + "/capacity/text()",
                XPathConstants.NUMBER, Double.class).intValue();

        Map<Day, List<Time>> map = new HashMap<>();

        getXPathStream(rootPath + "/day")
                .forEach(path -> {
                    try {
                        final String dayKey = findByXPath(path + "/weekday/text()",
                                XPathConstants.STRING, String.class);
                        if (Strings.isNullOrEmpty(dayKey)) {
                            return;
                        }
                        final Day day = Day.of(dayKey);
                        if (!map.containsKey(day)) {
                            map.put(day, new ArrayList<>());
                        }

                        map.get(day).addAll(getXPathStream(path + "/time")
                                .map(timePath -> {
                                    try {
                                        return findByXPath(timePath + "/starttime/text()",
                                                XPathConstants.STRING, String.class);
                                    } catch (XPathExpressionException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .filter(time -> !Strings.isNullOrEmpty(time))
                                .map(Time::of)
                                .collect(Collectors.toList()));
                    } catch (XPathExpressionException e) {
                        throw new RuntimeException(e);
                    }
                });

        RoomOccupation room = new RoomOccupation();
        room.setName(name);
        room.setCapacity(capacity);
        room.setOccupied(map);

        return Collections.singletonList(room);
    }
}
