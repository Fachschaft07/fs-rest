package edu.hm.cs.fs.restapi.controller.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;

import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Lesson;
import edu.hm.cs.fs.common.model.LessonGroup;
import edu.hm.cs.fs.restapi.parser.cache.CachedLessonParser;

/**
 * @author Fabio
 */
@RestController
public class TimetableController {
    /**
     * @param group
     * @return
     * @throws Exception
     */
    @RequestMapping("/rest/api/1/timetable/modules")
    public List<LessonGroup> getLessonsGroups(@RequestParam("group") Group group) throws Exception {
        Map<String, LessonGroup> result = new HashMap<>();
        CachedLessonParser.getInstance(group).getAll().stream().forEach(lesson -> {
            LessonGroup lessonGroup = new LessonGroup();
            lessonGroup.setGroup(group);
            lessonGroup.setTeacher(lesson.getTeacher());
            lessonGroup.setModule(lesson.getModule());
            lessonGroup.setGroups(new ArrayList<>());
            String key = "";

            if (lessonGroup.getModule() != null) {
                key += lessonGroup.getModule().getId();
            }

            if (lessonGroup.getTeacher() != null) {
                key += lessonGroup.getTeacher().getId();
            }

            if (!result.containsKey(key)) {
                result.put(key, lessonGroup);
            }
            if (lesson.getSuffix() != null) {
                final List<Integer> groups = result.get(key).getGroups();
                final Matcher matcher = Pattern.compile("([0-9]+)\\.").matcher(lesson.getSuffix());
                while (matcher.find()) {
                    final int pk = Integer.parseInt(matcher.group(1));
                    if (!groups.contains(pk)) {
                        groups.add(pk);
                    }
                    Collections.sort(groups);
                }
            }
        });
        return new ArrayList<>(result.values());
    }

    /**
     * @param group
     * @param moduleId
     * @param teacherId
     * @param pk
     * @return
     * @throws Exception
     */
    @RequestMapping("/rest/api/1/timetable/lessons")
    public List<Lesson> getLessons(@RequestParam("group") Group group,
                                   @RequestParam("module") String moduleId, @RequestParam(value = "teacher", defaultValue = "") String teacherId,
                                   @RequestParam(value = "pk", defaultValue = "0") int pk) throws Exception {
        return CachedLessonParser.getInstance(group).getAll().parallelStream()
                .filter(lesson -> moduleId.equals(lesson.getModule().getId()))
                .filter(lesson -> !Strings.isNullOrEmpty(teacherId) && lesson.getTeacher() != null)
                .filter(lesson -> teacherId.equals(lesson.getTeacher().getId()))
                .filter(lesson -> {
                    if (Strings.isNullOrEmpty(lesson.getSuffix())
                            || !lesson.getSuffix().contains("Praktikum")
                            && !lesson.getSuffix().contains("Teilgruppe")
                            && !lesson.getSuffix().contains("Übung")) {
                        return true; // Normal lesson
                    }
                    final Matcher matcher = Pattern.compile("([0-9]+)\\.").matcher(lesson.getSuffix());
                    while (matcher.find()) {
                        if (Integer.parseInt(matcher.group(1)) == pk) {
                            return true;
                        }
                    }
                    return false;
                }).collect(Collectors.toList());
    }
}
