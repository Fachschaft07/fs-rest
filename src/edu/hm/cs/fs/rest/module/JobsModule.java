package edu.hm.cs.fs.rest.module;

import java.io.IOException;
import java.util.List;

import edu.hm.cs.fs.rest.RESTInfo;
import edu.hm.cs.fs.rest.data.Article;

public class JobsModule extends Module<List<Article>> {

	public JobsModule() {
		super(null);
	}
	
	@Override
	public List<Article> getData(RESTInfo restInfo) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
