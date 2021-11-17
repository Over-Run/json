package org.overrun.json;

import org.jetbrains.annotations.Nullable;
import org.overrun.json.internal.JsonElementWriter;
import org.overrun.json.internal.JsonWriterWriter;

/**
 * @author squid233
 * @since 0.1.0
 */
public interface JsonWritable extends JsonElementWriter, JsonWriterWriter {
    static JsonWritable of(JsonElementWriter writer) {
        return new JsonWritable() {
            @Override
            @Nullable
            public JsonElement write() throws Exception {
                return writer.write();
            }
        };
    }

    static JsonWritable of(JsonWriterWriter writer) {
        return new JsonWritable() {
            @Override
            public void write(JsonWriter out) throws Exception {
                writer.write(out);
            }
        };
    }

    @Override
    @Nullable
    default JsonElement write() throws Exception {
        return null;
    }

    @Override
    default void write(JsonWriter out) throws Exception {
    }
}
