package edu.hm.cs.fs.restapi.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.jsoup.helper.StringUtil;

import edu.hm.cs.fs.common.constant.ExamType;
import edu.hm.cs.fs.common.constant.Offer;
import edu.hm.cs.fs.common.constant.Semester;
import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.constant.TeachingForm;
import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.ModuleCode;
import edu.hm.cs.fs.common.model.simple.SimpleModuleCode;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import edu.hm.cs.fs.restapi.parser.cache.CachedPersonParser;

/**
 * A modul can be choosen by a student. Some moduls are mandatory. (Url: <a
 * href="http://fi.cs.hm.edu/fi/rest/public/modul">http://fi.cs.hm.edu/fi/rest/ public/modul</a>)
 *
 * @author Fabio
 */
public class ModuleParser extends AbstractXmlParser<Module> {
    private static final String BASE_URL = "http://fi.cs.hm.edu/fi/rest/public/";
    private static final String URL = BASE_URL + "modul.xml";
    private static final String ROOT_NODE = "/modullist/modul";

    public ModuleParser() {
        super(URL, ROOT_NODE);
    }

    public ModuleParser(String moduleId) {
        super(BASE_URL + "modul/title/" + moduleId + ".xml", "modul");
    }

    @Override
    public List<Module> onCreateItems(final String rootPath) throws XPathExpressionException, MalformedURLException, IOException {
        String name;
        int credits;
        int sws;
        String responsible;
        List<SimplePerson> teachers = new ArrayList<>();
        List<String> languages = new ArrayList<>();
        TeachingForm teachingForm = null;
        String expenditure;
        String requirements;
        String goals;
        String content;
        String media;
        String literature;
        Study program = null;
        List<ModuleCode> modulCodes = new ArrayList<>();

        // Parse Elements...
        name = findByXPath(rootPath + "/name/text()",
                XPathConstants.STRING, String.class);
        credits = findByXPath(rootPath + "/credits/text()",
                XPathConstants.NUMBER, Double.class).intValue();
        sws = findByXPath(rootPath + "/sws/text()",
                XPathConstants.NUMBER, Double.class).intValue();
        responsible = findByXPath(rootPath + "/verantwortlich/text()",
                XPathConstants.STRING, String.class);

        final int countTeachers = getCountByXPath(rootPath + "/teacher");
        for (int indexTeacher = 1; indexTeacher <= countTeachers; indexTeacher++) {
            final String teacher = findByXPath(rootPath + "/teacher["
                    + indexTeacher + "]/text()", XPathConstants.STRING, String.class);
            if (!StringUtil.isBlank(teacher)) {
                new CachedPersonParser().findByIdSimple(teacher).ifPresent(teachers::add);
            }
        }

        final int countLanguage = getCountByXPath(rootPath + "/language");
        for (int indexLanguage = 1; indexLanguage <= countLanguage; indexLanguage++) {
            final String language = findByXPath(rootPath + "/language["
                    + indexLanguage + "]/text()", XPathConstants.STRING, String.class);
            if (!StringUtil.isBlank(language)) {
                languages.add(language);
            }
        }

        final String teachingFormStr = findByXPath(rootPath
                + "/lehrform/text()", XPathConstants.STRING, String.class);
        if (!StringUtil.isBlank(teachingFormStr)) {
            teachingForm = TeachingForm.of(teachingFormStr);
        }
        expenditure = findByXPath(rootPath + "/aufwand/text()",
                XPathConstants.STRING, String.class);
        requirements = findByXPath(rootPath + "/voraussetzungen/text()",
                XPathConstants.STRING, String.class);
        goals = findByXPath(rootPath + "/ziele/text()",
                XPathConstants.STRING, String.class);
        content = findByXPath(rootPath + "/inhalt/text()",
                XPathConstants.STRING, String.class);
        media = findByXPath(rootPath + "/medien/text()",
                XPathConstants.STRING, String.class);
        literature = findByXPath(rootPath + "/literatur/text()",
                XPathConstants.STRING, String.class);
        final String programStr = findByXPath(rootPath + "/program/text()",
                XPathConstants.STRING, String.class);
        if (!StringUtil.isBlank(programStr)) {
            program = Study.of(programStr);
        }

        final int countCodes = getCountByXPath(rootPath
                + "/modulcodes/modulcode");
        for (int indexCodes = 1; indexCodes <= countCodes; indexCodes++) {
            final String modulCodePath = rootPath
                    + "/modulcodes/modulcode[" + indexCodes + "]";

            final String modul = findByXPath(modulCodePath
                    + "/modul/text()", XPathConstants.STRING, String.class);
            final String regulation = findByXPath(modulCodePath
                    + "/regulation/text()", XPathConstants.STRING, String.class);
            final String stringOffer = findByXPath(modulCodePath
                    + "/angebot/text()", XPathConstants.STRING, String.class);
            Offer offer = null;
            if (!StringUtil.isBlank(stringOffer)) {
                offer = Offer.of(stringOffer);
            }
            ExamType services = null;
            final String servicesStr = findByXPath(modulCodePath
                    + "/leistungen/text()", XPathConstants.STRING, String.class);
            if(!StringUtil.isBlank(servicesStr)) {
                services = ExamType.of(servicesStr);
            }
            final String code = findByXPath(modulCodePath + "/code/text()",
                    XPathConstants.STRING, String.class);

            final List<Semester> semesterList = new ArrayList<>();
            final int countSemester = getCountByXPath(modulCodePath
                    + "/semester");
            for (int indexSemester = 1; indexSemester <= countSemester; indexSemester++) {
                final String semester = findByXPath(modulCodePath
                                + "/semester[" + indexSemester + "]/text()",
                        XPathConstants.STRING, String.class);
                if (!StringUtil.isBlank(semester)) {
                    semesterList.add(Semester.of(Integer.parseInt(semester)));
                }
            }

            final String curriculum = findByXPath(modulCodePath
                    + "/curriculum/text()", XPathConstants.STRING, String.class);

            ModuleCode moduleCode = new ModuleCode();
            moduleCode.setModul(modul);
            moduleCode.setRegulation(regulation);
            moduleCode.setOffer(offer);
            moduleCode.setServices(services);
            moduleCode.setCode(code);
            moduleCode.setSemesters(semesterList);
            moduleCode.setCurriculum(curriculum);

            modulCodes.add(moduleCode);
        }

        Module module = new Module();
        if(!modulCodes.isEmpty()) {
            module.setId(modulCodes.get(0).getModul());
            module.setName(name);
            module.setCredits(credits);
            module.setSws(sws);
            new CachedPersonParser().findByIdSimple(responsible).ifPresent(module::setResponsible);
            module.setTeachers(teachers);
            module.setLanguages(languages);
            module.setTeachingForm(teachingForm);
            module.setExpenditure(expenditure);
            module.setRequirements(requirements);
            module.setGoals(goals);
            module.setContent(content);
            module.setMedia(media);
            module.setLiterature(literature);
            module.setProgram(program);
            module.setModulCodes(modulCodes.stream().map(SimpleModuleCode::new).collect(Collectors.toList()));

            return Collections.singletonList(module);
        }
        return new ArrayList<>();
    }
}
