package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import edu.hm.cs.fs.restapi.parser.cache.CachedExamParser;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * @param group
     * @param moduleId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "getExams")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/exam", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "group", value = "Group in format [A-Z]{2}[0-9]{1}[A-Z]{1}", required = false, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "code", value = "Code of the exam (eg. 007)", required = false, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "module", value = "Module ID", required = false, dataType = "string", paramType = "query", defaultValue = "")
    })
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public List<Exam> getExams(@RequestParam(value = "group", defaultValue = "") Group group,
                               @RequestParam(value = "code", defaultValue = "") String code,
                               @RequestParam(value = "module", defaultValue = "") String moduleId) throws Exception {
        return CachedExamParser.getInstance().getAll().parallelStream()
                .filter(exam -> group == null || exam.getStudy() == group.getStudy())
                .filter(exam -> Strings.isNullOrEmpty(code) || exam.getCode().equals(code))
                .filter(exam -> Strings.isNullOrEmpty(moduleId) || exam.getModule() != null &&
                        exam.getModule().getId().equalsIgnoreCase(moduleId))
                .collect(Collectors.toList());
    }
}
