package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Lesson;
import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.Person;

import java.util.List;

/**
 * @author Fabio
 */
public class LessonParser implements Parser<Lesson> {
    private final ByIdParser<Person> personParser;
    private final ByIdParser<Module> moduleParser;
    private final Group group;

    /**
     * Creates an abstract parser for xml content.
     */
    public LessonParser(final ByIdParser<Person> personParser,
                        final ByIdParser<Module> moduleParser, final Group group) {
        this.personParser = personParser;
        this.moduleParser = moduleParser;
        this.group = group;
    }

    @Override
    public List<Lesson> getAll() {
        final List<Lesson> lessons = new LessonFk07Parser(personParser, moduleParser, group).getAll();
        if (Study.IB == group.getStudy()) {
            lessons.addAll(new LessonFk10Parser(group).getAll());
        }
        return lessons;
    }
}
