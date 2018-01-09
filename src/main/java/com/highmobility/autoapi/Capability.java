package com.highmobility.autoapi;

import com.highmobility.autoapi.property.CapabilityProperty;

import java.util.Arrays;

/**
 * This message is sent when a Get Capability message is received by the car.
 */
public class Capability extends Command {
    public static final Type TYPE = new Type(Identifier.CAPABILITIES, 0x04);
    CapabilityProperty capability;

    public CapabilityProperty getCapability() {
        return capability;
    }

    public Capability(byte[] bytes) throws CommandParseException {
         super(bytes);
         if (bytes.length < 8) return;
         capability = new CapabilityProperty(Arrays.copyOfRange(bytes, 3, bytes.length));

    }
}
