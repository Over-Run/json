package org.overrun.json;

import static org.overrun.json.JsonElement.*;

/**
 * @author squid233
 */
public class JsonTest {
    public static void main(String[] args) throws Exception {
        var json = new Json(true);
        class Organization implements JsonType {
            @Override
            public JsonElement write() {
                return ofObj(null,
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
                }
                in.endObject();
            }
        }
        var src = "{\n" +
            "  \"name1\": \"value1\",\n" +
            "  \"name2\": \"\\\"value2\",\n" +
            "  \"nullV\": null,\n" +
            "  \"bool1\": true,\n" +
            "  \"bool2\": false,\n" +
            "  \"int\": 123456,\n" +
            "  \"double1\": 1,\n" +
            "  \"double2\": 1.1,\n" +
            "  \"double3\": 1e1\n" +
            "}";
        var org = new Organization();
        System.out.println(json.toJson(org));
        System.out.println(Json.compress(src));
        json.fromJson(org, src);
    }
}
