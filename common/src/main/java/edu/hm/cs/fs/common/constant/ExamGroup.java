package edu.hm.cs.fs.common.constant;

/**
 * @author Fabio
 */
public enum ExamGroup {
    /**
     * Fachbezogenes Wahlpflichtfach
     */
    TRADE_RELATED_ELECTIVE_COURSE("compulsory"),
    /**
     * Wahlpflichtfach Anwendung
     */
    ELECTIVE_COURSE_APPLICATION("electiveapp"),
    /**
     * Wahlpflichtfach Informatik
     */
    ELECTIVE_COURSE_COMPUTER_SCIENCE("electivecs"),
    /**
     * Wahlpflichtfach Wirtschaft
     */
    ELECTIVE_COURSE_ECONOMY("electiveec"),
    /**
     * Wahlpflichtfach Mathematik
     */
    ELECTIVE_COURSE_MATHEMATICS("electivemath"),
    /**
     * Hauptstudium
     */
    MAIN_STUDY("graduate"),
    /**
     * Vertiefungsfach
     */
    MAJOR_SUBJECT("intensive"),
    /**
     * Praxissemester
     */
    INTERNSHIP("pract"),
    /**
     * Grundstudium
     */
    BASIC_STUDIES("under");

    private final String mKey;

    ExamGroup(final String key) {
        mKey = key;
    }

    /**
     * @param key
     * @return
     */
    public static ExamGroup of(final String key) {
        for (final ExamGroup type : values()) {
            if (type.getKey().equalsIgnoreCase(key)) {
                return type;
            }
        }
        return null;
    }

    /**
     * @return the key.
     */
    private String getKey() {
        return mKey;
    }
}
