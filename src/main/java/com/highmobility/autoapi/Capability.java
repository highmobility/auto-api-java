package com.highmobility.autoapi;

import java.util.Arrays;

/**
 * This message is sent when a Get Capability message is received by the car.
 */
public class Capability extends Command {
    public static final Type TYPE = new Type(Identifier.CAPABILITIES, 0x04);
    FeatureCapability capability;

    public FeatureCapability getCapability() {
        return capability;
    }

    public Capability(byte[] bytes) throws CommandParseException {
         super(bytes);
         if (bytes.length < 8) return;
         capability = FeatureCapability.fromBytes(Arrays.copyOfRange(bytes, 6, bytes.length));

    }
}
