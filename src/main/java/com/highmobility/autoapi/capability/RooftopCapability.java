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
 * Created by ttiganik on 13/12/2016.
 */

public class RooftopCapability extends FeatureCapability {
    public enum DimmingCapability {
        UNAVAILABLE((byte)0x00),
        AVAILABLE((byte)0x01),
        GET_STATE_AVAILABLE((byte)0x02),
        ONLY_OPAQUE_OR_TRANSPARENT((byte)0x03);

        public static DimmingCapability fromByte(byte value) throws CommandParseException {
            DimmingCapability[] capabilities = DimmingCapability.values();

            for (int i = 0; i < capabilities.length; i++) {
                DimmingCapability capability = capabilities[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        DimmingCapability(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    public enum OpenCloseCapability {
        UNAVAILABLE((byte)0x00),
        AVAILABLE((byte)0x01),
        GET_STATE_AVAILABLE((byte)0x02),
        ONLY_FULLY_OPEN_OR_CLOSED((byte)0x03);

        public static OpenCloseCapability fromByte(byte value) throws CommandParseException {
            OpenCloseCapability[] capabilities = OpenCloseCapability.values();

            for (int i = 0; i < capabilities.length; i++) {
                OpenCloseCapability capability = capabilities[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        OpenCloseCapability(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    DimmingCapability dimmingCapability;
    OpenCloseCapability openCloseCapability;

    public DimmingCapability getDimmingCapability() {
        return dimmingCapability;
    }

    public OpenCloseCapability getOpenCloseCapability() {
        return openCloseCapability;
    }

    public RooftopCapability(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.ROOFTOP);

        if (bytes.length != 5) throw new CommandParseException();
        dimmingCapability = DimmingCapability.fromByte(bytes[3]);
        openCloseCapability = OpenCloseCapability.fromByte(bytes[4]);
    }

    public RooftopCapability(DimmingCapability dimmingCapability, OpenCloseCapability openCloseCapability) {
        super(Command.Identifier.ROOFTOP);
        this.dimmingCapability = dimmingCapability;
        this.openCloseCapability = openCloseCapability;
    }

    @Override public byte[] getBytes() {
        byte[] bytes = getBytesWithCapabilityCount(2);
        bytes[3] = dimmingCapability.getByte();
        bytes[4] = openCloseCapability.getByte();
        return bytes;
    }
}
