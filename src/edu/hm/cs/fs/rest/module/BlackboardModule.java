package edu.hm.cs.fs.rest.module;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import edu.hm.cs.fs.rest.RESTInfo;
import edu.hm.cs.fs.rest.data.Message;
import edu.hm.cs.fs.rest.parser.BlackboardParser;
import edu.hm.cs.fs.rest.utils.Urls;
import edu.hm.cs.fs.rest.web.HttpURLConnector;

public class BlackboardModule extends Module<List<Message>> {

	private static final int NUMBER_OF_PARTS_ALL = 1;
	private static final int NUMBER_OF_PARTS_STUDY_GROUP = 2;
	private static final int NUMBER_OF_PARTS_SEMESTER = 3;
	private static final int NUMBER_OF_PARTS_LETTER = 4;

	public BlackboardModule() {
		super(new BlackboardParser());
	}

	@Override
	public List<Message> getData(RESTInfo restInfo) throws IOException {

		Map<String, String> parameters = restInfo.getParameters();
		String[] parts = restInfo.getPath().getParts();
		String url = "";
		
		int index = parts.length-2;
		
		switch (parts.length) {
			case NUMBER_OF_PARTS_LETTER:
				url = parts[index--]+url;
				
			case NUMBER_OF_PARTS_SEMESTER:
				url = parts[index--]+url;
				
			case NUMBER_OF_PARTS_STUDY_GROUP:
				url = "group/"+parts[index--]+url;
				
			case NUMBER_OF_PARTS_ALL:
				
				if(parameters.containsKey("search")){
					url += "/text?text="+parameters.get("search");
				}
				
				break;
		}

		HttpURLConnector connector = new HttpURLConnector(Urls.BLACKBOARD + url);
		HttpURLConnection connection = connector.connect();
		InputStream stream = connection.getInputStream();
		
		List<Message> blackboard = getParser().parse(stream);
		
		stream.close();
		connection.disconnect();
		
		return blackboard;
	}

}
