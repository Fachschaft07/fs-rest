package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimpleModule;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import org.jsoup.helper.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Lesson;
import edu.hm.cs.fs.restapi.parser.cache.CachedModuleParser;
import edu.hm.cs.fs.restapi.parser.cache.CachedPersonParser;

public class LessonFk07Parser extends AbstractXmlParser<Lesson> {
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/timetable/group/";
    private static final String ROOT_NODE = "/timetable/day";
    private final ByIdParser<Person> personParser;
    private final ByIdParser<Module> moduleParser;

    public LessonFk07Parser(final ByIdParser<Person> personParser, final ByIdParser<Module> moduleParser, Group group) {
        super(URL + group.toString().toLowerCase(Locale.getDefault()) + ".xml", ROOT_NODE);
        this.personParser = personParser;
        this.moduleParser = moduleParser;
    }

    @Override
    public List<Lesson> onCreateItems(String rootPath) throws Exception {
        final Day day;
        final String weekday = findByXPath(rootPath + "/weekday/text()", XPathConstants.STRING, String.class);
        if (!StringUtil.isBlank(weekday)) {
            day = Day.of(weekday);
        } else {
            day = null;
        }

        return getXPathStream(rootPath + "/time")
                .flatMap(path -> {
                    try {
                        return getXPathStream(path + "/entry");
                    } catch (XPathExpressionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(path -> {
                    try {
                        Lesson lesson = new Lesson();
                        lesson.setDay(day);

                        // If field 'type' contains >filler< continue with the next entry
                        final String type = findByXPath(path + "/type/text()", XPathConstants.STRING, String.class);
                        if ("filler".equalsIgnoreCase(type)) {
                            return null;
                        }

                        // Else go on...
                        final String startTimeStr = findByXPath(path + "/starttime/text()", XPathConstants.STRING, String.class);
                        if (!StringUtil.isBlank(startTimeStr)) {
                            lesson.setHour(Integer.parseInt(startTimeStr.split(":")[0]));
                            lesson.setMinute(Integer.parseInt(startTimeStr.split(":")[1]));
                        }
                        final String room = findByXPath(path + "/room/text()", XPathConstants.STRING, String.class);
                        final String teacherId = findByXPath(path + "/teacher/text()", XPathConstants.STRING, String.class);
                        final String suffix = findByXPath(path + "/suffix/text()", XPathConstants.STRING, String.class);
                        final String moduleId = findByXPath(path + "/title/text()", XPathConstants.STRING, String.class);

                        personParser.getById(teacherId).map(SimplePerson::new).ifPresent(lesson::setTeacher);
                        lesson.setRoom(room);
                        lesson.setSuffix(suffix);
                        moduleParser.getById(moduleId).map(SimpleModule::new).ifPresent(lesson::setModule);
                        return lesson;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(lesson -> lesson != null)
                .collect(Collectors.toList());
    }
}
