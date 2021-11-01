package org.overrun.json;

import static org.overrun.json.JsonElement.*;

/**
 * @author squid233
 */
public class JsonTest {
    public static void main(String[] args) {
        var json = new Json(true);
        var je = ofObj(null,
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
        var s = json.toJson(je);
        System.out.println(je);
        System.out.println(s);
        System.out.println(Json.compress(s));
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        try {
            json.fromJson(new JsonType() {
                @Override
                public JsonElement write() {
                    return null;
                }

                @Override
                public void read(JsonReader in) throws Exception {
                    in.beginObject();
                    while (in.hasNext()) {
                        System.out.print(in.nextName());
                        System.out.print(":");
                        System.out.println(in.nextString());
                    }
                    in.endObject();
                }
            }, "{\"name\":\"value\",\"name2\":\"value2\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
