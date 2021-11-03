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
                       String c,
                       int pos) {
        return "Expected '" +
            tk +
            "' but got '" +
            c +
            "' at pos " +
            pos;
    }

    private String ioe(String tk,
                       char c,
                       int pos) {
        return ioe(tk, valueOf(c), pos);
    }

    private String ioe(char tk,
                       char c,
                       int pos) {
        return ioe(valueOf(tk), c, pos);
    }

    private String ioe(String tk,
                       String c) {
        return ioe(tk, c, pos - 1);
    }

    private String ioe(String tk,
                       char c) {
        return ioe(tk, valueOf(c));
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
        int posOrg = pos;
        var c = src.charAt(pos);
        // check if string begin
        if (c == '"') {
            var sb = new StringBuilder();
            // check if string end
            while (src.charAt(++pos) != '"'
                || src.charAt(pos - 1) == '\\') {
                sb.append(src.charAt(pos));
            }
            ++pos;
            // check if separating name and value
            if (src.charAt(++pos - 1) != NAME_SEPARATOR) {
                throw new IOException(ioe(
                    NAME_SEPARATOR,
                    src.charAt(pos - 1),
                    pos
                ));
            }
            return sb.toString();
        }
        throw new IOException(ioe('"', c, posOrg + 1));
    }

    public String nextString()
        throws IOException {
        var c = src.charAt(pos);
        // check if string begin
        if (c == '"') {
            var sb = new StringBuilder();
            // check if string end
            while (src.charAt(++pos) != '"'
                || src.charAt(pos - 1) == '\\') {
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
        var sb = new StringBuilder();
        // check if null begin
        if (c == 'n') {
            sb.append(c);
            char c0;
            // check if null end
            while ((c0 = src.charAt(++pos)) != VALUE_SEPARATOR
                && c0 != END_OBJECT
                && c0 != END_ARRAY) {
                sb.append(src.charAt(pos));
            }
            ++pos;
            if (!"null".equals(sb.toString())) {
                throw new IOException(ioe("null", sb.toString()));
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
        var sb = new StringBuilder();
        // check if bool begin
        if (c == 't' || c == 'f') {
            sb.append(c);
            boolean b;
            char c0;
            // check if bool end
            while ((c0 = src.charAt(++pos)) != VALUE_SEPARATOR
                && c0 != END_OBJECT
                && c0 != END_ARRAY) {
                sb.append(src.charAt(pos));
            }
            ++pos;
            var s = sb.toString();
            if ("true".equals(s)) {
                b = true;
            } else if ("false".equals(s)) {
                b = false;
            } else {
                throw new IOException(ioe("true or false", s));
            }
            // check if separating values
            if (src.charAt(pos) == VALUE_SEPARATOR) {
                ++pos;
            }
            return b;
        }
        throw new IOException(ioe("t or f", c));
    }
}
