package edu.hm.cs.fs.common.constant;

import java.util.Arrays;

/**
 * @author Fabio
 */
public enum Sex {
    /**
     * Männlich
     */
    MALE("m"),
    /**
     * Weiblich
     */
    FEMALE("w", "f");

    private final String[] mKeys;

    Sex(final String... keys) {
        mKeys = keys;
    }

    /**
     * @param key
     * @return
     */
    public static Sex of(final String key) {
        for (final Sex sex : values()) {
            if (Arrays.asList(sex.getKeys()).contains(key)) {
                return sex;
            }
        }
        return null;
    }

    /**
     * @return the key.
     */
    private String[] getKeys() {
        return mKeys;
    }
}
