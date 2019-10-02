// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class TirePressure extends PropertyValueObject {
    public static final int SIZE = 5;

    Location location;
    Float pressure;

    /**
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return Tire pressure in BAR.
     */
    public Float getPressure() {
        return pressure;
    }

    public TirePressure(Location location, Float pressure) {
        super(5);
        update(location, pressure);
    }

    public TirePressure(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
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

    public void update(Location location, Float pressure) {
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