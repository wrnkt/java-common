package org.tanchee.common.strings;

import java.util.Arrays;

public class Strings {

    /**
     * Only applies one single capitalization.
     */
    public static final String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toTitleCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * Capitalizes every word in the {@link String}.
     */
    public static final String capitalizeAll(String s) {
        var words = s.split("\\s+");
        StringBuilder sb = new StringBuilder();
        Arrays.asList(words).forEach(
            word -> sb.append(capitalize(word))
        );
        return sb.toString();
    }

    /**
     * This one might be dumb. Pretty sure it is.
     */
    public static final String nullToEmpty(String s) {
        if (s == null) return "";
        return s;
    }

}
