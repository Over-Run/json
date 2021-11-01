package org.overrun.json;

import org.jetbrains.annotations.Nullable;

/**
 * the JSON type enumeration
 * <p>
 * This enumeration collects the different JSON types. It is internally used to
 * distinguish the stored values, and the functions rely on it.
 *
 * @author squid233
 * @since 0.1.0
 */
public enum ValueType {
    /**
     * null value
     */
    NULL(null),
    /**
     * object (unordered set of name/value pairs)
     */
    OBJECT(JsonElement[].class),
    /**
     * array (ordered collection of values)
     */
    ARRAY(JsonElement[].class),
    /**
     * string value
     */
    STRING(String.class),
    /**
     * boolean value
     */
    BOOLEAN(boolean.class),
    /**
     * number value (signed integer)
     */
    NUMBER_INTEGER(int.class),
    /**
     * number value (floating-point)
     */
    NUMBER_FLOAT(double.class),
    /**
     * binary array (ordered collection of bytes)
     */
    BINARY(byte[].class),
    /**
     * discarded by the parser callback function
     */
    DISCARDED(null);

    private static final int[] ORDER = {
        0 /* null */, 3 /* object */, 4 /* array */, 5 /* string */,
        1 /* boolean */, 2 /* integer */, 2 /* float */,
        6 /* binary */
    };
    @Nullable
    public final Class<?> type;

    ValueType(@Nullable Class<?> type) {
        this.type = type;
    }

    /**
     * comparison operator for JSON types
     *
     * @param r right
     * @return an ordering that is similar to Python:<br>
     * - order: null < boolean < number < object < array < string < binary<br>
     * - furthermore, each type is not smaller than itself<br>
     * - discarded values are not comparable<br>
     * - binary is represented as a b"" string in python and directly comparable to a
     * string; however, making a binary array directly comparable with a string would
     * be surprising behavior in a JSON file.<br>
     */
    public boolean less(final ValueType r) {
        final var li = ordinal();
        final var ri = r.ordinal();
        return li < ORDER.length
            && ri < ORDER.length
            && ORDER[li] < ORDER[ri];
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
