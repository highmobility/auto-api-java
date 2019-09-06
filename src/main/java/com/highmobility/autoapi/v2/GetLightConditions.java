// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all light conditions properties.
 */
public class GetLightConditions extends GetCommand {
    public GetLightConditions() {
        super(Identifier.LIGHT_CONDITIONS, getStateIdentifiers());
    }

    GetLightConditions(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02 };
    }
}