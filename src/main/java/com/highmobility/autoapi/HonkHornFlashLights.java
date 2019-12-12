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
 * The Honk Horn & Flash Lights capability
 */
public class HonkHornFlashLights {
    public static final int IDENTIFIER = Identifier.HONK_HORN_FLASH_LIGHTS;

    public static final byte PROPERTY_FLASHERS = 0x01;
    public static final byte PROPERTY_HONK_SECONDS = 0x02;
    public static final byte PROPERTY_FLASH_TIMES = 0x03;
    public static final byte PROPERTY_EMERGENCY_FLASHERS_STATE = 0x04;

    /**
     * Get flashers state
     */
    public static class GetFlashersState extends GetCommand {
        public GetFlashersState() {
            super(IDENTIFIER);
        }
    
        GetFlashersState(byte[] bytes) throws CommandParseException {
            super(bytes);
        }
    }
    
    /**
     * Get specific honk horn flash lights properties
     */
    public static class GetFlashersProperties extends GetCommand {
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
        public GetFlashersProperties(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers.getByteArray());
            this.propertyIdentifiers = propertyIdentifiers;
        }
    
        GetFlashersProperties(byte[] bytes) throws CommandParseException {
            super(bytes);
            propertyIdentifiers = getRange(3, getLength());
        }
    }

    /**
     * The honk horn flash lights state
     */
    public static class State extends SetCommand {
        Property<Flashers> flashers = new Property(Flashers.class, PROPERTY_FLASHERS);
        PropertyInteger honkSeconds = new PropertyInteger(PROPERTY_HONK_SECONDS, false);
        PropertyInteger flashTimes = new PropertyInteger(PROPERTY_FLASH_TIMES, false);
        Property<ActiveState> emergencyFlashersState = new Property(ActiveState.class, PROPERTY_EMERGENCY_FLASHERS_STATE);
    
        /**
         * @return The flashers
         */
        public Property<Flashers> getFlashers() {
            return flashers;
        }
    
        /**
         * @return Number of seconds to honk the horn
         */
        public PropertyInteger getHonkSeconds() {
            return honkSeconds;
        }
    
        /**
         * @return Number of times to flash the lights
         */
        public PropertyInteger getFlashTimes() {
            return flashTimes;
        }
    
        /**
         * @return The emergency flashers state
         */
        public Property<ActiveState> getEmergencyFlashersState() {
            return emergencyFlashersState;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_FLASHERS: return flashers.update(p);
                        case PROPERTY_HONK_SECONDS: return honkSeconds.update(p);
                        case PROPERTY_FLASH_TIMES: return flashTimes.update(p);
                        case PROPERTY_EMERGENCY_FLASHERS_STATE: return emergencyFlashersState.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            flashers = builder.flashers;
            honkSeconds = builder.honkSeconds;
            flashTimes = builder.flashTimes;
            emergencyFlashersState = builder.emergencyFlashersState;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<Flashers> flashers;
            private PropertyInteger honkSeconds;
            private PropertyInteger flashTimes;
            private Property<ActiveState> emergencyFlashersState;
    
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
            
            /**
             * @param honkSeconds Number of seconds to honk the horn
             * @return The builder
             */
            public Builder setHonkSeconds(Property<Integer> honkSeconds) {
                this.honkSeconds = new PropertyInteger(PROPERTY_HONK_SECONDS, false, 1, honkSeconds);
                addProperty(this.honkSeconds);
                return this;
            }
            
            /**
             * @param flashTimes Number of times to flash the lights
             * @return The builder
             */
            public Builder setFlashTimes(Property<Integer> flashTimes) {
                this.flashTimes = new PropertyInteger(PROPERTY_FLASH_TIMES, false, 1, flashTimes);
                addProperty(this.flashTimes);
                return this;
            }
            
            /**
             * @param emergencyFlashersState The emergency flashers state
             * @return The builder
             */
            public Builder setEmergencyFlashersState(Property<ActiveState> emergencyFlashersState) {
                this.emergencyFlashersState = emergencyFlashersState.setIdentifier(PROPERTY_EMERGENCY_FLASHERS_STATE);
                addProperty(this.emergencyFlashersState);
                return this;
            }
        }
    }

    /**
     * Honk flash
     */
    public static class HonkFlash extends SetCommand {
        PropertyInteger honkSeconds = new PropertyInteger(PROPERTY_HONK_SECONDS, false);
        PropertyInteger flashTimes = new PropertyInteger(PROPERTY_FLASH_TIMES, false);
    
        /**
         * @return The honk seconds
         */
        public PropertyInteger getHonkSeconds() {
            return honkSeconds;
        }
        
        /**
         * @return The flash times
         */
        public PropertyInteger getFlashTimes() {
            return flashTimes;
        }
        
        /**
         * Honk flash
         *
         * @param honkSeconds Number of seconds to honk the horn
         * @param flashTimes Number of times to flash the lights
         */
        public HonkFlash(@Nullable Integer honkSeconds, @Nullable Integer flashTimes) {
            super(IDENTIFIER);
        
            addProperty(this.honkSeconds.update(false, 1, honkSeconds));
            addProperty(this.flashTimes.update(false, 1, flashTimes));
            if (this.honkSeconds.getValue() == null && this.flashTimes.getValue() == null) throw new IllegalArgumentException();
            createBytes();
        }
    
        HonkFlash(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_HONK_SECONDS: return honkSeconds.update(p);
                        case PROPERTY_FLASH_TIMES: return flashTimes.update(p);
                    }
                    return null;
                });
            }
            if (this.honkSeconds.getValue() == null && this.flashTimes.getValue() == null) throw new NoPropertiesException();
        }
    }
    
    /**
     * Activate deactivate emergency flasher
     */
    public static class ActivateDeactivateEmergencyFlasher extends SetCommand {
        Property<ActiveState> emergencyFlashersState = new Property(ActiveState.class, PROPERTY_EMERGENCY_FLASHERS_STATE);
    
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
    
        ActivateDeactivateEmergencyFlasher(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_EMERGENCY_FLASHERS_STATE: return emergencyFlashersState.update(p);
                    }
                    return null;
                });
            }
            if (this.emergencyFlashersState.getValue() == null) 
                throw new NoPropertiesException();
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Flashers(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}