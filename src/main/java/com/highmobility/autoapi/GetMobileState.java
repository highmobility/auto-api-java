// TODO: license

package com.highmobility.autoapi;

/**
 * Get all mobile properties.
 */
public class GetMobileState extends GetCommand {
    public GetMobileState() {
        super(Identifier.MOBILE);
    }

    GetMobileState(byte[] bytes) {
        super(bytes);
    }
}