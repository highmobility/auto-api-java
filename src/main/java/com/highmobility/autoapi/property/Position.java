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

public class Position extends Property {
    Value position;

    @Nullable public Value getValue() {
        return position;
    }

    public Position(Value position) {
        this((byte) 0x00, position);
    }

    public Position(@Nullable Value position, @Nullable Calendar timestamp,
                    @Nullable PropertyFailure failure) {
        this(position);
        setTimestampFailure(timestamp, failure);
    }

    public Position() {
        super(unknownBytes);
    }

    public Position(byte identifier, Value position) {
        super(identifier, position == null ? 0 : 1);
        this.position = position;
        if (position != null) bytes[3] = position.getByte();
    }

    public Position(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 1) position = Value.fromByte(p.get(3));
        return this;
    }

    public Position(byte identifier) {
        super(identifier);
    }

    public enum Value {
        CLOSED((byte) 0x00),
        OPEN((byte) 0x01),
        INTERMEDIATE((byte) 0x02);

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
    }

}