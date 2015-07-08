package edu.hm.cs.fs.common.constant;

import java.util.Arrays;

/**
 * @author Fabio
 *
 */
public enum Sex {
	/** Männlich */
	MALE("m"),
	/** Weiblich */
	FEMALE("w", "f");

	private final String[] mKeys;

	Sex(final String... keys) {
		mKeys = keys;
	}

	/**
	 * @return the key.
	 */
	private String[] getKeys() {
		return mKeys;
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
		throw new IllegalArgumentException("Unable to convert this '" + key
				+ "' to a sex");
	}
}
