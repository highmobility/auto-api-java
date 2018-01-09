package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * This message is sent when a Get Vehicle Location message is received by the car.
 */
public class VehicleLocation extends Command {
    public static final Type TYPE = new Type(Identifier.VEHICLE_LOCATION, 0x01);

    private float latitude;
    private float longitude;

    /**
     * @return The latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * @return The longitude
     */
    public float getLongitude() {
        return longitude;
    }

    public VehicleLocation(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    latitude = Property.getFloat(property.getValueBytes());
                    break;
                case 0x02:
                    longitude = Property.getFloat(property.getValueBytes());
                    break;
            }
        }
    }
}