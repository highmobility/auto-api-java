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
