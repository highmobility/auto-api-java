// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class WheelRpm extends PropertyValueObject {
    public static final int SIZE = 3;

    Location location;
    int RPM;

    /**
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The RPM measured at this wheel.
     */
    public int getRPM() {
        return RPM;
    }

    public WheelRpm(Location location, int RPM) {
        super(3);
        update(location, RPM);
    }

    public WheelRpm() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        location = Location.fromByte(get(bytePosition));
        bytePosition += 1;

        RPM = Property.getUnsignedInt(bytes, bytePosition, 2);
    }

    public void update(Location location, int RPM) {
        this.location = location;
        this.RPM = RPM;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, location.getByte());
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(RPM, 2));
    }

    public void update(WheelRpm value) {
        update(value.location, value.RPM);
    }

    @Override public int getLength() {
        return 1 + 2;
    }
}