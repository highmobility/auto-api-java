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
import com.highmobility.autoapi.value.Time;

/**
 * The Vehicle Time capability
 */
public class VehicleTime {
    public static final int IDENTIFIER = Identifier.VEHICLE_TIME;

    public static final byte PROPERTY_VEHICLE_TIME = 0x01;

    /**
     * Get Vehicle Time property availability information
     */
    public static class GetVehicleTimeAvailability extends GetAvailabilityCommand {
        /**
         * Get every Vehicle Time property availability
         */
        public GetVehicleTimeAvailability() {
            super(IDENTIFIER);
        }
    
        GetVehicleTimeAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get vehicle time
     */
    public static class GetVehicleTime extends GetCommand<State> {
        /**
         * Get all Vehicle Time properties
         */
        public GetVehicleTime() {
            super(State.class, IDENTIFIER);
        }
    
        GetVehicleTime(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The vehicle time state
     */
    public static class State extends SetCommand {
        Property<Time> vehicleTime = new Property<>(Time.class, PROPERTY_VEHICLE_TIME);
    
        /**
         * @return Vehicle time in a 24h format
         */
        public Property<Time> getVehicleTime() {
            return vehicleTime;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_VEHICLE_TIME: return vehicleTime.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            vehicleTime = builder.vehicleTime;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<Time> vehicleTime;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param vehicleTime Vehicle time in a 24h format
             * @return The builder
             */
            public Builder setVehicleTime(Property<Time> vehicleTime) {
                this.vehicleTime = vehicleTime.setIdentifier(PROPERTY_VEHICLE_TIME);
                addProperty(this.vehicleTime);
                return this;
            }
        }
    }
}