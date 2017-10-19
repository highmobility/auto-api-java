package com.highmobility.autoapi.capability;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Command;

/**
 * Created by ttiganik on 12/12/2016.
 */

public class AvailableGetStateCapability extends FeatureCapability {
    public enum Capability {
        UNAVAILABLE((byte)0x00),
        AVAILABLE((byte)0x01),
        GET_STATE_AVAILABLE((byte)0x02);

        public static Capability fromByte(byte value) throws CommandParseException {
            Capability[] capabilities = Capability.values();

            for (int i = 0; i < capabilities.length; i++) {
                Capability capability = capabilities[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        Capability(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    Capability capability;

    public Capability getCapability() {
        return capability;
    }

    public AvailableGetStateCapability(Command.Identifier identifier, byte[] bytes) throws CommandParseException {
        super(identifier);
        if (bytes.length != 4) throw new CommandParseException();
        capability = Capability.fromByte(bytes[3]);
    }

    public AvailableGetStateCapability(Command.Identifier identifier, Capability capability) throws CommandParseException {
        super(identifier);
        this.capability = capability;
    }

    @Override public byte[] getBytes() {
        byte[] bytes = getBytesWithCapabilityCount(1);
        bytes[3] = capability.getByte();
        return bytes;
    }
}
