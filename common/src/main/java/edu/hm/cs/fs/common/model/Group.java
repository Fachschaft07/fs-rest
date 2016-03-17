package edu.hm.cs.fs.common.model;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.hm.cs.fs.common.constant.Letter;
import edu.hm.cs.fs.common.constant.Semester;
import edu.hm.cs.fs.common.constant.Study;

/**
 * @author Fabio
 */
public class Group {

    private static final Pattern PATTERN = Pattern
            .compile("([A-z]{2})([1-7]?)([A-z]?)");
    private final Study mStudy;
    private final Semester mSemester;
    private final Letter mLetter;

    public Group(final Study study, final Semester semester,
                 final Letter letter) {
        //if (study == null) {
        //    throw new NullPointerException();
        //}
        mStudy = study;
        mSemester = semester;
        mLetter = letter;
    }

    /**
     * @param name of the study group (for example: "IB1A" or "IB" or "IC3").
     * @return the study group.
     */
    public static Group of(final String name) {
        final Matcher matcher = PATTERN.matcher(name);

        if (!matcher.find()) {
            return new Group(null, null, null);
        }
        final Study studyGroup = Study.of(matcher.group(1));

        final Semester semester;
        final String semesterMatch = matcher.group(2);
        if (semesterMatch.length() > 0) {
            semester = Semester.valueOf("_" + semesterMatch);
        } else {
            semester = null;
        }

        final Letter letter;
        final String letterMatch = matcher.group(3);
        if (letterMatch.length() > 0) {
            letter = Letter.valueOf(letterMatch.toUpperCase(Locale.getDefault()));
        } else {
            letter = null;
        }

        return new Group(studyGroup, semester, letter);
    }

    /**
     * @return the study group.
     */
    public Study getStudy() {
        return mStudy;
    }

    /**
     * @return the semester.
     */
    public Semester getSemester() {
        return mSemester;
    }

    /**
     * @return the letter.
     */
    public Letter getLetter() {
        return mLetter;
    }

    @Override
    public String toString() {
        final StringBuilder strBuilder = new StringBuilder();
        if(getStudy() != null){
            strBuilder.append(getStudy().toString());
        }
        if (getSemester() != null) {
            strBuilder.append(getSemester().getNumber());
        }
        if (getLetter() != null) {
            strBuilder.append(getLetter().toString());
        }
        return strBuilder.toString();
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Group) {
            Group group = (Group) object;
            return getStudy() == group.getStudy() &&
                    getSemester() == group.getSemester() &&
                    getLetter() == group.getLetter();
        }
        return super.equals(object);
    }
}
