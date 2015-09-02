package edu.hm.cs.fs.common.constant;

/**
 * Created by Fabio on 11.03.2015.
 */
public enum MealType {
    MEAT("fleisch"), MEATLESS("fleischlos"), VEGAN("vegan");

    private final String mKey;

    private MealType(String key) {
        mKey = key;
    }

    public static MealType of(String key) {
        if ("f".equalsIgnoreCase(key)) {
            return MEATLESS;
        } else if ("v".equalsIgnoreCase(key)) {
            return VEGAN;
        } else {
            for (MealType mealType : values()) {
                if (mealType.toString().equalsIgnoreCase(key)) {
                    return mealType;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return mKey;
    }
}
