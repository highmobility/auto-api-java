package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * This is an evented message that is sent from the car every time the valet mode changes. This
 * message is also sent when a Get Valet Mode message is received by the car.
 */
public class ValetMode extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.VALET_MODE, 0x01);

    boolean active;

    /**
     *
     * @return A boolean indicating whether the valet mode is active or not.
     */
    public boolean isActive() {
        return active;
    }

    public ValetMode(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    active = Property.getBool(property.getValueByte());
                    break;
            }
        }
    }
}