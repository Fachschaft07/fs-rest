package edu.hm.cs.fs.common.constant;

/**
 * @author Fabio
 *
 */
public enum ExamType {
	/** schriftliche Prüfung - 60 Minuten */
	WRITTEN_EXAMINATION_60("sp60"),
	/** schriftliche Prüfung - 90 Minuten */
	WRITTEN_EXAMINATION_90("sp90"),
	/** mündliche Prüfung */
	ORAL_EXAMINATION("mp"),
	/** mündliche Prüfung mit bestandenem Leistungsnachweis */
	ORAL_EXAMINATION_WITH_ADMISSION_REQIREMENTS("mpln"),
	/** praktische Prüfung */
	PRACTICAL_EXAMINATION("pl"),
	/** praktische Prüfung */
	PROJECT_WORK("pa"),
	/** Referat */
	SEMINAR_PAPER("ref"),
	/** benotete Studienarbeit */
	GRADED_THESIS("bsta"),
	/** Seminararbeit */
	ESSAY("sema"),
	/** unbenotetes Kolloquium */
	UNGRADED_COLLOQUIUM("coll"),
	/** benotetes Kolloquium */
	GRADED_COLLOQUIUM("bcoll"),
	/** Bericht */
	REPORT("rep");

	private final String mKey;

	ExamType(final String key) {
		mKey = key;
	}

	/**
	 * @return the key.
	 */
	private String getKey() {
		return mKey;
	}

	/**
	 * @param key
	 * @return
	 */
	public static ExamType of(final String key) {
		for (final ExamType type : values()) {
			if (type.getKey().equalsIgnoreCase(key)) {
				return type;
			}
		}
		return null;
	}
}
