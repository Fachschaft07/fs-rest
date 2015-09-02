package edu.hm.cs.fs.common.constant;

/**
 * @author Fabio
 */
public enum PersonStatus {
    /**
     * Professor
     */
    PROFESSOR("prof"),
    /**
     * Mitarbeiter
     */
    EMPLOYEE("staff"),
    /**
     * Wissenschaftlicher Mitarbeiter
     */
    SCIENCE_EMPLOYEE("associate"),
    /**
     * Lehrbeauftragte
     */
    LECTURER("lba"),
    /**
     * Fellow
     */
    FELLOW("fellow"),
    /**
     * Emeritus
     */
    EMERITUS("emeritus"),
    /**
     * Gast
     */
    GUEST("guest");

    private final String mKey;

    PersonStatus(final String key) {
        mKey = key;
    }

    /**
     * @param key
     * @return
     */
    public static PersonStatus of(final String key) {
        for (final PersonStatus state : values()) {
            if (state.getKey().equalsIgnoreCase(key)) {
                return state;
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
