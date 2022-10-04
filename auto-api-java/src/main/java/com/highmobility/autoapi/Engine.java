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

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.EnabledState;
import com.highmobility.autoapi.value.OnOffState;
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.value.Bytes;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Engine capability
 */
public class Engine {
    public static final int IDENTIFIER = Identifier.ENGINE;

    public static final byte PROPERTY_STATUS = 0x01;
    public static final byte PROPERTY_START_STOP_STATE = 0x02;
    public static final byte PROPERTY_START_STOP_ENABLED = 0x03;
    public static final byte PROPERTY_PRECONDITIONING_ENABLED = 0x04;
    public static final byte PROPERTY_PRECONDITIONING_ACTIVE = 0x05;
    public static final byte PROPERTY_PRECONDITIONING_REMAINING_TIME = 0x06;
    public static final byte PROPERTY_PRECONDITIONING_ERROR = 0x07;
    public static final byte PROPERTY_PRECONDITIONING_STATUS = 0x08;
    public static final byte PROPERTY_LIMP_MODE = 0x09;

    /**
     * Get Engine property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Engine property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Engine property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Engine property availabilities
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
     * Get Engine properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Engine properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Engine properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Engine properties
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
     * Get specific Engine properties
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
     * Turn engine on off
     */
    public static class TurnEngineOnOff extends SetCommand {
        Property<OnOffState> status = new Property<>(OnOffState.class, PROPERTY_STATUS);
    
        /**
         * @return The status
         */
        public Property<OnOffState> getStatus() {
            return status;
        }
        
        /**
         * Turn engine on off
         * 
         * @param status The status
         */
        public TurnEngineOnOff(OnOffState status) {
            super(IDENTIFIER);
        
            addProperty(this.status.update(status));
            createBytes();
        }
    
        TurnEngineOnOff(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_STATUS) return status.update(p);
                    
