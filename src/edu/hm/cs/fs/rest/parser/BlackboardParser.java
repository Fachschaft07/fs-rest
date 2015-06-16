package edu.hm.cs.fs.rest.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import edu.hm.cs.fs.rest.data.Message;

public class BlackboardParser extends Parser<List<Message>> {

	@Override
	public List<Message> parse(InputStream stream)  {
		try {
			final BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			String line;
			
			while((line = reader.readLine()) != null ){
				System.out.println(line);
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}

}
