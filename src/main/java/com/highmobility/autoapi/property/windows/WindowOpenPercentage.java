package com.highmobility.autoapi.property.windows;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;

public class WindowOpenPercentage extends Property {
    WindowLocation location;
    Float openPercentage;

    /**
     * @return The window location.
     */
    public WindowLocation getLocation() {
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
        if (bytes.length < 5) throw new CommandParseException();
        location = WindowLocation.fromByte(bytes[3]);
        openPercentage = Property.getPercentage(bytes[4]);
    }

    public WindowOpenPercentage(WindowLocation location, Float openPercentage) {
        super((byte) 0x00, getBytes(location, openPercentage));
        this.location = location;
        this.openPercentage = openPercentage;
    }

    static byte[] getBytes(WindowLocation location, Float openPercentage) {
        byte[] value = new byte[2];
        value[0] = location.getByte();
        value[1] = Property.floatToIntPercentageByte(openPercentage);
        return value;
    }
}
