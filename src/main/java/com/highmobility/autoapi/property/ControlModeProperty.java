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

/**
 * The possible control mode values.
 */
public class ControlModeProperty extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public ControlModeProperty(byte identifier) {
        super(identifier);
    }

    public ControlModeProperty(@Nullable Value value, @Nullable Calendar timestamp,
                               @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public ControlModeProperty(Value value) {
        super(value);
        this.value = value;
        if (value != null) bytes[3] = value.getByte();
    }

    public ControlModeProperty(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() == 1) value = Value.fromByte(p.getValueByte());
        return this;
    }

    public enum Value implements PropertyValue {
        UNAVAILABLE((byte) 0x00),
        AVAILABLE((byte) 0x01),
        STARTED((byte) 0x02),
        FAILED_TO_START((byte) 0x03),
        ABORTED((byte) 0x04),
        ENDED((byte) 0x05),
        UNSUPPORTED((byte) 0xFF);

        public static Value fromByte(byte value) throws CommandParseException {
            Value[] allValues = Value.values();

            for (int i = 0; i < allValues.length; i++) {
                Value value1 = allValues[i];
                if (value1.getByte() == value) {
                    return value1;
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

        @Override public int getLength() {
            return 1;
        }
    }
}