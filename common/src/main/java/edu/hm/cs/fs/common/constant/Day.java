package edu.hm.cs.fs.common.constant;

/**
 * @author Fabio
 */
public enum Day {
    /**
     * Montag
     */
    MONDAY("mo", 2),
    /**
     * Dienstag
     */
    TUESDAY("di", 3),
    /**
     * Mittwoch
     */
    WEDNESDAY("mi", 4),
    /**
     * Donnerstag
     */
    THURSDAY("do", 5),
    /**
     * Freitag
     */
    FRIDAY("fr", 6),
    /**
     * Samstag
     */
    SATURDAY("sa", 7),
    /**
     * Sonntag
     */
    SUNDAY("so", 1);

    private final String mKey;

    private final int calendarId;

    Day(final String key, final int calendarId) {
        mKey = key;
        this.calendarId = calendarId;
    }

    /**
     * @param key
     * @return
     */
    public static Day of(final String key) {
        for (final Day day : values()) {
            if (day.getKey().equalsIgnoreCase(key)) {
                return day;
            }
        }
        return null;
    }

    public String getKey() {
        return mKey;
    }

    public int getCalendarId() {
        return calendarId;
    }
}
