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
 * Created by ttiganik on 13/12/2016.
 */

public class HonkFlashCapability extends FeatureCapability {
    AvailableCapability.Capability honkHornCapability;
    AvailableCapability.Capability flashLightsCapability;
    AvailableCapability.Capability emergencyFlasherCapability;

    public AvailableCapability.Capability getHonkHornCapability() {
        return honkHornCapability;
    }

    public AvailableCapability.Capability getFlashLightsCapability() {
        return flashLightsCapability;
    }

    public AvailableCapability.Capability getEmergencyFlasherCapability() {
        return emergencyFlasherCapability;
    }

    public HonkFlashCapability(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.HONK_FLASH);
        if (bytes.length != 6) throw new CommandParseException();
        honkHornCapability = AvailableCapability.Capability.fromByte(bytes[3]);
        flashLightsCapability = AvailableCapability.Capability.fromByte(bytes[4]);
        emergencyFlasherCapability = AvailableCapability.Capability.fromByte(bytes[5]);
    }

    public HonkFlashCapability(AvailableCapability.Capability honkHornCapability,
                               AvailableCapability.Capability flashLightsCapability,
                               AvailableCapability.Capability emergencyFlasherCapability) {
        super(Command.Identifier.HONK_FLASH);
        this.honkHornCapability = honkHornCapability;
        this.flashLightsCapability = flashLightsCapability;
        this.emergencyFlasherCapability = emergencyFlasherCapability;
    }

    @Override public byte[] getBytes() {
        byte[] bytes = getBytesWithCapabilityCount(3);
        bytes[3] = honkHornCapability.getByte();
        bytes[4] = flashLightsCapability.getByte();
        bytes[5] = emergencyFlasherCapability.getByte();

        return bytes;
    }
}
