package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * This message is sent when a Get Offroad State is received by the car.
 */
public class OffRoadState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.OFF_ROAD, 0x01);

    Integer routeIncline;
    Float wheelSuspension;

    /**
     * @return The route elevation incline in degrees, which is a negative number for decline
     */
    public Integer getRouteIncline() {
        return routeIncline;
    }

    /**
     * @return The wheel suspension level percentage, whereas 0 is no suspension and 1 maximum
     * suspension
     */
    public Float getWheelSuspension() {
        return wheelSuspension;
    }

    public OffRoadState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    routeIncline = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case 0x02:
                    wheelSuspension = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
            }
        }
    }
}