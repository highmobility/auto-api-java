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

public class SeatStateProperty extends Property {
    public enum Position {
        FRONT_LEFT((byte)0x00),
        FRONT_RIGHT((byte)0x01),
        REAR_LEFT((byte)0x02),
        REAR_RIGHT((byte)0x03),
        REAR_CENTER((byte)0x04);

        public static Position fromByte(byte byteValue) throws CommandParseException {
            Position[] values = Position.values();

            for (int i = 0; i < values.length; i++) {
                Position state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
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

    /**
     *
     * @return The position of the seat
     */
    public Position getPosition() {
        return position;
    }

    /**
     *
     * @return Person detected
     */
    public boolean isPersonDetected() {
        return personDetected;
    }

    /**
     *
     * @return Seatbelt fastened
     */
    public boolean isSeatbeltFastened() {
        return seatbeltFastened;
    }

    Position position;
    boolean personDetected;
    boolean seatbeltFastened;

    public SeatStateProperty(byte[] bytes) throws CommandParseException {
        super(bytes);

        position = Position.fromByte(bytes[3]);
        personDetected = Property.getBool(bytes[4]);
        seatbeltFastened = Property.getBool(bytes[4]);
    }

    public SeatStateProperty(byte identifier, Position position, boolean personDetected,
                             boolean seatbeltFastened) {
        super(identifier, 5);
        bytes[3] = position.getByte();
        bytes[4] = Property.boolToByte(personDetected);
        bytes[5] = Property.boolToByte(seatbeltFastened);

    }
}