package edu.hm.cs.fs.restapi.parser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * An very abstract parser for every type of web content parsing.
 *
 * @author Fabio
 */
public abstract class AbstractContentParser<T> implements Parser<T> {
	/** The url to parse. */
	private final String mUrl;
	private final Integer interval;

	private Date nextPoll;
	private List<T> cache;

	/**
	 * Creates an abstract content parser.
	 *
	 * @param url to parse.
	 */
	public AbstractContentParser(final String url, final Integer interval) {
		this.mUrl = url;
		this.interval = interval;
	}

	/**
	 * Downloads and reads the content from the web if a connection is
	 * available. Otherwise the offline file will be read.
	 *
	 * @return a list with the content.
	 */
	public final List<T> parse() {
		final Calendar now = Calendar.getInstance();
		if (cache == null || now.after(nextPoll)) {
			cache = read(mUrl);
			now.add(Calendar.MILLISECOND, interval);
			nextPoll = now.getTime();
		}
		return cache;
	}

	/**
	 * Reads the content from the specified url and parse it to objects.
	 *
	 * @param url to parse.
	 *
	 * @return the objects in a list.
	 */
	public abstract List<T> read(String url);
}
