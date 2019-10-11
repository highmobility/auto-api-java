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

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

public class SeatbeltState extends PropertyValueObject {
    public static final int SIZE = 2;

    SeatLocation seatLocation;
    FastenedState fastenedState;

    /**
     * @return The seat location.
     */
    public SeatLocation getSeatLocation() {
        return seatLocation;
    }

    /**
     * @return The fastened state.
     */
    public FastenedState getFastenedState() {
        return fastenedState;
    }

    public SeatbeltState(SeatLocation seatLocation, FastenedState fastenedState) {
        super(2);
        update(seatLocation, fastenedState);
    }

    public SeatbeltState(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public SeatbeltState() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        seatLocation = SeatLocation.fromByte(get(bytePosition));
        bytePosition += 1;

        fastenedState = FastenedState.fromByte(get(bytePosition));
    }

    public void update(SeatLocation seatLocation, FastenedState fastenedState) {
        this.seatLocation = seatLocation;
        this.fastenedState = fastenedState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, seatLocation.getByte());
        bytePosition += 1;

        set(bytePosition, fastenedState.getByte());
    }

    public void update(SeatbeltState value) {
        update(value.seatLocation, value.fastenedState);
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum FastenedState implements ByteEnum {
        NOT_FASTENED((byte) 0x00),
        FASTENED((byte) 0x01);
    
        public static FastenedState fromByte(byte byteValue) throws CommandParseException {
            FastenedState[] values = FastenedState.values();
    
            for (int i = 0; i < values.length; i++) {
                FastenedState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        FastenedState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}