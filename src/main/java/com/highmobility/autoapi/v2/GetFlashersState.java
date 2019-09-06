// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all honk horn flash lights properties.
 */
public class GetFlashersState extends GetCommand {
    public GetFlashersState() {
        super(Identifier.HONK_HORN_FLASH_LIGHTS, getStateIdentifiers());
    }

    GetFlashersState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01 };
    }
}