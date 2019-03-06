package com.highmobility.autoapi.property.windows;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.value.Location;
import com.highmobility.value.Bytes;

public class WindowOpenPercentage extends PropertyValueObject {
    Location location;
    Double openPercentage;

    /**
     * @return The window location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The window open percentage.
     */
    public Double getOpenPercentage() {
        return openPercentage;
    }

    public WindowOpenPercentage(Location location, Double openPercentage) {
        super(9);
        update(location, openPercentage);
    }

    public WindowOpenPercentage() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 9) throw new CommandParseException();
        location = Location.fromByte(get(0));
        openPercentage = Property.getDouble(getRange(1, 9));
    }

    public void update(Location location, Double openPercentage) {
        this.location = location;
        this.openPercentage = openPercentage;
        bytes = new byte[9];

        set(0, location.getByte());
        set(1, Property.doubleToBytes(openPercentage));
    }

    public void update(WindowOpenPercentage value) {
        update(value.location, value.openPercentage);
    }

}
