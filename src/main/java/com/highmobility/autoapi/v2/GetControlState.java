// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all remote control properties.
 */
public class GetControlState extends GetCommand {
    public GetControlState() {
        super(Identifier.REMOTE_CONTROL, getStateIdentifiers());
    }

    GetControlState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02 };
    }
}