package org.overrun.json;

/**
 * @author squid233
 * @since 0.2.0
 */
public class JsonWriter {
    private final Appendable out;

    public JsonWriter(Appendable out) {
        this.out = out;
    }

    @Override
    public String toString() {
        if (out instanceof CharSequence) {
            return out.toString();
        }
        return super.toString();
    }
}
