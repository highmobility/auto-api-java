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

package com.highmobility.autoapi.property.HomeCharger;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.Bytes;

public class PriceTariff extends Property {
    private static final byte identifier = 0x0C;
    private static final int valueSize = 8;

    PricingType pricingType;
    String currency;
    float price;

    /**
     * @return Pricing type
     */
    public PricingType getPricingType() {
        return pricingType;
    }

    /**
     * @return The currency alphabetic code per ISO 4217
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return The price
     */
    public float getPrice() {
        return price;
    }

    public PriceTariff(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length != 11) throw new CommandParseException();
        pricingType = PricingType.fromByte(bytes[3]);
        currency = Property.getString(bytes, 4, 3);
        price = Property.getFloat(bytes, 7);
    }

    public PriceTariff(PricingType pricingType, String currency, float price) {
        super(identifier, valueSize);
        bytes[3] = pricingType.getByte();
        Bytes.setBytes(bytes, Property.stringToBytes(currency), 4);
        Bytes.setBytes(bytes, Property.floatToBytes(price), 7);

        this.pricingType = pricingType;
        this.currency = currency;
        this.price = price;
    }

    @Override public byte getPropertyIdentifier() {
        return identifier;
    }

    @Override public int getPropertyLength() {
        return valueSize;
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
