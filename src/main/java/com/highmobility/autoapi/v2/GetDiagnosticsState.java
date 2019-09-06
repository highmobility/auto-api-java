// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all diagnostics properties.
 */
public class GetDiagnosticsState extends GetCommand {
    public GetDiagnosticsState() {
        super(Identifier.DIAGNOSTICS, getStateIdentifiers());
    }

    GetDiagnosticsState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x09, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e };
    }
}