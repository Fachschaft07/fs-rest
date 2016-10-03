package edu.hm.cs.fs.restapi.controller;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import edu.hm.cs.fs.common.model.*;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import edu.hm.cs.fs.restapi.parser.BookingParser;
import edu.hm.cs.fs.restapi.parser.cache.*;
import io.swagger.annotations.*;
import jdk.nashorn.internal.runtime.arrays.IteratorAction;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;

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
    @ApiOperation(value = "getLessonGroupsByGroup")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/timetable/modules", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "group", value = "Group in format [A-Z]{2}[0-9]{1}[A-Z]{1}", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
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
    @ApiOperation(value = "getLessons")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/timetable/lessons", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "group", value = "Group in format [A-Z]{2}[0-9]{1}[A-Z]{1}", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "module", value = "Module ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "teacher", value = "Teacher ID for more precision", required = false, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pk", value = "Number of placement group", required = false, dataType = "int", paramType = "query", defaultValue = "0")
    })
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public List<Lesson> getLessons(@RequestParam("group") Group group,
                                   @RequestParam("module") String moduleId,
                                   @RequestParam(value = "teacher", defaultValue = "") String teacherId,
                                   @RequestParam(value = "pk", defaultValue = "0") int pk) throws Exception {
        return CachedLessonParser.getInstance(group).getAll().parallelStream()
                .filter(lesson -> lesson.getModule()!=null&&moduleId.equals(lesson.getModule().getId()))
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

    /**
     * @param teacherId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "getLessonForTeacher")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/timetable/teacher", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "Teacher in format [A-Z]*", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public List<TeacherBooking> getLessonsForTeacher(@RequestParam("teacherId") String teacherId) throws Exception {

        return CachedBookingParser.getInstance()
                .getAll()
                .parallelStream()
                .filter(booking -> {
                    if(teacherId != null && booking.getTeacher() != null)
                        return teacherId.equals(booking.getTeacher().getId());

                    return false;
                })
                .collect(Collectors.toList());
    }
}
