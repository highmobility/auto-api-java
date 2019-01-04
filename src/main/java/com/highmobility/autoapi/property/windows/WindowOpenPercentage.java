package com.highmobility.autoapi.property.windows;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.value.Location;

public class WindowOpenPercentage extends Property {
    Location location;
    FloatProperty openPercentage;

    /**
     * @return The window location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The window open percentage.
     */
    public FloatProperty getOpenPercentage() {
        return openPercentage;
    }

    public WindowOpenPercentage(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 5) throw new CommandParseException();
        location = Location.fromByte(bytes[3]);
        openPercentage = Property.getPercentage(bytes[4]);
    }

    public WindowOpenPercentage(Location location, FloatProperty openPercentage) {
        super((byte) 0x00, getBytes(location, openPercentage));
        this.location = location;
        this.openPercentage = openPercentage;
    }

    static byte[] getBytes(Location location, FloatProperty openPercentage) {
        byte[] value = new byte[2];
        value[0] = location.getByte();
        value[1] = Property.floatToIntPercentageByte(openPercentage);
        return value;
    }
}
