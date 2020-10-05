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
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.value.Bytes;

public class DistanceOverTime extends PropertyValueObject {
    public static final int SIZE = 20;

    Length distance;
    Duration time;

    /**
     * @return Distance driven.
     */
    public Length getDistance() {
        return distance;
    }

    /**
     * @return Duration of time for the given distance.
     */
    public Duration getTime() {
        return time;
    }

    public DistanceOverTime(Length distance, Duration time) {
        super(0);

        this.distance = distance;
        this.time = time;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, distance);
        bytePosition += distance.getLength();

        set(bytePosition, time);
    }

    public DistanceOverTime(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 20) throw new CommandParseException();

        int bytePosition = 0;
        int distanceSize = Length.SIZE;
        distance = new Length(getRange(bytePosition, bytePosition + distanceSize));
        bytePosition += distanceSize;

        int timeSize = Duration.SIZE;
        time = new Duration(getRange(bytePosition, bytePosition + timeSize));
    }

    @Override public int getLength() {
        return 10 + 10;
    }
}