// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class ReadingLamp extends PropertyValueObject {
    public static final int SIZE = 2;

    Location location;
    ActiveState activeState;

    /**
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The active state.
     */
    public ActiveState getActiveState() {
        return activeState;
    }

    public ReadingLamp(Location location, ActiveState activeState) {
        super(2);
        update(location, activeState);
    }

    public ReadingLamp() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        location = Location.fromByte(get(bytePosition));
        bytePosition += 1;

        activeState = ActiveState.fromByte(get(bytePosition));
    }

    public void update(Location location, ActiveState activeState) {
        this.location = location;
        this.activeState = activeState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, location.getByte());
        bytePosition += 1;

        set(bytePosition, activeState.getByte());
    }

    public void update(ReadingLamp value) {
        update(value.location, value.activeState);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}