package edu.hm.cs.fs.restclient.typeadapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.hm.cs.fs.common.constant.Letter;
import edu.hm.cs.fs.common.constant.Semester;
import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.Group;

/**
 * Created by Fabio on 15.08.2015.
 */
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
        in.nextName();
        Study study = Study.valueOf(in.nextString());

        Letter letter = null;
        try {
            in.nextName();
            letter = Letter.valueOf(in.nextString());
        } catch (Exception ignored) {
            in.nextNull();
        }

        Semester semester = null;
        try {
            in.nextName();
            semester = Semester.valueOf(in.nextString());
        } catch (Exception ignored) {
            in.nextNull();
        }
        in.endObject();

        return new Group(study, semester, letter);
    }
}
