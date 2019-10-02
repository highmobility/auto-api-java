// TODO: license

package com.highmobility.autoapi;

/**
 * Get all trunk properties.
 */
public class GetTrunkState extends GetCommand {
    public GetTrunkState() {
        super(Identifier.TRUNK);
    }

    GetTrunkState(byte[] bytes) {
        super(bytes);
    }
}