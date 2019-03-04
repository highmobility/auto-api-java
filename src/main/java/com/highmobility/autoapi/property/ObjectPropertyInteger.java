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
import com.highmobility.utils.ByteUtils;

import javax.annotation.Nullable;

public class ObjectPropertyInteger extends ObjectProperty<Integer> {
    // since int has different signed and length options, its better to have a property subclass.
    boolean signed;

    public ObjectPropertyInteger(Integer value) {
        // This will create uint32. Can be updated later in {@link #update(byte, boolean, int)}
        super(value);
    }

    public ObjectPropertyInteger(Property p, boolean signed) throws CommandParseException {
        super(Integer.class, p.getPropertyIdentifier());
        this.signed = signed;
        update(p);
    }

    public ObjectPropertyInteger(byte identifier, boolean signed, int length, int value) {
        this(identifier, signed);
        this.value = value;
        this.bytes = getBytes(identifier, length, value);
        bytesUpdated();
    }

    public ObjectPropertyInteger(byte identifier, boolean signed) {
        super(Integer.class, identifier);
        this.signed = signed;
    }

    @Override public ObjectProperty update(Property p) throws CommandParseException {
        super.update(p);

        if (p.getValueLength() >= 1) {
            if (signed) value = getSignedInt(p.getValueBytesArray());
            else value = getUnsignedInt(p.getValueBytesArray());
        }

        return this;
    }

    /**
     * Reset the value length. This will create a new base byte array. It is used for Integer
     * builders because we don't want the user to bother about whether Integer is signed or how many
     * bytes is it's length.
     *
     * @param identifier The property identifier.
     * @param newLength  The new length.
     */
    public ObjectProperty update(byte identifier, boolean signed, int newLength) {
        byte[] bytes = baseBytes(identifier, newLength);

        /*
        Don't need to consider signed here because we are not resetting the value, it stays
        signed int from builder ctor. Bytes would be set the same for signed/unsigned.
         */

        if (value == null) return this;

        if (newLength == 1) {
            bytes[6] = value.byteValue();
        } else {
            ByteUtils.setBytes(bytes, intToBytes(value, newLength), 6);
        }

        return this;
    }

    public ObjectProperty update(byte identifier, boolean signed, int newLength,
                                 @Nullable Integer value) {
        this.value = value;
        return update(identifier, signed, newLength);
    }

    static byte[] getBytes(byte identifier, int length, Integer value) {
        byte[] bytes = baseBytes(identifier, length);

        if (value == null) return bytes;

        if (length == 1) {
            bytes[6] = value.byteValue();
        } else {
            ByteUtils.setBytes(bytes, intToBytes(value, length), 6);
        }

        return bytes;
    }
}
