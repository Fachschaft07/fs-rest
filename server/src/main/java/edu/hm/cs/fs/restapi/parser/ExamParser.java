package edu.hm.cs.fs.restapi.parser;

import com.google.common.base.Strings;
import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimpleModule;
import org.jsoup.helper.StringUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import edu.hm.cs.fs.common.constant.ExamGroup;
import edu.hm.cs.fs.common.constant.ExamType;
import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.Exam;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.simple.SimplePerson;

/**
 * The exams with every information. (Url: <a href="http://fi.cs.hm.edu/fi/rest/public/exam"
 * >http://fi.cs.hm.edu/fi/rest/public/exam</a>)
 *
 * @author Fabio
 */
public class ExamParser extends AbstractXmlParser<Exam> {
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/exam/date.xml";
    private static final String ROOT_NODE = "/examdate/day";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final ByIdParser<Person> personParser;
    private final ByIdParser<Module> moduleParser;

    public ExamParser(final ByIdParser<Person> personParser, final ByIdParser<Module> moduleParser) {
        super(URL, ROOT_NODE);
        this.personParser = personParser;
        this.moduleParser = moduleParser;
    }

    @Override
    public List<Exam> onCreateItems(final String rootPath) throws Exception {
        final String date = findByXPath(rootPath + "/date/text()", XPathConstants.STRING, String.class);
        return getXPathStream(rootPath + "/time")
                .map(path -> {
                    try {
                        final Date timestamp = DATE_FORMAT.parse(date + " " + findByXPath(path + "/time/text()",
                                XPathConstants.STRING, String.class));

                        return getXPathStream(path + "/examination")
                                .map(basePath -> {
                                    try {
                                        final List<String> rooms = getXPathStream(basePath + "/room")
                                                .map(roomPath -> {
                                                    try {
                                                        return findByXPath(roomPath + "/text()",
                                                                XPathConstants.STRING, String.class);
                                                    } catch (XPathExpressionException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                })
                                                .filter(room -> !Strings.isNullOrEmpty(room))
                                                .collect(Collectors.toList());

                                        return getXPathStream(basePath + "/exam")
                                                .map(examPath -> {
                                                    try {
                                                        final String code = findByXPath(examPath + "/code/text()",
                                                                XPathConstants.STRING, String.class);
                                                        final String groupStr = findByXPath(examPath + "/program/text()",
                                                                XPathConstants.STRING, String.class);
                                                        Study group = null;
                                                        if (!StringUtil.isBlank(groupStr)) {
                                                            group = Group.of(groupStr).getStudy();
                                                        }
                                                        final String moduleId = findByXPath(examPath + "/modul/text()",
                                                                XPathConstants.STRING, String.class);
                                                        final String subtitle = findByXPath(examPath + "/subtitle/text()",
                                                                XPathConstants.STRING, String.class);

                                                        final List<Study> references = getXPathStream(examPath + "/reference")
                                                                .map(refPath -> {
                                                                    try {
                                                                        return findByXPath(refPath + "/text()",
                                                                                XPathConstants.STRING, String.class);
                                                                    } catch (XPathExpressionException e) {
                                                                        throw new RuntimeException(e);
                                                                    }
                                                                })
                                                                .map(Study::of)
                                                                .collect(Collectors.toList());

                                                        final List<SimplePerson> examiners = getXPathStream(basePath + "/examiner")
                                                                .map(examinerPath -> {
                                                                    try {
                                                                        return findByXPath(examinerPath + "/text()",
                                                                                XPathConstants.STRING, String.class);
                                                                    } catch (XPathExpressionException e) {
                                                                        throw new RuntimeException(e);
                                                                    }
                                                                })
                                                                .map(person -> {
                                                                    try {
                                                                        return personParser.getById(person);
                                                                    } catch (Exception e) {
                                                                        throw new RuntimeException(e);
                                                                    }
                                                                })
                                                                .filter(Optional::isPresent)
                                                                .map(Optional::get)
                                                                .map(SimplePerson::new)
                                                                .collect(Collectors.toList());

                                                        final String typeStr = findByXPath(basePath + "/type/text()",
                                                                XPathConstants.STRING, String.class);
                                                        ExamType type = null;
                                                        if (!StringUtil.isBlank(typeStr)) {
                                                            type = ExamType.of(typeStr);
                                                        }

                                                        final String material = findByXPath(basePath + "/material/text()",
                                                                XPathConstants.STRING, String.class);
                                                        final String allocationStr = findByXPath(basePath + "/allocation/text()",
                                                                XPathConstants.STRING, String.class);
                                                        ExamGroup allocation = null;
                                                        if (!StringUtil.isBlank(allocationStr)) {
                                                            allocation = ExamGroup.of(allocationStr);
                                                        }

                                                        Exam exam = new Exam();
                                                        moduleParser.getById(moduleId)
                                                                .map(SimpleModule::new)
                                                                .ifPresent(exam::setModule);
                                                        exam.setAllocation(allocation);
                                                        exam.setCode(code);
                                                        exam.setExaminers(examiners);
                                                        exam.setStudy(group);
                                                        exam.setMaterial(material);
                                                        exam.setReferences(references);
                                                        exam.setSubtitle(subtitle);
                                                        exam.setType(type);
                                                        exam.setDate(timestamp);
                                                        exam.setRooms(rooms);
                                                        return exam;
                                                    } catch (Exception e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                })
                                                .collect(Collectors.toList());
                                    } catch (XPathExpressionException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .flatMap(Collection::parallelStream)
                                .collect(Collectors.toList());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
    }
}
