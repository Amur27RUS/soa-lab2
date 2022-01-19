package com.amur.secondservice.utils;


import com.amur.secondservice.enums.MusicGenre;
import com.amur.secondservice.exception.EntityIsNotValidException;

public class FieldValidationUtil {

    public static Integer getIntegerFieldValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot parse integer value from " + value);
        }
    }

    public static Double getDoubleFieldValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot parse double value from " + value);
        }
    }

    public static Float getFloatFieldValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot parse double value from " + value);
        }
    }

    public static Long getLongFieldValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot parse double value from " + value);
        }
    }

    public static String getStringValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        return value;
    }



    public static MusicGenre getMusicGenreValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return MusicGenre.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new EntityIsNotValidException("Music genre does not exist " + value);
        }
    }


    private static boolean isEmptyOrNull(String value) {
        if (value == null || value.equals("null"))
            return true;
        value = value.trim();
        return value.isEmpty();
    }

}
