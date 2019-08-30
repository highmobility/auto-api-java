// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class TirePressure extends PropertyValueObject {
    public static final int SIZE = 5;

    Location location;
    float pressure;

    /**
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return Tire temperature in BAR formatted in 4-bytes per IEEE 754.
     */
    public float getPressure() {
        return pressure;
    }

    public TirePressure(Location location, float pressure) {
        super(5);
        update(location, pressure);
    }

    public TirePressure() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 5) throw new CommandParseException();

        int bytePosition = 0;
        location = Location.fromByte(get(bytePosition));
        bytePosition += 1;

        pressure = Property.getFloat(bytes, bytePosition);
    }

    public void update(Location location, float pressure) {
        this.location = location;
        this.pressure = pressure;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, location.getByte());
        bytePosition += 1;

        set(bytePosition, Property.floatToBytes(pressure));
    }

    public void update(TirePressure value) {
        update(value.location, value.pressure);
    }

    @Override public int getLength() {
        return 1 + 4;
    }
}