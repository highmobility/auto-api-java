// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class SeatbeltFastened extends PropertyValueObject {
    public static final int SIZE = 2;

    SeatLocation seatLocation;
    Fastened fastened;

    /**
     * @return The seat location.
     */
    public SeatLocation getSeatLocation() {
        return seatLocation;
    }

    /**
     * @return The fastened.
     */
    public Fastened getFastened() {
        return fastened;
    }

    public SeatbeltFastened(SeatLocation seatLocation, Fastened fastened) {
        super(2);
        update(seatLocation, fastened);
    }

    public SeatbeltFastened() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        seatLocation = SeatLocation.fromByte(get(bytePosition));
        bytePosition += 1;

        fastened = Fastened.fromByte(get(bytePosition));
    }

    public void update(SeatLocation seatLocation, Fastened fastened) {
        this.seatLocation = seatLocation;
        this.fastened = fastened;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, seatLocation.getByte());
        bytePosition += 1;

        set(bytePosition, fastened.getByte());
    }

    public void update(SeatbeltFastened value) {
        update(value.seatLocation, value.fastened);
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum Fastened {
        NOT_FASTENED((byte)0x00),
        FASTENED((byte)0x01);
    
        public static Fastened fromByte(byte byteValue) throws CommandParseException {
            Fastened[] values = Fastened.values();
    
            for (int i = 0; i < values.length; i++) {
                Fastened state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Fastened(byte value) {
            this.value = value;
        }
    
        public byte getByte() {
            return value;
        }
    }
}