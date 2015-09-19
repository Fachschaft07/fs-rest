package edu.hm.cs.fs.restapi.parser.cache;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Lesson;
import edu.hm.cs.fs.restapi.parser.LessonParser;
import edu.hm.cs.fs.restapi.parser.ModuleParser;
import edu.hm.cs.fs.restapi.parser.PersonParser;

/**
 * @author Fabio
 */
public class CachedLessonParser extends CachedParser<Lesson> {
    /**
     * Creates a cached parser with a specified interval.
     *
     * @param group to parse the timetable from.
     */
    public CachedLessonParser(final Group group) {
        super(new LessonParser(new PersonParser(), new ModuleParser(new PersonParser()), group),
                "Timetable-" + group.toString(), 1, TimeUnit.DAYS);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ArrayList<Lesson>>() {
        }.getType();
    }
}
