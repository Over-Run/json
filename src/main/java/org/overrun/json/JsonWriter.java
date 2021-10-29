package org.overrun.json;

import java.io.IOException;

/**
 * Use stream-style to write objects to {@link JsonElement}.
 *
 * @author squid233
 * @since 0.1.0
 */
public class JsonWriter {
    private final Node node = new Node();
    private int stackDepth;

    private static final class Node {
        public ValueType type;
        public String name;
        public Object value;
        public Node child;

        public Node getChild(int depth) {
            var c = this;
            for (int i = 0; i < depth; i++) {
                c = c.child;
            }
            return c;
        }
    }

    public JsonWriter beginObject()
        throws IOException {
        
        ++stackDepth;
        return this;
    }

    public int getStackDepth() {
        return stackDepth;
    }
}
