package com.highmobility.autoapi.property.lights;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

public class FogLight extends Property {
    LightLocation location;
    boolean active;

    public FogLight(LightLocation location, boolean active) {
        super((byte) 0x00, 2);
        bytes[6] = location.getByte();
        bytes[7] = Property.boolToByte(active);
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

    public FogLight(Bytes bytes) throws CommandParseException {
        super(bytes);

        if (getValueLength() >= 2) {
            location = LightLocation.fromByte(bytes.get(6));
            active = Property.getBool(bytes.get(7));
        }
    }
}
