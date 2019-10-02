// TODO: license

package com.highmobility.autoapi;

/**
 * Get all climate properties.
 */
public class GetClimateState extends GetCommand {
    public GetClimateState() {
        super(Identifier.CLIMATE);
    }

    GetClimateState(byte[] bytes) {
        super(bytes);
    }
}