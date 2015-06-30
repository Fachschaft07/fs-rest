package edu.hm.cs.fs.restapi.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.hm.cs.fs.common.constant.PublicTransportLocation;
import edu.hm.cs.fs.common.model.PublicTransport;

/**
 * The mvv with every information.<br>
 * (Url (Lothstr.): <a href=
 * "http://www.mvg-live.de/ims/dfiStaticAnzeige.svc?haltestelle=Hochschule+M%fcnchen+%28Lothstra%dfe%29&tram=checked"
 * >http://www.mvg-live.de/ims/dfiStaticAnzeige.svc?haltestelle=Hochschule+M%
 * fcnchen+%28Lothstra%dfe%29&tram=checked</a><br>
 * Url (Pasing): <a href=
 * "http://www.mvg-live.de/ims/dfiStaticAnzeige.svc?haltestelle=Avenariusplatz&bus=checked"
 * >http://www.mvg-live.de/ims/dfiStaticAnzeige.svc?haltestelle=Avenariusplatz&
 * bus=checked</a>)
 *
 * @author Fabio
 */
public class PublicTransportParser extends AbstractHtmlParser<PublicTransport> {
	private static final String MVV_LOTHSTR = "http://www.mvg-live.de/ims/dfiStaticAnzeige.svc?haltestelle=Hochschule+M%fcnchen+%28Lothstra%dfe%29&tram=checked";
	private static final String MVV_PASING = "http://www.mvg-live.de/ims/dfiStaticAnzeige.svc?haltestelle=Avenariusplatz&bus=checked";

	/**
	 * Creates a parser for the mvv public transport content.
	 *
	 * @param location of the departure.
	 */
	public PublicTransportParser(final PublicTransportLocation location) {
		super(location == PublicTransportLocation.PASING ? MVV_PASING : MVV_LOTHSTR);
	}

	@Override
	public List<PublicTransport> readDoc(final Document document) {
		final List<PublicTransport> result = new ArrayList<>();

		final Elements rows = document.getElementsByTag("tr");
		for (final Element row : rows) {
			if (row.hasClass("rowOdd") || row.hasClass("rowEven")) {
				final String line = row.getElementsByClass("lineColumn").get(0).text();
				final String station = row.getElementsByClass("stationColumn").get(0).text();

				final int inMin = Integer.parseInt(row.getElementsByClass("inMinColumn").get(0).text());
				final Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MINUTE, -inMin);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				final Date departure = cal.getTime();

				final PublicTransport publicTransport = new PublicTransport();
				publicTransport.setLine(line);
				publicTransport.setDestination(station);
				publicTransport.setDeparture(departure);
				result.add(publicTransport);
			}
		}

		return result;
	}
}
