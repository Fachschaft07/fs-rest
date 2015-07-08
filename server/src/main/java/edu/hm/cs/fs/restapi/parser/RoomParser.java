package edu.hm.cs.fs.restapi.parser;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import java.util.Collections;
import java.util.List;

import edu.hm.cs.fs.common.model.Room;

/**
 * Created by Fabio on 27.04.2015.
 */
public class RoomParser extends AbstractXmlParser<Room> {
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/timetable/room.xml";
    private static final String ROOT_NODE = "/list/timetable";

    public RoomParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<Room> onCreateItems(String rootPath) throws XPathExpressionException {
        String name;

        // Parse Elements...
        name = findByXPath(rootPath + "/value/text()",
                XPathConstants.STRING, String.class);

        Room room = new Room();
        room.setName(name);

        return Collections.singletonList(room);
    }
}
