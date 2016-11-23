package edu.hm.cs.fs.restapi.parser;

import com.google.common.base.Strings;
import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.RoomType;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.RoomOccupation;

import javax.xml.xpath.XPathConstants;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<RoomOccupation> onCreateItems(final String rootPath) {
        final List<RoomOccupation> result = new ArrayList<>();

        // Parse Elements...
        final Optional<String> name = findString(rootPath + "/value/text()")
                .filter(tmp -> tmp.length() > 0)
                // Convert name from r0009 to R0.009
                .map(String::toUpperCase)
                .map(StringBuilder::new)
                .map(tmp -> tmp.insert(2, "."))
                .map(StringBuilder::toString);
        final Optional<Integer> capacity = findNumber(rootPath + "/capacity/text()").map(Double::intValue);

        final Map<Day, List<Time>> map = new HashMap<>();

        getXPathStream(rootPath + "/day")
                .forEach(path -> {
                    final Optional<Day> day = findString(path + "/weekday/text()").map(Day::of);
                    if (day.isPresent()) {
                        if (!map.containsKey(day.get())) {
                            map.put(day.get(), new ArrayList<>());
                        }

                        map.get(day.get()).addAll(
                                getXPathStream(path + "/time")
                                        .map(timePath -> findString(timePath + "/starttime/text()").map(Time::of).orElse(null))
                                        .filter(time -> time != null)
                                        .collect(Collectors.toList())
                        );
                    }
                });

        if(name.isPresent() && capacity.isPresent()) {
            RoomOccupation room = new RoomOccupation();
            room.setName(name.get());
            room.setCapacity(capacity.get());
            room.setOccupied(map);
            room.setRoomType(RoomType.getByName(name.get()));

            result.add(room);
        }

        return result;
    }

}
