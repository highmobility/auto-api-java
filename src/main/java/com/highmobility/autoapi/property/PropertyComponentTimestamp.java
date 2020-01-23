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

import com.highmobility.value.Bytes;

import java.util.Calendar;

public class PropertyComponentTimestamp extends PropertyComponent {
    private static final byte IDENTIFIER = 0x02;
    private Calendar timestamp;

    /**
     * @return The timestamp calendar.
     */
    public Calendar getCalendar() {
        return timestamp;
    }

    public PropertyComponentTimestamp(Bytes bytes) {
        super(bytes);
        timestamp = Property.getCalendar(valueBytes);
    }

    public PropertyComponentTimestamp(Calendar timestamp) {
        super(IDENTIFIER, PropertyComponentValue.CALENDAR_SIZE);
        this.timestamp = timestamp;
        valueBytes = new Bytes(Property.calendarToBytes(timestamp));
        set(3, valueBytes);
    }
}
