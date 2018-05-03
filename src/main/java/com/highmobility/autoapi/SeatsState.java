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
import java.util.Arrays;
import java.util.List;

/**
 * This message is sent when a Get Seats State message is received by the car. The new state is included
 * in the message payload and may be the result of user, device or car triggered action.
 */
public class SeatsState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.SEATS, 0x01);

    SeatStateProperty[] seatsStates;

    /**
     * @return All of the available seats states
     */
    public SeatStateProperty[] getSeatsStates() {
        return seatsStates;
    }

    /**
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

        seatsStates = seatStateProperties.toArray(new SeatStateProperty[seatStateProperties.size
                ()]);
    }

    @Override public boolean isState() {
        return true;
    }

    private SeatsState(Builder builder) {
        super(builder);
        seatsStates = builder.seatsStates.toArray(new SeatStateProperty[builder.seatsStates.size
                ()]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        List<SeatStateProperty> seatsStates = new ArrayList<>();

        /**
         * @param seatsStates All the seat states.
         * @return The builder.
         */
        public Builder setSeatsStates(SeatStateProperty[] seatsStates) {
            this.seatsStates = Arrays.asList(seatsStates);
            this.propertiesBuilder.clear();
            for (SeatStateProperty seatsState : seatsStates) {
                addProperty(seatsState);
            }

            return this;
        }

        /**
         * Add a single seat state.
         *
         * @param seatsState The seat property.
         * @return The builder.
         */
        public Builder addSeatState(SeatStateProperty seatsState) {
            seatsStates.add(seatsState);
            addProperty(seatsState);
            return this;
        }

        public Builder() {
            super(TYPE);
        }

        public SeatsState build() {
            return new SeatsState(this);
        }
    }
}