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

public class ChargingLocation extends PropertyValueObject {
    String municipality;
    String formattedAddress;
    String streetAddress;

    /**
     * @return Municipality component of the address.
     */
    public String getMunicipality() {
        return municipality;
    }

    /**
     * @return Full formatted address.
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     * @return Street address component.
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    public ChargingLocation(String municipality, String formattedAddress, String streetAddress) {
        super(0);

        this.municipality = municipality;
        this.formattedAddress = formattedAddress;
        this.streetAddress = streetAddress;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(Property.getUtf8Length(municipality), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(municipality));
        bytePosition += Property.getUtf8Length(municipality);

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(formattedAddress), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(formattedAddress));
        bytePosition += Property.getUtf8Length(formattedAddress);

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(streetAddress), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(streetAddress));
    }

    public ChargingLocation(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 6) throw new CommandParseException();

        int bytePosition = 0;
        int municipalitySize = getItemSize(bytePosition);
        bytePosition += 2;
        municipality = Property.getString(bytes, bytePosition, municipalitySize);
        bytePosition += municipalitySize;

        int formattedAddressSize = getItemSize(bytePosition);
        bytePosition += 2;
        formattedAddress = Property.getString(bytes, bytePosition, formattedAddressSize);
        bytePosition += formattedAddressSize;

        int streetAddressSize = getItemSize(bytePosition);
        bytePosition += 2;
        streetAddress = Property.getString(bytes, bytePosition, streetAddressSize);
    }

    @Override public int getLength() {
        return Property.getUtf8Length(municipality) + 2 + Property.getUtf8Length(formattedAddress) + 2 + Property.getUtf8Length(streetAddress) + 2;
    }
}