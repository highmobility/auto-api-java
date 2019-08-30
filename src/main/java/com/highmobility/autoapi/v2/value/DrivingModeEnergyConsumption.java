// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class DrivingModeEnergyConsumption extends PropertyValueObject {
    public static final int SIZE = 5;

    DrivingMode drivingMode;
    float consumption;

    /**
     * @return The driving mode.
     */
    public DrivingMode getDrivingMode() {
        return drivingMode;
    }

    /**
     * @return Energy consumption in the driving mode in kWh.
     */
    public float getConsumption() {
        return consumption;
    }

    public DrivingModeEnergyConsumption(DrivingMode drivingMode, float consumption) {
        super(5);
        update(drivingMode, consumption);
    }

    public DrivingModeEnergyConsumption() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 5) throw new CommandParseException();

        int bytePosition = 0;
        drivingMode = DrivingMode.fromByte(get(bytePosition));
        bytePosition += 1;

        consumption = Property.getFloat(bytes, bytePosition);
    }

    public void update(DrivingMode drivingMode, float consumption) {
        this.drivingMode = drivingMode;
        this.consumption = consumption;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, drivingMode.getByte());
        bytePosition += 1;

        set(bytePosition, Property.floatToBytes(consumption));
    }

    public void update(DrivingModeEnergyConsumption value) {
        update(value.drivingMode, value.consumption);
    }

    @Override public int getLength() {
        return 1 + 4;
    }
}