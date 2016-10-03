package edu.hm.cs.fs.restapi.parser;

import java.util.*;
import java.util.stream.Collectors;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import com.google.common.collect.Ordering;
import edu.hm.cs.fs.restapi.UrlHandler;
import edu.hm.cs.fs.restapi.UrlInfo;
import org.jsoup.helper.StringUtil;
import org.springframework.util.StringUtils;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Lesson;
import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimpleModule;
import edu.hm.cs.fs.common.model.simple.SimplePerson;

public class LessonFk07Parser extends AbstractXmlParser<Lesson> {
    private static final UrlInfo INFO = UrlHandler.getUrlInfo(UrlHandler.Url.LESSONFK7);

    private final ByIdParser<Person> personParser;
    private final ByIdParser<Module> moduleParser;

    public LessonFk07Parser(final ByIdParser<Person> personParser, final ByIdParser<Module> moduleParser, Group group) {
        super(INFO.getRequestUrl() + group.toString().toLowerCase(Locale.getDefault()) + ".xml", INFO.getRoot());
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

                        final Set<String> rooms = new TreeSet<String>();
                        getXPathStream(path + "/room")
                                .forEach(roomPath -> {
                                    try {
                                        final String room = findByXPath(roomPath + "/text()", XPathConstants.STRING, String.class);

                                        if(room != null && !StringUtils.isEmpty(room)){
                                            StringBuilder roomNameBuilder = new StringBuilder(room.toUpperCase());
                                            roomNameBuilder.insert(2, '.');
                                            rooms.add(roomNameBuilder.toString());
                                        }

                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                });

                        //final String room = findByXPath(path + "/room/text()", XPathConstants.STRING, String.class);
                        final String teacherId = findByXPath(path + "/teacher/text()", XPathConstants.STRING, String.class);
                        final String suffix = findByXPath(path + "/suffix/text()", XPathConstants.STRING, String.class);
                        final String moduleId = findByXPath(path + "/title/text()", XPathConstants.STRING, String.class);

                        personParser.getById(teacherId).map(SimplePerson::new).ifPresent(lesson::setTeacher);
                        moduleParser.getById(moduleId).map(SimpleModule::new).ifPresent(lesson::setModule);

                        lesson.setRooms(rooms);
                        lesson.setSuffix(suffix);

                        return lesson;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(lesson -> lesson != null)
                .collect(Collectors.toList());
    }
}
