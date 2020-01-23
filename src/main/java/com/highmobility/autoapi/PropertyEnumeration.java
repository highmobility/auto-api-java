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
package com.highmobility.autoapi;

import com.highmobility.autoapi.exception.ParseException;
import com.highmobility.autoapi.property.Property;
import java.util.Enumeration;

class PropertyEnumeration implements Enumeration {
    private int cursor;
    private final byte[] bytes;

    public PropertyEnumeration(byte[] bytes) {
        this(bytes, 3 + Command.HEADER_LENGTH);
    }

    public PropertyEnumeration(byte[] bytes, int cursor) {
        this.bytes = bytes;
        this.cursor = cursor;
    }

    @Override public boolean hasMoreElements() {
        return cursor + 3 <= bytes.length;
    }

    @Override public EnumeratedProperty nextElement() {
        byte propertyIdentifier = bytes[cursor];
        int propertyStart = cursor + 3;
        int propertySize = 0;

        try {
            propertySize = Property.getUnsignedInt(bytes, cursor + 1, 2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cursor += 3 + propertySize;
        return new EnumeratedProperty(propertyIdentifier, propertySize, propertyStart);
    }

    static class EnumeratedProperty {
        byte identifier;
        int size;
        int valueStart;

        public EnumeratedProperty(byte identifier, int size, int valueStart) {
            this.identifier = identifier;
            this.size = size;
            this.valueStart = valueStart;
        }

        public boolean isValid(int totalCommandLength) {
            return valueStart + size <= totalCommandLength;
        }
    }
}
