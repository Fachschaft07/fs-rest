package edu.hm.cs.fs.restapi.v1;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabio
 */
@RestController
public class TimetableController {
    /*
    @RequestMapping("/rest/api/1/timetable/module")
    public List<LessonGroup> getLessonsByTimetableGroup(@RequestParam(value = "group") Group group) {
        Map<String, LessonGroup> result = new HashMap<>();
        new LessonFk07Parser(group).parse().stream().peek(lesson -> {
            LessonGroup lessonGroup = new LessonGroup();
            lessonGroup.setTeacher(new SimplePerson(lesson.getTeacher()));
            lessonGroup.setModule(new SimpleModule(lesson.getModule()));
            lessonGroup.setGroups(new ArrayList<>());
            final String key = lessonGroup.getModule().getId() + lessonGroup.getTeacher().getId();
            if (result.containsKey(key)) {

            } else {
                result.put(key, lessonGroup);
            }
        });
        return new ArrayList<>();
    }
    */
}
