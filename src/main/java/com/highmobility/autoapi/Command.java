package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.Arrays;

public class Command {
    byte[] bytes;
    Type type;

    byte[] nonce;
    byte[] signature;

    Property[] properties;

    public byte[] getNonce() {
        return nonce;
    }

    public byte[] getSignature() {
        return signature;
    }

    public byte[] getSignedBytes() {
        return Arrays.copyOfRange(bytes, 0, bytes.length - 64 - 3);
    }

    public Type getType() {
        return type;
    }

    public byte[] getBytes() {
        return bytes;
    }

    /**
     * @return All of the properties with raw values
     */
    public Property[] getProperties() {
        return properties;
    }

    /**
     * @param type The command value to compare this command with.
     * @return True if the command has the given commandType.
     */
    public boolean is(Type type) {
        return type.equals(this.type);
    }

    Command(byte[] bytes) throws CommandParseException {
        if (bytes == null || bytes.length == 0) return; // empty IncomingCommand
        if (bytes.length < 3) throw new CommandParseException();
        this.bytes = bytes;
        type = new Type(bytes[0], bytes[1], bytes[2]);

        ArrayList<Property> builder = new ArrayList<>();
        PropertyEnumeration enumeration = new PropertyEnumeration(bytes);
        while (enumeration.hasMoreElements()) {
            PropertyEnumeration.EnumeratedProperty propertyEnumeration = enumeration.nextElement();
            Property property = new Property(Arrays.copyOfRange(bytes, propertyEnumeration
                    .valueStart - 3, propertyEnumeration.valueStart + propertyEnumeration.size));
            builder.add(property);

            if (property.getPropertyIdentifier() == 0xFFFFFFA0) { // dont know why just 0xA0 comparison
                // does not work
                if (propertyEnumeration.size != 9) {
                    throw new CommandParseException();
                }

                nonce = Arrays.copyOfRange(bytes, propertyEnumeration.valueStart,
                        propertyEnumeration.valueStart + propertyEnumeration.size);
            } else if (property.getPropertyIdentifier() == 0xFFFFFFA1) {
                if (propertyEnumeration.size != 64) {
                    throw new CommandParseException();
                }

                signature = Arrays.copyOfRange(bytes, propertyEnumeration.valueStart,
                        propertyEnumeration.valueStart + propertyEnumeration.size);
            }
        }

        properties = builder.toArray(new Property[builder.size()]);
    }

    /**
     * This is used when we do not want to throw when we know the bytes are ok (we construct them
     * ourselves)
     */
    Command(byte[] bytes, boolean internalConstructor) {
        this.bytes = bytes;
    }

    Command(Type type) {
        this.bytes = type.getIdentifierAndType();
    }
}
