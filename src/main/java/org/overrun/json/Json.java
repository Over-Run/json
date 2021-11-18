package org.overrun.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import static java.lang.Character.isWhitespace;
import static java.lang.String.valueOf;

/**
 * @author squid233
 * @since 0.1.0
 */
public class Json {
    public static final char BEGIN_ARRAY = '[';
    public static final char BEGIN_OBJECT = '{';
    public static final char END_ARRAY = ']';
    public static final char END_OBJECT = '}';
    public static final char NAME_SEPARATOR = ':';
    public static final char VALUE_SEPARATOR = ',';
    public static final int VERSION_MAJOR = 0;
    public static final int VERSION_MINOR = 2;
    public static final int VERSION_PATCH = 0;
    private final boolean prettyPrint;

    public Json(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    /**
     * Compress the json.
     *
     * @param str Json src.
     * @return Compressed string in new object.
     */
    public static String compress(String str) {
        var ln = str.toCharArray();
        var cpy = new int[ln.length];
        int l = 0;
        // copy array
        for (int i = 0; i < ln.length; i++) {
            cpy[i] = ln[i];
        }
        // iterate chars
        for (int i = 0; i < cpy.length; i++) {
            // check if whitespace
            if (isWhitespace(cpy[i])) {
                cpy[i] = -1;
                continue;
            }
            // check if string begin
            if (cpy[i] == '"') {
                // check if string end
                while (cpy[++i] != '"' || cpy[i - 1] == '\\') {
                    ++l;
                }
                ++l;
            }
            ++l;
        }
        var c = new char[l];
        for (int i = 0, j = 0; i < cpy.length; i++) {
            int ci = cpy[i];
            if (ci != -1) {
                c[j++] = (char) ci;
            }
        }
        return valueOf(c);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Writing JSON
    ///////////////////////////////////////////////////////////////////////////

    public void toJson(JsonElement json,
                       Appendable writer)
        throws IOException {
        writer.append(json.toJson(prettyPrint));
    }

    public void toJson(JsonElement json,
                       Writer writer)
        throws IOException {
        writer.write(json.toJson(prettyPrint));
    }

    public void toJson(JsonElement json,
                       StringBuilder writer) {
        writer.append(json.toJson(prettyPrint));
    }

    public String toJson(JsonElement json) {
        return json.toJson(prettyPrint);
    }

    public void toJson(JsonWritable json,
                       Appendable writer)
        throws Exception {
        var w = json.write();
        if (w != null) {
            toJson(w, writer);
        }
    }

    public void toJson(JsonWritable json,
                       Writer writer)
        throws Exception {
        var w = json.write();
        if (w != null) {
            toJson(w, writer);
        }
    }

    public void toJson(JsonWritable json,
                       StringBuilder writer)
        throws Exception {
        var w = json.write();
        if (w != null) {
            toJson(w, writer);
        }
    }

    public String toJson(JsonWritable json)
        throws Exception {
        var w = json.write();
        if (w != null) {
            return toJson(w);
        }
        return "null";
    }

    ///////////////////////////////////////////////////////////////////////////
    // Reading JSON
    ///////////////////////////////////////////////////////////////////////////

    public void load(JsonReadable type,
                     String src)
        throws Exception {
        type.read(new JsonReader(compress(src)));
    }

    public void load(JsonReadable type,
                     Reader reader)
        throws Exception {
        var sb = new StringBuilder();
        if (reader instanceof BufferedReader) {
            String ln;
            // append all lines
            while ((ln = ((BufferedReader) reader).readLine()) != null) {
                sb.append(ln);
            }
        } else {
            int read;
            // append all chars
            while ((read = reader.read()) != -1) {
                sb.append((char) read);
            }

        }
        load(type, sb.toString());
    }
}
