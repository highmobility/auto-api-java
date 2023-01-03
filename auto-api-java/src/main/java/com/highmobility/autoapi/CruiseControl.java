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

import com.highmobility.autoapi.capability.DisabledIn;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.measurement.Speed;
import com.highmobility.value.Bytes;
import javax.annotation.Nullable;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

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

    public static final DisabledIn[] disabledIn = new DisabledIn[] { DisabledIn.WEB };

    /**
     * Get Cruise Control property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Cruise Control property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Cruise Control property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Cruise Control property availabilities
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
     * Get Cruise Control properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Cruise Control properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Cruise Control properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Cruise Control properties
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
     * Get specific Cruise Control properties
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
     * Activate deactivate cruise control
     */
    public static class ActivateDeactivateCruiseControl extends SetCommand {
        Property<ActiveState> cruiseControl = new Property<>(ActiveState.class, PROPERTY_CRUISE_CONTROL);
        Property<Speed> targetSpeed = new Property<>(Speed.class, PROPERTY_TARGET_SPEED);
    
        /**
         * @return The cruise control
         */
        public Property<ActiveState> getCruiseControl() {
            return cruiseControl;
        }
        
        /**
         * @return The target speed
         */
        public Property<Speed> getTargetSpeed() {
            return targetSpeed;
        }
        
        /**
         * Activate deactivate cruise control
         * 
         * @param cruiseControl The cruise control
         * @param targetSpeed The target speed
         */
        public ActivateDeactivateCruiseControl(ActiveState cruiseControl, @Nullable Speed targetSpeed) {
            super(IDENTIFIER);
        
            addProperty(this.cruiseControl.update(cruiseControl));
            addProperty(this.targetSpeed.update(targetSpeed));
            createBytes();
        }
    
        ActivateDeactivateCruiseControl(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CRUISE_CONTROL: return cruiseControl.update(p);
                        case PROPERTY_TARGET_SPEED: return targetSpeed.update(p);
                    }
        
                    return null;
                });
            }
            if (this.cruiseControl.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The cruise control state
     */
    public static class State extends SetCommand {
        Property<ActiveState> cruiseControl = new Property<>(ActiveState.class, PROPERTY_CRUISE_CONTROL);
        Property<Limiter> limiter = new Property<>(Limiter.class, PROPERTY_LIMITER);
        Property<Speed> targetSpeed = new Property<>(Speed.class, PROPERTY_TARGET_SPEED);
        Property<ActiveState> adaptiveCruiseControl = new Property<>(ActiveState.class, PROPERTY_ADAPTIVE_CRUISE_CONTROL);
        Property<Speed> accTargetSpeed = new Property<>(Speed.class, PROPERTY_ACC_TARGET_SPEED);
    
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
         * @return The target speed
         */
        public Property<Speed> getTargetSpeed() {
            return targetSpeed;
        }
    
        /**
         * @return The adaptive cruise control
         */
        public Property<ActiveState> getAdaptiveCruiseControl() {
            return adaptiveCruiseControl;
        }
    
        /**
         * @return The target speed of the Adaptive Cruise Control
         */
        public Property<Speed> getAccTargetSpeed() {
            return accTargetSpeed;
        }
    
        State(byte[] bytes) {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
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
    
            throw new CommandParseException(
                enumValueDoesNotExist(Limiter.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Limiter(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}