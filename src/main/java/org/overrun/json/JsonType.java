package org.overrun.json;

/**
 * @author squid233
 * @since 0.1.0
 */
public interface JsonType {
    JsonElement write() throws Exception;

    void read(JsonReader in) throws Exception;
}
