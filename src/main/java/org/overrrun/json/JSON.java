package org.overrrun.json;

import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static org.overrrun.json.ValueType.ARRAY;
import static org.overrrun.json.ValueType.OBJECT;

/**
 * @author squid233
 * @since 0.1.0
 */
public class JSON {
    public static final int OVERRUN_JSON_VERSION_MAJOR = 1;
    public static final int OVERRUN_JSON_VERSION_MINOR = 0;
    public static final int OVERRUN_JSON_VERSION_PATCH = 0;
    private boolean prettyPrint;

    public JSON(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    private static void appendName(String name,
                                   Appendable writer)
            throws IOException {
        writer.append("\"").append(requireNonNull(name)).append("\":");
    }

    private void toJson(JsonElement json,
                        Appendable writer,
                        ValueType currVT,
                        ValueType lastVt,
                        int i,
                        int lastI,
                        JsonElement last)
            throws IOException {
        if (json != null) {
            if (json.isObject()) {
                if (json.name != null) {
                    appendName(json.name, writer);
                }
                currVT = OBJECT;
                lastI = i;
                last = json;
                lastVt = json.type;
                writer.append("{");
                if (json.value != null) {
                    int arrI = 0;
                    for (var v : (JsonElement[]) json.value) {
                        if (arrI > 0) {
                            writer.append(",");
                        }
                        toJson(v,
                                writer,
                                currVT,
                                lastVt,
                                i,
                                lastI,
                                last);
                        ++arrI;
                    }
                }
                //i = lastI;
                currVT = lastVt;
                writer.append("}");
            } else if (json.isArray()) {
                if (json.name != null) {
                    appendName(json.name, writer);
                }
                currVT = ARRAY;
                lastI = i;
                last = json;
                lastVt = json.type;
                writer.append("[");
                if (json.value != null) {
                    int arrI = 0;
                    for (var v : (JsonElement[]) json.value) {
                        if (arrI > 0) {
                            writer.append(",");
                        }
                        toJson(v,
                                writer,
                                currVT,
                                lastVt,
                                i,
                                lastI,
                                last);
                        ++arrI;
                    }
                }
                //i = lastI;
                currVT = lastVt;
                writer.append("]");
            } else if (json.isString()) {
                if (i > 0) {
                    writer.append(",");
                }
                ++i;
                assert currVT == OBJECT || currVT == ARRAY;
                if (currVT == OBJECT) {
                    writer.append("\"")
                            .append(json.name)
                            .append("\":\"")
                            .append(json.value.toString())
                            .append("\"");
                } else {
                    writer.append("\"")
                            .append(json.value.toString())
                            .append("\"");
                }
            }
        }
    }

    public void toJson(JsonElement json,
                       Appendable writer)
            throws IOException {
        toJson(json,
                writer,
                null,
                null,
                0,
                0,
                null);
    }

    public void toJson(JsonElement json,
                       StringBuilder writer) {
        try {
            toJson(json, (Appendable) writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
