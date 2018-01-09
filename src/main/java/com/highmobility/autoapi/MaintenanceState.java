package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * This message is sent when a Get Vehicle Location message is received by the car.
 */
public class MaintenanceState extends Command {
    public static final Type TYPE = new Type(Identifier.MAINTENANCE, 0x01);

    private int kilometersToNextService;
    private int daysToNextService;

    /**
     *
     * @return Amount of kilometers until next servicing of the car
     */
    public int getKilometersToNextService() {
        return kilometersToNextService;
    }

    /**
     *
     * @return Number of days until next servicing of the car, whereas negative is overdue
     */
    public int getDaysToNextService() {
        return daysToNextService;
    }

    public MaintenanceState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    daysToNextService = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case 0x02:
                    kilometersToNextService = Property.getUnsignedInt(property.getValueBytes());
                    break;
            }
        }
    }
}