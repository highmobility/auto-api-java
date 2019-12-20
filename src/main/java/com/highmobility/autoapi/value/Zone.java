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

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Zone extends PropertyValueObject {
    public static final int SIZE = 2;

    Integer horizontal;
    Integer vertical;

    /**
     * @return Horizontal component of the matrix.
     */
    public Integer getHorizontal() {
        return horizontal;
    }

    /**
     * @return Vertical component of the matrix.
     */
    public Integer getVertical() {
        return vertical;
    }

    public Zone(Integer horizontal, Integer vertical) {
        super(2);
        update(horizontal, vertical);
    }

    public Zone(Property property) throws CommandParseException {
        super();
        if (property.getValueComponent() == null) throw new CommandParseException();
        update(property.getValueComponent().getValueBytes());
    }

    public Zone() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        horizontal = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        vertical = Property.getUnsignedInt(bytes, bytePosition, 1);
    }

    public void update(Integer horizontal, Integer vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(horizontal, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(vertical, 1));
    }

    public void update(Zone value) {
        update(value.horizontal, value.vertical);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}