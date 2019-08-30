// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class PriceTariff extends PropertyValueObject {
    PricingType pricingType;
    float price;
    String currency;

    /**
     * @return The pricing type.
     */
    public PricingType getPricingType() {
        return pricingType;
    }

    /**
     * @return The price in 4-bytes per IEEE 754.
     */
    public float getPrice() {
        return price;
    }

    /**
     * @return The currency alphabetic code per ISO 4217 or crypto currency symbol.
     */
    public String getCurrency() {
        return currency;
    }

    public PriceTariff(PricingType pricingType, float price, String currency) {
        super(0);
        update(pricingType, price, currency);
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

    public void update(PricingType pricingType, float price, String currency) {
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

    public enum PricingType {
        STARTING_FEE((byte)0x00),
        PER_MINUTE((byte)0x01),
        PER_KWH((byte)0x02);
    
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
    
        public byte getByte() {
            return value;
        }
    }
}