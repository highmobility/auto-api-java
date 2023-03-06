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
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.value.Bytes;

public class AcceleratorDuration extends PropertyValueObject {
    public static final int SIZE = 18;

    Double pedalPositionThreshold;
    Duration duration;

    /**
     * @return The accelerator pedal position threshold percentage.
     */
    public Double getPedalPositionThreshold() {
        return pedalPositionThreshold;
    }

    /**
     * @return The duration of the accelerator pedal position.
     */
    public Duration getDuration() {
        return duration;
    }

    public AcceleratorDuration(Double pedalPositionThreshold, Duration duration) {
        super(0);

        this.pedalPositionThreshold = pedalPositionThreshold;
        this.duration = duration;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.doubleToBytes(pedalPositionThreshold));
        bytePosition += 8;

        set(bytePosition, duration);
    }

    public AcceleratorDuration(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 18) throw new CommandParseException();

        int bytePosition = 0;
        pedalPositionThreshold = Property.getDouble(bytes, bytePosition);
        bytePosition += 8;

        int durationSize = Duration.SIZE;
        duration = new Duration(getRange(bytePosition, bytePosition + durationSize));
    }

    @Override public int getLength() {
        return 8 + 10;
    }
}