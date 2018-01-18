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
 * Created by ttiganik on 12/12/2016.
 */

public class ClimateCapability extends FeatureCapability {
    public enum ProfileCapability {
        UNAVAILABLE((byte)0x00),
        AVAILABLE((byte)0x01),
        GET_STATE_AVAILABLE((byte)0x02),
        NO_SCHEDULING((byte)0x03);

        public static ProfileCapability fromByte(byte value) throws CommandParseException {
            ProfileCapability[] capabilities = ProfileCapability.values();

            for (int i = 0; i < capabilities.length; i++) {
                ProfileCapability capability = capabilities[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        ProfileCapability(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    AvailableGetStateCapability.Capability climateCapability;
    ProfileCapability profileCapability;

    public AvailableGetStateCapability.Capability getClimateCapability() {
        return climateCapability;
    }

    public ProfileCapability getProfileCapability() {
        return profileCapability;
    }

    public ClimateCapability(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.CLIMATE);

        if (bytes.length != 5) throw new CommandParseException();

        climateCapability = AvailableGetStateCapability.Capability.fromByte(bytes[3]);
        profileCapability = ProfileCapability.fromByte(bytes[4]);
    }

    public ClimateCapability(AvailableGetStateCapability.Capability climateCapability,
            ClimateCapability.ProfileCapability profileCapability) {
        super(Command.Identifier.CLIMATE);
        this.climateCapability = climateCapability;
        this.profileCapability = profileCapability;
    }

    @Override public byte[] getBytes() {
        byte[] bytes = getBytesWithCapabilityCount(2);
        bytes[3] = climateCapability.getByte();
        bytes[4] = profileCapability.getByte();
        return bytes;
    }
}
