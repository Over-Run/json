package org.overrun.json;

/**
 * @author squid233
 * @since 0.1.0
 */
@FunctionalInterface
public interface JsonReadable {
    /**
     * Read from a reader.
     *
     * @param in The reader.
     * @throws Exception {@link java.io.IOException} when malformed reading
     */
    void read(JsonReader in) throws Exception;
}
