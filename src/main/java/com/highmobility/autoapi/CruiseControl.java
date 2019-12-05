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
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.value.Bytes;
import javax.annotation.Nullable;

/**
 * The Cruise Control capability
 */
public class CruiseControl {
    public static final int IDENTIFIER = Identifier.CRUISE_CONTROL;

    public static final byte PROPERTY_CRUISE_CONTROL = 0x01;
    public static final byte PROPERTY_LIMITER = 0x02;
    public static final byte PROPERTY_TARGET_SPEED = 0x03;
    public static final byte PROPERTY_ADAPTIVE_CRUISE_CONTROL = 0x04;
    public static final byte PROPERTY_ACC_TARGET_SPEED = 0x05;

    /**
     * Get all cruise control properties
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
     * Get specific cruise control properties
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
     * The cruise control state
     */
    public static class State extends SetCommand {
        Property<ActiveState> cruiseControl = new Property(ActiveState.class, PROPERTY_CRUISE_CONTROL);
        Property<Limiter> limiter = new Property(Limiter.class, PROPERTY_LIMITER);
        PropertyInteger targetSpeed = new PropertyInteger(PROPERTY_TARGET_SPEED, true);
        Property<ActiveState> adaptiveCruiseControl = new Property(ActiveState.class, PROPERTY_ADAPTIVE_CRUISE_CONTROL);
        PropertyInteger accTargetSpeed = new PropertyInteger(PROPERTY_ACC_TARGET_SPEED, true);
    
        /**
         * @return The cruise control
         */
        public Property<ActiveState> getCruiseControl() {
            return cruiseControl;
        }
    
        /**
         * @return The limiter
         */
        public Property<Limiter> getLimiter() {
            return limiter;
        }
    
        /**
         * @return The target speed in km/h
         */
        public PropertyInteger getTargetSpeed() {
            return targetSpeed;
        }
    
        /**
         * @return The adaptive cruise control
         */
        public Property<ActiveState> getAdaptiveCruiseControl() {
            return adaptiveCruiseControl;
        }
    
        /**
         * @return The target speed in km/h of the Adaptive Cruise Control
         */
        public PropertyInteger getAccTargetSpeed() {
            return accTargetSpeed;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CRUISE_CONTROL: return cruiseControl.update(p);
                        case PROPERTY_LIMITER: return limiter.update(p);
                        case PROPERTY_TARGET_SPEED: return targetSpeed.update(p);
                        case PROPERTY_ADAPTIVE_CRUISE_CONTROL: return adaptiveCruiseControl.update(p);
                        case PROPERTY_ACC_TARGET_SPEED: return accTargetSpeed.update(p);
                    }
    
                    return null;
                });
            }
        }
    }

    /**
     * Activate deactivate cruise control
     */
    public static class ActivateDeactivateCruiseControl extends SetCommand {
        Property<ActiveState> cruiseControl = new Property(ActiveState.class, PROPERTY_CRUISE_CONTROL);
        PropertyInteger targetSpeed = new PropertyInteger(PROPERTY_TARGET_SPEED, true);
    
        /**
         * @return The cruise control
         */
        public Property<ActiveState> getCruiseControl() {
            return cruiseControl;
        }
        
        /**
         * @return The target speed
         */
        public PropertyInteger getTargetSpeed() {
            return targetSpeed;
        }
        
        /**
         * Activate deactivate cruise control
         *
         * @param cruiseControl The cruise control
         * @param targetSpeed The target speed in km/h
         */
        public ActivateDeactivateCruiseControl(ActiveState cruiseControl, @Nullable Integer targetSpeed) {
            super(IDENTIFIER);
        
            addProperty(this.cruiseControl.update(cruiseControl));
            addProperty(this.targetSpeed.update(true, 2, targetSpeed));
            createBytes();
        }
    
        ActivateDeactivateCruiseControl(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CRUISE_CONTROL: return cruiseControl.update(p);
                        case PROPERTY_TARGET_SPEED: return targetSpeed.update(p);
                    }
                    return null;
                });
            }
            if (this.cruiseControl.getValue() == null) 
                throw new NoPropertiesException();
        }
    }

    public enum Limiter implements ByteEnum {
        NOT_SET((byte) 0x00),
        HIGHER_SPEED_REQUESTED((byte) 0x01),
        LOWER_SPEED_REQUESTED((byte) 0x02),
        SPEED_FIXED((byte) 0x03);
    
        public static Limiter fromByte(byte byteValue) throws CommandParseException {
            Limiter[] values = Limiter.values();
    
            for (int i = 0; i < values.length; i++) {
                Limiter state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Limiter(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}