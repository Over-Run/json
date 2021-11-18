package org.overrun.json;

import org.jetbrains.annotations.Nullable;

/**
 * @author squid233
 * @since 0.1.0
 */
@FunctionalInterface
public interface JsonWritable {
    /**
     * Write this to an element.
     *
     * @return The element.
     * @throws Exception Any exceptions
     */
    @Nullable
    JsonElement write() throws Exception;
}
