package org.overrun.json;

/**
 * @author squid233
 * @since 0.1.0
 */
public interface JsonType {
    /**
     * Write this to an element.
     *
     * @return The element.
     * @throws Exception Any exceptions
     */
    JsonElement write() throws Exception;

    /**
     * Read from a reader.
     *
     * @param in The reader.
     * @throws Exception {@link java.io.IOException} when malformed reading
     */
    void read(JsonReader in) throws Exception;
}
