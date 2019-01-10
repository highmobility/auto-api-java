package com.highmobility.autoapi.property.windows;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.value.Location;
import com.highmobility.autoapi.property.value.Position;

public class WindowPosition extends Property {
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

    public WindowPosition(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 5) throw new CommandParseException();
        location = Location.fromByte(bytes[3]);
        position = Position.fromByte(bytes[4]);
    }

    public WindowPosition(Location location, Position
            position) {
        super((byte) 0x00, getBytes(location, position));
        this.location = location;
        this.position = position;
    }

    private static byte[] getBytes(Location location, Position position) {
        byte[] bytes = new byte[2];
        bytes[0] = location.getByte();
        bytes[1] = position.getByte();
        return bytes;
    }


}
