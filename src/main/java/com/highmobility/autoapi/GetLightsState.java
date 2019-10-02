// TODO: license

package com.highmobility.autoapi;

/**
 * Get all lights properties.
 */
public class GetLightsState extends GetCommand {
    public GetLightsState() {
        super(Identifier.LIGHTS);
    }

    GetLightsState(byte[] bytes) {
        super(bytes);
    }
}