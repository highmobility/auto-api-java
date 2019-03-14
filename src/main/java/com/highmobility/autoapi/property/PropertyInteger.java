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
import com.highmobility.value.Bytes;

import javax.annotation.Nullable;

// since int has different signed and length options, its better to have a property subclass.
public class PropertyInteger extends Property<Integer> {
    boolean signed;

    public PropertyInteger(Integer value) {
        // This will create int32. Can be updated later in {@link #update(byte, boolean, int)}
        super(value);
    }

    // used in builders. Create new bytes
    public PropertyInteger(byte identifier, boolean signed, int length, Property<Integer> value) {
        super(Integer.class, identifier);
        this.failure = value.failure;
        this.timestamp = value.timestamp;
        update(signed, length, value.value.value);
    }

    // used in fields
    public PropertyInteger(byte identifier, boolean signed) {
        super(Integer.class, identifier);
        this.signed = signed;
    }

    @Override public Property update(Property p) throws CommandParseException {
        // this copies the components and creates bytes
        super.update(p);

        if (p.getValueComponent().getValueBytes().getLength() >= 1) {
            if (signed) value.value = getSignedInt(p.getValueComponent().getValueBytes());
            else value.value = getUnsignedInt(p.getValueComponent().getValueBytes());
        }

        return this;
    }

    /**
     * Reset the value. This will create new bytes. It is used in Integer builders internally
     * because we don't want to bother the user about integer length or sign.
     *
     * @param newLength The new length.
     */
    public Property update(boolean signed, int newLength, @Nullable Integer value) {
        this.signed = signed;
        this.value = new PropertyComponentValueInteger(value, signed, newLength);
        createBytesFromComponents(bytes[0]);
        return this;
    }

    // int needs to be updated later, so builder users dont need to consider the int length or sign
    private class PropertyComponentValueInteger extends PropertyComponentValue<Integer> {
        PropertyComponentValueInteger(Integer value, boolean signed, int newLength) {
            super(PropertyComponentValue.IDENTIFIER, newLength);
            this.valueBytes = new Bytes(intToBytes(value, newLength));
            set(3, valueBytes);
            this.value = value;
        }
    }
}
