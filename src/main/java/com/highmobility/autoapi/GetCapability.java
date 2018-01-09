package com.highmobility.autoapi;

import com.highmobility.utils.Bytes;

/**
 * Get the capability of a certain feature. The car will respond with the Capability message - to
 * what extent the capability is supported, if at all.
 */
public class GetCapability extends Command {
    public static final Type TYPE = new Type(Identifier.CAPABILITIES, 0x03);

    public GetCapability(Type type) {
        super(getBytes(type), true);
    }

    static byte[] getBytes(Type type) {
        byte[] bytes = new byte[5];
        Bytes.setBytes(bytes, TYPE.getIdentifierAndType(), 0);
        Bytes.setBytes(bytes, type.getIdentifier(), 3);
        return bytes;
    }

    GetCapability(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
