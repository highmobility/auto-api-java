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

import com.highmobility.autoapi.capability.DisabledIn;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;

/**
 * The Driver Fatigue capability
 */
public class DriverFatigue {
    public static final int IDENTIFIER = Identifier.DRIVER_FATIGUE;

    public static final byte PROPERTY_DETECTED_FATIGUE_LEVEL = 0x01;

    public static final DisabledIn[] disabledIn = new DisabledIn[] { DisabledIn.WEB };

    /**
     * Get all driver fatigue properties
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
     * The driver fatigue state
     */
    public static class State extends SetCommand {
        Property<DetectedFatigueLevel> detectedFatigueLevel = new Property(DetectedFatigueLevel.class, PROPERTY_DETECTED_FATIGUE_LEVEL);
    
        /**
         * @return The detected fatigue level
         */
        public Property<DetectedFatigueLevel> getDetectedFatigueLevel() {
            return detectedFatigueLevel;
        }
    
        State(byte[] bytes) throws CommandParseException {
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        DetectedFatigueLevel(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}