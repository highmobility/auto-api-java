// TODO: license

package com.highmobility.autoapi;

/**
 * Get all doors properties.
 */
public class GetDoorsState extends GetCommand {
    public GetDoorsState() {
        super(Identifier.DOORS);
    }

    GetDoorsState(byte[] bytes) {
        super(bytes);
    }
}