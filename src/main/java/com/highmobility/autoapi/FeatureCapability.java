package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;

/**
 * Created by ttiganik on 14/10/2016.
 */
public class FeatureCapability {
    Type[] types;

    byte[] identifier;

    public Type[] getTypes() {
        return types;
    }

    public boolean isSupported(Type type) {
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(type)) return true;
        }

        return false;
    }

    public byte[] getIdentifier() {
        return identifier;
    }

    FeatureCapability(byte[] identifier, Type[] types) {
        this.types = types;
        this.identifier = identifier;
    }

    public static FeatureCapability fromBytes(byte[] bytes) throws CommandParseException {
        if (bytes.length == 1) throw new CommandParseException();
        byte[] identifier = new byte[] { bytes[0], bytes[1] };
        FeatureCapability featureCapability = null;

        if (bytes.length == 2) {
            featureCapability = new FeatureCapability(identifier, new Type[0]);
        }
        else {
            ArrayList<Type> types = new ArrayList<>();
            for (int i = 2; i < bytes.length; i++) {
                Type type = new Type(identifier, bytes[i]);
                types.add(type);
            }

            featureCapability = new FeatureCapability(identifier, types.toArray(new Type[types.size()]));
        }

        return featureCapability;
    }

    byte[] getBytes() {
        byte[] bytes = new byte[5 + types.length];
        bytes[0] = 0x01;
        byte[] lengthBytes = Property.intToBytes((2 + types.length), 2); //TODO: test that 0x00 bytes are in front.
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
