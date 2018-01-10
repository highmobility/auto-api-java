package com.highmobility.autoapi;

import com.highmobility.autoapi.property.CoordinatesProperty;
import com.highmobility.autoapi.property.Property;

/**
 * This message is sent when a Get Vehicle Location message is received by the car.
 */
public class VehicleLocation extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.VEHICLE_LOCATION, 0x01);

    private CoordinatesProperty coordinates;
    private Float heading;

    /**
     *
     * @return The vehicle coordinates
     */
    public CoordinatesProperty getCoordinates() {
        return coordinates;
    }

    /**
     *
     * @return The Heading
     */
    public Float getHeading() {
        return heading;
    }

    public VehicleLocation(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    coordinates = new CoordinatesProperty(property.getPropertyBytes());
                    break;
                case 0x02:
                    heading = Property.getFloat(property.getValueBytes());
                    break;
            }
        }
    }


}