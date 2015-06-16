package edu.hm.cs.fs.rest.parser;

import java.io.InputStream;

public abstract class Parser<R> {

	public abstract R parse(final InputStream stream);

}
