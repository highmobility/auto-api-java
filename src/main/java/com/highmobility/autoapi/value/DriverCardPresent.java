// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

public class DriverCardPresent extends PropertyValueObject {
    public static final int SIZE = 2;

    Integer driverNumber;
    CardPresent cardPresent;

    /**
     * @return The driver number.
     */
    public Integer getDriverNumber() {
        return driverNumber;
    }

    /**
     * @return The card present.
     */
    public CardPresent getCardPresent() {
        return cardPresent;
    }

    public DriverCardPresent(Integer driverNumber, CardPresent cardPresent) {
        super(2);
        update(driverNumber, cardPresent);
    }

    public DriverCardPresent(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public DriverCardPresent() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        driverNumber = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        cardPresent = CardPresent.fromByte(get(bytePosition));
    }

    public void update(Integer driverNumber, CardPresent cardPresent) {
        this.driverNumber = driverNumber;
        this.cardPresent = cardPresent;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(driverNumber, 1));
        bytePosition += 1;

        set(bytePosition, cardPresent.getByte());
    }

    public void update(DriverCardPresent value) {
        update(value.driverNumber, value.cardPresent);
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum CardPresent implements ByteEnum {
        NOT_PRESENT((byte) 0x00),
        PRESENT((byte) 0x01);
    
        public static CardPresent fromByte(byte byteValue) throws CommandParseException {
            CardPresent[] values = CardPresent.values();
    
            for (int i = 0; i < values.length; i++) {
                CardPresent state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        CardPresent(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}