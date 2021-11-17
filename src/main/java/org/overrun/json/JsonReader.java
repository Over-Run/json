package org.overrun.json;

import java.io.IOException;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static org.overrun.json.Escape.unescape;

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
        return ioe(tk, c, pos);
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

    private void nextChar(char tk) throws IOException {
        var c = src.charAt(pos);
        if (c == tk) ++pos;
        else throw new IOException(ioe(tk, c));
    }

    public void beginObject() throws IOException {
        nextChar(BEGIN_OBJECT);
    }

    public void beginArray() throws IOException {
        nextChar(BEGIN_ARRAY);
    }

    public void endObject() throws IOException {
        nextChar(END_OBJECT);
    }

    public void endArray() throws IOException {
        nextChar(END_ARRAY);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Next
    ///////////////////////////////////////////////////////////////////////////

    public String nextName() throws IOException {
        int posOrg = pos;
        var c = src.charAt(pos);
        // check if string begin
        if (c == '"') {
            var sb = new StringBuilder();
            // check if string end
            while (src.charAt(++pos) != '"'
                || src.charAt(pos - 1) == '\\')
                sb.append(src.charAt(pos));
            ++pos;
            // check if separating name and value
            if (src.charAt(++pos - 1) != NAME_SEPARATOR) {
                int np = pos;
                // restore pos
                pos = posOrg;
                throw new IOException(ioe(
                    NAME_SEPARATOR,
                    src.charAt(np - 1),
                    np
                ));
            }
            return unescape(sb.toString());
        }
        throw new IOException(ioe('"', c, posOrg + 1));
    }

    private void separateValue() {
        // check if separating values
        if (src.charAt(pos) == VALUE_SEPARATOR)
            ++pos;
    }

    public String nextString() throws IOException {
        var c = src.charAt(pos);
        // check if string begin
        if (c == '"') {
            var sb = new StringBuilder();
            // check if string end
            while (src.charAt(++pos) != '"'
                || src.charAt(pos - 1) == '\\')
                sb.append(src.charAt(pos));
            ++pos;
            separateValue();
            return unescape(sb.toString());
        }
        throw new IOException(ioe('"', c));
    }

    private void nextValue(StringBuilder sb) {
        char c0;
        while ((c0 = src.charAt(++pos)) != VALUE_SEPARATOR
            && c0 != END_OBJECT
            && c0 != END_ARRAY)
            sb.append(src.charAt(pos));
    }

    public void nextNull() throws IOException {
        int posOrg = pos;
        var c = src.charAt(pos);
        var sb = new StringBuilder();
        // check if null begin
        if (c == 'n') {
            sb.append(c);
            // check if null end
            nextValue(sb);
            ++pos;
            if (!"null".equals(sb.toString())) {
                int np = pos;
                pos = posOrg;
                throw new IOException(ioe("null", sb.toString(), np));
            }
            separateValue();
            return;
        }
        throw new IOException(ioe('n', c));
    }

    public boolean nextBoolean() throws IOException {
        int posOrg = pos;
        var c = src.charAt(pos);
        var sb = new StringBuilder();
        // check if bool begin
        if (c == 't' || c == 'f') {
            sb.append(c);
            boolean b;
            // check if bool end
            nextValue(sb);
            var s = sb.toString();
            if ("true".equals(s)) b = true;
            else if ("false".equals(s)) b = false;
            else {
                int np = pos;
                pos = posOrg;
                throw new IOException(ioe("true or false", s, np));
            }
            separateValue();
            return b;
        }
        throw new IOException(ioe("t or f", c));
    }

    private String nextNumber() throws IOException {
        var c = src.charAt(pos);
        var sb = new StringBuilder();
        // check if number begin
        if (c >= '0' && c <= '9') {
            sb.append(c);
            // check if number end
            nextValue(sb);
            separateValue();
            return sb.toString();
        }
        throw new IOException(ioe("0 to 9", c));
    }

    public int nextInt() throws IOException {
        return parseInt(nextNumber());
    }

    public double nextDouble() throws IOException {
        return parseDouble(nextNumber());
    }
}
