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
import com.highmobility.autoapi.property.ByteEnum;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;


public enum DrivingMode implements ByteEnum {
    REGULAR((byte) 0x00),
    ECO((byte) 0x01),
    SPORT((byte) 0x02),
    SPORT_PLUS((byte) 0x03),
    ECO_PLUS((byte) 0x04),
    COMFORT((byte) 0x05);

    public static DrivingMode fromByte(byte byteValue) throws CommandParseException {
        DrivingMode[] values = DrivingMode.values();

        for (int i = 0; i < values.length; i++) {
            DrivingMode state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException(
            enumValueDoesNotExist(DrivingMode.class.getSimpleName(), byteValue)
        );
    }

    private final byte value;

    DrivingMode(byte value) {
        this.value = value;
    }

    @Override public byte getByte() {
        return value;
    }
}