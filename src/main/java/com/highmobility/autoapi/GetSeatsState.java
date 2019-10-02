// TODO: license

package com.highmobility.autoapi;

/**
 * Get all seats properties.
 */
public class GetSeatsState extends GetCommand {
    public GetSeatsState() {
        super(Identifier.SEATS);
    }

    GetSeatsState(byte[] bytes) {
        super(bytes);
    }
}