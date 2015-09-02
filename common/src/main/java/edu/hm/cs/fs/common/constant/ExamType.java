package edu.hm.cs.fs.common.constant;

import java.util.Arrays;

/**
 * @author Fabio
 */
public enum ExamType {
    /**
     * schriftliche Prüfung - 60 Minuten
     */
    WRITTEN_EXAMINATION_60("sp60"),
    /**
     * schriftliche Prüfung - 90 Minuten
     */
    WRITTEN_EXAMINATION_90("sp90", "sp"),
    /**
     * mündliche Prüfung
     */
    ORAL_EXAMINATION("mp"),
    /**
     * mündliche Prüfung mit bestandenem Leistungsnachweis
     */
    ORAL_EXAMINATION_WITH_ADMISSION_REQIREMENTS("mpln"),
    /**
     * praktische Prüfung
     */
    PRACTICAL_EXAMINATION("pl"),
    /**
     * praktische Prüfung
     */
    PROJECT_WORK("pa"),
    /**
     * Referat
     */
    SEMINAR_PAPER("ref"),
    /**
     * benotete Studienarbeit
     */
    GRADED_THESIS("bsta"),
    /**
     * Seminararbeit
     */
    ESSAY("sema", "sa"),
    /**
     * unbenotetes Kolloquium
     */
    UNGRADED_COLLOQUIUM("coll"),
    /**
     * benotetes Kolloquium
     */
    GRADED_COLLOQUIUM("bcoll"),
    /**
     * Bericht
     */
    REPORT("rep"),
    /**
     * Benotete schriftliche Prüfung (90 Minuten, 60%) und benotete Studienarbeit (40%)
     */
    WRITTEN_EXAMINATION_90_ESSAY("sa40sp60"),
    /**
     * Benotete mündliche Prüfung (60%) und benotete Studienarbeit (40%)
     */
    ORAL_EXAMINATION_ESSAY("sa40mp60"),
    /**
     * Benotete Projektarbeit (60%) und benotetes Referat (40%)
     */
    PROJECT_SEMINAR_PAPER("p60r40"),
    /**
     * Benotetes Kolloquium (60%) und benotete Studienarbeit (40%)
     */
    GRADED_COLLOQUIUM_ESSAY("sa40kol60"),
    /**
     * Benotete Klausur (60 Minuten, 60%) und benotete Studienarbeit (40%)
     */
    WRITTEN_EXAMINATION_60_ESSAY("sa40kl60"),
    /**
     * Benotete Seminararbeit (60%) und benotetes Referat (40%)
     */
    ESSAY_SEMINAR_PAPER("ref40sem60"),
    /**
     * Benotete schriftliche Prüfung (90 Minuten); Zulassungsvoraussetzung: erfolgreich bestandener Leistungsnachweis
     */
    WRITTEN_EXAMINATION_REQUIREMENTS("spln"),
    /**
     * Je nach Fach
     */
    UNKNOWN("nachfrag");

    private final String[] mKey;

    ExamType(final String... key) {
        mKey = key;
    }

    /**
     * @param key
     * @return
     */
    public static ExamType of(final String key) {
        for (final ExamType type : values()) {
            if (Arrays.asList(type.getKeys()).contains(key)) {
                return type;
            }
        }
        return null;
    }

    /**
     * @return the key.
     */
    private String[] getKeys() {
        return mKey;
    }
}
