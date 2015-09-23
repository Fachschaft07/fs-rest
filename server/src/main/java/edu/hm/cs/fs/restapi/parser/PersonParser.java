package edu.hm.cs.fs.restapi.parser;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.xml.xpath.XPathConstants;

import org.jsoup.helper.StringUtil;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Faculty;
import edu.hm.cs.fs.common.constant.PersonStatus;
import edu.hm.cs.fs.common.constant.Sex;
import edu.hm.cs.fs.common.model.Person;

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
    private final String mPersonId;

    public PersonParser() {
        super(URL, ROOT_NODE);
        mPersonId = null;
    }

    private PersonParser(final String personId) {
        super(BASE_URL + "person/name/" + personId + ".xml", "person");
        mPersonId = personId;
    }

    @Override
    public List<Person> onCreateItems(final String rootPath) throws Exception {
        String mId;
        String mLastName;
        String mFirstName;
        Sex mSex = null;
        String mTitle;
        Faculty mFaculty = null;
        PersonStatus mStatus = null;
        boolean mHidden;
        String mEmail;
        String mWebsite;
        String mPhone;
        String mFunction;
        String mFocus;
        String mPublication;
        String mOffice;
        boolean mEmailOptin;
        boolean mReferenceOptin;
        Day mOfficeHourWeekday = null;
        String mOfficeHourTime;
        String mOfficeHourRoom;
        String mOfficeHourComment;
        String mEinsichtDate;
        String mEinsichtTime;
        String mEinsichtRoom;
        String mEinsichtComment;

        // Parse Elements...
        if (mPersonId == null) {
            mId = findByXPath(rootPath + "/id/text()",
                    XPathConstants.STRING, String.class);
        } else {
            mId = mPersonId;
        }
        mLastName = findByXPath(rootPath + "/lastname/text()",
                XPathConstants.STRING, String.class);
        mFirstName = findByXPath(rootPath + "/firstname/text()",
                XPathConstants.STRING, String.class);
        final String sex = findByXPath(rootPath + "/sex/text()",
                XPathConstants.STRING, String.class);
        if (!StringUtil.isBlank(sex)) {
            mSex = Sex.of(sex);
        }
        mTitle = findByXPath(rootPath + "/title/text()",
                XPathConstants.STRING, String.class);
        final String faculty = findByXPath(rootPath + "/faculty/text()",
                XPathConstants.STRING, String.class);
        if (!StringUtil.isBlank(faculty)) {
            mFaculty = Faculty.of(faculty);
        }
        final String status = findByXPath(rootPath + "/status/text()",
                XPathConstants.STRING, String.class);
        if (!StringUtil.isBlank(status)) {
            mStatus = PersonStatus.of(status);
        }
        mHidden = findByXPath(rootPath + "/hidden/text()",
                XPathConstants.BOOLEAN, Boolean.class);
        mEmail = findByXPath(rootPath + "/email/text()",
                XPathConstants.STRING, String.class);
        mWebsite = findByXPath(rootPath + "/website/text()",
                XPathConstants.STRING, String.class);
        mPhone = findByXPath(rootPath + "/phone/text()",
                XPathConstants.STRING, String.class);
        if (mPhone.length() == 5) {
            // Vierstellige Telefonnummern sind Durchwahlen nach 089-1265-
            // Remove all '-'
            mPhone = "0891265" + mPhone.substring(1);
        }
        mFunction = findByXPath(rootPath + "/function/text()",
                XPathConstants.STRING, String.class);
        mFocus = findByXPath(rootPath + "/focus/text()",
                XPathConstants.STRING, String.class);
        mPublication = findByXPath(rootPath + "/publication/text()",
                XPathConstants.STRING, String.class);
        mOffice = findByXPath(rootPath + "/office/text()",
                XPathConstants.STRING, String.class);
        mEmailOptin = findByXPath(rootPath + "/emailoptin/text()",
                XPathConstants.BOOLEAN, Boolean.class);
        mReferenceOptin = findByXPath(rootPath + "/referenceoptin/text()",
                XPathConstants.BOOLEAN, Boolean.class);
        final String officeHourWeekday = findByXPath(rootPath
                + "/officehourweekday/text()", XPathConstants.STRING, String.class);
        if (!StringUtil.isBlank(officeHourWeekday)) {
            mOfficeHourWeekday = Day.of(officeHourWeekday);
        }
        mOfficeHourTime = findByXPath(rootPath + "/officehourtime/text()",
                XPathConstants.STRING, String.class);
        mOfficeHourRoom = findByXPath(rootPath + "/officehourroom/text()",
                XPathConstants.STRING, String.class);
        mOfficeHourComment = findByXPath(rootPath
                + "/officehourcomment/text()", XPathConstants.STRING, String.class);
        mEinsichtDate = findByXPath(rootPath + "/einsichtdate/text()",
                XPathConstants.STRING, String.class);
        mEinsichtTime = findByXPath(rootPath + "/einsichttime/text()",
                XPathConstants.STRING, String.class);
        mEinsichtRoom = findByXPath(rootPath + "/einsichtroom/text()",
                XPathConstants.STRING, String.class);
        mEinsichtComment = findByXPath(
                rootPath + "/einsichtcomment/text()", XPathConstants.STRING, String.class);

        Person person = new Person();
        person.setId(mId);
        person.setLastName(mLastName);
        person.setFirstName(mFirstName);
        person.setSex(mSex);
        person.setTitle(mTitle);
        person.setFaculty(mFaculty);
        person.setStatus(mStatus);
        person.setHidden(mHidden);
        person.setEmail(mEmail);
        person.setPhone(mPhone);
        person.setWebsite(mWebsite);
        person.setFunction(mFunction);
        person.setFocus(mFocus);
        person.setPublication(mPublication);
        person.setOffice(mOffice);
        person.setEmailOptin(mEmailOptin);
        person.setReferenceOptin(mReferenceOptin);
        person.setOfficeHourWeekday(mOfficeHourWeekday);
        person.setOfficeHourTime(mOfficeHourTime);
        person.setOfficeHourRoom(mOfficeHourRoom);
        person.setOfficeHourComment(mOfficeHourComment);
        person.setEinsichtDate(mEinsichtDate);
        person.setEinsichtTime(mEinsichtTime);
        person.setEinsichtRoom(mEinsichtRoom);
        person.setEinsichtComment(mEinsichtComment);

        return Collections.singletonList(person);
    }

    @Override
    public Optional<Person> getById(String itemId) throws Exception {
        try {
            return new PersonParser(itemId).getAll().parallelStream()
                    .filter(item -> itemId.equalsIgnoreCase(item.getId()))
                    .findAny();
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
