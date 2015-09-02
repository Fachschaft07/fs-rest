package edu.hm.cs.fs.restapi.parser;

import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public LessonFk07Parser(Group group) {
        super(URL + group.toString().toLowerCase(Locale.getDefault()) + ".xml", ROOT_NODE);
    }

    @Override
    public List<Lesson> onCreateItems(String rootPath) throws XPathExpressionException {
        List<Lesson> result = new ArrayList<>();
        Day day = null;
        Time time = null;

        final String weekday = findByXPath(rootPath + "/weekday/text()", XPathConstants.STRING, String.class);
        if (!StringUtil.isBlank(weekday)) {
            day = Day.of(weekday);
        }

        final int countTime = getCountByXPath(rootPath + "/time");
        for (int indexTime = 1; indexTime <= countTime; indexTime++) {
            final int countEntry = getCountByXPath(rootPath + "/time[" + indexTime + "]/entry");

            for (int indexEntry = 1; indexEntry <= countEntry; indexEntry++) {
                final String secondPath = rootPath + "/time[" + indexTime + "]/entry[" + indexEntry + "]";

                // If field 'type' contains >filler< continue with the next entry
                final String type = findByXPath(secondPath + "/type/text()", XPathConstants.STRING, String.class);
                if ("filler".equalsIgnoreCase(type)) {
                    continue;
                }

                // Else go on...
                final String startTimeStr = findByXPath(secondPath + "/starttime/text()", XPathConstants.STRING, String.class);
                if (!StringUtil.isBlank(startTimeStr)) {
                    time = Time.of(startTimeStr);
                }
                final String room = findByXPath(secondPath + "/room/text()", XPathConstants.STRING, String.class);
                final String teacherId = findByXPath(secondPath + "/teacher/text()", XPathConstants.STRING, String.class);
                final String suffix = findByXPath(secondPath + "/suffix/text()", XPathConstants.STRING, String.class);
                final String moduleId = findByXPath(secondPath + "/title/text()", XPathConstants.STRING, String.class);

                Lesson lesson = new Lesson();
                lesson.setDay(day);
                new CachedPersonParser().findByIdSimple(teacherId).ifPresent(lesson::setTeacher);
                lesson.setRoom(room);
                lesson.setSuffix(suffix);
                lesson.setTime(time);
                new CachedModuleParser().findByIdSimple(moduleId).ifPresent(lesson::setModule);

                result.add(lesson);
            }
        }
        return result;
    }
}
