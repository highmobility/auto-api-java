// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class TireTemperature extends PropertyValueObject {
    public static final int SIZE = 5;

    Location location;
    float temperature;

    /**
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return Tire temperature in BAR formatted in 4-bytes per IEEE 754.
     */
    public float getTemperature() {
        return temperature;
    }

    public TireTemperature(Location location, float temperature) {
        super(5);
        update(location, temperature);
    }

    public TireTemperature() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 5) throw new CommandParseException();

        int bytePosition = 0;
        location = Location.fromByte(get(bytePosition));
        bytePosition += 1;

        temperature = Property.getFloat(bytes, bytePosition);
    }

    public void update(Location location, float temperature) {
        this.location = location;
        this.temperature = temperature;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, location.getByte());
        bytePosition += 1;

        set(bytePosition, Property.floatToBytes(temperature));
    }

    public void update(TireTemperature value) {
        update(value.location, value.temperature);
    }

    @Override public int getLength() {
        return 1 + 4;
    }
}