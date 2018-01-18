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
