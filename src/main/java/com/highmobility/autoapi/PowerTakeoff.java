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

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.value.Bytes;

/**
 * The Power Take-Off capability
 */
public class PowerTakeoff {
    public static final int IDENTIFIER = Identifier.POWER_TAKEOFF;

    public static final byte PROPERTY_STATUS = 0x01;
    public static final byte PROPERTY_ENGAGED = 0x02;

    /**
     * Get all power takeoff properties
     */
    public static class GetState extends GetCommand {
        public GetState() {
            super(IDENTIFIER);
        }
    
        GetState(byte[] bytes) {
            super(bytes);
        }
    }
    
    /**
     * Get specific power takeoff properties
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
    
        GetProperties(byte[] bytes) {
            super(bytes);
            propertyIdentifiers = getRange(3, getLength());
        }
    }

    /**
     * The power takeoff state
     */
    public static class State extends SetCommand {
        Property<ActiveState> status = new Property(ActiveState.class, PROPERTY_STATUS);
        Property<Engaged> engaged = new Property(Engaged.class, PROPERTY_ENGAGED);
    
        /**
         * @return The status
         */
        public Property<ActiveState> getStatus() {
            return status;
        }
    
        /**
         * @return The engaged
         */
        public Property<Engaged> getEngaged() {
            return engaged;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                        case PROPERTY_ENGAGED: return engaged.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            status = builder.status;
            engaged = builder.engaged;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<ActiveState> status;
            private Property<Engaged> engaged;
    
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
            public Builder setStatus(Property<ActiveState> status) {
                this.status = status.setIdentifier(PROPERTY_STATUS);
                addProperty(this.status);
                return this;
            }
            
            /**
             * @param engaged The engaged
             * @return The builder
             */
            public Builder setEngaged(Property<Engaged> engaged) {
                this.engaged = engaged.setIdentifier(PROPERTY_ENGAGED);
                addProperty(this.engaged);
                return this;
            }
        }
    }

    /**
     * Activate deactivate power takeoff
     */
    public static class ActivateDeactivatePowerTakeoff extends SetCommand {
        Property<ActiveState> status = new Property(ActiveState.class, PROPERTY_STATUS);
    
        /**
         * @return The status
         */
        public Property<ActiveState> getStatus() {
            return status;
        }
        
        /**
         * Activate deactivate power takeoff
         *
         * @param status The status
         */
        public ActivateDeactivatePowerTakeoff(ActiveState status) {
            super(IDENTIFIER);
        
            addProperty(this.status.update(status));
            createBytes();
        }
    
        ActivateDeactivatePowerTakeoff(byte[] bytes) throws CommandParseException, NoPropertiesException {
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

    public enum Engaged implements ByteEnum {
        NOT_ENGAGED((byte) 0x00),
        ENGAGED((byte) 0x01);
    
        public static Engaged fromByte(byte byteValue) throws CommandParseException {
            Engaged[] values = Engaged.values();
    
            for (int i = 0; i < values.length; i++) {
                Engaged state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Engaged(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}