package edu.hm.cs.fs.rest.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ResponseUtils {

	public enum ServeType {
		PLAIN("plain"), JSON("json"), XML("xml"), HTML("html");

		private final String type;

		private ServeType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

	}

	public static void serve(HttpServletResponse response, String outputType, Object data) throws IOException, IllegalArgumentException {
		ServeType serveType = ResponseUtils.getServeType(outputType);

		if (serveType == null) {
			throw new IllegalArgumentException("No matching Response Type found!");
		}

		response.setCharacterEncoding("UTF-8");
		
		switch ( serveType) {
			case JSON:
				serveJSON(response, data);
				break;
			case XML:
				serveXML(response, data);
				break;
			case HTML:
				serveHTML(response, data);
				break;
			case PLAIN:
			default:
				servePlain(response, data);
				break;
		}
	}

	public static void servePlain(HttpServletResponse response, Object text) {

	}

	public static void serveJSON(HttpServletResponse response, Object json) throws IOException {
		String jsonString = new Gson().toJson(json);

		response.setContentType("text/javascript");
		response.setContentLength(jsonString.length());

		response.getWriter().write(jsonString);
	}

	public static void serveXML(HttpServletResponse response, Object xml) {

	}

	public static void serveHTML(HttpServletResponse response, Object html) {

	}

	public static ServeType getServeType(String type) {
		return ServeType.valueOf(type.toUpperCase());
	}

	public static List<String> parsePath(String uri, String servletPath) {
		int length = servletPath.length();
		int index = uri.indexOf(servletPath);

		String path = uri.substring(index + length).replaceAll("/", " ").trim();

		return new ArrayList<String>(Arrays.asList(path.split(" ")));
	}
}
