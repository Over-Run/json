package org.overrun.json.internal;

import org.jetbrains.annotations.Nullable;
import org.overrun.json.JsonElement;

/**
 * @author squid233
 * @since 0.2.0
 */
@FunctionalInterface
public interface JsonElementWriter {
    /**
     * Write this to an element.
     *
     * @return The element.
     * @throws Exception Any exceptions
     */
    @Nullable
    JsonElement write() throws Exception;
}
