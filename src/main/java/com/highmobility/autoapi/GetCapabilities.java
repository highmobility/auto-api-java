// TODO: license

package com.highmobility.autoapi;

/**
 * Get all capabilities properties.
 */
public class GetCapabilities extends GetCommand {
    public GetCapabilities() {
        super(Identifier.CAPABILITIES);
    }

    GetCapabilities(byte[] bytes) {
        super(bytes);
    }
}