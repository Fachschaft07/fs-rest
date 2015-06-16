package edu.hm.cs.fs.rest.module;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.hm.cs.fs.rest.RESTInfo;
import edu.hm.cs.fs.rest.data.PublicTransport;
import edu.hm.cs.fs.rest.parser.MvvParser;
import edu.hm.cs.fs.rest.utils.Urls;
import edu.hm.cs.fs.rest.web.HttpURLConnector;

public class PublicTransportModule extends Module<List<PublicTransport>> {

	private static final String PARAMETER_LOCATION = "position";
	
	private final HttpURLConnector connectorLoth;
	private final HttpURLConnector connectorPasing;
	
	public PublicTransportModule() {
		super(new MvvParser()); 
		connectorLoth = new HttpURLConnector(Urls.MVV_LOTHSTR);
		connectorPasing = new HttpURLConnector(Urls.MVV_PASING);
		
	}
	
	@Override
	public List<PublicTransport> getData(RESTInfo restInfo) throws IOException {

		String location = "all";
		Map<String, String> parameters = restInfo.getParameters();
		
		if(parameters.containsKey(PARAMETER_LOCATION)){
			location = parameters.get(PARAMETER_LOCATION);
		}
		
		List<PublicTransport> transport = new ArrayList<PublicTransport>();
		
		if("pasing".equals(location)||"all".equals(location)){
			transport.addAll(connectAndParse(connectorPasing));
		}
		
		if("loth".equals(location)||"all".equals(location)){
			transport.addAll(connectAndParse(connectorLoth));
		}
		
		return transport;
	}

	private List<PublicTransport> connectAndParse(HttpURLConnector connector) throws IOException {
		HttpURLConnection connection = connectorPasing.connect();
		InputStream stream = connection.getInputStream();
		
		List<PublicTransport> transport = getParser().parse(stream);
		
		stream.close();
		connection.disconnect();
		
		return transport;
	}
	
}
