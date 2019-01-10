package com.highmobility.autoapi.property.lights;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

public class InteriorLamp extends Property {
    LightLocation location;
    boolean active;

    public InteriorLamp(LightLocation location, boolean active) {
        super((byte) 0x00, 2);
        bytes[3] = location.getByte();
        bytes[4] = Property.boolToByte(active);
        this.location = location;
        this.active = active;
    }

    /**
     * @return The light location.
     */
    public LightLocation getLocation() {
        return location;
    }

    /**
     * @return The light state.
     */
    public boolean isActive() {
        return active;
    }

    public InteriorLamp(Bytes bytes) throws CommandParseException {
        super(bytes);
        if (getValueLength() >= 2) {
            location = LightLocation.fromByte(bytes.get(3));
            active = Property.getBool(bytes.get(4));
        }
    }
}
