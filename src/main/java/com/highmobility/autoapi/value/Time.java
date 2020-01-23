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

public class Time extends PropertyValueObject {
    public static final int SIZE = 2;

    Integer hour;
    Integer minute;

    /**
     * @return Value between 0 and 23.
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * @return Value between 0 and 59.
     */
    public Integer getMinute() {
        return minute;
    }

    public Time(Integer hour, Integer minute) {
        super(2);
        update(hour, minute);
    }

    public Time(Property property) throws CommandParseException {
        super();
        if (property.getValueComponent() == null) throw new CommandParseException();
        update(property.getValueComponent().getValueBytes());
    }

    public Time() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        hour = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        minute = Property.getUnsignedInt(bytes, bytePosition, 1);
    }

    public void update(Integer hour, Integer minute) {
        this.hour = hour;
        this.minute = minute;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(hour, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(minute, 1));
    }

    public void update(Time value) {
        update(value.hour, value.minute);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}