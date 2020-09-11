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
import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.value.Bytes;
import javax.annotation.Nullable;

/**
 * The Navi Destination capability
 */
public class NaviDestination {
    public static final int IDENTIFIER = Identifier.NAVI_DESTINATION;

    public static final byte PROPERTY_COORDINATES = 0x01;
    public static final byte PROPERTY_DESTINATION_NAME = 0x02;
    public static final byte PROPERTY_DATA_SLOTS_FREE = 0x03;
    public static final byte PROPERTY_DATA_SLOTS_MAX = 0x04;
    public static final byte PROPERTY_ARRIVAL_DURATION = 0x05;
    public static final byte PROPERTY_DISTANCE_TO_DESTINATION = 0x06;

    /**
     * Get Navi Destination property availability information.
     */
    public static class GetNaviDestinationAvailability extends GetAvailabilityCommand {
        /**
         * Get every Navi Destination property availability
         */
        public GetNaviDestinationAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Navi Destination property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetNaviDestinationAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Navi Destination property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetNaviDestinationAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetNaviDestinationAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get navi destination
     */
    public static class GetNaviDestination extends GetCommand<State> {
        /**
         * Get all Navi Destination properties
         */
        public GetNaviDestination() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Navi Destination properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetNaviDestination(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Navi Destination properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetNaviDestination(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetNaviDestination(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Navi Destination properties
     * 
     * @deprecated use {@link GetNaviDestination#GetNaviDestination(byte...)} instead
     */
    @Deprecated
    public static class GetNaviDestinationProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetNaviDestinationProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetNaviDestinationProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetNaviDestinationProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Set navi destination
     */
    public static class SetNaviDestination extends SetCommand {
        Property<Coordinates> coordinates = new Property<>(Coordinates.class, PROPERTY_COORDINATES);
        Property<String> destinationName = new Property<>(String.class, PROPERTY_DESTINATION_NAME);
    
        /**
         * @return The coordinates
         */
        public Property<Coordinates> getCoordinates() {
            return coordinates;
        }
        
        /**
         * @return The destination name
         */
        public Property<String> getDestinationName() {
            return destinationName;
        }
        
        /**
         * Set navi destination
         * 
         * @param coordinates The coordinates
         * @param destinationName Destination name
         */
        public SetNaviDestination(Coordinates coordinates, @Nullable String destinationName) {
            super(IDENTIFIER);
        
            addProperty(this.coordinates.update(coordinates));
            addProperty(this.destinationName.update(destinationName));
            createBytes();
        }
    
        SetNaviDestination(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_COORDINATES: return coordinates.update(p);
                        case PROPERTY_DESTINATION_NAME: return destinationName.update(p);
                    }
                    return null;
                });
            }
            if (this.coordinates.getValue() == null) 
                throw new NoPropertiesException();
        }
    }

    /**
     * The navi destination state
     */
    public static class State extends SetCommand {
        Property<Coordinates> coordinates = new Property<>(Coordinates.class, PROPERTY_COORDINATES);
        Property<String> destinationName = new Property<>(String.class, PROPERTY_DESTINATION_NAME);
        PropertyInteger dataSlotsFree = new PropertyInteger(PROPERTY_DATA_SLOTS_FREE, false);
        PropertyInteger dataSlotsMax = new PropertyInteger(PROPERTY_DATA_SLOTS_MAX, false);
        Property<Duration> arrivalDuration = new Property<>(Duration.class, PROPERTY_ARRIVAL_DURATION);
        Property<Length> distanceToDestination = new Property<>(Length.class, PROPERTY_DISTANCE_TO_DESTINATION);
    
        /**
         * @return The coordinates
         */
        public Property<Coordinates> getCoordinates() {
            return coordinates;
        }
    
        /**
         * @return Destination name
         */
        public Property<String> getDestinationName() {
            return destinationName;
        }
    
        /**
         * @return Remaining number of POI data slots available.
         */
        public PropertyInteger getDataSlotsFree() {
            return dataSlotsFree;
        }
    
        /**
         * @return Maximum number of POI data slots.
         */
        public PropertyInteger getDataSlotsMax() {
            return dataSlotsMax;
        }
    
        /**
         * @return Remaining time until reaching the destination.
         */
        public Property<Duration> getArrivalDuration() {
            return arrivalDuration;
        }
    
        /**
         * @return Remaining distance to reach the destination.
         */
        public Property<Length> getDistanceToDestination() {
            return distanceToDestination;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_COORDINATES: return coordinates.update(p);
                        case PROPERTY_DESTINATION_NAME: return destinationName.update(p);
                        case PROPERTY_DATA_SLOTS_FREE: return dataSlotsFree.update(p);
                        case PROPERTY_DATA_SLOTS_MAX: return dataSlotsMax.update(p);
                        case PROPERTY_ARRIVAL_DURATION: return arrivalDuration.update(p);
                        case PROPERTY_DISTANCE_TO_DESTINATION: return distanceToDestination.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            coordinates = builder.coordinates;
            destinationName = builder.destinationName;
            dataSlotsFree = builder.dataSlotsFree;
            dataSlotsMax = builder.dataSlotsMax;
            arrivalDuration = builder.arrivalDuration;
            distanceToDestination = builder.distanceToDestination;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<Coordinates> coordinates;
            private Property<String> destinationName;
            private PropertyInteger dataSlotsFree;
            private PropertyInteger dataSlotsMax;
            private Property<Duration> arrivalDuration;
            private Property<Length> distanceToDestination;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param coordinates The coordinates
             * @return The builder
             */
            public Builder setCoordinates(Property<Coordinates> coordinates) {
                this.coordinates = coordinates.setIdentifier(PROPERTY_COORDINATES);
                addProperty(this.coordinates);
                return this;
            }
            
            /**
             * @param destinationName Destination name
             * @return The builder
             */
            public Builder setDestinationName(Property<String> destinationName) {
                this.destinationName = destinationName.setIdentifier(PROPERTY_DESTINATION_NAME);
                addProperty(this.destinationName);
                return this;
            }
            
            /**
             * @param dataSlotsFree Remaining number of POI data slots available.
             * @return The builder
             */
            public Builder setDataSlotsFree(Property<Integer> dataSlotsFree) {
                this.dataSlotsFree = new PropertyInteger(PROPERTY_DATA_SLOTS_FREE, false, 1, dataSlotsFree);
                addProperty(this.dataSlotsFree);
                return this;
            }
            
            /**
             * @param dataSlotsMax Maximum number of POI data slots.
             * @return The builder
             */
            public Builder setDataSlotsMax(Property<Integer> dataSlotsMax) {
                this.dataSlotsMax = new PropertyInteger(PROPERTY_DATA_SLOTS_MAX, false, 1, dataSlotsMax);
                addProperty(this.dataSlotsMax);
                return this;
            }
            
            /**
             * @param arrivalDuration Remaining time until reaching the destination.
             * @return The builder
             */
            public Builder setArrivalDuration(Property<Duration> arrivalDuration) {
                this.arrivalDuration = arrivalDuration.setIdentifier(PROPERTY_ARRIVAL_DURATION);
                addProperty(this.arrivalDuration);
                return this;
            }
            
            /**
             * @param distanceToDestination Remaining distance to reach the destination.
             * @return The builder
             */
            public Builder setDistanceToDestination(Property<Length> distanceToDestination) {
                this.distanceToDestination = distanceToDestination.setIdentifier(PROPERTY_DISTANCE_TO_DESTINATION);
                addProperty(this.distanceToDestination);
                return this;
            }
        }
    }
}