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
import com.highmobility.value.Bytes;

public class KeyValue extends PropertyValueObject {
    String key;
    String value;

    /**
     * @return Key for the value.
     */
    public String getKey() {
        return key;
    }

    /**
     * @return Value for the key.
     */
    public String getValue() {
        return value;
    }

    public KeyValue(String key, String value) {
        super(0);

        this.key = key;
        this.value = value;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(key.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(key));
        bytePosition += key.length();

        set(bytePosition, Property.intToBytes(value.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(value));
    }

    public KeyValue(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 4) throw new CommandParseException();

        int bytePosition = 0;
        int keySize = getItemSize(bytePosition);
        bytePosition += 2;
        key = Property.getString(bytes, bytePosition, keySize);
        bytePosition += keySize;

        int valueSize = getItemSize(bytePosition);
        bytePosition += 2;
        value = Property.getString(bytes, bytePosition, valueSize);
    }

    @Override public int getLength() {
        return key.length() + 2 + value.length() + 2;
    }
}