// TODO: license

package com.highmobility.autoapi;

/**
 * Get all windscreen properties.
 */
public class GetWindscreenState extends GetCommand {
    public GetWindscreenState() {
        super(Identifier.WINDSCREEN);
    }

    GetWindscreenState(byte[] bytes) {
        super(bytes);
    }
}