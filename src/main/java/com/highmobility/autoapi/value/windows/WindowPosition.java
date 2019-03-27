package com.highmobility.autoapi.value.windows;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.value.Location;
import com.highmobility.value.Bytes;

public class WindowPosition extends PropertyValueObject {
    Location location;
    Position position;

    /**
     * @return The window location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The window position.
     */
    public Position getPosition() {
        return position;
    }

    public WindowPosition(Location location, Position position) {
        super(2);
        update(location, position);
    }

    public WindowPosition() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();
        location = Location.fromByte(get(0));
        position = Position.fromByte(get(1));
    }

    public void update(Location location, Position position) {
        this.location = location;
        this.position = position;
        bytes = new byte[2];

        set(0, location.getByte());
        set(1, position.getByte());
    }

    public void update(WindowPosition value) {
        update(value.location, value.position);
    }
}
