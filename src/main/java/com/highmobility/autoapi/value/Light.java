// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Light extends PropertyValueObject {
    public static final int SIZE = 2;

    LocationLongitudinal locationLongitudinal;
    ActiveState activeState;

    /**
     * @return The location longitudinal.
     */
    public LocationLongitudinal getLocationLongitudinal() {
        return locationLongitudinal;
    }

    /**
     * @return The active state.
     */
    public ActiveState getActiveState() {
        return activeState;
    }

    public Light(LocationLongitudinal locationLongitudinal, ActiveState activeState) {
        super(2);
        update(locationLongitudinal, activeState);
    }

    public Light(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public Light() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        locationLongitudinal = LocationLongitudinal.fromByte(get(bytePosition));
        bytePosition += 1;

        activeState = ActiveState.fromByte(get(bytePosition));
    }

    public void update(LocationLongitudinal locationLongitudinal, ActiveState activeState) {
        this.locationLongitudinal = locationLongitudinal;
        this.activeState = activeState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, locationLongitudinal.getByte());
        bytePosition += 1;

        set(bytePosition, activeState.getByte());
    }

    public void update(Light value) {
        update(value.locationLongitudinal, value.activeState);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}