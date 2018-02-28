/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.SeatStateProperty;

import java.util.ArrayList;

/**
 * This message is sent when a Get Vehicle Time message is received by the car. The local time of
 * the car is returned, hence the UTC timezone offset is included as well.
 */
public class SeatsState extends CommandWithProperties {
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