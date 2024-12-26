package com.hit.common.utils;

import lombok.experimental.UtilityClass;

import java.text.Normalizer;
import java.util.regex.Pattern;

@UtilityClass
public class StringUtils {

    public static boolean isEmptyOrBlank(CharSequence cs) {
        return isEmpty(cs) || isBlank(cs);
    }

    public static boolean isNotEmptyOrBlank(CharSequence cs) {
        return !isEmpty(cs) || !isBlank(cs);
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.isEmpty();
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static String camelToSnake(String str) {
        StringBuilder result = new StringBuilder();

        char c = str.charAt(0);
        result.append(Character.toLowerCase(c));

        for (int i = 1; i < str.length(); i++) {

            char ch = str.charAt(i);

            if (Character.isUpperCase(ch)) {
                result.append('_');
                result.append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String snakeToCamel(String str) {
        str = str.substring(0, 1).toLowerCase() + str.substring(1);
        StringBuilder result = new StringBuilder(str);

        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == '_') {
                result.deleteCharAt(i);
                result.replace(i, i + 1, String.valueOf(Character.toUpperCase(result.charAt(i))));
            }
        }
        return result.toString();
    }

    public static String removeAccent(String input) {
        if (isEmpty(input)) {
            return input;
        }
        String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp)
                .replaceAll("")
                .replace('đ', 'd').replace('Đ', 'D')
                .replaceAll("\\P{Print}", "");
    }

    public static String removeSpecialCharacters(String input) {
        return input.replaceAll("[^\\p{L}\\s]", "");
    }

    public static String buildLikeOperatorUpper(String value) {
        if (isNotEmptyOrBlank(value)) return null;
        return "%".concat(value.trim().toUpperCase()).concat("%");
    }

    public static String buildLikeOperator(String value) {
        if (isNotEmptyOrBlank(value)) return null;
        return "%".concat(value.trim()).concat("%");
    }
}
