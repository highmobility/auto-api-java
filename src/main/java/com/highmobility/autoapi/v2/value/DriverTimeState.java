// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class DriverTimeState extends PropertyValueObject {
    public static final int SIZE = 2;

    int driverNumber;
    TimeState timeState;

    /**
     * @return The driver number.
     */
    public int getDriverNumber() {
        return driverNumber;
    }

    /**
     * @return The time state.
     */
    public TimeState getTimeState() {
        return timeState;
    }

    public DriverTimeState(int driverNumber, TimeState timeState) {
        super(2);
        update(driverNumber, timeState);
    }

    public DriverTimeState() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        driverNumber = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        timeState = TimeState.fromByte(get(bytePosition));
    }

    public void update(int driverNumber, TimeState timeState) {
        this.driverNumber = driverNumber;
        this.timeState = timeState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(driverNumber, 1));
        bytePosition += 1;

        set(bytePosition, timeState.getByte());
    }

    public void update(DriverTimeState value) {
        update(value.driverNumber, value.timeState);
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum TimeState {
        NORMAL((byte)0x00),
        FIFTEEN_MIN_BEFORE_FOUR((byte)0x01),
        FOUR_REACHED((byte)0x02),
        FIFTEEN_MIN_BEFORE_NINE((byte)0x03),
        NINE_REACHED((byte)0x04),
        FIFTEEN_MIN_BEFORE_SIXTEEN((byte)0x05),
        SIXTEEN_REACHED((byte)0x06);
    
        public static TimeState fromByte(byte byteValue) throws CommandParseException {
            TimeState[] values = TimeState.values();
    
            for (int i = 0; i < values.length; i++) {
                TimeState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        TimeState(byte value) {
            this.value = value;
        }
    
        public byte getByte() {
            return value;
        }
    }
}