package edu.hm.cs.fs.restapi.parser;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.jsoup.helper.StringUtil;

import com.google.common.base.Strings;

import edu.hm.cs.fs.common.constant.ExamType;
import edu.hm.cs.fs.common.constant.Offer;
import edu.hm.cs.fs.common.constant.Semester;
import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.constant.TeachingForm;
import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.ModuleCode;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimpleModuleCode;
import edu.hm.cs.fs.common.model.simple.SimplePerson;

/**
 * A modul can be choosen by a student. Some moduls are mandatory. (Url: <a
 * href="http://fi.cs.hm.edu/fi/rest/public/modul">http://fi.cs.hm.edu/fi/rest/ public/modul</a>)
 *
 * @author Fabio
 */
public class ModuleParser extends AbstractXmlParser<Module> implements ByIdParser<Module> {
    private static final String BASE_URL = "http://fi.cs.hm.edu/fi/rest/public/";
    private static final String URL = BASE_URL + "modul.xml";
    private static final String ROOT_NODE = "/modullist/modul";
    private final ByIdParser<Person> personParser;

    public ModuleParser(ByIdParser<Person> personParser) {
        super(URL, ROOT_NODE);
        this.personParser = personParser;
    }

    private ModuleParser(ByIdParser<Person> personParser, String moduleId) {
        super(BASE_URL + "modul/title/" + moduleId + ".xml", "modul");
        this.personParser = personParser;
    }

