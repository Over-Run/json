package org.overrun.json;

import java.io.IOException;

import static java.lang.String.valueOf;

/**
 * @author squid233
 * @since 0.1.0
 */
public final class JsonReader {
    public static final char BEGIN_ARRAY = '[';
    public static final char BEGIN_OBJECT = '{';
    public static final char END_ARRAY = ']';
    public static final char END_OBJECT = '}';
    public static final char NAME_SEPARATOR = ':';
    public static final char VALUE_SEPARATOR = ',';
    private final String src;
    private int pos;

    public JsonReader(String src) {
        this.src = src;
    }

    private String ioe(String tk,
                       char c) {
        return "Expected '" +
            tk +
            "' but got '" +
            c +
            "' at pos " +
            (pos + 1);
    }

    private String ioe(char tk,
                       char c) {
        return ioe(valueOf(tk), c);
    }

    public boolean hasNext() {
        return pos + 1 < src.length();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Begin or end
    ///////////////////////////////////////////////////////////////////////////

    public void beginObject()
        throws IOException {
        var c = src.charAt(pos);
        if (c == BEGIN_OBJECT) {
            ++pos;
            return;
        }
        throw new IOException(ioe(BEGIN_OBJECT, c));
    }

    public void beginArray()
        throws IOException {
        var c = src.charAt(pos);
        if (c == BEGIN_ARRAY) {
            ++pos;
            return;
        }
        throw new IOException(ioe(BEGIN_ARRAY, c));
    }

    public void endObject()
        throws IOException {
        var c = src.charAt(pos);
        if (c == END_OBJECT) {
            ++pos;
            return;
        }
        throw new IOException(ioe(END_OBJECT, c));
    }

    public void endArray()
        throws IOException {
        var c = src.charAt(pos);
        if (c == END_ARRAY) {
            ++pos;
            return;
        }
        throw new IOException(ioe(END_ARRAY, c));
    }

    ///////////////////////////////////////////////////////////////////////////
    // Next
    ///////////////////////////////////////////////////////////////////////////

    public String nextName()
        throws IOException {
        var c = src.charAt(pos);
        // check if string begin
        if (c == '"') {
            var sb = new StringBuilder();
            // check if string end
            while (src.charAt(++pos) != '"') {
                sb.append(src.charAt(pos));
            }
            ++pos;
            // check if separating name and value
            if (src.charAt(++pos - 1) != NAME_SEPARATOR) {
                throw new IOException(ioe(NAME_SEPARATOR, c));
            }
            return sb.toString();
        }
        throw new IOException(ioe('"', c));
    }

    public String nextString()
        throws IOException {
        var c = src.charAt(pos);
        // check if string begin
        if (c == '"') {
            var sb = new StringBuilder();
            // check if string end
            while (src.charAt(++pos) != '"') {
                sb.append(src.charAt(pos));
            }
            ++pos;
            // check if separating values
            if (src.charAt(pos) == VALUE_SEPARATOR) {
                ++pos;
            }
            return sb.toString();
        }
        throw new IOException(ioe('"', c));
    }

    public void nextNull()
        throws IOException {
        var c = src.charAt(pos);
        // check if null begin
        if (c == 'n') {
            if (src.startsWith("null", pos)) {
                pos += 4;
            } else {
                throw new IOException(ioe("null", c));
            }
            // check if separating values
            if (src.charAt(pos) == VALUE_SEPARATOR) {
                ++pos;
            }
            return;
        }
        throw new IOException(ioe('n', c));
    }

    public boolean nextBoolean()
        throws IOException {
        var c = src.charAt(pos);
        // check if bool begin
        if (c == 't' || c == 'f') {
            boolean b;
            if (src.startsWith("true", pos)) {
                pos += 4;
                b = true;
            } else if (src.startsWith("false", pos)) {
                pos += 5;
                b = false;
            } else {
                throw new IOException(ioe("true or false", c));
            }
            // check if separating values
            if (src.charAt(pos) == VALUE_SEPARATOR) {
                ++pos;
            }
            return b;
        }
        throw new IOException(ioe("true or false", c));
    }
}
