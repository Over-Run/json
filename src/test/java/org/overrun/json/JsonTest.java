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
            ofArr("members",
                ofObj(null,
                    ofStr("name", "ARMrAmzing"),
                    ofBool("isMember", true),
                    ofBool("isOwner", false)
                ),
                ofObj(null,
                    ofStr("name", "beanflame"),
                    ofBool("isMember", true),
                    ofBool("isOwner", false)
                ),
                ofObj(null,
                    ofStr("name", "crazy_piggy"),
                    ofBool("isMember", true),
                    ofBool("isOwner", false)
                ),
                ofObj(null,
                    ofStr("name", "squid233"),
                    ofBool("isMember", true),
                    ofBool("isOwner", true)
                ),
                ofObj(null,
                    ofStr("name", "Teddy Li"),
                    ofBool("isMember", true),
                    ofBool("isOwner", false
                    )
                ),
                ofObj(null,
                    ofStr("name", "wkmyc"),
                    ofBool("isMember", true),
                    ofBool("isOwner", false)
                )
            )
        );
        System.out.println(json.toJson(je));
    }
}
