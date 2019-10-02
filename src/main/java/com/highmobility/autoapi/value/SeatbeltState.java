// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

public class SeatbeltState extends PropertyValueObject {
    public static final int SIZE = 2;

    SeatLocation seatLocation;
    FastenedState fastenedState;

    /**
     * @return The seat location.
     */
    public SeatLocation getSeatLocation() {
        return seatLocation;
    }

    /**
     * @return The fastened state.
     */
    public FastenedState getFastenedState() {
        return fastenedState;
    }

    public SeatbeltState(SeatLocation seatLocation, FastenedState fastenedState) {
        super(2);
        update(seatLocation, fastenedState);
    }

    public SeatbeltState(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public SeatbeltState() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        seatLocation = SeatLocation.fromByte(get(bytePosition));
        bytePosition += 1;

        fastenedState = FastenedState.fromByte(get(bytePosition));
    }

    public void update(SeatLocation seatLocation, FastenedState fastenedState) {
        this.seatLocation = seatLocation;
        this.fastenedState = fastenedState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, seatLocation.getByte());
        bytePosition += 1;

        set(bytePosition, fastenedState.getByte());
    }

    public void update(SeatbeltState value) {
        update(value.seatLocation, value.fastenedState);
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum FastenedState implements ByteEnum {
        NOT_FASTENED((byte) 0x00),
        FASTENED((byte) 0x01);
    
        public static FastenedState fromByte(byte byteValue) throws CommandParseException {
            FastenedState[] values = FastenedState.values();
    
            for (int i = 0; i < values.length; i++) {
                FastenedState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        FastenedState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}