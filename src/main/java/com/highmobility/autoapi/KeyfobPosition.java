package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * This is an evented message that is sent by the car every time the relative position of the keyfob
 * changes. This message is also sent when a Get Keyfob Position message is received by the car.
 */
public class KeyfobPosition extends Command {
    public static final Type TYPE = new Type(Identifier.KEYFOB_POSITION, 0x01);

    com.highmobility.autoapi.property.KeyfobPosition keyfobPosition;

    public com.highmobility.autoapi.property.KeyfobPosition getKeyfobPosition() {
        return keyfobPosition;
    }

    public KeyfobPosition(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    keyfobPosition = com.highmobility.autoapi.property.KeyfobPosition.fromByte(
                            property.getValueByte()
                    );
                    break;
            }
        }
    }
}