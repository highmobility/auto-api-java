// TODO: license

package com.highmobility.autoapi;

/**
 * Get all hood properties.
 */
public class GetHoodState extends GetCommand {
    public GetHoodState() {
        super(Identifier.HOOD);
    }

    GetHoodState(byte[] bytes) {
        super(bytes);
    }
}