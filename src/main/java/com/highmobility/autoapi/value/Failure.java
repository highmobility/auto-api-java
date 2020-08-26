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
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

import static com.highmobility.utils.ByteUtils.hexFromByte;

public class Failure extends PropertyValueObject {
    Reason reason;
    String description;

    /**
     * @return The reason.
     */
    public Reason getReason() {
        return reason;
    }

    /**
     * @return Failure description.
     */
    public String getDescription() {
        return description;
    }

    public Failure(Reason reason, String description) {
        super(0);

        this.reason = reason;
        this.description = description;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, reason.getByte());
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(description), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(description));
    }

    public Failure(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        reason = Reason.fromByte(get(bytePosition));
        bytePosition += 1;

        int descriptionSize = getItemSize(bytePosition);
        bytePosition += 2;
        description = Property.getString(bytes, bytePosition, descriptionSize);
    }

    @Override public int getLength() {
        return 1 + Property.getUtf8Length(description) + 2;
    }

    public enum Reason implements ByteEnum {
        RATE_LIMIT((byte) 0x00),
        EXECUTION_TIMEOUT((byte) 0x01),
        FORMAT_ERROR((byte) 0x02),
        UNAUTHORISED((byte) 0x03),
        UNKNOWN((byte) 0x04),
        PENDING((byte) 0x05),
        INTERNAL_OEM_ERROR((byte) 0x06);
    
        public static Reason fromByte(byte byteValue) throws CommandParseException {
            Reason[] values = Reason.values();
    
            for (int i = 0; i < values.length; i++) {
                Reason state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum Reason does not contain " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        Reason(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}