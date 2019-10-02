// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class DrivingModeActivationPeriod extends PropertyValueObject {
    public static final int SIZE = 9;

    DrivingMode drivingMode;
    Double percentage;

    /**
     * @return The driving mode.
     */
    public DrivingMode getDrivingMode() {
        return drivingMode;
    }

    /**
     * @return Percentage value between 0.0 - 1.0 (0% - 100%).
     */
    public Double getPercentage() {
        return percentage;
    }

    public DrivingModeActivationPeriod(DrivingMode drivingMode, Double percentage) {
        super(9);
        update(drivingMode, percentage);
    }

    public DrivingModeActivationPeriod(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
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

    public void update(DrivingMode drivingMode, Double percentage) {
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