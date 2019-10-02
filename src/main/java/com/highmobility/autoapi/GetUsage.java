// TODO: license

package com.highmobility.autoapi;

/**
 * Get all usage properties.
 */
public class GetUsage extends GetCommand {
    public GetUsage() {
        super(Identifier.USAGE);
    }

    GetUsage(byte[] bytes) {
        super(bytes);
    }
}