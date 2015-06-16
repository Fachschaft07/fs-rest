package edu.hm.cs.fs.rest.module;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import edu.hm.cs.fs.rest.RESTInfo;
import edu.hm.cs.fs.rest.data.Room;
import edu.hm.cs.fs.rest.parser.RoomParser;
import edu.hm.cs.fs.rest.utils.Urls;
import edu.hm.cs.fs.rest.web.HttpURLConnector;

public class RoomsModule extends Module<List<Room>> {
	
	public RoomsModule() {
		super(new RoomParser());
	}
	
	@Override
	public List<Room> getData(RESTInfo restInfo) throws IOException {
		
		final Calendar calendar = Calendar.getInstance();
    	String weekday =  ""+calendar.get(Calendar.DAY_OF_WEEK);
    	String hour = ""+calendar.get(Calendar.HOUR_OF_DAY);
    	String minutes = ""+calendar.get(Calendar.MINUTE);
    	
    	Map<String, String> parameters = restInfo.getParameters();
    	
    	// get weekday
    	if(parameters.containsKey("weekday")){
    		weekday = parameters.get("weekday");
    	}
    	
    	// get hour
    	if(parameters.containsKey("hour")){
    		hour = parameters.get("hour");
    	}
    	
    	// get minute
    	if(parameters.containsKey("minutes")){
    		minutes = parameters.get("minutes");
    	}
    	
    	HttpURLConnector connector = new HttpURLConnector(Urls.ROOMS + weekday + "&clockTime="+hour+":"+minutes);
    	HttpURLConnection connection = connector.connect();
    	InputStream stream = connection.getInputStream();
    	
    	List<Room> rooms = getParser().parse(stream);
    	
    	stream.close();
    	connection.disconnect();
		
		return rooms;
	}

}
