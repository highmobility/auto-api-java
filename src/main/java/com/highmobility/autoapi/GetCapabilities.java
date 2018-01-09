package com.highmobility.autoapi;

/**
 * Get the vehicle capabilities. The car will respond with the Capabilities message that manifests
 * all different APIs that are enabled on the specific car. It is good practice to only inspect the
 * vehicle capabilities the first time when access is gained. The capabilities are fixed for each
 * car type and will not change between every session unless the user meanwhile receives new
 * permissions (requires a whole new certificate).
 */
public class GetCapabilities extends Command {
    public static final Type TYPE = new Type(Identifier.CAPABILITIES, 0x00);

    public GetCapabilities() {
        super(TYPE);
    }

    GetCapabilities(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
