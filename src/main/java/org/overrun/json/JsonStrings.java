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
public class JsonStrings {
    private static final Pattern ESCAPE =
        compile("\\\\([\\\\\"bftnr]|(u)([0-9a-fA-F]{4}))");
    private static final Map<String, String> CACHE =
        new HashMap<>();

    public static String escape(String s) {
        if (!s.contains("\\")) {
            return s;
        }
        if (CACHE.containsKey(s)) {
            return CACHE.get(s);
        }
        try (var sc = new Scanner(s)) {
            String s1 = s;
            var st = sc.findAll(ESCAPE).toArray(MatchResult[]::new);
            for (MatchResult result : st) {
                // \,",b,f,t,n,r
                if (result.group(2) == null) {
                    switch (result.group(1)) {
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
                        valueOf((char)parseInt(result.group(3), 16))
                    );
                }
            }
            CACHE.put(s, s1);
            return s1;
        }
    }
}
