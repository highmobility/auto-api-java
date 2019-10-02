// TODO: license

package com.highmobility.autoapi;

/**
 * Get all windows properties.
 */
public class GetWindows extends GetCommand {
    public GetWindows() {
        super(Identifier.WINDOWS);
    }

    GetWindows(byte[] bytes) {
        super(bytes);
    }
}