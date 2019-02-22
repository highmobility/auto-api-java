package com.highmobility.autoapi.property.windows;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.value.Location;
import com.highmobility.utils.ByteUtils;

public class WindowOpenPercentage extends Property {
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

    public WindowOpenPercentage(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 8) throw new CommandParseException();
        location = Location.fromByte(bytes[6]);
        openPercentage = Property.getDouble(bytes, 7);
    }

    public WindowOpenPercentage(Location location, Double openPercentage) {
        super((byte) 0x00, getBytes(location, openPercentage));
        this.location = location;
        this.openPercentage = openPercentage;
    }

    static byte[] getBytes(Location location, Double openPercentage) {
        byte[] value = new byte[9];
        value[0] = location.getByte();
        ByteUtils.setBytes(value, Property.doubleToBytes(openPercentage), 1);
        return value;
    }
}
