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
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

public class SeatbeltState extends PropertyValueObject {
    public static final int SIZE = 2;

    SeatLocation location;
    FastenedState fastenedState;

    /**
     * @return The location.
     */
    public SeatLocation getLocation() {
        return location;
    }

    /**
     * @return The fastened state.
     */
    public FastenedState getFastenedState() {
        return fastenedState;
    }

    public SeatbeltState(SeatLocation location, FastenedState fastenedState) {
        super(0);

        this.location = location;
        this.fastenedState = fastenedState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, location.getByte());
        bytePosition += 1;

        set(bytePosition, fastenedState.getByte());
    }

    public SeatbeltState(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        location = SeatLocation.fromByte(get(bytePosition));
        bytePosition += 1;

        fastenedState = FastenedState.fromByte(get(bytePosition));
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum FastenedState implements ByteEnum {
        NOT_FASTENED((byte) 0x00),
        FASTENED((byte) 0x01);
    
        public static FastenedState fromByte(byte byteValue) throws CommandParseException {
            FastenedState[] values = FastenedState.values();
    
            for (int i = 0; i < values.length; i++) {
                FastenedState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(FastenedState.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        FastenedState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}