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

public class AddressComponent extends PropertyValueObject {
    Type type;
    String value;

    /**
     * @return Component type.
     */
    public Type getType() {
        return type;
    }

    /**
     * @return Value for the component.
     */
    public String getValue() {
        return value;
    }

    public AddressComponent(Type type, String value) {
        super(0);

        this.type = type;
        this.value = value;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, type.getByte());
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(value), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(value));
    }

    public AddressComponent(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        type = Type.fromByte(get(bytePosition));
        bytePosition += 1;

        int valueSize = getItemSize(bytePosition);
        bytePosition += 2;
        value = Property.getString(bytes, bytePosition, valueSize);
    }

    @Override public int getLength() {
        return 1 + Property.getUtf8Length(value) + 2;
    }

    public enum Type implements ByteEnum {
        CITY((byte) 0x00),
        COUNTRY((byte) 0x01),
        COUNTRY_SHORT((byte) 0x02),
        DISTRICT((byte) 0x03),
        POSTAL_CODE((byte) 0x04),
        STREET((byte) 0x05),
        STATE_PROVINCE((byte) 0x06),
        OTHER((byte) 0x07);
    
        public static Type fromByte(byte byteValue) throws CommandParseException {
            Type[] values = Type.values();
    
            for (int i = 0; i < values.length; i++) {
                Type state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum Type does not contain " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        Type(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}