package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

import java.io.UnsupportedEncodingException;

/**
 * This message is sent when a Get Navi Destination message is received by the car.
 */
public class NaviDestination extends Command {
    public static final Type TYPE = new Type(Identifier.NAVI_DESTINATION, 0x01);

    private Float latitude;
    private Float longitude;
    private String name;

    /**
     * @return The latitude
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * @return The longitude
     */
    public Float getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public NaviDestination(byte[] bytes) throws CommandParseException {
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
                case 0x03:
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