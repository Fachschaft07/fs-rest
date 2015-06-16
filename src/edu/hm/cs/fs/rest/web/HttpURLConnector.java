package edu.hm.cs.fs.rest.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpURLConnector {
	
	private URL url;
	
	public HttpURLConnector(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
		}
	}
	
	public HttpURLConnector(URL url) {
		this.url = url;
	}
	
	public HttpURLConnection connect() {
		HttpURLConnection connection = null;
		
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		} catch (IOException e){
		}
		
		return connection;
	}
	
	public void disconnect(HttpURLConnection connection) {
		connection.disconnect();
	}
	
}
