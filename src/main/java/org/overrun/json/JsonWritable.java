package org.overrun.json;

/**
 * @author squid233
 * @since 0.1.0
 */
public interface JsonWritable {
    /**
     * Write this to an element.
     *
     * @return The element.
     * @throws Exception Any exceptions
     */
    JsonElement write() throws Exception;
}
