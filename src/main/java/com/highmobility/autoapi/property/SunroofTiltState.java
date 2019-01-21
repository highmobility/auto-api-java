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

import java.util.Calendar;

import javax.annotation.Nullable;

public class SunroofTiltState extends Property {
    Value value;

    public Value getValue() {
        return value;
    }

    public SunroofTiltState(Value sunroofTiltState) {
        this((byte) 0x00, sunroofTiltState);
    }

    public SunroofTiltState(@Nullable Value sunroofTiltState,
                            @Nullable Calendar timestamp,
                            @Nullable PropertyFailure failure) {
        this(sunroofTiltState);
        setTimestampFailure(timestamp, failure);
    }

    public SunroofTiltState(byte identifier, Value sunroofTiltState) {
        super(identifier, sunroofTiltState == null ? 0 : 1);
        this.value = sunroofTiltState;
        if (sunroofTiltState != null) bytes[3] = sunroofTiltState.getByte();
    }

    public SunroofTiltState(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 1) value = Value.fromByte(p.get(3));
        return this;
    }

    public SunroofTiltState(byte identifier) {
        super(identifier);
    }

    // TBODO: ctors
    public enum Value {
        CLOSED((byte) 0x00),
        TILTED((byte) 0x01),
        HALF_TILTED((byte) 0x02);

        public static Value fromByte(byte byteValue) throws CommandParseException {
            Value[] values = Value.values();

            for (int i = 0; i < values.length; i++) {
                Value state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Value(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}