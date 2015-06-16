package edu.hm.cs.fs.rest.module;

import java.io.IOException;

import edu.hm.cs.fs.rest.RESTInfo;
import edu.hm.cs.fs.rest.parser.Parser;

public abstract class Module<R> {

	private final Parser<R> parser;
	
	protected Module(Parser<R> parser) {
		this.parser = parser;
	}
	
	protected Parser<R> getParser() {
		return parser;
	}
	
	public abstract R getData(RESTInfo restInfo) throws IOException;
	
}
