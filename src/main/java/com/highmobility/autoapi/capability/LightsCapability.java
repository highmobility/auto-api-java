package com.highmobility.autoapi.capability;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;

/**
 * Created by root on 6/20/17.
 */

public class LightsCapability extends FeatureCapability {
    AvailableGetStateCapability.Capability exteriorLightsCapability;
    AvailableGetStateCapability.Capability interiorLightsCapability;
    AvailableCapability.Capability ambientLightsCapability;

    public LightsCapability(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.LIGHTS);
        if (bytes.length != 6) throw new CommandParseException();
        exteriorLightsCapability = AvailableGetStateCapability.Capability.fromByte(bytes[3]);
        interiorLightsCapability = AvailableGetStateCapability.Capability.fromByte(bytes[4]);
        ambientLightsCapability = AvailableCapability.Capability.fromByte(bytes[5]);
    }

    public LightsCapability(AvailableGetStateCapability.Capability exteriorLightsCapability,
                            AvailableGetStateCapability.Capability interiorLightsCapability,
                            AvailableCapability.Capability ambientLightsCapability) {
        super(Command.Identifier.LIGHTS);

        this.exteriorLightsCapability = exteriorLightsCapability;
        this.interiorLightsCapability = interiorLightsCapability;
        this.ambientLightsCapability = ambientLightsCapability;
    }

    public AvailableGetStateCapability.Capability getExteriorLightsCapability() {
        return exteriorLightsCapability;
    }

    public AvailableGetStateCapability.Capability getInteriorLightsCapability() {
        return interiorLightsCapability;
    }

    public AvailableCapability.Capability getAmbientLightsCapability() {
        return ambientLightsCapability;
    }

    @Override public byte[] getBytes() {
        byte[] bytes = getBytesWithCapabilityCount(3);
        bytes[3] = exteriorLightsCapability.getByte();
        bytes[4] = interiorLightsCapability.getByte();
        bytes[5] = ambientLightsCapability.getByte();
        return bytes;
    }
}
