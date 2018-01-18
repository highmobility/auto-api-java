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

package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Command;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class RemoteControl extends FeatureState {
    public enum State {
        UNAVAILABLE((byte)0x00),
        AVAILABLE((byte)0x01),
        STARTED((byte)0x02);

        public static State fromByte(byte value) throws CommandParseException {
            State[] capabilities = State.values();

            for (int i = 0; i < capabilities.length; i++) {
                State capability = capabilities[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        State(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    State state;

    public State getState() {
        return state;
    }

    public RemoteControl(State state) {
        super(Command.Identifier.REMOTE_CONTROL);
        this.state = state;

        bytes = getBytesWithOneByteLongFields(1);
        bytes[3] = state.getByte();
    }

    RemoteControl(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.REMOTE_CONTROL);

        if (bytes.length != 4) throw new CommandParseException();
        state = State.fromByte(bytes[3]);
        this.bytes = bytes;
    }
}