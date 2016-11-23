package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Lesson;
import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimpleModule;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import org.jsoup.helper.StringUtil;
import org.springframework.util.StringUtils;

import javax.xml.xpath.XPathConstants;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Lesson> onCreateItems(String rootPath) {
        final Day day = findString(rootPath + "/weekday/text()").map(Day::of).orElse(null);
        return getXPathStream(rootPath + "/time")
                .flatMap(path -> getXPathStream(path + "/entry"))
                .parallel()
                .map(path -> {
                    final Optional<String> type = findString(path + "/type/text()")
                            .filter("filler"::equalsIgnoreCase);
                    final Optional<Integer[]> startTime = findString(path + "/starttime/text()")
                            .filter(tmp -> tmp.length() > 0)
                            .map(tmp -> tmp.split(":"))
                            .map(tmp -> new Integer[] { Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1])});
                    final String room = findString(path + "/room/text()")
                            .map(String::toUpperCase)
                            .filter(tmp -> tmp.length() > 0)
                            .map(StringBuilder::new)
                            .map(tmp -> tmp.insert(2, "."))
                            .map(StringBuilder::toString)
                            .orElse("");
                    final Optional<String> teacherId = findString(path + "/teacher/text()");
                    final String suffix = findString(path + "/suffix/text()").orElse("");
                    final Optional<String> moduleId = findString(path + "/title/text()");

                    if(type.isPresent() && startTime.isPresent() && teacherId.isPresent() && moduleId.isPresent()) {
                        final Lesson lesson = new Lesson();
                        lesson.setDay(day);
                        lesson.setSuffix(suffix);
                        lesson.setRoom(room);
                        lesson.setHour(startTime.get()[0]);
                        lesson.setMinute(startTime.get()[1]);
                        personParser.getById(teacherId.get()).map(SimplePerson::new).ifPresent(lesson::setTeacher);
                        moduleParser.getById(moduleId.get()).map(SimpleModule::new).ifPresent(lesson::setModule);
                        return lesson;
                    }
                    return null;
                })
                .filter(lesson -> lesson != null)
                .collect(Collectors.toList());
    }
}
