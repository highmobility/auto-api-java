// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Coordinates extends PropertyValueObject {
    public static final int SIZE = 16;

    double latitude;
    double longitude;

    /**
     * @return Latitude in 8-bytes per IEEE 754.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return Longitude in 8-bytes per IEEE 754.
     */
    public double getLongitude() {
        return longitude;
    }

    public Coordinates(double latitude, double longitude) {
        super(16);
        update(latitude, longitude);
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

    public void update(double latitude, double longitude) {
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