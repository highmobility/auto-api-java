// TODO: license

package com.highmobility.autoapi;

/**
 * Get all keyfob position properties.
 */
public class GetKeyfobPosition extends GetCommand {
    public GetKeyfobPosition() {
        super(Identifier.KEYFOB_POSITION);
    }

    GetKeyfobPosition(byte[] bytes) {
        super(bytes);
    }
}