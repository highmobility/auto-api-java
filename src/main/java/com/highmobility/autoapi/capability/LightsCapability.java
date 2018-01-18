/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

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
