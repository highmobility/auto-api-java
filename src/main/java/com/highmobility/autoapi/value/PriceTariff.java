/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

public class PriceTariff extends PropertyValueObject {
    PricingType pricingType;
    Float price;
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
    public Float getPrice() {
        return price;
    }

    /**
     * @return The currency alphabetic code per ISO 4217 or crypto currency symbol.
     */
    public String getCurrency() {
        return currency;
    }

    public PriceTariff(PricingType pricingType, Float price, String currency) {
        super(0);
        update(pricingType, price, currency);
    }

    public PriceTariff(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public PriceTariff() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 7) throw new CommandParseException();

        int bytePosition = 0;
        pricingType = PricingType.fromByte(get(bytePosition));
        bytePosition += 1;

        price = Property.getFloat(bytes, bytePosition);
        bytePosition += 4;

        int currencySize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        currency = Property.getString(value, bytePosition, currencySize);
    }

    public void update(PricingType pricingType, Float price, String currency) {
        this.pricingType = pricingType;
        this.price = price;
        this.currency = currency;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, pricingType.getByte());
        bytePosition += 1;

        set(bytePosition, Property.floatToBytes(price));
        bytePosition += 4;

        set(bytePosition, Property.intToBytes(currency.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(currency));
    }

    public void update(PriceTariff value) {
        update(value.pricingType, value.price, value.currency);
    }

    @Override public int getLength() {
        return 1 + 4 + currency.length() + 2;
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        PricingType(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}