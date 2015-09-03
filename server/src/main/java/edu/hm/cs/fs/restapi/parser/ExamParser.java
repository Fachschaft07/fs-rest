package edu.hm.cs.fs.restapi.parser;

import org.jsoup.helper.StringUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import edu.hm.cs.fs.common.constant.ExamGroup;
import edu.hm.cs.fs.common.constant.ExamType;
import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.Exam;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import edu.hm.cs.fs.restapi.parser.cache.CachedModuleParser;
import edu.hm.cs.fs.restapi.parser.cache.CachedPersonParser;

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

    public ExamParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<Exam> onCreateItems(final String rootPath) throws XPathExpressionException {
        final List<Exam> result = new ArrayList<>();

        final String date = findByXPath(rootPath + "/date/text()", XPathConstants.STRING, String.class);
        final int countTime = getCountByXPath(rootPath + "/time");
        for (int indexTime = 1; indexTime <= countTime; indexTime++) {
            final String time = findByXPath(rootPath + "/time[" + indexTime + "]/time/text()",
                    XPathConstants.STRING, String.class);
            try {
                final Date timestamp = DATE_FORMAT.parse(date + " " + time);

                final int countExamination = getCountByXPath(rootPath + "/time[" + indexTime + "]/examination");
                for (int indexExamination = 1; indexExamination <= countExamination; indexExamination++) {
                    List<String> rooms = new ArrayList<>();
                    final int countRoom = getCountByXPath(rootPath + "/time[" + indexTime + "]/examination[" +
                            indexExamination + "]/room");
                    for (int indexRoom = 1; indexRoom <= countRoom; indexRoom++) {
                        final String room = findByXPath(rootPath + "/time[" + indexTime + "]/examination[" +
                                        indexExamination + "]/room[" + indexRoom + "]/text()",
                                XPathConstants.STRING, String.class);
                        rooms.add(room);
                    }

                    final int countExams = getCountByXPath(rootPath + "/time[" + indexTime + "]/examination[" +
                            indexExamination + "]/exam");
                    for (int indexExams = 1; indexExams <= countExams; indexExams++) {
                        final String basePath = rootPath + "/time[" + indexTime + "]/examination[" + indexExamination
                                + "]/exam[" + indexExams + "]";

                        final String code = findByXPath(basePath + "/code/text()",
                                XPathConstants.STRING, String.class);
                        final String groupStr = findByXPath(basePath + "/program/text()",
                                XPathConstants.STRING, String.class);
                        Study group = null;
                        if (!StringUtil.isBlank(groupStr)) {
                            group = Group.of(groupStr).getStudy();
                        }
                        final String moduleId = findByXPath(basePath + "/modul/text()",
                                XPathConstants.STRING, String.class);
                        final String subtitle = findByXPath(basePath + "/subtitle/text()",
                                XPathConstants.STRING, String.class);

                        final int countRef = getCountByXPath(basePath + "/reference");
                        final List<Study> references = new ArrayList<>();
                        for (int indexRef = 1; indexRef <= countRef; indexRef++) {
                            final String ref = findByXPath(basePath + "/reference[" + indexRef + "]/text()",
                                    XPathConstants.STRING, String.class);
                            if (!StringUtil.isBlank(ref)) {
                                references.add(Study.of(ref));
                            }
                        }

                        final int countExaminer = getCountByXPath(basePath + "/examiner");
                        final List<SimplePerson> examiners = new ArrayList<>();
                        for (int indexExaminer = 1; indexExaminer <= countExaminer; indexExaminer++) {
                            final String personId = findByXPath(basePath + "/examiner[" + indexExaminer + "]/text()",
                                    XPathConstants.STRING, String.class);
                            if (!StringUtil.isBlank(personId)) {
                                new CachedPersonParser().findByIdSimple(personId).ifPresent(examiners::add);
                            }
                        }

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
                        new CachedModuleParser().findByIdSimple(moduleId).ifPresent(exam::setModule);
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

                        result.add(exam);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
