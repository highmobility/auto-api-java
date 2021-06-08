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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.PersonDetected;
import com.highmobility.autoapi.value.SeatLocation;
import com.highmobility.autoapi.value.SeatbeltState;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/**
 * The Seats capability
 */
public class Seats {
    public static final int IDENTIFIER = Identifier.SEATS;

    public static final byte PROPERTY_PERSONS_DETECTED = 0x02;
    public static final byte PROPERTY_SEATBELTS_STATE = 0x03;

    /**
     * Get Seats property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Seats property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Seats property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Seats property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get Seats properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Seats properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Seats properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Seats properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Seats properties
     * 
     * @deprecated use {@link GetState#GetState(byte...)} instead
     */
    @Deprecated
    public static class GetProperties extends GetCommand<State> {
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
        List<Property<PersonDetected>> personsDetected;
        List<Property<SeatbeltState>> seatbeltsState;
    
        /**
         * @return The persons detected
         */
        public List<Property<PersonDetected>> getPersonsDetected() {
            return personsDetected;
        }
    
        /**
         * @return The seatbelts state
         */
        public List<Property<SeatbeltState>> getSeatbeltsState() {
            return seatbeltsState;
        }
    
        /**
         * @param location The seat location.
         * @return A person detection on a seat.
         */
        @Nullable public Property<PersonDetected> getPersonDetection(SeatLocation location) {
            for (int i = 0; i < personsDetected.size(); i++) {
                Property<PersonDetected> property = personsDetected.get(i);
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
            for (int i = 0; i < seatbeltsState.size(); i++) {
                Property<SeatbeltState> property = seatbeltsState.get(i);
                if (property.getValue() != null && property.getValue().getLocation() == location)
                    return property;
            }
    
            return null;
        }
    
        State(byte[] bytes) {
            super(bytes);
    
            final ArrayList<Property<PersonDetected>> personsDetectedBuilder = new ArrayList<>();
            final ArrayList<Property<SeatbeltState>> seatbeltsStateBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_PERSONS_DETECTED:
                            Property<PersonDetected> personDetected = new Property<>(PersonDetected.class, p);
                            personsDetectedBuilder.add(personDetected);
                            return personDetected;
                        case PROPERTY_SEATBELTS_STATE:
                            Property<SeatbeltState> seatbeltState = new Property<>(SeatbeltState.class, p);
                            seatbeltsStateBuilder.add(seatbeltState);
                            return seatbeltState;
                    }
    
                    return null;
                });
            }
    
            personsDetected = personsDetectedBuilder;
            seatbeltsState = seatbeltsStateBuilder;
        }
    
        public static final class Builder extends SetCommand.Builder<Builder> {
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                SetCommand baseSetCommand = super.build();
                Command resolved = CommandResolver.resolve(baseSetCommand.getByteArray());
                return (State) resolved;
            }
    
            /**
             * Add an array of persons detected
             * 
             * @param personsDetected The persons detected
             * @return The builder
             */
            public Builder setPersonsDetected(Property<PersonDetected>[] personsDetected) {
                for (int i = 0; i < personsDetected.length; i++) {
                    addPersonDetected(personsDetected[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single person detected
             * 
             * @param personDetected The person detected
             * @return The builder
             */
            public Builder addPersonDetected(Property<PersonDetected> personDetected) {
                personDetected.setIdentifier(PROPERTY_PERSONS_DETECTED);
                addProperty(personDetected);
                return this;
            }
            
            /**
             * Add an array of seatbelts state
             * 
             * @param seatbeltsState The seatbelts state
             * @return The builder
             */
            public Builder setSeatbeltsState(Property<SeatbeltState>[] seatbeltsState) {
                for (int i = 0; i < seatbeltsState.length; i++) {
                    addSeatbeltState(seatbeltsState[i]);
                }
            
                return this;
            }
            /**
             * Add a single seatbelt state
             * 
             * @param seatbeltState The seatbelt state
             * @return The builder
             */
            public Builder addSeatbeltState(Property<SeatbeltState> seatbeltState) {
                seatbeltState.setIdentifier(PROPERTY_SEATBELTS_STATE);
                addProperty(seatbeltState);
                return this;
            }
        }
    }
}