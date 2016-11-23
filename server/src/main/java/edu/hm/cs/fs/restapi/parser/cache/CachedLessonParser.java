package edu.hm.cs.fs.restapi.parser.cache;

import com.google.gson.reflect.TypeToken;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Lesson;
import edu.hm.cs.fs.restapi.parser.LessonParser;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Fabio
 */
public class CachedLessonParser extends CachedParser<Lesson> {

    private static Map<String, CachedLessonParser> instances = new HashMap<>();

    private static Logger LOGGER = Logger.getLogger(CachedLessonParser.class);

    /**
     * Creates a cached parser with a specified interval.
     *
     * @param group to parse the timetable from.
     */
    private CachedLessonParser(final Group group) {
        super(new LessonParser(CachedPersonParser.getInstance(), CachedModuleParser.getInstance(), group),
                "Timetable-" + group.toString(), 4, TimeUnit.HOURS, UpdateType.NONE);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ArrayList<Lesson>>() {
        }.getType();
    }

    public static CachedLessonParser getInstance(final Group group) {
        if (!instances.containsKey(group.toString())) {
            CachedLessonParser parser = new CachedLessonParser(group);

            try {
                parser.updateCache();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

            instances.put(group.toString(), parser);
        }
        return instances.get(group.toString());
    }
}