    @Override
    public List<Module> onCreateItems(final String rootPath) throws Exception {
        String name;
        int credits;
        int sws;
        String responsible;
        TeachingForm teachingForm = null;
        String expenditure;
        String requirements;
        String goals;
        String content;
        String media;
        String literature;
        Study program = null;

        // Parse Elements...
        name = findByXPath(rootPath + "/name/text()",
                XPathConstants.STRING, String.class);
        credits = findByXPath(rootPath + "/credits/text()",
                XPathConstants.NUMBER, Double.class).intValue();
        sws = findByXPath(rootPath + "/sws/text()",
                XPathConstants.NUMBER, Double.class).intValue();
        responsible = findByXPath(rootPath + "/verantwortlich/text()",
                XPathConstants.STRING, String.class);

        final List<SimplePerson> teachers = getXPathStream(rootPath + "/teacher")
                .map(path -> {
                    try {
                        return findByXPath(path + "/text()",
                                XPathConstants.STRING, String.class);
                    } catch (XPathExpressionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(person -> !Strings.isNullOrEmpty(person))
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

        final List<String> languages = getXPathStream(rootPath + "/language")
                .map(path -> {
                    try {
                        return findByXPath(path + "/text()",
                                XPathConstants.STRING, String.class);
                    } catch (XPathExpressionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(language -> !Strings.isNullOrEmpty(language))
                .collect(Collectors.toList());

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

        List<ModuleCode> moduleCodes = getXPathStream(rootPath + "/modulcodes/modulcode")
                .map(path -> {
                  
                    try {
                        final String modul = findByXPath(path
                                + "/modul/text()", XPathConstants.STRING, String.class);
                        final String regulation = findByXPath(path
                                + "/regulation/text()", XPathConstants.STRING, String.class);
                        final String stringOffer = findByXPath(path
                                + "/angebot/text()", XPathConstants.STRING, String.class);
                        Offer offer = null;
                        if (!StringUtil.isBlank(stringOffer)) {
                            offer = Offer.of(stringOffer);
                        }
                        ExamType services = null;
                        final String servicesStr = findByXPath(path
                                + "/leistungen/text()", XPathConstants.STRING, String.class);
                        if (!StringUtil.isBlank(servicesStr)) {
                            services = ExamType.of(servicesStr);
                        }
                        final String code = findByXPath(path + "/code/text()",
                                XPathConstants.STRING, String.class);

                        final List<Semester> semesterList = getXPathStream(path + "/semester")
                                .map(semesterPath -> {
                                    try {
                                        return findByXPath(semesterPath + "/text()",
                                                XPathConstants.STRING, String.class);
                                    } catch (XPathExpressionException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .filter(semester -> !Strings.isNullOrEmpty(semester))
                                .map(semester -> Semester.of(Integer.parseInt(semester)))
                                .collect(Collectors.toList());

                        final String curriculum = findByXPath(path
                                + "/curriculum/text()", XPathConstants.STRING, String.class);

                        ModuleCode moduleCode = new ModuleCode();
                        moduleCode.setModul(modul);
                        moduleCode.setRegulation(regulation);
                        moduleCode.setOffer(offer);
                        moduleCode.setServices(services);
                        moduleCode.setCode(code);
                        moduleCode.setSemesters(semesterList);
                        moduleCode.setCurriculum(curriculum);
                        return moduleCode;
                    } catch (XPathExpressionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(moduleCode -> moduleCode != null)
                .collect(Collectors.toList());
        
        if(moduleCodes == null || moduleCodes.isEmpty()){
          moduleCodes = getXPathStream(rootPath + "/modulcode")
              .map(path -> {
                
                  try {
                      final String modul = findByXPath(path
                              + "/modul/text()", XPathConstants.STRING, String.class);
                      final String regulation = findByXPath(path
                              + "/regulation/text()", XPathConstants.STRING, String.class);
                      final String stringOffer = findByXPath(path
                              + "/angebot/text()", XPathConstants.STRING, String.class);
                      Offer offer = null;
                      if (!StringUtil.isBlank(stringOffer)) {
                          offer = Offer.of(stringOffer);
                      }
                      ExamType services = null;
                      final String servicesStr = findByXPath(path
                              + "/leistungen/text()", XPathConstants.STRING, String.class);
                      if (!StringUtil.isBlank(servicesStr)) {
                          services = ExamType.of(servicesStr);
                      }
                      final String code = findByXPath(path + "/code/text()",
                              XPathConstants.STRING, String.class);

                      final List<Semester> semesterList = getXPathStream(path + "/semester")
                              .map(semesterPath -> {
                                  try {
                                      return findByXPath(semesterPath + "/text()",
                                              XPathConstants.STRING, String.class);
                                  } catch (XPathExpressionException e) {
                                      throw new RuntimeException(e);
                                  }
                              })
                              .filter(semester -> !Strings.isNullOrEmpty(semester))
                              .map(semester -> Semester.of(Integer.parseInt(semester)))
                              .collect(Collectors.toList());

                      final String curriculum = findByXPath(path
                              + "/curriculum/text()", XPathConstants.STRING, String.class);

                      ModuleCode moduleCode = new ModuleCode();
                      moduleCode.setModul(modul);
                      moduleCode.setRegulation(regulation);
                      moduleCode.setOffer(offer);
                      moduleCode.setServices(services);
                      moduleCode.setCode(code);
                      moduleCode.setSemesters(semesterList);
                      moduleCode.setCurriculum(curriculum);
                      return moduleCode;
                  } catch (XPathExpressionException e) {
                      throw new RuntimeException(e);
                  }
              })
              .filter(moduleCode -> moduleCode != null)
              .collect(Collectors.toList());
        }

        Module module = new Module();
        moduleCodes.stream().findFirst().map(ModuleCode::getModul).ifPresent(module::setId);
        module.setName(name);
        module.setCredits(credits);
        module.setSws(sws);
        personParser.getById(responsible).map(SimplePerson::new).ifPresent(module::setResponsible);
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
        module.setModulCodes(moduleCodes.parallelStream().map(SimpleModuleCode::new).collect(Collectors.toList()));

        return Collections.singletonList(module);
    }

    @Override
    public Optional<Module> getById(String itemId) throws Exception {
        try {
          return new ModuleParser(personParser, itemId).getAll().parallelStream()
              .filter(module -> itemId.equals(module.getId()))
              .findAny();
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
