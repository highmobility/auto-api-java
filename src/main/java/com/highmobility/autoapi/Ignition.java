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
import com.highmobility.autoapi.value.OnOffState;
import com.highmobility.value.Bytes;

/**
 * The Ignition capability
 */
public class Ignition {
    public static final int IDENTIFIER = Identifier.IGNITION;

    public static final byte PROPERTY_STATUS = 0x01;
    public static final byte PROPERTY_ACCESSORIES_STATUS = 0x02;

    /**
     * Get all ignition properties
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
     * Get specific ignition properties
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
            propertyIdentifiers = getRange(3, getLength());
        }
    }

    /**
     * The ignition state
     */
    public static class State extends SetCommand {
        Property<OnOffState> status = new Property(OnOffState.class, PROPERTY_STATUS);
        Property<OnOffState> accessoriesStatus = new Property(OnOffState.class, PROPERTY_ACCESSORIES_STATUS);
    
        /**
         * @return The status
         */
        public Property<OnOffState> getStatus() {
            return status;
        }
    
        /**
         * @return The accessories status
         */
        public Property<OnOffState> getAccessoriesStatus() {
            return accessoriesStatus;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                        case PROPERTY_ACCESSORIES_STATUS: return accessoriesStatus.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            status = builder.status;
            accessoriesStatus = builder.accessoriesStatus;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<OnOffState> status;
            private Property<OnOffState> accessoriesStatus;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param status The status
             * @return The builder
             */
            public Builder setStatus(Property<OnOffState> status) {
                this.status = status.setIdentifier(PROPERTY_STATUS);
                addProperty(this.status);
                return this;
            }
            
            /**
             * @param accessoriesStatus The accessories status
             * @return The builder
             */
            public Builder setAccessoriesStatus(Property<OnOffState> accessoriesStatus) {
                this.accessoriesStatus = accessoriesStatus.setIdentifier(PROPERTY_ACCESSORIES_STATUS);
                addProperty(this.accessoriesStatus);
                return this;
            }
        }
    }

    /**
     * Turn ignition on off
     */
    public static class TurnIgnitionOnOff extends SetCommand {
        Property<OnOffState> status = new Property(OnOffState.class, PROPERTY_STATUS);
    
        /**
         * @return The status
         */
        public Property<OnOffState> getStatus() {
            return status;
        }
        
        /**
         * Turn ignition on off
         *
         * @param status The status
         */
        public TurnIgnitionOnOff(OnOffState status) {
            super(IDENTIFIER);
        
            addProperty(this.status.update(status));
            createBytes();
        }
    
        TurnIgnitionOnOff(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                    }
                    return null;
                });
            }
            if (this.status.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
}