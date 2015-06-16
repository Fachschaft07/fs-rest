package edu.hm.cs.fs.rest.module;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

import edu.hm.cs.fs.rest.RESTInfo;
import edu.hm.cs.fs.rest.data.Meal;
import edu.hm.cs.fs.rest.parser.MensaParser;
import edu.hm.cs.fs.rest.utils.Urls;
import edu.hm.cs.fs.rest.web.HttpURLConnector;

public class MealsModule extends Module<List<Meal>> {

	private final HttpURLConnector connector;
	
	public MealsModule() {
		super(new MensaParser());
		connector = new HttpURLConnector(Urls.MENSA);
	}
	
	@Override
	public List<Meal> getData(RESTInfo restInfo) throws IOException {
		HttpURLConnection connection = connector.connect();
		InputStream stream = connection.getInputStream();
		
		List<Meal> meals = getParser().parse(stream);
		
		stream.close();
		connection.disconnect();
		
		return meals;
	}

}
