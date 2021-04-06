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
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.value.Bytes;

public class TripMeter extends PropertyValueObject {
    public static final int SIZE = 11;

    Integer id;
    Length distance;

    /**
     * @return The id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return The distance.
     */
    public Length getDistance() {
        return distance;
    }

    public TripMeter(Integer id, Length distance) {
        super(0);

        this.id = id;
        this.distance = distance;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(id, 1));
        bytePosition += 1;

        set(bytePosition, distance);
    }

    public TripMeter(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 11) throw new CommandParseException();

        int bytePosition = 0;
        id = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        int distanceSize = Length.SIZE;
        distance = new Length(getRange(bytePosition, bytePosition + distanceSize));
    }

    @Override public int getLength() {
        return 1 + 10;
    }
}