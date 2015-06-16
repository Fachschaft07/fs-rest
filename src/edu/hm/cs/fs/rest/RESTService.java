package edu.hm.cs.fs.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.hm.cs.fs.rest.RESTInfo.Path;
import edu.hm.cs.fs.rest.module.BlackboardModule;
import edu.hm.cs.fs.rest.module.ExamModule;
import edu.hm.cs.fs.rest.module.JobsModule;
import edu.hm.cs.fs.rest.module.MealsModule;
import edu.hm.cs.fs.rest.module.MeetingsModule;
import edu.hm.cs.fs.rest.module.Module;
import edu.hm.cs.fs.rest.module.NewsModule;
import edu.hm.cs.fs.rest.module.PresencesModule;
import edu.hm.cs.fs.rest.module.PublicTransportModule;
import edu.hm.cs.fs.rest.module.RoomsModule;
import edu.hm.cs.fs.rest.utils.ResponseUtils;

/**
 * Servlet implementation class RestService
 */
@WebServlet("/service/*")
public class RESTService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final Map<String, Module<?>> modules;

	public RESTService() {
		// init modules
		modules = new HashMap<String, Module<?>>();

		modules.put("exams", new ExamModule());
		modules.put("jobs", new JobsModule());
		modules.put("blackboard", new BlackboardModule());
		modules.put("news", new NewsModule());
		modules.put("meetings", new MeetingsModule());
		modules.put("rooms", new RoomsModule());
		modules.put("presences", new PresencesModule());
		modules.put("publicTransport", new PublicTransportModule());
		modules.put("meals", new MealsModule());

	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RESTInfo info;
		Path path;
		Module<?> module;
		
		
		try {
			info = parse(request.getPathInfo(), request.getQueryString());
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			// TODO send error message
			return;
		}

		path = info.getPath();
		module = modules.get(path.getBase());

		if(module==null){
			// TODO send error message
			return;
		}
		
		ResponseUtils.serve(response, info.getFormat(), module.getData(info));
	}

	private RESTInfo parse(String pathInfo, String query) throws IllegalArgumentException, UnsupportedEncodingException {
		pathInfo = pathInfo.toLowerCase();
		pathInfo = pathInfo.replaceAll("/", " ");
		pathInfo = pathInfo.trim();
		
		String[] uriParts =  pathInfo.split(" ");
		String[] pairs = null;
		Map<String, String> query_pairs = null;

		if (uriParts.length < 2) {
			throw new IllegalArgumentException("Wrong URL Format!");
		}

		if (query != null) {
			query_pairs = new HashMap<>();
			pairs = query.split("&");

			for (String pair : pairs) {
				int idx = pair.indexOf("=");
				query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
			}
		}

		return new RESTInfo(uriParts[0],  Arrays.copyOfRange(uriParts, 1, uriParts.length), query_pairs);
	}

}
