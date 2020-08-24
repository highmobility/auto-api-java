/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import javax.annotation.Nullable;
import com.highmobility.autoapi.value.SeatLocation;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.PersonDetected;
import com.highmobility.autoapi.value.SeatbeltState;
import java.util.ArrayList;
import java.util.List;
import com.highmobility.value.Bytes;

/**
 * The Seats capability
 */
public class Seats {
    public static final int IDENTIFIER = Identifier.SEATS;

    public static final byte PROPERTY_PERSONS_DETECTED = 0x02;
    public static final byte PROPERTY_SEATBELTS_STATE = 0x03;

    /**
     * Get all seats properties
     */
    public static class GetState extends GetCommand {
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        GetState(byte[] bytes) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific seats properties
     */
    public static class GetProperties extends GetCommand {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The seats state
     */
    public static class State extends SetCommand {
        Property<PersonDetected>[] personsDetected;
        Property<SeatbeltState>[] seatbeltsState;
    
        /**
         * @return The persons detected
         */
        public Property<PersonDetected>[] getPersonsDetected() {
            return personsDetected;
        }
    
        /**
         * @return The seatbelts state
         */
        public Property<SeatbeltState>[] getSeatbeltsState() {
            return seatbeltsState;
        }
    
        /**
         * @param location The seat location.
         * @return A person detection on a seat.
         */
        @Nullable public Property<PersonDetected> getPersonDetection(SeatLocation location) {
            for (int i = 0; i < personsDetected.length; i++) {
                Property<PersonDetected> property = personsDetected[i];
                if (property.getValue() != null && property.getValue().getLocation() == location)
                    return property;
            }
    
            return null;
        }
    
        /**
         * @param location The seat location.
         * @return The seat belt state.
         */
        @Nullable public Property<SeatbeltState> getSeatBeltFastened(SeatLocation location) {
            for (int i = 0; i < seatbeltsState.length; i++) {
                Property<SeatbeltState> property = seatbeltsState[i];
                if (property.getValue() != null && property.getValue().getLocation() == location)
                    return property;
            }
    
            return null;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            ArrayList<Property> personsDetectedBuilder = new ArrayList<>();
            ArrayList<Property> seatbeltsStateBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_PERSONS_DETECTED:
                            Property<PersonDetected> personDetected = new Property(PersonDetected.class, p);
                            personsDetectedBuilder.add(personDetected);
                            return personDetected;
                        case PROPERTY_SEATBELTS_STATE:
                            Property<SeatbeltState> seatbeltState = new Property(SeatbeltState.class, p);
                            seatbeltsStateBuilder.add(seatbeltState);
                            return seatbeltState;
                    }
    
                    return null;
                });
            }
    
            personsDetected = personsDetectedBuilder.toArray(new Property[0]);
            seatbeltsState = seatbeltsStateBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
            super(builder);
    
            personsDetected = builder.personsDetected.toArray(new Property[0]);
            seatbeltsState = builder.seatbeltsState.toArray(new Property[0]);
        }
    
        public static final class Builder extends SetCommand.Builder {
            private List<Property> personsDetected = new ArrayList<>();
            private List<Property> seatbeltsState = new ArrayList<>();
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * Add an array of persons detected.
             * 
             * @param personsDetected The persons detected
             * @return The builder
             */
            public Builder setPersonsDetected(Property<PersonDetected>[] personsDetected) {
                this.personsDetected.clear();
                for (int i = 0; i < personsDetected.length; i++) {
                    addPersonDetected(personsDetected[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single person detected.
             * 
             * @param personDetected The person detected
             * @return The builder
             */
            public Builder addPersonDetected(Property<PersonDetected> personDetected) {
                personDetected.setIdentifier(PROPERTY_PERSONS_DETECTED);
                addProperty(personDetected);
                personsDetected.add(personDetected);
                return this;
            }
            
            /**
             * Add an array of seatbelts state.
             * 
             * @param seatbeltsState The seatbelts state
             * @return The builder
             */
            public Builder setSeatbeltsState(Property<SeatbeltState>[] seatbeltsState) {
                this.seatbeltsState.clear();
                for (int i = 0; i < seatbeltsState.length; i++) {
                    addSeatbeltState(seatbeltsState[i]);
                }
            
                return this;
            }
            /**
             * Add a single seatbelt state.
             * 
             * @param seatbeltState The seatbelt state
             * @return The builder
             */
            public Builder addSeatbeltState(Property<SeatbeltState> seatbeltState) {
                seatbeltState.setIdentifier(PROPERTY_SEATBELTS_STATE);
                addProperty(seatbeltState);
                seatbeltsState.add(seatbeltState);
                return this;
            }
        }
    }
}