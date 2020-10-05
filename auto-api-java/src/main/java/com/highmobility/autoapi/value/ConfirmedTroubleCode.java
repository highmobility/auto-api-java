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

public class ConfirmedTroubleCode extends PropertyValueObject {
    String ID;
    String ecuAddress;
    String ecuVariantName;
    String status;

    /**
     * @return Identifier.
     */
    public String getID() {
        return ID;
    }

    /**
     * @return Electronic Control Unit address.
     */
    public String getEcuAddress() {
        return ecuAddress;
    }

    /**
     * @return Electronic Control Unit variant name.
     */
    public String getEcuVariantName() {
        return ecuVariantName;
    }

    /**
     * @return Status.
     */
    public String getStatus() {
        return status;
    }

    public ConfirmedTroubleCode(String ID, String ecuAddress, String ecuVariantName, String status) {
        super(0);

        this.ID = ID;
        this.ecuAddress = ecuAddress;
        this.ecuVariantName = ecuVariantName;
        this.status = status;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(Property.getUtf8Length(ID), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(ID));
        bytePosition += Property.getUtf8Length(ID);

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(ecuAddress), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(ecuAddress));
        bytePosition += Property.getUtf8Length(ecuAddress);

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(ecuVariantName), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(ecuVariantName));
        bytePosition += Property.getUtf8Length(ecuVariantName);

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(status), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(status));
    }

    public ConfirmedTroubleCode(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 8) throw new CommandParseException();

        int bytePosition = 0;
        int IDSize = getItemSize(bytePosition);
        bytePosition += 2;
        ID = Property.getString(bytes, bytePosition, IDSize);
        bytePosition += IDSize;

        int ecuAddressSize = getItemSize(bytePosition);
        bytePosition += 2;
        ecuAddress = Property.getString(bytes, bytePosition, ecuAddressSize);
        bytePosition += ecuAddressSize;

        int ecuVariantNameSize = getItemSize(bytePosition);
        bytePosition += 2;
        ecuVariantName = Property.getString(bytes, bytePosition, ecuVariantNameSize);
        bytePosition += ecuVariantNameSize;

        int statusSize = getItemSize(bytePosition);
        bytePosition += 2;
        status = Property.getString(bytes, bytePosition, statusSize);
    }

    @Override public int getLength() {
        return Property.getUtf8Length(ID) + 2 + Property.getUtf8Length(ecuAddress) + 2 + Property.getUtf8Length(ecuVariantName) + 2 + Property.getUtf8Length(status) + 2;
    }
}