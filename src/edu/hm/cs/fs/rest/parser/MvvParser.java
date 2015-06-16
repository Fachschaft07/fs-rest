package edu.hm.cs.fs.rest.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.hm.cs.fs.rest.data.PublicTransport;

/**
 * @author Fabio
 * 
 */
public class MvvParser extends Parser<List<PublicTransport>> {
	
	private final int NR_OF_ITEMS_TO_LOAD = 6;

	private final Pattern linePattern = Pattern.compile("(.*)(<td class=\"lineColumn\">)(\\d{1,3})(</td>)(.*)");
	private final Pattern timePattern = Pattern.compile("(.*)(<td class=\"inMinColumn\">)(\\d{1,3})(</td>)(.*)");
	
	@Override
	public List<PublicTransport> parse(InputStream stream) {
		final ArrayList<PublicTransport> content = new ArrayList<PublicTransport>();
		try {
			final BufferedReader br = new BufferedReader(new InputStreamReader(stream, "ISO-8859-15"));

			String line = br.readLine();

			int index = 0;

			Matcher m;

			String lineNr = "";
			String dest = "";
			String time = "";

			while (line != null) {

				if (index < NR_OF_ITEMS_TO_LOAD) {
					line = br.readLine();

					m = linePattern.matcher(line);
					if (m.matches()) {
						lineNr = m.group(3);
						continue;
					}

					line = line.replace('\t', ' ').trim();
					if (!line.startsWith("<") && !line.equals("")) {
						dest = line;
						continue;
					}

					m = timePattern.matcher(line);
					if (m.matches()) {
						time = m.group(3);
						final int lineNumber = Integer.parseInt(lineNr.trim());
						final int depTime = Integer.parseInt(time.trim());
						content.add(new PublicTransport(lineNumber, dest.trim(), depTime));
						index++;
						continue;
					}
				} else {
					break;
				}
			}
		} catch (final IOException e) {
			//Log.e(TAG, "", e);
		} catch (final NullPointerException e) {
			//Log.e(TAG, "", e);
		}

		return content;
	}
}
