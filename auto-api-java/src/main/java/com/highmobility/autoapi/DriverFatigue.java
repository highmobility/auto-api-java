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

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Driver Fatigue capability
 */
public class DriverFatigue {
    public static final int IDENTIFIER = Identifier.DRIVER_FATIGUE;

    public static final byte PROPERTY_DETECTED_FATIGUE_LEVEL = 0x01;

    public static final DisabledIn[] disabledIn = new DisabledIn[] { DisabledIn.WEB };

    /**
     * Get Driver Fatigue property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Driver Fatigue property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        GetStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get Driver Fatigue properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Driver Fatigue properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        GetState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The driver fatigue state
     */
    public static class State extends SetCommand {
        Property<DetectedFatigueLevel> detectedFatigueLevel = new Property<>(DetectedFatigueLevel.class, PROPERTY_DETECTED_FATIGUE_LEVEL);
    
        /**
         * @return The detected fatigue level
         */
        public Property<DetectedFatigueLevel> getDetectedFatigueLevel() {
            return detectedFatigueLevel;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DETECTED_FATIGUE_LEVEL: return detectedFatigueLevel.update(p);
                    }
    
                    return null;
                });
            }
        }
    }

    public enum DetectedFatigueLevel implements ByteEnum {
        LIGHT((byte) 0x00),
        PAUSE_RECOMMENDED((byte) 0x01),
        ACTION_NEEDED((byte) 0x02),
        CAR_READY_TO_TAKE_OVER((byte) 0x03);
    
        public static DetectedFatigueLevel fromByte(byte byteValue) throws CommandParseException {
            DetectedFatigueLevel[] values = DetectedFatigueLevel.values();
    
            for (int i = 0; i < values.length; i++) {
                DetectedFatigueLevel state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(DetectedFatigueLevel.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        DetectedFatigueLevel(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}