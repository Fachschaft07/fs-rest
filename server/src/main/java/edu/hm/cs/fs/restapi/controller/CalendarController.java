package edu.hm.cs.fs.restapi.controller;

import com.google.common.base.Strings;
import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.*;
import edu.hm.cs.fs.restapi.parser.ExamParser;
import edu.hm.cs.fs.restapi.parser.LessonFk07Parser;
import edu.hm.cs.fs.restapi.parser.LessonFk10Parser;
import edu.hm.cs.fs.restapi.parser.TerminParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fabio
 */
@RestController
public class CalendarController {
    /**
     *
     * @return
     */
    @RequestMapping("/rest/api/calendar/termin")
    public List<Termin> termin() {
        return new TerminParser().parse().parallelStream()
                .filter(termin -> !termin.getSubject().endsWith("erster Tag") &&
                        !termin.getSubject().endsWith("letzter Tag"))
                .sorted((termin1, termin2) -> termin1.getDate().compareTo(termin2.getDate()))
                .collect(Collectors.toList());
    }

    /**
     *
     * @return
     */
    @RequestMapping("/rest/api/calendar/holiday")
    public List<Holiday> holiday() {
        final List<Termin> termins = new TerminParser().parse();
        return termins.parallelStream()
                .filter(termin -> termin.getSubject().endsWith("erster Tag"))
                .map(termin -> {
                    Holiday holiday = new Holiday();
                    holiday.setName(extractName(termin.getSubject(), termin.getDate()));
                    holiday.setStart(termin.getDate());
                    termins.parallelStream()
                            .filter(termin1 -> termin1.getSubject().endsWith("letzter Tag"))
                            .filter(termin1 -> extractName(termin1.getSubject(), termin1.getDate())
                                    .equalsIgnoreCase(holiday.getName()))
                            .findFirst()
                            .ifPresent(termin1 -> holiday.setEnd(termin1.getDate()));
                    return holiday;
                })
                .sorted((holiday1, holiday2) -> holiday1.getStart().compareTo(holiday2.getStart()))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param subject
     * @param date
     * @return
     */
    private String extractName(String subject, Date date) {
        String tmpName = subject.substring(0, subject.indexOf(",")).trim();
        if (tmpName.startsWith("Weihnachtsferien") && subject.endsWith("erster Tag")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            tmpName += "/" + (cal.get(Calendar.YEAR) + 1);
        } else if (tmpName.startsWith("Weihnachtsferien") && subject.endsWith("letzter Tag")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            tmpName = tmpName.replaceAll("[0-9]+", "") + (cal.get(Calendar.YEAR) - 1) + "/" + cal.get(Calendar.YEAR);
        }
        return tmpName;
    }

    /**
     *
     * @param study
     * @param module
     * @return
     */
    @RequestMapping("/rest/api/calendar/exam")
    public List<Exam> exam(@RequestParam(value="study", defaultValue = "") String study,
                           @RequestParam(value="module", defaultValue = "") String module) {
        return new ExamParser().parse().parallelStream()
                .filter(exam -> Strings.isNullOrEmpty(study) || exam.getStudy() == Group.of(study).getStudy())
                .filter(exam -> Strings.isNullOrEmpty(module) || exam.getModule() != null &&
                        exam.getModule().getName().equalsIgnoreCase(module))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param group
     * @param module
     * @return
     */
    @RequestMapping("/rest/api/calendar/timetable")
    public List<Lesson> timetable(@RequestParam(value = "group") String group,
                               @RequestParam(value = "module", defaultValue = "") String module) {
        final Group realGroup = Group.of(group);
        // Read all available lessons
        final List<Lesson> lessons = new LessonFk07Parser(realGroup).parse();
        if(realGroup.getStudy() == Study.IB) {
            lessons.addAll(new LessonFk10Parser(realGroup).parse());
        }
        return lessons.parallelStream()
                .filter(lesson -> module.isEmpty() || lesson.getModule().getName().equalsIgnoreCase(module))
                .collect(Collectors.toList());
    }
}
