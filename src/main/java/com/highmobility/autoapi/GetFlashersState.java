// TODO: license

package com.highmobility.autoapi;

/**
 * Get all honk horn flash lights properties.
 */
public class GetFlashersState extends GetCommand {
    public GetFlashersState() {
        super(Identifier.HONK_HORN_FLASH_LIGHTS);
    }

    GetFlashersState(byte[] bytes) {
        super(bytes);
    }
}