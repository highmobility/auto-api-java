package com.highmobility.autoapi;

import com.highmobility.autoapi.property.CoordinatesProperty;
import com.highmobility.autoapi.property.Property;

import java.io.UnsupportedEncodingException;

/**
 * This message is sent when a Get Navi Destination message is received by the car.
 */
public class NaviDestination extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.NAVI_DESTINATION, 0x01);

    private CoordinatesProperty coordinates;
    private String name;

    /**
     * @return The coordinates
     */
    public CoordinatesProperty getCoordinates() {
        return coordinates;
    }

    /**
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    public NaviDestination(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    coordinates = new CoordinatesProperty(property.getPropertyBytes());
                    break;
                case 0x02:
                    try {
                        name = Property.getString(property.getValueBytes());
                    } catch (UnsupportedEncodingException e) {
                        throw new CommandParseException(CommandParseException.CommandExceptionCode.UNSUPPORTED_VALUE_TYPE);
                    }
                    break;
            }
        }
    }
}