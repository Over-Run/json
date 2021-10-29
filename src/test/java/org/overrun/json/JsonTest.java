package org.overrun.json;

import static org.overrun.json.ValueType.*;

/**
 * @author squid233
 */
public class JsonTest {
    public static void main(String[] args) {
        var json = new JSON(true);
        var je = new JsonElement(
            OBJECT, null,
            new JsonElement[]{
                new JsonElement(
                    STRING, "org_name", "Overrun Organization"
                ),
                new JsonElement(
                    ARRAY, "members",
                    new JsonElement[]{
                        new JsonElement(OBJECT, null,
                            new JsonElement[]{
                                new JsonElement(
                                    STRING, "name", "ARMrAmzing"
                                ),
                                new JsonElement(
                                    BOOLEAN, "isMember", true
                                ),
                                new JsonElement(
                                    BOOLEAN, "isOwner", false
                                )
                            }
                        ),
                        new JsonElement(OBJECT, null,
                            new JsonElement[]{
                                new JsonElement(
                                    STRING, "name", "beanflame"
                                ),
                                new JsonElement(
                                    BOOLEAN, "isMember", true
                                ),
                                new JsonElement(
                                    BOOLEAN, "isOwner", false
                                )
                            }
                        ),
                        new JsonElement(OBJECT, null,
                            new JsonElement[]{
                                new JsonElement(
                                    STRING, "name", "crazy_piggy"
                                ),
                                new JsonElement(
                                    BOOLEAN, "isMember", true
                                ),
                                new JsonElement(
                                    BOOLEAN, "isOwner", false
                                )
                            }
                        ),
                        new JsonElement(OBJECT, null,
                            new JsonElement[]{
                                new JsonElement(
                                    STRING, "name", "squid233"
                                ),
                                new JsonElement(
                                    BOOLEAN, "isMember", true
                                ),
                                new JsonElement(
                                    BOOLEAN, "isOwner", true
                                )
                            }
                        ),
                        new JsonElement(OBJECT, null,
                            new JsonElement[]{
                                new JsonElement(
                                    STRING, "name", "Teddy Li"
                                ),
                                new JsonElement(
                                    BOOLEAN, "isMember", true
                                ),
                                new JsonElement(
                                    BOOLEAN, "isOwner", false
                                )
                            }
                        ),
                        new JsonElement(OBJECT, null,
                            new JsonElement[]{
                                new JsonElement(
                                    STRING, "name", "wkmyc"
                                ),
                                new JsonElement(
                                    BOOLEAN, "isMember", true
                                ),
                                new JsonElement(
                                    BOOLEAN, "isOwner", false
                                )
                            }
                        )
                    }
                )
            }
        );
        System.out.println(json.toJson(je));
    }
}
