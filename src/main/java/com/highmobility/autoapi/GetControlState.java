// TODO: license

package com.highmobility.autoapi;

/**
 * Get all remote control properties.
 */
public class GetControlState extends GetCommand {
    public GetControlState() {
        super(Identifier.REMOTE_CONTROL);
    }

    GetControlState(byte[] bytes) {
        super(bytes);
    }
}