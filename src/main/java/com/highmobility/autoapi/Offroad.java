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
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.value.Bytes;

/**
 * The Offroad capability
 */
public class Offroad {
    public static final int IDENTIFIER = Identifier.OFFROAD;

    public static final byte PROPERTY_ROUTE_INCLINE = 0x01;
    public static final byte PROPERTY_WHEEL_SUSPENSION = 0x02;

    /**
     * Get all offroad properties
     */
    public static class GetState extends GetCommand {
        public GetState() {
            super(IDENTIFIER);
        }
    
        GetState(byte[] bytes) throws CommandParseException {
            super(bytes);
        }
    }
    
    /**
     * Get specific offroad properties
     */
    public static class GetProperties extends GetCommand {
        Bytes propertyIdentifiers;
    
        /**
         * @return The property identifiers.
         */
        public Bytes getPropertyIdentifiers() {
            return propertyIdentifiers;
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers.getByteArray());
            this.propertyIdentifiers = propertyIdentifiers;
        }
    
        GetProperties(byte[] bytes) throws CommandParseException {
            super(bytes);
            propertyIdentifiers = getRange(COMMAND_TYPE_POSITION + 1, getLength());
        }
    }

    /**
     * The offroad state
     */
    public static class State extends SetCommand {
        PropertyInteger routeIncline = new PropertyInteger(PROPERTY_ROUTE_INCLINE, true);
        Property<Double> wheelSuspension = new Property(Double.class, PROPERTY_WHEEL_SUSPENSION);
    
        /**
         * @return The route elevation incline in degrees, which is a negative number for decline
         */
        public PropertyInteger getRouteIncline() {
            return routeIncline;
        }
    
        /**
         * @return The wheel suspension level percentage, whereas 0.0 is no suspension and 1.0 maximum suspension
         */
        public Property<Double> getWheelSuspension() {
            return wheelSuspension;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_ROUTE_INCLINE: return routeIncline.update(p);
                        case PROPERTY_WHEEL_SUSPENSION: return wheelSuspension.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            routeIncline = builder.routeIncline;
            wheelSuspension = builder.wheelSuspension;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private PropertyInteger routeIncline;
            private Property<Double> wheelSuspension;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param routeIncline The route elevation incline in degrees, which is a negative number for decline
             * @return The builder
             */
            public Builder setRouteIncline(Property<Integer> routeIncline) {
                this.routeIncline = new PropertyInteger(PROPERTY_ROUTE_INCLINE, true, 2, routeIncline);
                addProperty(this.routeIncline);
                return this;
            }
            
            /**
             * @param wheelSuspension The wheel suspension level percentage, whereas 0.0 is no suspension and 1.0 maximum suspension
             * @return The builder
             */
            public Builder setWheelSuspension(Property<Double> wheelSuspension) {
                this.wheelSuspension = wheelSuspension.setIdentifier(PROPERTY_WHEEL_SUSPENSION);
                addProperty(this.wheelSuspension);
                return this;
            }
        }
    }
}