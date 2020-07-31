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

public class OemTroubleCodeValue extends PropertyValueObject {
    String ID;
    KeyValue keyValue;

    /**
     * @return Identifier for the trouble code.
     */
    public String getID() {
        return ID;
    }

    /**
     * @return Key-value pair for the trouble codde.
     */
    public KeyValue getKeyValue() {
        return keyValue;
    }

    public OemTroubleCodeValue(String ID, KeyValue keyValue) {
        super(0);

        this.ID = ID;
        this.keyValue = keyValue;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(ID.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(ID));
        bytePosition += ID.length();

        set(bytePosition, keyValue);
    }

    public OemTroubleCodeValue(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        int IDSize = getItemSize(bytePosition);
        bytePosition += 2;
        ID = Property.getString(bytes, bytePosition, IDSize);
        bytePosition += IDSize;

        int keyValueSize = getItemSize(bytePosition);
        bytePosition += 2;
        keyValue = new KeyValue(getRange(bytePosition, bytePosition + keyValueSize));
    }

    @Override public int getLength() {
        return ID.length() + 2 + 0;
    }
}