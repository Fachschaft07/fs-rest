package edu.hm.cs.fs.common.constant;

/**
 * @author Fabio
 */
public enum TeachingForm {
    /**
     * Seminaristischer Unterricht - Praktikum
     */
    LECTORS_PLACEMENT("su-praktikum"),
    /**
     * Praktikum
     */
    PLACEMENT("praktikum"),
    /**
     * Projekt
     */
    PROJECT("projekt"),
    /**
     * Seminar
     */
    SEMINAR("seminar"),
    /**
     * Seminaristischer Unterricht - Übungen
     */
    LECTORS_EXERCISES("su-uebungen"),
    /**
     * Seminaristischer Unterricht
     */
    LECTORS("su"),
    /**
     * Nachfach
     */
    NACHFACH("nachfach"),
    /**
     * Selbst
     */
    SELF("selbst");

    private final String mKey;

    TeachingForm(final String key) {
        mKey = key;
    }

    /**
     * @param teachingForm
     * @return
     */
    public static TeachingForm of(final String teachingForm) {
        for (final TeachingForm teachForm : values()) {
            if (teachForm.getKey().equalsIgnoreCase(teachingForm)) {
                return teachForm;
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
