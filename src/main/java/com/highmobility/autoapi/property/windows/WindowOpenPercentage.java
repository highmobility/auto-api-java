package com.highmobility.autoapi.property.windows;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.value.Location;

public class WindowOpenPercentage extends Property {
    Location location;
    Float openPercentage;

    /**
     * @return The window location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The window open percentage.
     */
    public Float getOpenPercentage() {
        return openPercentage;
    }

    public WindowOpenPercentage(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 8) throw new CommandParseException();
        location = Location.fromByte(bytes[6]);
        openPercentage = Property.getPercentage(bytes[7]);
    }

    public WindowOpenPercentage(Location location, Float openPercentage) {
        super((byte) 0x00, getBytes(location, openPercentage));
        this.location = location;
        this.openPercentage = openPercentage;
    }

    static byte[] getBytes(Location location, Float openPercentage) {
        byte[] value = new byte[2];
        value[0] = location.getByte();
        value[1] = Property.floatToIntPercentageByte(openPercentage);
        return value;
    }
}
