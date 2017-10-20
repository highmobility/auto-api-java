package com.highmobility.autoapi.capability;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;

/**
 * Created by root on 6/20/17.
 */
public class WindscreenCapability extends FeatureCapability {
    AvailableGetStateCapability.Capability windscreenDamageCapability;
    AvailableCapability.Capability wiperCapability;

    public WindscreenCapability(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.WINDSCREEN);
        if (bytes.length != 5) throw new CommandParseException();
        wiperCapability = AvailableCapability.Capability.fromByte(bytes[3]);
        windscreenDamageCapability = AvailableGetStateCapability.Capability.fromByte(bytes[4]);
    }

    public WindscreenCapability(AvailableCapability.Capability wiperCapability,
                                AvailableGetStateCapability.Capability windscreenDamageCapability) {
        super(Command.Identifier.WINDSCREEN);
        this.wiperCapability = wiperCapability;
        this.windscreenDamageCapability = windscreenDamageCapability;
    }

    public AvailableGetStateCapability.Capability getWindscreenDamageCapability() {
        return windscreenDamageCapability;
    }

    public AvailableCapability.Capability getWiperCapability() {
        return wiperCapability;
    }

    @Override public byte[] getBytes() {
        byte[] bytes = getBytesWithCapabilityCount(2);
        bytes[3] = wiperCapability.getByte();
        bytes[4] = windscreenDamageCapability.getByte();
        return bytes;
    }
}
