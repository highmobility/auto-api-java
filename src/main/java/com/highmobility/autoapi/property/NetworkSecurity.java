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

public class NetworkSecurity extends Property {

    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public NetworkSecurity(byte identifier) {
        super(identifier);
    }

    public NetworkSecurity(Value value) {
        super(value);
        this.value = value;
        if (value != null) bytes[3] = value.getByte();
    }

    public NetworkSecurity(@Nullable Value value, @Nullable Calendar timestamp,
                           @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public NetworkSecurity(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 1) value = value.fromByte(p.get(3));
        return this;
    }

    public enum Value implements PropertyValue {
        NONE((byte) 0x00),
        WEP((byte) 0x01),
        WPA_WPA2_PERSONAL((byte) 0x02),
        WPA2_PERSONAL((byte) 0x03);

        public static Value fromByte(byte value) throws CommandParseException {
            Value[] values = Value.values();

            for (int i = 0; i < values.length; i++) {
                Value type = values[i];
                if (type.getByte() == value) {
                    return type;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        public byte getByte() {
            return value;
        }

        Value(byte value) {
            this.value = value;
        }

        @Override public int getLength() {
            return 1;
        }
    }
}