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

public class IntArrayProperty extends Property {
    // int is expected to be 1 byte, unsigned
    int[] value;

    @Nullable public int[] getValue() {
        return value;
    }

    public IntArrayProperty(@Nullable int[] value, @Nullable Calendar timestamp,
                            @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public IntArrayProperty(int[] value) {
        this((byte) 0x00, value);
    }

    public IntArrayProperty(byte identifier, int[] value) {
        super(identifier, value == null ? 0 : value.length);
        this.value = value;
        if (value != null) setBytes(value);
    }

    public IntArrayProperty(byte identifier) {
        super(identifier);
    }

    public IntArrayProperty(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        int length = p.getValueLength();
        if (length > 0) {
            value = new int[length];
            for (int i = 0; i < length; i++) {
                value[i] = Property.getUnsignedInt(p.getByteArray()[i + 3]);
            }
        }

        return this;
    }

    void setBytes(int[] value) {
        for (int i = 0; i < value.length; i++) {
            bytes[3 + i] = Property.intToBytes(value[i], 1)[0];
        }
    }
}