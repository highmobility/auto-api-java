package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Identifier;
import com.highmobility.autoapi.Type;
import com.highmobility.utils.Bytes;

import java.util.ArrayList;

/**
 * Created by ttiganik on 14/10/2016.
 */
public class CapabilityProperty extends Property {
    static final byte defaultIdentifier     = 0x01;

    Type[] types;

    byte[] identifier;

    /**
     *
     * @return All of the command types supported for this category
     */
    public Type[] getTypes() {
        return types;
    }

    /**
     *
     * @param type The Type of the command to check for
     * @return True if command is supported
     */
    public boolean isSupported(Type type) {
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(type)) return true;
        }

        return false;
    }

    /**
     *
     * @return The command's category identifier
     */
    public byte[] getIdentifier() {
        return identifier;
    }

    public CapabilityProperty(byte[] bytes) throws CommandParseException {
        super(bytes);

        int propertyLength = Property.getUnsignedInt(bytes, 1, 2);
        identifier = new byte[] { bytes[3], bytes[4] };
        if (propertyLength < 3) return;

        ArrayList<Type> builder = new ArrayList<>();

        for (int i = 5; i < 3 + propertyLength; i++) {
            Type type = new Type(identifier, bytes[i]);
            builder.add(type);
        }

        types = builder.toArray(new Type[builder.size()]);
    }

    /**
     *
     * @param categoryIdentifier the 2 byte identifier of the category
     * @param types All the types supported for the given category identifier
     * @throws IllegalArgumentException when types are not from the same category or parameters are invalid
     */
    public CapabilityProperty(byte[] categoryIdentifier, Type[] types) throws IllegalArgumentException {
        super(defaultIdentifier, getValue(categoryIdentifier, types));
    }

    /**
     *
     * @param categoryIdentifier the identifier of the category
     * @param types All the types supported for the given category identifier
     * @throws IllegalArgumentException when types are not from the same category or parameters are invalid
     */
    public CapabilityProperty(Identifier categoryIdentifier, Type[] types) throws IllegalArgumentException {
        this(categoryIdentifier.getBytes(), types);
    }

    static byte[] getValue(byte[] categoryIdentifier, Type[] types) throws IllegalArgumentException {
        byte[] bytes = new byte[2 + types.length];
        Bytes.setBytes(bytes, categoryIdentifier, 0);
        for (int i = 0; i < types.length; i++) {
            Type type = types[i];
            bytes[2 + i] = type.getType();
            if (type.getIdentifierAndType()[0] != categoryIdentifier[0]
                || type.getIdentifierAndType()[1] != categoryIdentifier[1]) throw new IllegalArgumentException();
        }

        return bytes;
    }

    byte[] getBytes() {
        byte[] bytes = new byte[5 + types.length];
        bytes[0] = 0x01;
        byte[] lengthBytes = Property.intToBytes((2 + types.length), 2);
        bytes[1] = lengthBytes[0];
        bytes[2] = lengthBytes[1];

        if (types != null) {
            for (int i = 0; i < types.length; i++) {
                bytes[3 + i] = types[i].getType();
            }
        }

        return bytes;
    }
}
