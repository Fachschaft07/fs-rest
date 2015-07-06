package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.Exam;
import edu.hm.cs.fs.restapi.parser.ExamParser;
import edu.hm.cs.fs.restapi.parser.Parser;

/**
 *
 */
@RestController
public class ExamController {
	private final Parser<Exam> parser;

	/**
	 *
	 */
	public ExamController() {
		parser = new ExamParser();
	}

	/**
	 *
	 * @return
	 */
	@RequestMapping("/rest/api/exams")
	public List<Exam> getExams() {
        return parser.parse();
    }

	/**
	 *
	 * @param group
	 * @param code
	 * @return
	 */
	@RequestMapping("/rest/api/exam")
	public List<Exam> getExams(@RequestParam(value="group", defaultValue = "") String group,
			@RequestParam(value="code", defaultValue = "") String code) {
        return getExams().stream()
        		.filter(exam -> exam.getGroup().toLowerCase().equals(group.toLowerCase()))
        		.filter(exam -> exam.getCode().equals(code))
        		.collect(Collectors.toList());
    }
}
