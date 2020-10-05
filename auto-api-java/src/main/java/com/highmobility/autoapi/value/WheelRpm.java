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
import com.highmobility.autoapi.value.measurement.AngularVelocity;
import com.highmobility.value.Bytes;

public class WheelRpm extends PropertyValueObject {
    public static final int SIZE = 11;

    LocationWheel location;
    AngularVelocity RPM;

    /**
     * @return Wheel location.
     */
    public LocationWheel getLocation() {
        return location;
    }

    /**
     * @return The RPM measured at this wheel.
     */
    public AngularVelocity getRPM() {
        return RPM;
    }

    public WheelRpm(LocationWheel location, AngularVelocity RPM) {
        super(0);

        this.location = location;
        this.RPM = RPM;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, location.getByte());
        bytePosition += 1;

        set(bytePosition, RPM);
    }

    public WheelRpm(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 11) throw new CommandParseException();

        int bytePosition = 0;
        location = LocationWheel.fromByte(get(bytePosition));
        bytePosition += 1;

        int RPMSize = AngularVelocity.SIZE;
        RPM = new AngularVelocity(getRange(bytePosition, bytePosition + RPMSize));
    }

    @Override public int getLength() {
        return 1 + 10;
    }
}