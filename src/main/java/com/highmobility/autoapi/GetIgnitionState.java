// TODO: license

package com.highmobility.autoapi;

/**
 * Get all ignition properties.
 */
public class GetIgnitionState extends GetCommand {
    public GetIgnitionState() {
        super(Identifier.IGNITION);
    }

    GetIgnitionState(byte[] bytes) {
        super(bytes);
    }
}