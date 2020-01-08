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

    WindowLocation windowLocation;
    Double percentage;

    /**
     * @return The window location.
     */
    public WindowLocation getWindowLocation() {
        return windowLocation;
    }

    /**
     * @return Percentage value between 0.0 - 1.0 (0% - 100%).
     */
    public Double getPercentage() {
        return percentage;
    }

    public WindowOpenPercentage(WindowLocation windowLocation, Double percentage) {
        super(9);
        update(windowLocation, percentage);
    }

    public WindowOpenPercentage(Property property) throws CommandParseException {
        super();
        if (property.getValueComponent() == null) throw new CommandParseException();
        update(property.getValueComponent().getValueBytes());
    }

    public WindowOpenPercentage() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 9) throw new CommandParseException();

        int bytePosition = 0;
        windowLocation = WindowLocation.fromByte(get(bytePosition));
        bytePosition += 1;

        percentage = Property.getDouble(bytes, bytePosition);
    }

    public void update(WindowLocation windowLocation, Double percentage) {
        this.windowLocation = windowLocation;
        this.percentage = percentage;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, windowLocation.getByte());
        bytePosition += 1;

        set(bytePosition, Property.doubleToBytes(percentage));
    }

    public void update(WindowOpenPercentage value) {
        update(value.windowLocation, value.percentage);
    }

    @Override public int getLength() {
        return 1 + 8;
    }
}