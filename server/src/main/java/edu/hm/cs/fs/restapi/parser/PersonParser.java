package edu.hm.cs.fs.restapi.parser;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Faculty;
import edu.hm.cs.fs.common.constant.PersonStatus;
import edu.hm.cs.fs.common.constant.Sex;
import edu.hm.cs.fs.common.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Every persons who work at the {@link Faculty#_07}. (Url: <a href="http://fi.cs.hm.edu/fi/rest/public/person"
 * >http://fi.cs.hm.edu/fi/rest/public/person</a>)
 *
 * @author Fabio
 */
public class PersonParser extends AbstractXmlParser<Person> implements ByIdParser<Person> {
    private static final String BASE_URL = "http://fi.cs.hm.edu/fi/rest/public/";
    private static final String URL = BASE_URL + "person.xml";
    private static final String ROOT_NODE = "/persons/person";

    public PersonParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<Person> onCreateItems(final String rootPath) {
        final List<Person> result = new ArrayList<>();

        // Parse Elements...
        final Optional<String> id = findString(rootPath + "/id/text()");
        final Optional<String> lastName = findString(rootPath + "/lastname/text()");
        final Optional<String> firstName = findString(rootPath + "/firstname/text()");
        final Optional<Sex> sex = findString(rootPath + "/sex/text()").map(Sex::of);
        final String title = findString(rootPath + "/title/text()").orElse("");
        final Optional<Faculty> faculty = findString(rootPath + "/faculty/text()").map(Faculty::of);
        final Optional<PersonStatus> status = findString(rootPath + "/status/text()").map(PersonStatus::of);
        final boolean hidden = findBoolean(rootPath + "/hidden/text()").orElse(false);
        final String email = findString(rootPath + "/email/text()").orElse("");
        final String website = findString(rootPath + "/website/text()").orElse("");
        final String phone = findString(rootPath + "/phone/text()").map(tmp -> {
            if (tmp.length() == 4) {
                // Vierstellige Telefonnummern sind Durchwahlen nach 089-1265-
                // Remove all '-'
                return ("089-1265-" + tmp).replaceAll("-", "");
            }
            return tmp;
        }).orElse("");
        final String function = findString(rootPath + "/function/text()").orElse("");
        final String focus = findString(rootPath + "/focus/text()").orElse("");
        final String publication = findString(rootPath + "/publication/text()").orElse("");
        final String office = findString(rootPath + "/office/text()").orElse("");
        final boolean emailOptin = findBoolean(rootPath + "/emailoptin/text()").orElse(false);
        final boolean referenceOptin = findBoolean(rootPath + "/referenceoptin/text()").orElse(false);
        final Day officeHourWeekday = findString(rootPath + "/officehourweekday/text()").map(Day::of).orElse(null);
        final String officeHourTime = findString(rootPath + "/officehourtime/text()").orElse("");
        final String officeHourRoom = findString(rootPath + "/officehourroom/text()").orElse("");
        final String officeHourComment = findString(rootPath + "/officehourcomment/text()").orElse("");
        final String einsichtDate = findString(rootPath + "/einsichtdate/text()").orElse("");
        final String einsichtTime = findString(rootPath + "/einsichttime/text()").orElse("");
        final String einsichtRoom = findString(rootPath + "/einsichtroom/text()").orElse("");
        final String einsichtComment = findString(rootPath + "/einsichtcomment/text()").orElse("");

        if (id.isPresent() && lastName.isPresent() && firstName.isPresent() && sex.isPresent() && faculty.isPresent() && status.isPresent()) {
            Person person = new Person();
            person.setId(id.get());
            person.setLastName(lastName.get());
            person.setFirstName(firstName.get());
            person.setSex(sex.get());
            person.setTitle(title);
            person.setFaculty(faculty.get());
            person.setStatus(status.get());
            person.setHidden(hidden);
            person.setEmail(email);
            person.setPhone(phone);
            person.setWebsite(website);
            person.setFunction(function);
            person.setFocus(focus);
            person.setPublication(publication);
            person.setOffice(office);
            person.setEmailOptin(emailOptin);
            person.setReferenceOptin(referenceOptin);
            person.setOfficeHourWeekday(officeHourWeekday);
            person.setOfficeHourTime(officeHourTime);
            person.setOfficeHourRoom(officeHourRoom);
            person.setOfficeHourComment(officeHourComment);
            person.setEinsichtDate(einsichtDate);
            person.setEinsichtTime(einsichtTime);
            person.setEinsichtRoom(einsichtRoom);
            person.setEinsichtComment(einsichtComment);

            result.add(person);
        }

        return result;
    }

    @Override
    public Optional<Person> getById(final String itemId) {
        return getAll().parallelStream()
                .filter(item -> itemId.equalsIgnoreCase(item.getId()))
                .findAny();
    }
}
