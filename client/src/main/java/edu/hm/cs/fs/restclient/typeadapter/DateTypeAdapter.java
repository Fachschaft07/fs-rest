package edu.hm.cs.fs.restclient.typeadapter;

import java.io.IOException;
import java.util.Date;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Created by Fabio on 15.08.2015.
 */
public class DateTypeAdapter extends TypeAdapter<Date> {
    @Override
    public void write(final JsonWriter out, final Date value) throws IOException {
        out.value(value.getTime());
    }

    @Override
    public Date read(final JsonReader in) throws IOException {
        return new Date(in.nextLong());
    }
}
