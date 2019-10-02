// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Coordinates extends PropertyValueObject {
    public static final int SIZE = 16;

    Double latitude;
    Double longitude;

    /**
     * @return Latitude.
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @return Longitude.
     */
    public Double getLongitude() {
        return longitude;
    }

    public Coordinates(Double latitude, Double longitude) {
        super(16);
        update(latitude, longitude);
    }

    public Coordinates(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public Coordinates() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 16) throw new CommandParseException();

        int bytePosition = 0;
        latitude = Property.getDouble(bytes, bytePosition);
        bytePosition += 8;

        longitude = Property.getDouble(bytes, bytePosition);
    }

    public void update(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.doubleToBytes(latitude));
        bytePosition += 8;

        set(bytePosition, Property.doubleToBytes(longitude));
    }

    public void update(Coordinates value) {
        update(value.latitude, value.longitude);
    }

    @Override public int getLength() {
        return 8 + 8;
    }
}