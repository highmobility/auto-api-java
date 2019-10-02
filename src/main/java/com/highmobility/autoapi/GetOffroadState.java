// TODO: license

package com.highmobility.autoapi;

/**
 * Get all offroad properties.
 */
public class GetOffroadState extends GetCommand {
    public GetOffroadState() {
        super(Identifier.OFFROAD);
    }

    GetOffroadState(byte[] bytes) {
        super(bytes);
    }
}