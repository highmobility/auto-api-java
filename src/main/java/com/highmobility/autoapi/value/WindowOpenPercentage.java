// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class WindowOpenPercentage extends PropertyValueObject {
    public static final int SIZE = 9;

    WindowLocation windowLocation;
    Double percentage;

    /**
     * @return The window location.
     */
    public WindowLocation getWindowLocation() {
        return windowLocation;
    }

    /**
     * @return Percentage value between 0.0 - 1.0 (0% - 100%).
     */
    public Double getPercentage() {
        return percentage;
    }

    public WindowOpenPercentage(WindowLocation windowLocation, Double percentage) {
        super(9);
        update(windowLocation, percentage);
    }

    public WindowOpenPercentage(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public WindowOpenPercentage() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 9) throw new CommandParseException();

        int bytePosition = 0;
        windowLocation = WindowLocation.fromByte(get(bytePosition));
        bytePosition += 1;

        percentage = Property.getDouble(bytes, bytePosition);
    }

    public void update(WindowLocation windowLocation, Double percentage) {
        this.windowLocation = windowLocation;
        this.percentage = percentage;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, windowLocation.getByte());
        bytePosition += 1;

        set(bytePosition, Property.doubleToBytes(percentage));
    }

    public void update(WindowOpenPercentage value) {
        update(value.windowLocation, value.percentage);
    }

    @Override public int getLength() {
        return 1 + 8;
    }
}