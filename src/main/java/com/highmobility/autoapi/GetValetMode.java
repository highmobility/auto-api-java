// TODO: license

package com.highmobility.autoapi;

/**
 * Get all valet mode properties.
 */
public class GetValetMode extends GetCommand {
    public GetValetMode() {
        super(Identifier.VALET_MODE);
    }

    GetValetMode(byte[] bytes) {
        super(bytes);
    }
}