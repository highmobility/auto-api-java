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

public class WindowOpenPercentage extends PropertyValueObject {
    public static final int SIZE = 9;

    WindowLocation location;
    Double openPercentage;

    /**
     * @return The location.
     */
    public WindowLocation getLocation() {
        return location;
    }

    /**
     * @return The open percentage.
     */
    public Double getOpenPercentage() {
        return openPercentage;
    }

    public WindowOpenPercentage(WindowLocation location, Double openPercentage) {
        super(0);

        this.location = location;
        this.openPercentage = openPercentage;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, location.getByte());
        bytePosition += 1;

        set(bytePosition, Property.doubleToBytes(openPercentage));
    }

    public WindowOpenPercentage(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 9) throw new CommandParseException();

        int bytePosition = 0;
        location = WindowLocation.fromByte(get(bytePosition));
        bytePosition += 1;

        openPercentage = Property.getDouble(bytes, bytePosition);
    }

    @Override public int getLength() {
        return 1 + 8;
    }
}