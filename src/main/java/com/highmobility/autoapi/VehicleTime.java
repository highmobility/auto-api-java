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
import com.highmobility.autoapi.value.Time;

/**
 * The Vehicle Time capability
 */
public class VehicleTime {
    public static final int IDENTIFIER = Identifier.VEHICLE_TIME;

    public static final byte PROPERTY_VEHICLE_TIME = 0x01;

    /**
     * Get vehicle time
     */
    public static class GetVehicleTime extends GetCommand {
        public GetVehicleTime() {
            super(IDENTIFIER);
        }
    
        GetVehicleTime(byte[] bytes) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * The vehicle time state
     */
    public static class State extends SetCommand {
        Property<Time> vehicleTime = new Property(Time.class, PROPERTY_VEHICLE_TIME);
    
        /**
         * @return Vehicle time in a 24h format
         */
        public Property<Time> getVehicleTime() {
            return vehicleTime;
        }
    
        State(byte[] bytes) throws CommandParseException {
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