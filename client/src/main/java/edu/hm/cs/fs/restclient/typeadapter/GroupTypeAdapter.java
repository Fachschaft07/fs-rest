package edu.hm.cs.fs.restclient.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import edu.hm.cs.fs.common.constant.Letter;
import edu.hm.cs.fs.common.constant.Semester;
import edu.hm.cs.fs.common.model.Group;

public class GroupTypeAdapter extends TypeAdapter<Group> {
    @Override
    public void write(final JsonWriter out, final Group value) throws IOException {
        out.beginObject();
        out.name("study").value(value.getStudy() != null ? value.getStudy().name() : null);
        out.name("semester").value(value.getSemester() != null ? value.getSemester().name() : null);
        out.name("letter").value(value.getLetter() != null ? value.getLetter().name() : null);
        out.endObject();
    }

    @Override
    public Group read(final JsonReader in) throws IOException {
        in.beginObject();
        final String[] content = new String[3];
        for (final String aContent : content) {
            extractContent(in, content);
        }
        in.endObject();

        StringBuilder builder = new StringBuilder();
        for (final String aContent : content) {
            if (aContent != null) {
                builder.append(aContent);
            }
        }

        return Group.of(builder.toString());
    }

    private void extractContent(final JsonReader in, final String[] content) throws IOException {
        final String name = in.nextName();
        if ("study".equals(name)) {
            content[0] = in.nextString();
        } else if ("semester".equals(name)) {
            try {
                content[1] = Integer.toString(Semester.valueOf(in.nextString()).getNumber());
            } catch (Exception ignored) {
                in.nextNull();
            }
        } else {
            try {
                content[2] = Letter.valueOf(in.nextString()).name();
            } catch (Exception ignored) {
                in.nextNull();
            }
        }
    }
}
