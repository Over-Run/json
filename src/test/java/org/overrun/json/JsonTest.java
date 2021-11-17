package org.overrun.json;

import org.intellij.lang.annotations.Language;

import java.io.IOException;

import static org.overrun.json.JsonElement.*;

/**
 * @author squid233
 */
public class JsonTest {
    private static void write(Json json) throws Exception {
        System.out.println(json.toJson(JsonWritable.of(() ->
            ofObj(null,
                ofStr("org_name", "Overrun Organization"),
                ofObj("members",
                    ofObj("ARMrAmzing",
                        ofBool("isMember", true),
                        ofBool("isOwner", false),
                        ofInt("permissions", 1)
                    ),
                    ofObj("beanflame",
                        ofBool("isMember", true),
                        ofBool("isOwner", false),
                        ofInt("permissions", 1)
                    ),
                    ofObj("crazy_piggy",
                        ofBool("isMember", true),
                        ofBool("isOwner", false),
                        ofInt("permissions", 1)
                    ),
                    ofObj("squid233",
                        ofBool("isMember", true),
                        ofBool("isOwner", true),
                        ofInt("permissions", 2)
                    ),
                    ofObj("Teddy Li",
                        ofBool("isMember", true),
                        ofBool("isOwner", false),
                        ofInt("permissions", 1)
                    ),
                    ofObj("wkmyc",
                        ofBool("isMember", true),
                        ofBool("isOwner", false),
                        ofInt("permissions", 1)
                    )
                )
            ))));
    }

    private static void compressRead(Json json) throws Exception {
        @Language("JSON")
        var src = "{\n" +
            // String value
            "  \"name1\": \"value1\",\n" +
            // String value with escaping char
            "  \"name2\": \"\\\"value2\",\n" +
            // String value with Chinese, English and Japanese
            "  \"name3\": \"CJK統合漢字\",\n" +
            // Null value
            "  \"nullV\": null,\n" +
            // Boolean value (true)
            "  \"bool1\": true,\n" +
            // Boolean value (false)
            "  \"bool2\": false,\n" +
            // Int value
            "  \"int\": 123456,\n" +
            // Double value (int format)
            "  \"double1\": 1,\n" +
            // Double value
            "  \"double2\": 1.1,\n" +
            // Double value (scientific format)
            "  \"double3\": 1.233e2\n" +
            "}";
        System.out.println(Json.compress(src));
        json.load(in -> {
            in.beginObject();
            while (in.hasNext()) {
                System.out.print(in.nextName());
                System.out.print(":");
                Object o;
                try {
                    o = in.nextString();
                } catch (IOException e) {
                    try {
                        o = null;
                        in.nextNull();
                    } catch (IOException ex) {
                        try {
                            o = in.nextBoolean();
                        } catch (IOException exc) {
                            try {
                                o = in.nextDouble();
                            } catch (IOException exception) {
                                o = in.nextInt();
                            }
                        }
                    }
                }
                System.out.println(o);
            }
            /*for (int i = 0; i < 3; i++) {
                System.out.print(in.nextName());
                System.out.print(":");
                System.out.println(in.nextString());
            }
            System.out.print(in.nextName());
            System.out.print(":");
            in.nextNull();
            System.out.println((Object) null);
            for (int i = 0; i < 2; i++) {
                System.out.print(in.nextName());
                System.out.print(":");
                System.out.println(in.nextBoolean());
            }
            System.out.print(in.nextName());
            System.out.print(":");
            System.out.println(in.nextInt());
            for (int i = 0; i < 3; i++) {
                System.out.print(in.nextName());
                System.out.print(":");
                System.out.println(in.nextDouble());
            }*/
            in.endObject();
        }, src);
    }

    public static void main(String[] args) throws Exception {
        var json = new Json(true);
        write(json);
        compressRead(json);
    }
}
