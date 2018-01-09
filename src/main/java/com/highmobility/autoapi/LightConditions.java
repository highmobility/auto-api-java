package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * This message is sent when a Get Light Conditions is received by the car.
 */
public class LightConditions extends Command {
    public static final Type TYPE = new Type(Identifier.LIGHT_CONDITIONS, 0x01);

    Float outsideLight;
    Float insideLight;

    /**
     *
     * @return Measured outside illuminance in lux
     */
    public Float getOutsideLight() {
        return outsideLight;
    }

    /**
     *
     * @return Measured inside illuminance in lux
     */
    public Float getInsideLight() {
        return insideLight;
    }

    public LightConditions(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    outsideLight = Property.getFloat(property.getValueBytes());
                    break;
                case 0x02:
                    insideLight = Property.getFloat(property.getValueBytes());
                    break;
            }
        }
    }
}