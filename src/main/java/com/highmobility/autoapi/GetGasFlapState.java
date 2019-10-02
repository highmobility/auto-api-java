// TODO: license

package com.highmobility.autoapi;

/**
 * Get all fueling properties.
 */
public class GetGasFlapState extends GetCommand {
    public GetGasFlapState() {
        super(Identifier.FUELING);
    }

    GetGasFlapState(byte[] bytes) {
        super(bytes);
    }
}