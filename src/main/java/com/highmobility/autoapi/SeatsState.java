package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.SeatStateProperty;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * This message is sent when a Get Vehicle Time message is received by the car. The local time of
 * the car is returned, hence the UTC timezone offset is included as well.
 */
public class SeatsState extends Command {
    public static final Type TYPE = new Type(Identifier.SEATS, 0x01);


    SeatStateProperty[] seatsStates;

    /**
     *
     * @return All of the available seats states
     */
    public SeatStateProperty[] getSeatsStates() {
        return seatsStates;
    }

    /**
     *
     * @param position The seat position
     * @return A seat state for the given position
     */
    public SeatStateProperty getSeatState(SeatStateProperty.Position position) {
        for (int i = 0; i < seatsStates.length; i++) {
            SeatStateProperty property = seatsStates[i];
            if (property.getPosition() == position) return property;
        }

        return null;
    }

    public SeatsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<SeatStateProperty> seatStateProperties = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    seatStateProperties.add(new SeatStateProperty(property.getPropertyBytes()));
                    break;
            }
        }

        seatsStates = seatStateProperties.toArray(new SeatStateProperty[seatStateProperties.size()]);
    }
}