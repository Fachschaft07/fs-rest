package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.constant.ExamGroup;
import edu.hm.cs.fs.common.constant.ExamType;
import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.Exam;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimpleModule;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import org.apache.log4j.Logger;

import javax.xml.xpath.XPathConstants;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The exams with every information. (Url: <a href="http://fi.cs.hm.edu/fi/rest/public/exam"
 * >http://fi.cs.hm.edu/fi/rest/public/exam</a>)
 *
 * @author Fabio
 */
public class ExamParser extends AbstractXmlParser<Exam> {
    private final static Logger LOG = Logger.getLogger(ExamParser.class);
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
    public List<Exam> onCreateItems(final String rootPath) {
        final List<Exam> result = new ArrayList<>();

        final Optional<String> date = findString(rootPath + "/date/text()");
        if (date.isPresent()) {
            result.addAll(getXPathStream(rootPath + "/time")
                    .parallel()
                    .flatMap(path -> {
                        try {
                            final Date timestamp = DATE_FORMAT.parse(date.get() + " " + findString(path + "/time/text()"));

                            return getXPathStream(path + "/examination")
                                    .parallel()
                                    .flatMap(basePath -> {
                                        final List<String> rooms = getXPathStream(basePath + "/room")
                                                .map(roomPath -> findString(roomPath + "/text()"))
                                                .filter(Optional::isPresent)
                                                .map(Optional::get)
                                                .map(room -> room.substring(0, 1).toUpperCase() + "." + room.substring(1, room.length()))
                                                .collect(Collectors.toList());

                                        return getXPathStream(basePath + "/exam")
                                                .parallel()
                                                .map(examPath -> {
                                                    final Optional<String> code = findString(examPath + "/code/text()");
                                                    final Study group = findString(examPath + "/program/text()")
                                                            .filter(tmp -> tmp != null && tmp.length() > 0)
                                                            .map(Group::of)
                                                            .map(Group::getStudy)
                                                            .orElse(null);
                                                    final Optional<String> moduleId = findString(examPath + "/modul/text()");
                                                    final String subtitle = findString(examPath + "/subtitle/text()").orElse("");
                                                    final Optional<String> examiner1 = findString(examPath + "/examiner/text()");
                                                    final Optional<String> examiner2 = findString(examPath + "/examiner2/text()");
                                                    final ExamType type = findString(examPath + "/type/text()")
                                                            .filter(tmp -> tmp != null && tmp.length() > 0)
                                                            .map(ExamType::of)
                                                            .orElse(null);
                                                    final String material = findString(examPath + "/material/text()").orElse("");
                                                    final ExamGroup allocation = findString(examPath + "/allocation/text()")
                                                            .filter(tmp -> tmp != null && tmp.length() > 0)
                                                            .map(ExamGroup::of)
                                                            .orElse(null);
                                                    final List<Study> references = getXPathStream(examPath + "/reference")
                                                            .map(refPath -> findString(refPath + "/text()"))
                                                            .filter(Optional::isPresent)
                                                            .map(Optional::get)
                                                            .map(Study::of)
                                                            .filter(study -> study != null)
                                                            .collect(Collectors.toList());

                                                    if (code.isPresent() && moduleId.isPresent() && examiner1.isPresent() && examiner2.isPresent()) {
                                                        final List<SimplePerson> examiners = new ArrayList<>();
                                                        personParser.getById(examiner1.get()).map(SimplePerson::new).ifPresent(examiners::add);
                                                        personParser.getById(examiner2.get()).map(SimplePerson::new).ifPresent(examiners::add);

                                                        Exam exam = new Exam();
                                                        moduleParser.getById(moduleId.get())
                                                                .map(SimpleModule::new)
                                                                .ifPresent(exam::setModule);
                                                        exam.setAllocation(allocation);
                                                        exam.setCode(code.get());
                                                        exam.setExaminers(examiners);
                                                        exam.setStudy(group);
                                                        exam.setMaterial(material);
                                                        exam.setReferences(references);
                                                        exam.setSubtitle(subtitle);
                                                        exam.setType(type);
                                                        exam.setDate(timestamp);
                                                        exam.setRooms(rooms);
                                                        return exam;
                                                    }
                                                    return null;
                                                })
                                                .filter(exam -> exam != null);
                                    });
                        } catch (Exception e) {
                            LOG.error(e);
                            return Stream.empty();
                        }
                    })
                    .collect(Collectors.toList()));
        }
        return result;
    }
}
