package edu.hm.cs.fs.common.constant;

/**
 * @author Fabio
 */
public enum Offer {
    /**
     * Einzeln
     */
    SINGLE("einzeln"),
    /**
     * Wechsel
     */
    SWITCHED("wechsel"),
    /**
     * Sommersemester
     */
    SUMMER_SEMESTER("ss"),
    /**
     * Wintersemester
     */
    WINTER_SEMESTER("ws"),
    /**
     * Alle
     */
    ALL("alle");

    private final String mKey;

    Offer(final String key) {
        mKey = key;
    }

    /**
     * @param key
     * @return
     */
    public static Offer of(final String key) {
        for (final Offer offer : values()) {
            if (offer.getKey().equalsIgnoreCase(key)) {
                return offer;
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
