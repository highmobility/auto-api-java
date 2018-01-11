package com.highmobility.autoapi;

import com.highmobility.utils.Bytes;

import java.util.Arrays;

/**
 * Get the capability of a certain feature. The car will respond with the Capability message - to
 * what extent the capability is supported, if at all.
 */
public class GetCapability extends Command {
    public static final Type TYPE = new Type(Identifier.CAPABILITIES, 0x02);

    byte[] categoryBytes;
    Identifier category;

    /**
     * @return The identifier of the category the capabilities were requested for.
     */
    public byte[] getCapabilityIdentifierBytes() {
        return categoryBytes;
    }

    /**
     * @return The identifier of the category the capabilities were requested for.
     */
    public Identifier getCapabilityIdentifier() {
        return category;
    }

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
        categoryBytes = Arrays.copyOfRange(bytes, 3, 5);
        category = Identifier.fromBytes(categoryBytes);
    }
}
