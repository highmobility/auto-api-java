// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all start stop properties.
 */
public class GetStartStopState extends GetCommand {
    public GetStartStopState() {
        super(Identifier.START_STOP, getStateIdentifiers());
    }

    GetStartStopState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01 };
    }
}