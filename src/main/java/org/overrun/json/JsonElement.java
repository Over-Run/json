package org.overrun.json;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import static org.overrun.json.JsonReader.*;
import static org.overrun.json.ValueType.*;

/**
 * @author squid233
 * @since 0.1.0
 */
public final class JsonElement {
    public final ValueType type;
    @Nullable
    public final String name;
    public final Object value;
    public JsonElement base;

    public static class Builder {
        public ValueType type;
        @Nullable
        public String name;
        public Object value;

        public Builder type(ValueType type) {
            this.type = type;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder value(Object value) {
            this.value = value;
            return this;
        }

        public JsonElement build() {
            return new JsonElement(type, name, value);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static JsonElement ofNul(String name) {
        return new JsonElement(NULL, name, null);
    }

    public static JsonElement ofObj(String name, JsonElement... value) {
        return new JsonElement(OBJECT, name, value);
    }

    public static JsonElement ofArr(String name, JsonElement... value) {
        return new JsonElement(ARRAY, name, value);
    }

    public static JsonElement ofStr(String name, String value) {
        return new JsonElement(STRING, name, value);
    }

    public static JsonElement ofBool(String name, boolean value) {
        return new JsonElement(BOOLEAN, name, value);
    }

    public static JsonElement ofInt(String name, int value) {
        return new JsonElement(NUMBER_INTEGER, name, value);
    }

    public static JsonElement ofFlt(String name, double value) {
        return new JsonElement(NUMBER_FLOAT, name, value);
    }

    public static JsonElement ofFlt(String name, float value) {
        return ofFlt(name, (double) value);
    }

    public static JsonElement ofBin(String name, byte[] value) {
        return new JsonElement(BINARY, name, value);
    }

    public JsonElement(ValueType type,
                       @Nullable String name,
                       Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
        if (value.getClass() == JsonElement[].class) {
            for (var e : (JsonElement[]) value) {
                e.base = this;
            }
        }
    }

    public boolean isNull() {
        return type == NULL;
    }

    public boolean isObject() {
        return type == OBJECT;
    }

    public boolean isArray() {
        return type == ARRAY;
    }

    public boolean isString() {
        return type == STRING;
    }

    public boolean isBoolean() {
        return type == BOOLEAN;
    }

    public boolean isInt() {
        return type == NUMBER_INTEGER;
    }

    public boolean isFloat() {
        return type == NUMBER_FLOAT;
    }

    public boolean isNumber() {
        return isInt() || isFloat();
    }

    public boolean isBinary() {
        return type == BINARY;
    }

    public boolean isDiscarded() {
        return type == DISCARDED;
    }

    private void appendName(StringBuilder sb,
                            boolean prettyPrint) {
        if (name != null) {
            sb.append("\"")
                    .append(name)
                    .append("\"")
                    .append(NAME_SEPARATOR);
            if (prettyPrint) {
                sb.append(" ");
            }
        }
    }

    private String toJson(final boolean prettyPrint,
                          int indent) {
        var sb = new StringBuilder();
        switch (type) {
            case NULL:
                appendName(sb, prettyPrint);
                sb.append("null");
                break;
            case OBJECT:
                appendName(sb, prettyPrint);
                sb.append(BEGIN_OBJECT);
                int arri = 0;
                for (var v : (JsonElement[]) value) {
                    if (arri > 0) {
                        sb.append(VALUE_SEPARATOR);
                    }
                    if (prettyPrint) {
                        sb.append("\n")
                                .append(" ".repeat(indent + 2));
                    }
                    sb.append(v.toJson(prettyPrint, indent + 2));
                    ++arri;
                }
                if (prettyPrint) {
                    sb.append("\n")
                            .append(" ".repeat(indent));
                }
                sb.append(END_OBJECT);
                break;
            case ARRAY:
                appendName(sb, prettyPrint);
                sb.append(BEGIN_ARRAY);
                arri = 0;
                for (var v : (JsonElement[]) value) {
                    if (arri > 0) {
                        sb.append(VALUE_SEPARATOR);
                    }
                    if (prettyPrint) {
                        sb.append("\n")
                                .append(" ".repeat(indent + 2));
                    }
                    sb.append(v.toJson(prettyPrint, indent + 2));
                    ++arri;
                }
                if (prettyPrint) {
                    sb.append("\n")
                            .append(" ".repeat(indent));
                }
                sb.append(END_ARRAY);
                break;
            case STRING:
                appendName(sb, prettyPrint);
                sb.append("\"")
                        .append(value)
                        .append("\"");
                break;
            case BOOLEAN:
                appendName(sb, prettyPrint);
                sb.append(value);
                break;
        }
        return sb.toString();
    }

    String toJson(boolean prettyPrint) {
        return toJson(prettyPrint, 0);
    }

    @Override
    public String toString() {
        return "{type=" +
                type +
                ", name=" +
                name +
                ", value={" +
                (value instanceof JsonElement[]
                        ? Arrays.toString((JsonElement[]) value)
                        : value) +
                "}}";
    }
}
