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

public class PriceTariff extends PropertyValueObject {
    PricingType pricingType;
    Double price;
    String currency;

    /**
     * @return The pricing type.
     */
    public PricingType getPricingType() {
        return pricingType;
    }

    /**
     * @return The price.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @return The currency alphabetic code per ISO 4217 or crypto currency symbol.
     */
    public String getCurrency() {
        return currency;
    }

    public PriceTariff(PricingType pricingType, Double price, String currency) {
        super(0);

        this.pricingType = pricingType;
        this.price = price;
        this.currency = currency;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, pricingType.getByte());
        bytePosition += 1;

        set(bytePosition, Property.doubleToBytes(price));
        bytePosition += 8;

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(currency), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(currency));
    }

    public PriceTariff(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 11) throw new CommandParseException();

        int bytePosition = 0;
        pricingType = PricingType.fromByte(get(bytePosition));
        bytePosition += 1;

        price = Property.getDouble(bytes, bytePosition);
        bytePosition += 8;

        int currencySize = getItemSize(bytePosition);
        bytePosition += 2;
        currency = Property.getString(bytes, bytePosition, currencySize);
    }

    @Override public int getLength() {
        return 1 + 8 + Property.getUtf8Length(currency) + 2;
    }

    public enum PricingType implements ByteEnum {
        STARTING_FEE((byte) 0x00),
        PER_MINUTE((byte) 0x01),
        PER_KWH((byte) 0x02);
    
        public static PricingType fromByte(byte byteValue) throws CommandParseException {
            PricingType[] values = PricingType.values();
    
            for (int i = 0; i < values.length; i++) {
                PricingType state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("PriceTariff.PricingType does not contain: " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        PricingType(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}