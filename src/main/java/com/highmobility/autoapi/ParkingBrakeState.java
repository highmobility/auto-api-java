package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * This message is sent when a Get Parking Brake State message is received by the car.
 */
public class ParkingBrakeState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.PARKING_BRAKE, 0x01);

    boolean active;

    /**
     *
     * @return True if parking brake is active
     */
    public boolean isActive() {
        return active;
    }

    public ParkingBrakeState(byte[] bytes) throws CommandParseException {
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