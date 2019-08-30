// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class DriverWorkingState extends PropertyValueObject {
    public static final int SIZE = 2;

    int driverNumber;
    WorkingState workingState;

    /**
     * @return The driver number.
     */
    public int getDriverNumber() {
        return driverNumber;
    }

    /**
     * @return The working state.
     */
    public WorkingState getWorkingState() {
        return workingState;
    }

    public DriverWorkingState(int driverNumber, WorkingState workingState) {
        super(2);
        update(driverNumber, workingState);
    }

    public DriverWorkingState() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        driverNumber = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        workingState = WorkingState.fromByte(get(bytePosition));
    }

    public void update(int driverNumber, WorkingState workingState) {
        this.driverNumber = driverNumber;
        this.workingState = workingState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(driverNumber, 1));
        bytePosition += 1;

        set(bytePosition, workingState.getByte());
    }

    public void update(DriverWorkingState value) {
        update(value.driverNumber, value.workingState);
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum WorkingState {
        RESTING((byte)0x00),
        DRIVER_AVAILABLE((byte)0x01),
        WORKING((byte)0x02),
        DRIVING((byte)0x03);
    
        public static WorkingState fromByte(byte byteValue) throws CommandParseException {
            WorkingState[] values = WorkingState.values();
    
            for (int i = 0; i < values.length; i++) {
                WorkingState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        WorkingState(byte value) {
            this.value = value;
        }
    
        public byte getByte() {
            return value;
        }
    }
}