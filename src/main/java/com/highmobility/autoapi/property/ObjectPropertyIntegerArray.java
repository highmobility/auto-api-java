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

import javax.annotation.Nullable;

public class ObjectPropertyIntegerArray extends ObjectProperty<int[]> {
    // int is expected to be 1 byte, unsigned
    int[] value;

    @Nullable public int[] getValue() {
        return value;
    }

    public ObjectPropertyIntegerArray(int[] value) {
        this((byte) 0x00, value);
    }

    public ObjectPropertyIntegerArray(Property p) throws CommandParseException {
        super(int[].class, p.getPropertyIdentifier());
        update(p);
    }

    public ObjectPropertyIntegerArray(byte identifier, int[] value) {
        this(identifier);
        update(value);
    }

    public ObjectPropertyIntegerArray(byte identifier) {
        super(int[].class, identifier);
    }

    @Override public ObjectProperty update(int[] value) {
        this.value = value;

        if (bytes.length != 6 + value.length) bytes = baseBytes(getPropertyIdentifier(),
                value.length);

        for (int i = 0; i < value.length; i++) {
            byte byteValue = Property.intToBytes(value[i], 1)[0];
            bytes[6 + i] = byteValue;
        }

        return this;
    }

    @Override public ObjectProperty update(Property p) throws CommandParseException {
        super.update(p);
        int length = p.getValueLength();
        if (length > 0) {
            value = new int[length];
            for (int i = 0; i < length; i++) {
                value[i] = Property.getUnsignedInt(p.getByteArray()[i + 6]);
            }
        }

        return this;
    }
}