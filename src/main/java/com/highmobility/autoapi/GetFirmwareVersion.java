package com.highmobility.autoapi;

/**
 * Get the firmware version. The car will respond with the Firmware Version message. No permissions
 * are needed for this other than an authenticated state.
 */
public class GetFirmwareVersion extends Command {
    public static final Type TYPE = new Type(Identifier.FIRMWARE_VERSION, 0x00);

    public GetFirmwareVersion() {
        super(TYPE);
    }

    GetFirmwareVersion(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
