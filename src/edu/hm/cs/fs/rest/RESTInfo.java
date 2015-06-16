package edu.hm.cs.fs.rest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RESTInfo {
	
	private final String format;
	private final Path path;

	private Map<String, String> parameters;
	
	public RESTInfo(String format, String[] path, Map<String, String> parameters) {
		this.format = format;
		this.path = new Path(path);
		this.parameters = new HashMap<String, String>();
		
		if(parameters!=null){
			this.parameters.putAll(parameters);
		}
	}

	public String getFormat() {
		return format;
	}

	public Path getPath() {
		return path;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	
	public class Path {
		
		private final String base;
		private final String[] parts;
		
		public Path(String[] parts) {
			base = parts[0];
			this.parts = Arrays.copyOfRange(parts, 1, parts.length);
		}
		
		public String getBase() {
			return base;
		}
		
		public String[] getParts() {
			return parts;
		}
	}
}
