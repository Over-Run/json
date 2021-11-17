package org.overrun.json.internal;

import org.overrun.json.JsonWriter;

/**
 * @author squid233
 * @since 0.2.0
 */
@FunctionalInterface
public interface JsonWriterWriter {
    /**
     * Write json to the writer.
     *
     * @param out Writer.
     * @throws Exception Any exceptions
     * @since 0.2.0
     */
    void write(JsonWriter out) throws Exception;
}
