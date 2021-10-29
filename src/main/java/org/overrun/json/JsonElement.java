package org.overrun.json;

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
        return type == ValueType.NULL;
    }

    public boolean isObject() {
        return type == ValueType.OBJECT;
    }

    public boolean isArray() {
        return type == ValueType.ARRAY;
    }

    public boolean isString() {
        return type == ValueType.STRING;
    }

    public boolean isBoolean() {
        return type == ValueType.BOOLEAN;
    }

    public boolean isInt() {
        return type == ValueType.NUMBER_INTEGER;
    }

    public boolean isFloat() {
        return type == ValueType.NUMBER_FLOAT;
    }

    public boolean isNumber() {
        return isInt() || isFloat();
    }

    public boolean isBinary() {
        return type == ValueType.BINARY;
    }

    public boolean isDiscarded() {
        return type == ValueType.DISCARDED;
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
                          int indent) {
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
                sb.append("}");
                break;
            case ARRAY:
                appendName(sb, prettyPrint);
                sb.append("[");
                arri = 0;
                for (var v : (JsonElement[]) value) {
                    if (arri > 0) {
                        sb.append(",");
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
                sb.append("]");
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

    protected String toJson(boolean prettyPrint) {
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
