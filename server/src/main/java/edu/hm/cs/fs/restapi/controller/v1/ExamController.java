package edu.hm.cs.fs.restapi.controller.v1;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;

import edu.hm.cs.fs.common.model.Exam;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.restapi.parser.ExamParser;
import edu.hm.cs.fs.restapi.parser.ModuleParser;
import edu.hm.cs.fs.restapi.parser.PersonParser;

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
     * @throws Exception
     */
    @RequestMapping("/rest/api/1/exam")
    public List<Exam> getExams(@RequestParam(value = "group", defaultValue = "") Group group,
                               @RequestParam(value = "module", defaultValue = "") String moduleId) throws Exception {
        return new ExamParser(new PersonParser(), new ModuleParser(new PersonParser())).getAll().parallelStream()
                .filter(exam -> group == null || exam.getStudy() == group.getStudy())
                .filter(exam -> Strings.isNullOrEmpty(moduleId) || exam.getModule() != null &&
                        exam.getModule().getId().equalsIgnoreCase(moduleId))
                .collect(Collectors.toList());
    }
}
