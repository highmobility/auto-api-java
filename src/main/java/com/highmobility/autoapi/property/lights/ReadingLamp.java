package com.highmobility.autoapi.property.lights;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.value.Location;
import com.highmobility.value.Bytes;

public class ReadingLamp extends Property {
    Location location;
    boolean active;

    public ReadingLamp(Location location, boolean active) {
        super((byte) 0x00, 2);
        bytes[6] = location.getByte();
        bytes[7] = Property.boolToByte(active);
        this.location = location;
        this.active = active;
    }

    /**
     * @return The light location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The light state.
     */
    public boolean isActive() {
        return active;
    }

    public ReadingLamp(Bytes bytes) throws CommandParseException {
        super(bytes);
        if (getValueLength() >= 2) {
            location = Location.fromByte(bytes.get(6));
            active = Property.getBool(bytes.get(7));
        }
    }
}
