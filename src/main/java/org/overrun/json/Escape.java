package org.overrun.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.util.regex.Pattern.compile;

/**
 * @author squid233
 * @since 0.1.0
 */
public class Escape {
    private static final Pattern UNESCAPE =
        compile("\\\\([\\\\\"bftnr]|(u)([0-9a-fA-F]{4}))");
    private static final Map<String, String> UNESCAPE_CACHE =
        new HashMap<>();
    private static final Map<String, String> ESCAPE_CACHE =
        new HashMap<>();

    /**
     * Escape a string.
     *
     * @param s The string.
     * @return Escaped string.
     * @since 0.2.0
     */
    public static String escape(final String s) {
        if (ESCAPE_CACHE.containsKey(s)) {
            return ESCAPE_CACHE.get(s);
        }
        var s1 = s.replaceAll("/", "\\/")
            .replaceAll("\\\\", "\\\\")
            .replaceAll("\"", "\\\"")
            .replaceAll("\b", "\\b")
            .replaceAll("\f", "\\f")
            .replaceAll("\t", "\\t")
            .replaceAll("\n", "\\n")
            .replaceAll("\r", "\\r");
        ESCAPE_CACHE.put(s, s1);
        return s1;
    }

    /**
     * Unescape a string.
     *
     * @param s The string.
     * @return Unescaped string.
     */
    public static String unescape(final String s) {
        if (!s.contains("\\")) {
            return s;
        }
        if (UNESCAPE_CACHE.containsKey(s)) {
            return UNESCAPE_CACHE.get(s);
        }
        try (var sc = new Scanner(s)) {
            String s1 = s;
            var st = sc.findAll(UNESCAPE).toArray(MatchResult[]::new);
            for (MatchResult result : st) {
                // /,\,",b,f,t,n,r
                if (result.group(2) == null) {
                    switch (result.group(1)) {
                        case "/":
                            s1 = s1.replace("\\/", "/");
                            break;
                        case "\\":
                            s1 = s1.replace("\\\\", "\\");
                            break;
                        case "\"":
                            s1 = s1.replace("\\\"", "\"");
                            break;
                        case "b":
                            s1 = s1.replace("\\b", "\b");
                            break;
                        case "f":
                            s1 = s1.replace("\\f", "\f");
                            break;
                        case "t":
                            s1 = s1.replace("\\t", "\t");
                            break;
                        case "n":
                            s1 = s1.replace("\\n", "\n");
                            break;
                        case "r":
                            s1 = s1.replace("\\r", "\r");
                            break;
                    }
                }
                // unicode
                else {
                    s1 = s1.replace(
                        result.group(0),
                        valueOf((char) parseInt(result.group(3), 16))
                    );
                }
            }
            UNESCAPE_CACHE.put(s, s1);
            return s1;
        }
    }
}
