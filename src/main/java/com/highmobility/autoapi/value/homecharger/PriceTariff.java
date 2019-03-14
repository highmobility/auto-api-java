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

package com.highmobility.autoapi.value.homecharger;

import com.highmobility.autoapi.CommandParseException;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyComponentValue;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

import java.io.UnsupportedEncodingException;

public class PriceTariff extends PropertyValueObject {
    PricingType pricingType;
    String currency;
    float price;

    /**
     * @return The pricing type.
     */
    public PricingType getPricingType() {
        return pricingType;
    }

    /**
     * @return The currency name.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return The price.
     */
    public float getPrice() {
        return price;
    }

    public PriceTariff(PricingType pricingType, String currency, float price) {
        super(1 + 1 + currency.length() + 4);
        update(pricingType, currency, price);
    }

    public PriceTariff() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 6) throw new CommandParseException();

        pricingType = PricingType.fromByte(get(0));
        price = Property.getFloat(bytes, 1);
        int currencyLength = Property.getUnsignedInt(bytes, 5, 1);
        currency = Property.getString(bytes, 6, currencyLength);
    }

    public void update(PricingType pricingType, String currency, float price) {
        this.pricingType = pricingType;
        this.currency = currency;
        this.price = price;

        bytes = new byte[1 + 1 + currency.length() + 4];

        set(0, pricingType.getByte());
        set(1, Property.floatToBytes(price));
        set(5, (byte) currency.length());
        try {
            set(6, currency.getBytes(PropertyComponentValue.CHARSET));
        } catch (UnsupportedEncodingException e) {
            // ignore
            e.printStackTrace();
        }
    }

    public void update(PriceTariff value) {
        update(value.pricingType, value.currency, value.price);
    }

    public enum PricingType {
        STARTING_FEE((byte) 0x00),
        PER_MINUTE((byte) 0x01),
        PER_KWH((byte) 0x02);

        public static PricingType fromByte(byte byteValue) {
            PricingType[] values = PricingType.values();

            for (int i = 0; i < values.length; i++) {
                PricingType state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            return null;
        }

        private byte value;

        PricingType(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}
