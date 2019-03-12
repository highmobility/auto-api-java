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
import com.highmobility.autoapi.property.seats.PersonDetected;
import com.highmobility.autoapi.property.seats.SeatBeltFastened;
import com.highmobility.autoapi.property.seats.SeatLocation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * This message is sent when a Get Seats State message is received by the car. The new state is
 * included in the message payload and may be the result of user, device or car triggered action.
 */
public class SeatsState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.SEATS, 0x01);

    Property<PersonDetected>[] personsDetected;
    Property<SeatBeltFastened>[] seatBeltsFastened;

    /**
     * @return All of the person detections.
     */
    public Property<PersonDetected>[] getPersonsDetected() {
        return personsDetected;
    }

    /**
     * @param location The seat location.
     * @return A person detection on a seat.
     */
    @Nullable public Property<PersonDetected> getPersonDetection(SeatLocation location) {
        for (int i = 0; i < personsDetected.length; i++) {
            Property<PersonDetected> property = personsDetected[i];
            if (property.getValue() != null && property.getValue().getSeatLocation() == location)
                return property;
        }

        return null;
    }

    /**
     * @return All of the person detections.
     */
    public Property<SeatBeltFastened>[] getSeatBeltsFastened() {
        return seatBeltsFastened;
    }

    /**
     * @param location The seat location.
     * @return The seat belt state.
     */
    @Nullable public Property<SeatBeltFastened> getSeatBeltFastened(SeatLocation location) {
        for (int i = 0; i < seatBeltsFastened.length; i++) {
            Property<SeatBeltFastened> property = seatBeltsFastened[i];
            if (property.getValue() != null && property.getValue().getSeatLocation() == location)
                return property;
        }

        return null;
    }

    SeatsState(byte[] bytes) {
        super(bytes);

        ArrayList<Property<PersonDetected>> personsDetected = new ArrayList<>();
        ArrayList<Property<SeatBeltFastened>> seatBeltsFastened = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case PersonDetected.IDENTIFIER:
                        // if property, create new property.
                        Property d = new Property(PersonDetected.class, p);
                        personsDetected.add(d);
                        return d;
                    case SeatBeltFastened.IDENTIFIER:
                        Property f = new Property(SeatBeltFastened.class, p);
                        seatBeltsFastened.add(f);
                        return f;
                }

                return null;
            });
        }

        this.personsDetected = personsDetected.toArray(new Property[0]);
        this.seatBeltsFastened = seatBeltsFastened.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private SeatsState(Builder builder) {
        super(builder);
        personsDetected = builder.personsDetected.toArray(new Property[0]);
        seatBeltsFastened = builder.seatBeltsFastened.toArray(new Property[0]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        List<Property<PersonDetected>> personsDetected = new ArrayList<>();
        List<Property<SeatBeltFastened>> seatBeltsFastened = new ArrayList<>();

        public Builder setPersonsDetected(Property<PersonDetected>[] personsDetected) {
            this.personsDetected.clear();
            for (Property<PersonDetected> personDetected : personsDetected) {
                addPersonDetected(personDetected);
            }
            return this;
        }

        public Builder addPersonDetected(Property<PersonDetected> personDetected) {
            addProperty(personDetected);
            personsDetected.add(personDetected.setIdentifier(PersonDetected.IDENTIFIER));
            return this;
        }

        public Builder setSeatBeltsFastened(List<Property<SeatBeltFastened>> seatBeltsFastened) {
            this.seatBeltsFastened.clear();

            for (Property<SeatBeltFastened> seatBeltFastened : seatBeltsFastened) {
                addSeatBeltFastened(seatBeltFastened);
            }

            return this;
        }

        public void addSeatBeltFastened(Property<SeatBeltFastened> seatBeltFastened) {
            addProperty(seatBeltFastened);
            seatBeltsFastened.add(seatBeltFastened.setIdentifier(SeatBeltFastened.IDENTIFIER));
        }

        public Builder() {
            super(TYPE);
        }

        public SeatsState build() {
            return new SeatsState(this);
        }
    }
}