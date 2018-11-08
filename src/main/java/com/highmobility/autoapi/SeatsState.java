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
import com.highmobility.autoapi.property.seats.SeatLocation;
import com.highmobility.autoapi.property.seats.SeatBeltFastened;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * This message is sent when a Get Seats State message is received by the car. The new state is
 * included in the message payload and may be the result of user, device or car triggered action.
 */
public class SeatsState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.SEATS, 0x01);

    PersonDetected[] personsDetected;
    SeatBeltFastened[] seatBeltsFastened;

    /**
     * @return All of the person detections.
     */
    public PersonDetected[] getPersonsDetected() {
        return personsDetected;
    }

    /**
     * @param location The seat location.
     * @return A person detection on a seat.
     */
    @Nullable public PersonDetected getPersonDetection(SeatLocation location) {
        for (int i = 0; i < personsDetected.length; i++) {
            PersonDetected property = personsDetected[i];
            if (property.getSeatLocation() == location) return property;
        }

        return null;
    }

    /**
     * @return All of the person detections.
     */
    public SeatBeltFastened[] getSeatBeltsFastened() {
        return seatBeltsFastened;
    }

    /**
     * @param location The seat location.
     * @return The seat belt state.
     */
    @Nullable public SeatBeltFastened getSeatBeltFastened(SeatLocation location) {
        for (int i = 0; i < seatBeltsFastened.length; i++) {
            SeatBeltFastened property = seatBeltsFastened[i];
            if (property.getSeatLocation() == location) return property;
        }

        return null;
    }

    public SeatsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<PersonDetected> personsDetected = new ArrayList<>();
        ArrayList<SeatBeltFastened> seatBeltsFastened = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case PersonDetected.IDENTIFIER:
                    personsDetected.add(new PersonDetected(property.getPropertyBytes()));
                    break;
                case SeatBeltFastened.IDENTIFIER:
                    seatBeltsFastened.add(new SeatBeltFastened(property.getPropertyBytes()));
                    break;
            }
        }

        this.personsDetected = personsDetected.toArray(new PersonDetected[0]);
        this.seatBeltsFastened = seatBeltsFastened.toArray(new SeatBeltFastened[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private SeatsState(Builder builder) {
        super(builder);
        personsDetected = builder.personsDetected.toArray(new PersonDetected[0]);
        seatBeltsFastened = builder.seatBeltsFastened.toArray(new SeatBeltFastened[0]);

    }

    public static final class Builder extends CommandWithProperties.Builder {
        List<PersonDetected> personsDetected = new ArrayList<>();
        List<SeatBeltFastened> seatBeltsFastened = new ArrayList<>();

        public Builder setPersonsDetected(PersonDetected[] personsDetected) {
            this.personsDetected.clear();
            for (PersonDetected personDetected : personsDetected) {
                addPersonDetected(personDetected);
            }
            return this;
        }

        public Builder addPersonDetected(PersonDetected personDetected) {
            addProperty(personDetected);
            personsDetected.add(personDetected);
            return this;
        }

        public Builder setSeatBeltsFastened(List<SeatBeltFastened> seatBeltsFastened) {
            this.seatBeltsFastened.clear();

            for (SeatBeltFastened seatBeltFastened : seatBeltsFastened) {
                addSeatBeltFastened(seatBeltFastened);
            }

            return this;
        }

        public void addSeatBeltFastened(SeatBeltFastened seatBeltFastened) {
            addProperty(seatBeltFastened);
            seatBeltsFastened.add(seatBeltFastened);
        }

        public Builder() {
            super(TYPE);
        }

        public SeatsState build() {
            return new SeatsState(this);
        }
    }
}