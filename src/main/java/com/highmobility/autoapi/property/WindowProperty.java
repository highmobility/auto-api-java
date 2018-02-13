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

package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public class WindowProperty extends Property {
    public static final byte IDENTIFIER = 0x01;
    Position position;
    State state;

    public Position getPosition() {
        return position;
    }

    public State getState() {
        return state;
    }

    public WindowProperty(Position position, State state) {
        super(IDENTIFIER, 2);
        this.state = state;
        this.position = position;
        bytes[3] = position.getByte();
        bytes[4] = state.getByte();
    }

    public WindowProperty(byte positionByte, byte stateByte) throws CommandParseException {
        this(Position.fromByte(positionByte), State.fromByte(stateByte));
    }

    public WindowProperty(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 5) throw new CommandParseException();
        position = WindowProperty.Position.fromByte(bytes[3]);
        state = WindowProperty.State.fromByte(bytes[4]);
    }

    public enum Position {
        FRONT_LEFT((byte) 0x00),
        FRONT_RIGHT((byte) 0x01),
        REAR_RIGHT((byte) 0x02),
        REAR_LEFT((byte) 0x03),
        HATCH((byte) 0x04);

        public static Position fromByte(byte value) throws CommandParseException {
            Position[] allValues = Position.values();

            for (int i = 0; i < allValues.length; i++) {
                Position value1 = allValues[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Position(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }

    public enum State {
        CLOSED((byte) 0x00),
        OPEN((byte) 0x01);

        public static State fromByte(byte value) throws CommandParseException {
            State[] allValues = State.values();

            for (int i = 0; i < allValues.length; i++) {
                State value1 = allValues[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        State(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}