                    return null;
                });
            }
            if (this.status.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Enable disable start stop
     */
    public static class EnableDisableStartStop extends SetCommand {
        Property<EnabledState> startStopEnabled = new Property<>(EnabledState.class, PROPERTY_START_STOP_ENABLED);
    
        /**
         * @return The start stop enabled
         */
        public Property<EnabledState> getStartStopEnabled() {
            return startStopEnabled;
        }
        
        /**
         * Enable disable start stop
         * 
         * @param startStopEnabled Indicates if the automatic start-stop system is enabled or not
         */
        public EnableDisableStartStop(EnabledState startStopEnabled) {
            super(IDENTIFIER);
        
            addProperty(this.startStopEnabled.update(startStopEnabled));
            createBytes();
        }
    
        EnableDisableStartStop(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_START_STOP_ENABLED) return startStopEnabled.update(p);
                    
                    return null;
                });
            }
            if (this.startStopEnabled.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The engine state
     */
    public static class State extends SetCommand {
        Property<OnOffState> status = new Property<>(OnOffState.class, PROPERTY_STATUS);
        Property<ActiveState> startStopState = new Property<>(ActiveState.class, PROPERTY_START_STOP_STATE);
        Property<EnabledState> startStopEnabled = new Property<>(EnabledState.class, PROPERTY_START_STOP_ENABLED);
        Property<EnabledState> preconditioningEnabled = new Property<>(EnabledState.class, PROPERTY_PRECONDITIONING_ENABLED);
        Property<ActiveState> preconditioningActive = new Property<>(ActiveState.class, PROPERTY_PRECONDITIONING_ACTIVE);
        Property<Duration> preconditioningRemainingTime = new Property<>(Duration.class, PROPERTY_PRECONDITIONING_REMAINING_TIME);
        Property<PreconditioningError> preconditioningError = new Property<>(PreconditioningError.class, PROPERTY_PRECONDITIONING_ERROR);
        Property<PreconditioningStatus> preconditioningStatus = new Property<>(PreconditioningStatus.class, PROPERTY_PRECONDITIONING_STATUS);
        Property<ActiveState> limpMode = new Property<>(ActiveState.class, PROPERTY_LIMP_MODE);
    
        /**
         * @return The status
         */
        public Property<OnOffState> getStatus() {
            return status;
        }
    
        /**
         * @return Indicates wheter the start-stop system is currently active or not
         */
        public Property<ActiveState> getStartStopState() {
            return startStopState;
        }
    
        /**
         * @return Indicates if the automatic start-stop system is enabled or not
         */
        public Property<EnabledState> getStartStopEnabled() {
            return startStopEnabled;
        }
    
        /**
         * @return Use of the engine pre-conditioning is enabled.
         */
        public Property<EnabledState> getPreconditioningEnabled() {
            return preconditioningEnabled;
        }
    
        /**
         * @return Pre-conditioning is running.
         */
        public Property<ActiveState> getPreconditioningActive() {
            return preconditioningActive;
        }
    
        /**
         * @return Remaining time of pre-conditioning.
         */
        public Property<Duration> getPreconditioningRemainingTime() {
            return preconditioningRemainingTime;
        }
    
        /**
         * @return Reason for not carrying out pre-conditioning.
         */
        public Property<PreconditioningError> getPreconditioningError() {
            return preconditioningError;
        }
    
        /**
         * @return Status of the pre-conditioning system.
         */
        public Property<PreconditioningStatus> getPreconditioningStatus() {
            return preconditioningStatus;
        }
    
        /**
         * @return Indicates wheter the engine is in fail-safe mode.
         */
        public Property<ActiveState> getLimpMode() {
            return limpMode;
        }
    
        State(byte[] bytes) {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                        case PROPERTY_START_STOP_STATE: return startStopState.update(p);
                        case PROPERTY_START_STOP_ENABLED: return startStopEnabled.update(p);
                        case PROPERTY_PRECONDITIONING_ENABLED: return preconditioningEnabled.update(p);
                        case PROPERTY_PRECONDITIONING_ACTIVE: return preconditioningActive.update(p);
                        case PROPERTY_PRECONDITIONING_REMAINING_TIME: return preconditioningRemainingTime.update(p);
                        case PROPERTY_PRECONDITIONING_ERROR: return preconditioningError.update(p);
                        case PROPERTY_PRECONDITIONING_STATUS: return preconditioningStatus.update(p);
                        case PROPERTY_LIMP_MODE: return limpMode.update(p);
                    }
    
                    return null;
                });
            }
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
             * @param status The status
             * @return The builder
             */
            public Builder setStatus(Property<OnOffState> status) {
                Property property = status.setIdentifier(PROPERTY_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param startStopState Indicates wheter the start-stop system is currently active or not
             * @return The builder
             */
            public Builder setStartStopState(Property<ActiveState> startStopState) {
                Property property = startStopState.setIdentifier(PROPERTY_START_STOP_STATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param startStopEnabled Indicates if the automatic start-stop system is enabled or not
             * @return The builder
             */
            public Builder setStartStopEnabled(Property<EnabledState> startStopEnabled) {
                Property property = startStopEnabled.setIdentifier(PROPERTY_START_STOP_ENABLED);
                addProperty(property);
                return this;
            }
            
            /**
             * @param preconditioningEnabled Use of the engine pre-conditioning is enabled.
             * @return The builder
             */
            public Builder setPreconditioningEnabled(Property<EnabledState> preconditioningEnabled) {
                Property property = preconditioningEnabled.setIdentifier(PROPERTY_PRECONDITIONING_ENABLED);
                addProperty(property);
                return this;
            }
            
            /**
             * @param preconditioningActive Pre-conditioning is running.
             * @return The builder
             */
            public Builder setPreconditioningActive(Property<ActiveState> preconditioningActive) {
                Property property = preconditioningActive.setIdentifier(PROPERTY_PRECONDITIONING_ACTIVE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param preconditioningRemainingTime Remaining time of pre-conditioning.
             * @return The builder
             */
            public Builder setPreconditioningRemainingTime(Property<Duration> preconditioningRemainingTime) {
                Property property = preconditioningRemainingTime.setIdentifier(PROPERTY_PRECONDITIONING_REMAINING_TIME);
                addProperty(property);
                return this;
            }
            
            /**
             * @param preconditioningError Reason for not carrying out pre-conditioning.
             * @return The builder
             */
            public Builder setPreconditioningError(Property<PreconditioningError> preconditioningError) {
                Property property = preconditioningError.setIdentifier(PROPERTY_PRECONDITIONING_ERROR);
                addProperty(property);
                return this;
            }
            
            /**
             * @param preconditioningStatus Status of the pre-conditioning system.
             * @return The builder
             */
            public Builder setPreconditioningStatus(Property<PreconditioningStatus> preconditioningStatus) {
                Property property = preconditioningStatus.setIdentifier(PROPERTY_PRECONDITIONING_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param limpMode Indicates wheter the engine is in fail-safe mode.
             * @return The builder
             */
            public Builder setLimpMode(Property<ActiveState> limpMode) {
                Property property = limpMode.setIdentifier(PROPERTY_LIMP_MODE);
                addProperty(property);
                return this;
            }
        }
    }

    public enum PreconditioningError implements ByteEnum {
        LOW_FUEL((byte) 0x00),
        LOW_BATTERY((byte) 0x01),
        QUOTA_EXCEEDED((byte) 0x02),
        HEATER_FAILURE((byte) 0x03),
        COMPONENT_FAILURE((byte) 0x04),
        OPEN_OR_UNLOCKED((byte) 0x05),
        OK((byte) 0x06);
    
        public static PreconditioningError fromByte(byte byteValue) throws CommandParseException {
            PreconditioningError[] values = PreconditioningError.values();
    
            for (int i = 0; i < values.length; i++) {
                PreconditioningError state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(PreconditioningError.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        PreconditioningError(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum PreconditioningStatus implements ByteEnum {
        STANDBY((byte) 0x00),
        HEATING((byte) 0x01),
        COOLING((byte) 0x02),
        VENTILATION((byte) 0x03),
        INACTIVE((byte) 0x04);
    
        public static PreconditioningStatus fromByte(byte byteValue) throws CommandParseException {
            PreconditioningStatus[] values = PreconditioningStatus.values();
    
            for (int i = 0; i < values.length; i++) {
                PreconditioningStatus state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(PreconditioningStatus.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        PreconditioningStatus(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}