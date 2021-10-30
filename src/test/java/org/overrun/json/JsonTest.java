package org.overrun.json;

import static org.overrun.json.JsonElement.*;

/**
 * @author squid233
 */
public class JsonTest {
    public static void main(String[] args) {
        var json = new JSON(true);
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
        System.out.println(json.toJson(je));
    }
}
