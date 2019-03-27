package com.highmobility.autoapi.value.lights;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class InteriorLamp extends PropertyValueObject {
    LightLocation location;
    boolean active;

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

    public InteriorLamp() {
    }

    public InteriorLamp(LightLocation location, boolean active) {
        super(2);
        this.location = location;
        this.active = active;

        set(0, location.getByte());
        set(1, Property.boolToByte(active));
    }

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        location = LightLocation.fromByte(get(0));
        active = Property.getBool(get(1));
    }
}