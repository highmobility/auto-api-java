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

public class ChargingPoint extends PropertyValueObject {
    String city;
    String postalCode;
    String street;
    String provider;

    /**
     * @return City the charging point is in..
     */
    public String getCity() {
        return city;
    }

    /**
     * @return Postal code the charging point is at..
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @return Street address the chargin point is at..
     */
    public String getStreet() {
        return street;
    }

    /**
     * @return The provider name of the charging point..
     */
    public String getProvider() {
        return provider;
    }

    public ChargingPoint(String city, String postalCode, String street, String provider) {
        super(0);

        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.provider = provider;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(Property.getUtf8Length(city), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(city));
        bytePosition += Property.getUtf8Length(city);

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(postalCode), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(postalCode));
        bytePosition += Property.getUtf8Length(postalCode);

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(street), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(street));
        bytePosition += Property.getUtf8Length(street);

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(provider), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(provider));
    }

    public ChargingPoint(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 8) throw new CommandParseException();

        int bytePosition = 0;
        int citySize = getItemSize(bytePosition);
        bytePosition += 2;
        city = Property.getString(bytes, bytePosition, citySize);
        bytePosition += citySize;

        int postalCodeSize = getItemSize(bytePosition);
        bytePosition += 2;
        postalCode = Property.getString(bytes, bytePosition, postalCodeSize);
        bytePosition += postalCodeSize;

        int streetSize = getItemSize(bytePosition);
        bytePosition += 2;
        street = Property.getString(bytes, bytePosition, streetSize);
        bytePosition += streetSize;

        int providerSize = getItemSize(bytePosition);
        bytePosition += 2;
        provider = Property.getString(bytes, bytePosition, providerSize);
    }

    @Override public int getLength() {
        return Property.getUtf8Length(city) + 2 + Property.getUtf8Length(postalCode) + 2 + Property.getUtf8Length(street) + 2 + Property.getUtf8Length(provider) + 2;
    }
}