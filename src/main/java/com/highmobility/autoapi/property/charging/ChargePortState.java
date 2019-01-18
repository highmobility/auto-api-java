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

package com.highmobility.autoapi.property.charging;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.PropertyTimestamp;

import java.util.Calendar;

import javax.annotation.Nullable;

public class ChargePortState extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public ChargePortState(byte identifier) {
        super(identifier);
    }

    public ChargePortState(Value value) {
        this((byte) 0x00, value);
    }

    public ChargePortState(@Nullable Value value, @Nullable Calendar timestamp,
                           @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public ChargePortState(byte identifier, Value value) {
        super(identifier, value == null ? 0 : 1);
        this.value = value;
        if (value != null) bytes[3] = value.getByte();
    }

    public ChargePortState(Property p) throws CommandParseException {
        super(p);
        update(p, null, null, false);
    }

    @Override
    public boolean update(Property p, PropertyFailure failure, PropertyTimestamp timestamp,
                          boolean propertyInArray) throws CommandParseException {
        if (p != null) {
            value = value.fromByte(p.get(3));
        }
        return super.update(p, failure, timestamp, propertyInArray);
    }

    /**
     * The possible charge port states
     */
    public enum Value {
        CLOSED((byte) 0x00), OPEN((byte) 0x01);

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