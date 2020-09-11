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
import com.highmobility.value.Bytes;

import static com.highmobility.utils.ByteUtils.hexFromByte;

/**
 * The Keyfob Position capability
 */
public class KeyfobPosition {
    public static final int IDENTIFIER = Identifier.KEYFOB_POSITION;

    public static final byte PROPERTY_LOCATION = 0x01;

    public static final DisabledIn[] disabledIn = new DisabledIn[] { DisabledIn.WEB };

    /**
     * Get Keyfob Position property availability information.
     */
    public static class GetKeyfobPositionAvailability extends GetAvailabilityCommand {
        /**
         * Get every Keyfob Position property availability
         */
        public GetKeyfobPositionAvailability() {
            super(IDENTIFIER);
        }
    
        GetKeyfobPositionAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get keyfob position
     */
    public static class GetKeyfobPosition extends GetCommand<State> {
        /**
         * Get all Keyfob Position properties
         */
        public GetKeyfobPosition() {
            super(State.class, IDENTIFIER);
        }
    
        GetKeyfobPosition(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The keyfob position state
     */
    public static class State extends SetCommand {
        Property<Location> location = new Property<>(Location.class, PROPERTY_LOCATION);
    
        /**
         * @return The location
         */
        public Property<Location> getLocation() {
            return location;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_LOCATION: return location.update(p);
                    }
    
                    return null;
                });
            }
        }
    }

    public enum Location implements ByteEnum {
        OUT_OF_RANGE((byte) 0x00),
        OUTSIDE_DRIVER_SIDE((byte) 0x01),
        OUTSIDE_IN_FRONT_OF_CAR((byte) 0x02),
        OUTSIDE_PASSENGER_SIDE((byte) 0x03),
        OUTSIDE_BEHIND_CAR((byte) 0x04),
        INSIDE_CAR((byte) 0x05);
    
        public static Location fromByte(byte byteValue) throws CommandParseException {
            Location[] values = Location.values();
    
            for (int i = 0; i < values.length; i++) {
                Location state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("KeyfobPosition.Location does not contain: " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        Location(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}