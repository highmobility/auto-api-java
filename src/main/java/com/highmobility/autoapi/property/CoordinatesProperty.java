package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

import java.io.UnsupportedEncodingException;

public class CoordinatesProperty extends Property {
    float latitude;
    float longitude;

    /**
     *
     * @return The latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     *
     * @return The longitude
     */
    public float getLongitude() {
        return longitude;
    }

    public CoordinatesProperty(byte[] bytes) throws CommandParseException {
        super(bytes);

        latitude = Property.getFloat(bytes, 3);
        longitude = Property.getFloat(bytes, 7);
    }

    public CoordinatesProperty(byte identifier, Axle type, Integer springRate, Integer
            maximumPossibleRate, Integer minimumPossibleRate) throws UnsupportedEncodingException {
        super(identifier, 5);
        bytes[3] = type.getByte();
        bytes[4] = springRate.byteValue();
        bytes[5] = maximumPossibleRate.byteValue();
        bytes[6] = minimumPossibleRate.byteValue();
    }
}