// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class TireTemperature extends PropertyValueObject {
    public static final int SIZE = 5;

    Location location;
    Float temperature;

    /**
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return Tire temperature in Celsius.
     */
    public Float getTemperature() {
        return temperature;
    }

    public TireTemperature(Location location, Float temperature) {
        super(5);
        update(location, temperature);
    }

    public TireTemperature(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
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

    public void update(Location location, Float temperature) {
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