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

import com.highmobility.autoapi.exception.ParseException;
import com.highmobility.value.Bytes;

public class PropertyComponent extends Bytes {
    byte identifier;
    Bytes valueBytes;

    /**
     * @return The component value bytes, without the header.
     */
    public Bytes getValueBytes() {
        return valueBytes;
    }

    /**
     * @return The identifier.
     */
    public byte getIdentifier() {
        return identifier;
    }

    PropertyComponent(Bytes componentBytes) {
        if (componentBytes.getLength() < 3) throw new ParseException();
        bytes = componentBytes.getByteArray();
        identifier = bytes[0];
        valueBytes = componentBytes.getRange(3, getLength());
    }

    PropertyComponent(byte identifier, Bytes valueBytes) {
        this(identifier, valueBytes.getLength());
        this.valueBytes = valueBytes;
        set(3, valueBytes);
    }

    // property header:010015 component header: 010012 value: 68747470733a2f2f676f6f676c652e636f6d
    // 01001B 010018 01001501001268747470733A2F2F676F6F676C652E636F6D
    PropertyComponent(byte identifier, int valueSize) {
        this.identifier = identifier;
        bytes = new byte[3 + valueSize];
        // component identifier
        bytes[0] = identifier;
        // component length
        set(1, Property.intToBytes(valueSize, 2));
        valueBytes = new Bytes(valueSize);
    }
}
