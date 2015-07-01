package edu.hm.cs.fs.restapi.parser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.jsoup.helper.StringUtil;

import edu.hm.cs.fs.common.constant.ExamGroup;
import edu.hm.cs.fs.common.constant.ExamType;
import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.Exam;
import edu.hm.cs.fs.common.model.Group;

/**
 * The exams with every information. (Url: <a
 * href="http://fi.cs.hm.edu/fi/rest/public/exam"
 * >http://fi.cs.hm.edu/fi/rest/public/exam</a>)
 *
 * @author Fabio
 *
 */
public class ExamParser extends AbstractXmlParser<Exam> {
	private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/exam.xml";
	private static final String ROOT_NODE = "/examlist/exam";

	public ExamParser() {
		super(URL, ROOT_NODE);
	}

	@Override
	public Exam onCreateItem(final String rootPath) throws XPathExpressionException {
		String id;
		String code;
		Study group = null;
		String modul;
		String subtitle;
		List<String> references = new ArrayList<>();
		List<String> examiners = new ArrayList<>();
		ExamType type = null;
		String material;
		ExamGroup allocation = null;

		// Parse Elements...
		id = findByXPath(rootPath + "/id/text()", XPathConstants.STRING, String.class);
		code = findByXPath(rootPath + "/code/text()", XPathConstants.STRING, String.class);
		final String groupStr = findByXPath(rootPath + "/program/text()", XPathConstants.STRING, String.class);
		if (!StringUtil.isBlank(groupStr)) {
			group = Group.of(groupStr).getStudy();
		}
		modul = findByXPath(rootPath + "/modul/text()", XPathConstants.STRING, String.class);
		subtitle = findByXPath(rootPath + "/subtitle/text()", XPathConstants.STRING, String.class);

		final int countRef = getCountByXPath(rootPath + "/reference");
		for (int indexRef = 1; indexRef <= countRef; indexRef++) {
			final String ref = findByXPath(rootPath + "/reference[" + indexRef + "]/text()", XPathConstants.STRING, String.class);
			if (!StringUtil.isBlank(ref)) {
				references.add(ref);
			}
		}

		final int countExaminer = getCountByXPath(rootPath + "/examiner");
		for (int indexExaminer = 1; indexExaminer <= countExaminer; indexExaminer++) {
			final String examiner = findByXPath(rootPath + "/examiner[" + indexExaminer + "]/text()", XPathConstants.STRING, String.class);
			if (!StringUtil.isBlank(examiner)) {
				examiners.add(examiner);
			}
		}

		final String typeStr = findByXPath(rootPath + "/type/text()", XPathConstants.STRING, String.class);
		if (!StringUtil.isBlank(typeStr)) {
			type = ExamType.of(typeStr);
		}

		material = findByXPath(rootPath + "/material/text()", XPathConstants.STRING, String.class);
		final String allocationStr = findByXPath(rootPath + "/allocation/text()", XPathConstants.STRING, String.class);
		if (!StringUtil.isBlank(allocationStr)) {
			allocation = ExamGroup.of(allocationStr);
		}

		Exam exam = new Exam();
		exam.setId(id);
		exam.setModule(modul);
		exam.setAllocation(allocation.toString());
		exam.setCode(code);
		exam.setExaminers(examiners);
		exam.setGroup(group.toString());
		exam.setMaterial(material);
		exam.setReferences(references);
		exam.setSubtitle(subtitle);
		exam.setType(type.toString());

		return exam;
	}
}
