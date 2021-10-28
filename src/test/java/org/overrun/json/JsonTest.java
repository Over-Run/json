package org.overrun.json;

import org.overrrun.json.JsonElement;

import static org.overrrun.json.ValueType.*;

/**
 * @author squid233
 */
public class JsonTest {
    public static void main(String[] args) {
        var je = new JsonElement(
            OBJECT, null,
            new JsonElement[]{
                new JsonElement(
                    STRING, "org_name", "Overrun Organization"
                ),
                new JsonElement(
                    ARRAY, "members",
                    new JsonElement[]{
                        new JsonElement(
                            STRING, null, "ARMrAmzing"
                        ),
                        new JsonElement(
                            STRING, null, "beanflame"
                        ),
                        new JsonElement(
                            STRING, null, "crazy_piggy"
                        ),
                        new JsonElement(
                            STRING, null, "squid233"
                        ),
                        new JsonElement(
                            STRING, null, "Teddy Li"
                        ),
                        new JsonElement(
                            STRING, null, "wkmyc"
                        ),
                    }
                )
            }
        );
        System.out.println(je.toJson(true));
    }
}
