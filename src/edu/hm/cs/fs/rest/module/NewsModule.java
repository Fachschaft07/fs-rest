package edu.hm.cs.fs.rest.module;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import edu.hm.cs.fs.rest.RESTInfo;
import edu.hm.cs.fs.rest.data.Article;
import edu.hm.cs.fs.rest.parser.NewsParser;
import edu.hm.cs.fs.rest.utils.Urls;
import edu.hm.cs.fs.rest.web.HttpURLConnector;

public class NewsModule extends Module<List<Article>> {

	public NewsModule() {
		super(new NewsParser());
	}
	
	@Override
	public List<Article> getData(RESTInfo restInfo) throws IOException {
		
		String url = Urls.NEWS;
		
		Map<String, String> parameters = restInfo.getParameters();
		
		if(parameters.containsKey("search")){
			url += "&"+parameters.get("search");
		}
		
		HttpURLConnector connector = new HttpURLConnector(url);
		HttpURLConnection connection = connector.connect();
		InputStream stream = connection.getInputStream();
		
		List<Article> articles = getParser().parse(stream);
		
		stream.close();
		connection.disconnect();
		
		return articles;
	}

}
