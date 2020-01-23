/*
 * The MIT License
 *
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.value.Bytes;

import javax.annotation.Nullable;

// since int has different signed and length options, its better to have a subclass.
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

    public PropertyInteger(int identifier, boolean signed, int length, Property<Integer> value) {
        this((byte) identifier, signed, length, value);
    }

    // used in fields
    public PropertyInteger(byte identifier, boolean signed) {
        super(Integer.class, identifier);
        this.signed = signed;
    }

    public PropertyInteger(int identifier, boolean signed) {
        this((byte) identifier, signed);
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
     * Reset the value and create new bytes according to the new length. Used internally because we
     * don't want to bother the user about integer length or sign.
     *
     * @param newLength The new length.
     * @param signed    The sign.
     * @param value     The value.
     * @return Self.
     */
    public Property update(boolean signed, int newLength, @Nullable Integer value) {
        this.signed = signed;
        if (value != null) {
            this.value = new PropertyComponentValueInteger(value, signed, newLength);
            createBytesFromComponents(bytes[0]);
        }
        return this;
    }

    // int needs to be updated later, so builder users dont need to consider the int length or sign
    private static class PropertyComponentValueInteger extends PropertyComponentValue<Integer> {
        PropertyComponentValueInteger(Integer value, boolean signed, int newLength) {
            super(PropertyComponentValue.IDENTIFIER, newLength);
            this.valueBytes = new Bytes(intToBytes(value, newLength));
            set(3, valueBytes);
            this.value = value;
        }
    }
}
