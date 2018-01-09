package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

import java.util.Calendar;

/**
 * This message is sent when a Get Vehicle Time message is received by the car. The local time of
 * the car is returned, hence the UTC timezone offset is included as well.
 */
public class VehicleTime extends Command {
    public static final Type TYPE = new Type(Identifier.VEHICLE_TIME, 0x01);

    Calendar vehicleTime;

    /**
     *
     * @return Vehicle time
     */
    public Calendar getVehicleTime() {
        return vehicleTime;
    }

    public VehicleTime(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    vehicleTime = Property.getCalendar(property.getValueBytes());
                    break;
            }
        }
    }
}