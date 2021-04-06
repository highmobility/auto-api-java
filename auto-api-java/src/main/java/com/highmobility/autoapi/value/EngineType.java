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


public enum EngineType implements ByteEnum {
    UNKNOWN((byte) 0x00),
    ALL_ELECTRIC((byte) 0x01),
    COMBUSTION_ENGINE((byte) 0x02),
    PHEV((byte) 0x03),
    HYDROGEN((byte) 0x04),
    HYDROGEN_HYBRID((byte) 0x05),
    PETROL((byte) 0x06),
    ELECTRIC((byte) 0x07),
    GAS((byte) 0x08),
    DIESEL((byte) 0x09),
    GASOLINE((byte) 0x0a),
    CNG((byte) 0x0b),
    LPG((byte) 0x0c);

    public static EngineType fromByte(byte byteValue) throws CommandParseException {
        EngineType[] values = EngineType.values();

        for (int i = 0; i < values.length; i++) {
            EngineType state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException(
            enumValueDoesNotExist(EngineType.class.getSimpleName(), byteValue)
        );
    }

    private final byte value;

    EngineType(byte value) {
        this.value = value;
    }

    @Override public byte getByte() {
        return value;
    }
}