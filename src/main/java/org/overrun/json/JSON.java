package org.overrun.json;

import java.io.IOException;

/**
 * @author squid233
 * @since 0.1.0
 */
public class JSON {
    public static final int OVERRUN_JSON_VERSION_MAJOR = 0;
    public static final int OVERRUN_JSON_VERSION_MINOR = 1;
    public static final int OVERRUN_JSON_VERSION_PATCH = 0;
    private final boolean prettyPrint;

    public JSON(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public void toJson(JsonElement json,
                       Appendable writer)
            throws IOException {
        writer.append(json.toJson(prettyPrint));
    }

    public void toJson(JsonElement json,
                       StringBuilder writer) {
        writer.append(json.toJson(prettyPrint));
    }

    public String toJson(JsonElement json) {
        return json.toJson(prettyPrint);
    }
}
