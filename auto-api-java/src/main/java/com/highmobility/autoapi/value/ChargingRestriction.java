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

public class ChargingRestriction extends PropertyValueObject {
    public static final int SIZE = 2;

    ActiveState active;
    Limit limit;

    /**
     * @return Indicates whether the charging current used to charge the vehicle is limited..
     */
    public ActiveState getActive() {
        return active;
    }

    /**
     * @return The limit.
     */
    public Limit getLimit() {
        return limit;
    }

    public ChargingRestriction(ActiveState active, Limit limit) {
        super(0);

        this.active = active;
        this.limit = limit;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, active.getByte());
        bytePosition += 1;

        set(bytePosition, limit.getByte());
    }

    public ChargingRestriction(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        active = ActiveState.fromByte(get(bytePosition));
        bytePosition += 1;

        limit = Limit.fromByte(get(bytePosition));
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum Limit implements ByteEnum {
        MAX((byte) 0x00),
        REDUCED((byte) 0x01),
        MIN((byte) 0x02);
    
        public static Limit fromByte(byte byteValue) throws CommandParseException {
            Limit[] values = Limit.values();
    
            for (int i = 0; i < values.length; i++) {
                Limit state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Limit.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Limit(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}