// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class DrivingModeActivationPeriod extends PropertyValueObject {
    public static final int SIZE = 9;

    DrivingMode drivingMode;
    double percentage;

    /**
     * @return The driving mode.
     */
    public DrivingMode getDrivingMode() {
        return drivingMode;
    }

    /**
     * @return Percentage value between 0.0 - 1.0 (0% - 100%).
     */
    public double getPercentage() {
        return percentage;
    }

    public DrivingModeActivationPeriod(DrivingMode drivingMode, double percentage) {
        super(9);
        update(drivingMode, percentage);
    }

    public DrivingModeActivationPeriod() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 9) throw new CommandParseException();

        int bytePosition = 0;
        drivingMode = DrivingMode.fromByte(get(bytePosition));
        bytePosition += 1;

        percentage = Property.getDouble(bytes, bytePosition);
    }

    public void update(DrivingMode drivingMode, double percentage) {
        this.drivingMode = drivingMode;
        this.percentage = percentage;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, drivingMode.getByte());
        bytePosition += 1;

        set(bytePosition, Property.doubleToBytes(percentage));
    }

    public void update(DrivingModeActivationPeriod value) {
        update(value.drivingMode, value.percentage);
    }

    @Override public int getLength() {
        return 1 + 8;
    }
}