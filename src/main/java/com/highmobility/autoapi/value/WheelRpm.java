// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class WheelRpm extends PropertyValueObject {
    public static final int SIZE = 3;

    Location location;
    Integer RPM;

    /**
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The RPM measured at this wheel.
     */
    public Integer getRPM() {
        return RPM;
    }

    public WheelRpm(Location location, Integer RPM) {
        super(3);
        update(location, RPM);
    }

    public WheelRpm(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
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

    public void update(Location location, Integer RPM) {
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