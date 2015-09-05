package edu.hm.cs.fs.restapi.v1;

import com.google.common.base.Strings;
import edu.hm.cs.fs.common.model.Exam;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.restapi.parser.ExamParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.xpath.XPathExpressionException;

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
     * @throws IOException 
     * @throws XPathExpressionException 
     * @throws MalformedURLException 
     */
    @RequestMapping("/rest/api/1/exam")
    public List<Exam> getExams(@RequestParam(value = "group", defaultValue = "") Group group,
                               @RequestParam(value = "module", defaultValue = "") String moduleId) throws MalformedURLException, XPathExpressionException, IOException {
        return new ExamParser().parse().parallelStream()
                .filter(exam -> group == null || exam.getStudy() == group.getStudy())
                .filter(exam -> Strings.isNullOrEmpty(moduleId) || exam.getModule() != null &&
                        exam.getModule().getId().equalsIgnoreCase(moduleId))
                .collect(Collectors.toList());
    }
}
