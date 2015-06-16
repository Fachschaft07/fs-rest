package edu.hm.cs.fs.rest.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.hm.cs.fs.rest.data.Room;
import edu.hm.cs.fs.rest.data.Room.Type;

/**
 * @author Fabio
 * 
 */
public class RoomParser extends Parser<List<Room>> {
	public RoomParser() {
		super();
	}

	@Override
	public List<Room> parse(final InputStream stream) {
		final List<Room> roomList = new ArrayList<Room>();

		try {
			final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			final Document doc = builder.parse(stream);

			final NodeList nodes = doc.getElementsByTagName("room");
			for (int i = 0; i < nodes.getLength(); i++) {
				final Element element = (Element) nodes.item(i);
				final String parentName = nodes.item(i).getParentNode().getNodeName();

				if (parentName.equals("auditoriums")) {
					roomList.add(new Room(Type.HALL, getElementValue(element, "roomName"), "bis "
							+ getElementValue(element, "freeUntil")));
				}

				if (parentName.equals("laboratories")) {
					roomList.add(new Room(Type.LABORATORY, getElementValue(element, "roomName"), "bis "
							+ getElementValue(element, "freeUntil")));
				}
			}
		} catch (final ParserConfigurationException e) {
			//Log.e(TAG, "", e);
		} catch (final SAXException e) {
			//Log.e(TAG, "", e);
		} catch (final IOException e) {
			//Log.e(TAG, "", e);
		} catch (final NullPointerException e) {
			//Log.e(TAG, "", e);
		}

		return roomList;
	}

	private String getElementValue(final Element parent, final String label) {
		return parent.getElementsByTagName(label).item(0).getTextContent();
	}
}
