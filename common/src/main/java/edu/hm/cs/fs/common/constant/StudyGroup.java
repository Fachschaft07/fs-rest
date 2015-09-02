package edu.hm.cs.fs.common.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Fabio
 */
public final class StudyGroup {
    private static final Pattern PATTERN = Pattern
            .compile("([A-z]{2})([1-7]{0,1})([A-z]{0,1})");
    private final Study mStudy;
    private final Semester mSemester;
    private final Letter mLetter;

    private StudyGroup(final Study study, final Semester semester,
                       final Letter letter) {
        if (study == null) {
            throw new NullPointerException();
        }
        mStudy = study;
        mSemester = semester;
        mLetter = letter;
    }

    /**
     * @param name of the study group (for example: "IB1A" or "IB" or "IC3").
     * @return the study group.
     */
    public static StudyGroup of(final String name) {
        final Matcher matcher = PATTERN.matcher(name);

        if (!matcher.find()) // Muss immer vorhanden sein!
            return null;
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
            letter = Letter.valueOf(letterMatch.toUpperCase());
        } else {
            letter = null;
        }

        return new StudyGroup(studyGroup, semester, letter);
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
        strBuilder.append(getStudy().toString());
        if (getSemester() != null) {
            strBuilder.append(getSemester().getNumber());
        }
        if (getLetter() != null) {
            strBuilder.append(getLetter().toString());
        }
        return strBuilder.toString();
    }
}
