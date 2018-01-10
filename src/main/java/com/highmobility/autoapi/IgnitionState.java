package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * This is an evented message that is sent from the car every time the ignition state changes. This
 * message is also sent when a Get Ignition State is received by the car. The new status is included
 * in the message payload and may be the result of user, device or car triggered action.
 */
public class IgnitionState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.ENGINE, 0x01);

    boolean on;

    /**
     * @return the ignition state
     */
    public boolean isOn() {
        return on;
    }

    public IgnitionState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    on = Property.getBool(property.getValueByte());
                    break;
            }
        }
    }
}