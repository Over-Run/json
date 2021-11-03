package org.overrun.json;

import static org.overrun.json.JsonElement.*;

/**
 * @author squid233
 */
public class JsonTest {
    public static void main(String[] args) {
        var json = new Json(true);
        class Organization implements JsonType {
            @Override
            public JsonElement write() {
                return ofObj(null,
                    ofStr("org_name", "Overrun Organization"),
                    ofObj("members",
                        ofObj("ARMrAmzing",
                            ofBool("isMember", true),
                            ofBool("isOwner", false)
                        ),
                        ofObj("beanflame",
                            ofBool("isMember", true),
                            ofBool("isOwner", false)
                        ),
                        ofObj("crazy_piggy",
                            ofBool("isMember", true),
                            ofBool("isOwner", false)
                        ),
                        ofObj("squid233",
                            ofBool("isMember", true),
                            ofBool("isOwner", true)
                        ),
                        ofObj("Teddy Li",
                            ofBool("isMember", true),
                            ofBool("isOwner", false)
                        ),
                        ofObj("wkmyc",
                            ofBool("isMember", true),
                            ofBool("isOwner", false)
                        )
                    )
                );
            }

            @Override
            public void read(JsonReader in) throws Exception {
                in.beginObject();
                for (int i = 0; i < 2; i++) {
                    System.out.print(in.nextName());
                    System.out.print(":");
                    System.out.println(in.nextString());
                }
                System.out.print(in.nextName());
                in.nextNull();
                System.out.print(":");
                System.out.println((Object) null);
                for (int i = 0; i < 2; i++) {
                    System.out.print(in.nextName());
                    System.out.print(":");
                    System.out.println(in.nextBoolean());
                }
                in.endObject();
            }
        }
        var src = "{\n" +
            "  \"name\": \"value\",\n" +
            "  \"name2\": \"\\\"value2\",\n" +
            "  \"nullV\": null,\n" +
            "  \"bool1\": true,\n" +
            "  \"bool2\": false\n" +
            "}";
        System.out.println(Json.compress(src));
        try {
            json.fromJson(
                new Organization(),
                src
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
