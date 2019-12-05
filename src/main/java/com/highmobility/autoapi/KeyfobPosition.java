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

/**
 * The Keyfob Position capability
 */
public class KeyfobPosition {
    public static final int IDENTIFIER = Identifier.KEYFOB_POSITION;

    public static final byte PROPERTY_LOCATION = 0x01;

    /**
     * Get keyfob position
     */
    public static class GetKeyfobPosition extends GetCommand {
        public GetKeyfobPosition() {
            super(IDENTIFIER);
        }
    
        GetKeyfobPosition(byte[] bytes) {
            super(bytes);
        }
    }

    /**
     * The keyfob position state
     */
    public static class State extends SetCommand {
        Property<Location> location = new Property(Location.class, PROPERTY_LOCATION);
    
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Location(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}