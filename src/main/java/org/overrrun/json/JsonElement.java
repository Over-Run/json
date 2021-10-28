package org.overrrun.json;

import static org.overrrun.json.ValueType.*;

/**
 * @author squid233
 * @since 0.1.0
 */
public class JsonElement {
    public final ValueType type;
    public final String name;
    public final Object value;

    public JsonElement(ValueType type,
                       String name,
                       Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
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
                .append("\":");
            if (prettyPrint) {
                sb.append(" ");
            }
        }
    }

    private String toJson(final boolean prettyPrint,
                          int i) {
        var sb = new StringBuilder();
        switch (type) {
            case NULL:
                appendName(sb, prettyPrint);
                sb.append("null");
                break;
            case OBJECT:
                appendName(sb, prettyPrint);
                sb.append("{");
                int arri = 0;
                for (var v : (JsonElement[]) value) {
                    if (arri > 0) {
                        sb.append(",");
                        if (prettyPrint) {
                            sb.append(" ");
                        }
                    }
                    sb.append(v.toJson(prettyPrint, i));
                    ++arri;
                }
                sb.append("}");
                break;
            case ARRAY:
                appendName(sb, prettyPrint);
                sb.append("[");
                arri = 0;
                for (var v : (JsonElement[]) value) {
                    if (arri > 0) {
                        sb.append(",");
                        if (prettyPrint) {
                            sb.append(" ");
                        }
                    }
                    sb.append(v.toJson(prettyPrint, i));
                    ++arri;
                }
                sb.append("]");
                break;
            case STRING:
                appendName(sb, prettyPrint);
                sb.append("\"")
                    .append(value)
                    .append("\"");
                break;
        }
        return sb.toString();
    }

    public String toJson(boolean prettyPrint) {
        return toJson(prettyPrint, 0);
    }

    @Override
    public String toString() {
        return "{type=" +
            type +
            ", name=" +
            name +
            ", value={" +
            value +
            "}}";
    }
}
