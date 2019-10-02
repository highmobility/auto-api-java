// TODO: license

package com.highmobility.autoapi;

/**
 * Get all light conditions properties.
 */
public class GetLightConditions extends GetCommand {
    public GetLightConditions() {
        super(Identifier.LIGHT_CONDITIONS);
    }

    GetLightConditions(byte[] bytes) {
        super(bytes);
    }
}