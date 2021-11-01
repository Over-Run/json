package org.overrun.json;

import java.io.IOException;

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
    private int pos = 1;

    public JsonReader(String src) {
        this.src = src;
    }

    private String ioe(char tk,
                       char c) {
        return "Expected '" +
            tk +
            "' but got '" +
            c +
            "' at pos " +
            pos;
    }

    public boolean hasNext() {
        return pos < src.length();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Begin or end
    ///////////////////////////////////////////////////////////////////////////

    public void beginObject()
        throws IOException {
        var c = src.charAt(pos - 1);
        if (c == BEGIN_OBJECT) {
            ++pos;
            return;
        }
        throw new IOException(ioe(BEGIN_OBJECT, c));
    }

    public void beginArray()
        throws IOException {
        var c = src.charAt(pos - 1);
        if (c == BEGIN_ARRAY) {
            ++pos;
            return;
        }
        throw new IOException(ioe(BEGIN_ARRAY, c));
    }

    public void endObject()
        throws IOException {
        var c = src.charAt(pos - 1);
        if (c == END_OBJECT) {
            ++pos;
            return;
        }
        throw new IOException(ioe(END_OBJECT, c));
    }

    public void endArray()
        throws IOException {
        var c = src.charAt(pos - 1);
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
        var c = src.charAt(pos - 1);
        if (c == '"') {
            var sb = new StringBuilder();
            while (src.charAt(++pos - 1) != '"') {
                sb.append(src.charAt(pos - 1));
            }
            ++pos;
            if (src.charAt(++pos - 2) != NAME_SEPARATOR) {
                throw new IOException(ioe(NAME_SEPARATOR, c));
            }
            return sb.toString();
        }
        throw new IOException(ioe('"', c));
    }

    public String nextString()
        throws IOException {
        var c = src.charAt(pos - 1);
        if (c == '"') {
            var sb = new StringBuilder();
            while (src.charAt(++pos - 1) != '"') {
                sb.append(src.charAt(pos - 1));
            }
            ++pos;
            if (src.charAt(pos - 1) == VALUE_SEPARATOR) {
                ++pos;
            }
            return sb.toString();
        }
        throw new IOException(ioe('"', c));
    }
}
