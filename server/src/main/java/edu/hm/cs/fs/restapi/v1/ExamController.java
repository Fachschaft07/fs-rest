package edu.hm.cs.fs.restapi.v1;

import com.google.common.base.Strings;
import edu.hm.cs.fs.common.model.Exam;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.restapi.parser.ExamParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Fabio on 03.09.2015.
 */
@RestController
public class ExamController {
    /**
     *
     * @param group
     * @param moduleId
     * @return
     */
    @RequestMapping("/rest/api/1/calendar/exam")
    public List<Exam> getExams(@RequestParam(value = "group", defaultValue = "") Group group,
                               @RequestParam(value = "moduleId", defaultValue = "") String moduleId) {
        return new ExamParser().parse().parallelStream()
                .filter(exam -> exam.getStudy() == group.getStudy())
                .filter(exam -> Strings.isNullOrEmpty(moduleId) || exam.getModule() != null &&
                        exam.getModule().getId().equalsIgnoreCase(moduleId))
                .collect(Collectors.toList());
    }
}
