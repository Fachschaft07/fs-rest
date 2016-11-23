package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.constant.*;
import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.ModuleCode;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimpleModuleCode;
import edu.hm.cs.fs.common.model.simple.SimplePerson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<Module> onCreateItems(final String rootPath) {
        final List<Module> result = new ArrayList<>();

        // Parse Elements...
        final Optional<String> name = findString(rootPath + "/name/text()");
        final Optional<Integer> credits = findNumber(rootPath + "/credits/text()").map(Double::intValue);
        final Optional<Integer> sws = findNumber(rootPath + "/sws/text()").map(Double::intValue);
        final Optional<String> responsible = findString(rootPath + "/verantwortlich/text()");

        final List<SimplePerson> teachers = getXPathStream(rootPath + "/teacher")
                .parallel()
                .map(path -> findString(path + "/text()"))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(personParser::getById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(SimplePerson::new)
                .collect(Collectors.toList());

        final List<String> languages = getXPathStream(rootPath + "/language")
                .parallel()
                .map(path -> findString(path + "/text()"))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        final Optional<TeachingForm> teachingForm = findString(rootPath + "/lehrform/text()").map(TeachingForm::of);
        final String expenditure = findString(rootPath + "/aufwand/text()").orElse("");
        final String requirements = findString(rootPath + "/voraussetzungen/text()").orElse("");
        final String goals = findString(rootPath + "/ziele/text()").orElse("");
        final String content = findString(rootPath + "/inhalt/text()").orElse("");
        final String media = findString(rootPath + "/medien/text()").orElse("");
        final String literature = findString(rootPath + "/literatur/text()").orElse("");
        final Study program = findString(rootPath + "/program/text()").map(Study::of).orElse(null);

        final List<ModuleCode> moduleCodes = getXPathStream(rootPath + "/*/modulcode")
                .parallel()
                .map(path -> {
                    final Optional<String> modul = findString(path + "/modul/text()");
                    final Optional<String> regulation = findString(path + "/regulation/text()");
                    final Offer offer = findString(path + "/angebot/text()").map(Offer::of).orElse(null);
                    final ExamType services = findString(path + "/leistungen/text()").map(ExamType::of).orElse(null);
                    final Optional<String> code = findString(path + "/code/text()");

                    final List<Semester> semesterList = getXPathStream(path + "/semester")
                            .map(semesterPath -> findNumber(semesterPath + "/text()").map(Double::intValue).map(Semester::of))
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(Collectors.toList());

                    final Optional<String> curriculum = findString(path + "/curriculum/text()");

                    if (modul.isPresent() && regulation.isPresent() && code.isPresent() && curriculum.isPresent()) {
                        ModuleCode moduleCode = new ModuleCode();
                        moduleCode.setModul(modul.get());
                        moduleCode.setRegulation(regulation.get());
                        moduleCode.setOffer(offer);
                        moduleCode.setServices(services);
                        moduleCode.setCode(code.get());
                        moduleCode.setSemesters(semesterList);
                        moduleCode.setCurriculum(curriculum.get());
                        return moduleCode;
                    }
                    return null;
                })
                .filter(moduleCode -> moduleCode != null)
                .collect(Collectors.toList());

        if (name.isPresent() && credits.isPresent() && sws.isPresent() && responsible.isPresent() && teachingForm.isPresent()) {
            Module module = new Module();
            moduleCodes.stream().findFirst().map(ModuleCode::getModul).ifPresent(module::setId);
            module.setName(name.get());
            module.setCredits(credits.get());
            module.setSws(sws.get());
            personParser.getById(responsible.get()).map(SimplePerson::new).ifPresent(module::setResponsible);
            module.setTeachers(teachers);
            module.setLanguages(languages);
            module.setTeachingForm(teachingForm.get());
            module.setExpenditure(expenditure);
            module.setRequirements(requirements);
            module.setGoals(goals);
            module.setContent(content);
            module.setMedia(media);
            module.setLiterature(literature);
            module.setProgram(program);
            module.setModulCodes(moduleCodes.parallelStream().collect(Collectors.toList()));

            result.add(module);
        }

        return result;
    }

    @Override
    public Optional<Module> getById(String itemId) {
        return getAll().parallelStream()
                .filter(module -> itemId.equals(module.getId()))
                .findAny();
    }
}
