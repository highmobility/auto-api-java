package com.highmobility.autoapi.capability;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.WindowState;

import java.util.concurrent.locks.Lock;

/**
 * Created by ttiganik on 12/12/2016.
 */

public class TrunkAccessCapability extends FeatureCapability {
    /**
     * The possible lock positions
     */
    public enum LockCapability {
        UNAVAILABLE((byte)0x00),
        AVAILABLE((byte)0x01),
        GET_STATE_AVAILABLE((byte)0x02),
        GET_STATE_UNLOCK_AVAILABLE((byte)0x03);

        public static LockCapability fromByte(byte value) throws CommandParseException {
            LockCapability[] capabilities = LockCapability.values();

            for (int i = 0; i < capabilities.length; i++) {
                LockCapability capability = capabilities[i];
                if (capability.getCapabilityByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        LockCapability(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getCapabilityByte() {
            return capabilityByte;
        }
    }

    /**
     * The possible trunk positions
     */
    public enum PositionCapability {
        UNAVAILABLE((byte)0x00),
        AVAILABLE((byte)0x01),
        GET_STATE_AVAILABLE((byte)0x02),
        GET_STATE_OPEN_AVAILABLE((byte)0x03);

        public static PositionCapability fromByte(byte value) throws CommandParseException {
            PositionCapability[] capabilities = PositionCapability.values();

            for (int i = 0; i < capabilities.length; i++) {
                PositionCapability capability = capabilities[i];
                if (capability.getCapabilityByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        PositionCapability(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getCapabilityByte() {
            return capabilityByte;
        }
    }

    LockCapability lockCapability;
    PositionCapability positionCapability;

    /**
     * @return the current lock status of the trunk
     */
    public LockCapability getLockCapability() {
        return lockCapability;
    }

    /**
     *
     * @return the current positionCapability of the trunk
     */
    public PositionCapability getPositionCapability() {
        return positionCapability;
    }

    public TrunkAccessCapability(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.TRUNK_ACCESS);
        if (bytes.length != 5) throw new CommandParseException();
        lockCapability = LockCapability.fromByte(bytes[3]);
        positionCapability = PositionCapability.fromByte(bytes[4]);
    }

    public TrunkAccessCapability(LockCapability lockCapability,
                                 PositionCapability positionCapability) throws CommandParseException {
        super(Command.Identifier.TRUNK_ACCESS);
        this.lockCapability = lockCapability;
        this.positionCapability = positionCapability;
    }

    @Override public byte[] getBytes() {
        byte[] bytes = getBytesWithCapabilityCount(2);
        bytes[3] = lockCapability.getCapabilityByte();
        bytes[4] = positionCapability.getCapabilityByte();
        return bytes;


    }
}
