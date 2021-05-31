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
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.value.Bytes;
import javax.annotation.Nullable;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Honk Horn &amp; Flash Lights capability
 */
public class HonkHornFlashLights {
    public static final int IDENTIFIER = Identifier.HONK_HORN_FLASH_LIGHTS;

    public static final byte PROPERTY_FLASHERS = 0x01;
    public static final byte PROPERTY_HONK_SECONDS = 0x02;
    public static final byte PROPERTY_FLASH_TIMES = 0x03;
    public static final byte PROPERTY_EMERGENCY_FLASHERS_STATE = 0x04;
    public static final byte PROPERTY_HONK_TIME = 0x05;

    /**
     * Get Honk Horn &amp; Flash Lights property availability information
     */
    public static class GetFlashersStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Honk Horn &amp; Flash Lights property availability
         */
        public GetFlashersStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Honk Horn &amp; Flash Lights property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetFlashersStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Honk Horn &amp; Flash Lights property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetFlashersStateAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetFlashersStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get flashers state
     */
    public static class GetFlashersState extends GetCommand<State> {
        /**
         * Get all Honk Horn &amp; Flash Lights properties
         */
        public GetFlashersState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Honk Horn &amp; Flash Lights properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetFlashersState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Honk Horn &amp; Flash Lights properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetFlashersState(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetFlashersState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Honk Horn &amp; Flash Lights properties
     * 
     * @deprecated use {@link GetFlashersState#GetFlashersState(byte...)} instead
     */
    @Deprecated
    public static class GetFlashersProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetFlashersProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetFlashersProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetFlashersProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Honk flash
     */
    public static class HonkFlash extends SetCommand {
        PropertyInteger flashTimes = new PropertyInteger(PROPERTY_FLASH_TIMES, false);
        Property<Duration> honkTime = new Property<>(Duration.class, PROPERTY_HONK_TIME);
    
        /**
         * @return The flash times
         */
        public PropertyInteger getFlashTimes() {
            return flashTimes;
        }
        
        /**
         * @return The honk time
         */
        public Property<Duration> getHonkTime() {
            return honkTime;
        }
        
        /**
         * Honk flash
         * 
         * @param flashTimes Number of times to flash the lights
         * @param honkTime Time to honk the horn
         */
        public HonkFlash(@Nullable Integer flashTimes, @Nullable Duration honkTime) {
            super(IDENTIFIER);
        
            addProperty(this.flashTimes.update(false, 1, flashTimes));
            addProperty(this.honkTime.update(honkTime));
            if (this.flashTimes.getValue() == null && this.honkTime.getValue() == null) throw new IllegalArgumentException();
            createBytes();
        }
    
        HonkFlash(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_FLASH_TIMES: return flashTimes.update(p);
                        case PROPERTY_HONK_TIME: return honkTime.update(p);
                    }
        
                    return null;
                });
            }
            if (this.flashTimes.getValue() == null && this.honkTime.getValue() == null) {
                throw new PropertyParseException(optionalPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Activate deactivate emergency flasher
     */
    public static class ActivateDeactivateEmergencyFlasher extends SetCommand {
        Property<ActiveState> emergencyFlashersState = new Property<>(ActiveState.class, PROPERTY_EMERGENCY_FLASHERS_STATE);
    
        /**
         * @return The emergency flashers state
         */
        public Property<ActiveState> getEmergencyFlashersState() {
            return emergencyFlashersState;
        }
        
        /**
         * Activate deactivate emergency flasher
         * 
         * @param emergencyFlashersState The emergency flashers state
         */
        public ActivateDeactivateEmergencyFlasher(ActiveState emergencyFlashersState) {
            super(IDENTIFIER);
        
            addProperty(this.emergencyFlashersState.update(emergencyFlashersState));
            createBytes();
        }
    
        ActivateDeactivateEmergencyFlasher(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_EMERGENCY_FLASHERS_STATE) return emergencyFlashersState.update(p);
                    
                    return null;
                });
            }
            if (this.emergencyFlashersState.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The honk horn flash lights state
     */
    public static class State extends SetCommand {
        Property<Flashers> flashers = new Property<>(Flashers.class, PROPERTY_FLASHERS);
    
        /**
         * @return The flashers
         */
        public Property<Flashers> getFlashers() {
            return flashers;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_FLASHERS: return flashers.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            flashers = builder.flashers;
        }
    
        public static final class Builder extends SetCommand.Builder<Builder> {
            private Property<Flashers> flashers;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param flashers The flashers
             * @return The builder
             */
            public Builder setFlashers(Property<Flashers> flashers) {
                this.flashers = flashers.setIdentifier(PROPERTY_FLASHERS);
                addProperty(this.flashers);
                return this;
            }
        }
    }

    public enum Flashers implements ByteEnum {
        INACTIVE((byte) 0x00),
        EMERGENCY_FLASHER_ACTIVE((byte) 0x01),
        LEFT_FLASHER_ACTIVE((byte) 0x02),
        RIGHT_FLASHER_ACTIVE((byte) 0x03);
    
        public static Flashers fromByte(byte byteValue) throws CommandParseException {
            Flashers[] values = Flashers.values();
    
            for (int i = 0; i < values.length; i++) {
                Flashers state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Flashers.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Flashers(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}