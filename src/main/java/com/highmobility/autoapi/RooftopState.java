package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * This is an evented message that is sent from the car every time the rooftop state changes. This
 * message is also sent when a Get Rooftop State is received by the car.
 */
public class RooftopState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.ROOFTOP, 0x01);

    Float dimmingPercentage;
    Float openPercentage;

    /**
     *
     * @return The dim percentage of the rooftop.
     */
    public Float getDimmingPercentage() {
        return dimmingPercentage;
    }

    /**
     *
     * @return The percentage of how much the rooftop is open.
     */
    public Float getOpenPercentage() {
        return openPercentage;
    }

    RooftopState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    dimmingPercentage = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case 0x02:
                    openPercentage = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
            }
        }
    }
}
