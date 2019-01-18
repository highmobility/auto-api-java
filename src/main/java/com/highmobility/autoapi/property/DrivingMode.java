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

public class DrivingMode extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public DrivingMode(Value Value) {
        this((byte) 0x00, Value);
    }

    public DrivingMode(@Nullable Value Value, @Nullable Calendar timestamp,
                       @Nullable PropertyFailure failure) {
        this(Value);
        setTimestampFailure(timestamp, failure);
    }

    public DrivingMode(byte identifier, Value value) {
        super(identifier, value == null ? 0 : 1);
        this.value = value;
        if (value != null) bytes[3] = value.getByte();
    }

    public DrivingMode(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 1) value = value.fromByte(p.get(3));
        return this;
    }

    public DrivingMode(byte identifier) {
        super(identifier);
    }

    public enum Value {
        REGULAR((byte) 0x00),
        ECO((byte) 0x01),
        SPORT((byte) 0x02),
        SPORT_PLUS((byte) 0x03),
        ECO_PLUS((byte) 0x04);

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