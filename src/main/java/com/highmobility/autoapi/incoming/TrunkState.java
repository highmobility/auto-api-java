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

package com.highmobility.autoapi.incoming;

import com.highmobility.autoapi.CommandParseException;

/**
 * This is an evented message that is sent from the car every time the trunk state changes. This
 * message is also sent when a Get Trunk State is received by the car. The new status is included
 * in the message payload and may be the result of user, device or car triggered action.
 */

public class TrunkState extends IncomingCommand {
    /**
     * The possible trunk lock states
     */
    public enum LockState {
        UNLOCKED((byte)0x00),
        LOCKED((byte)0x01),
        UNSUPPORTED((byte)0xFF);

        public static LockState fromByte(byte value) throws CommandParseException {
            LockState[] capabilities = LockState.values();

            for (int i = 0; i < capabilities.length; i++) {
                LockState capability = capabilities[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        LockState(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    /**
     * The possible trunk positions
     */
    public enum Position {
        CLOSED((byte)0x00),
        OPEN((byte)0x01),
        UNSUPPORTED((byte)0xFF);

        public static Position fromByte(byte value) throws CommandParseException {
            Position[] capabilities = Position.values();

            for (int i = 0; i < capabilities.length; i++) {
                Position capability = capabilities[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        Position(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    /**
     * @return the current lock status of the trunk
     */
    public LockState getLockState() {
        return state;
    }

    /**
     * @return the current position of the trunk
     */
    public Position getPosition() {
        return position;
    }

    LockState state;
    Position position;

    public TrunkState(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length != 5) {
            throw new CommandParseException();
        }

        state = LockState.fromByte(bytes[3]);
        position = Position.fromByte(bytes[4]);
    }
}