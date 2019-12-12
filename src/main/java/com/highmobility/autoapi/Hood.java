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
 * The Hood capability
 */
public class Hood {
    public static final int IDENTIFIER = Identifier.HOOD;

    public static final byte PROPERTY_POSITION = 0x01;

    /**
     * Get all hood properties
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
     * The hood state
     */
    public static class State extends SetCommand {
        Property<Position> position = new Property(Position.class, PROPERTY_POSITION);
    
        /**
         * @return The position
         */
        public Property<Position> getPosition() {
            return position;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_POSITION: return position.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            position = builder.position;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<Position> position;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param position The position
             * @return The builder
             */
            public Builder setPosition(Property<Position> position) {
                this.position = position.setIdentifier(PROPERTY_POSITION);
                addProperty(this.position);
                return this;
            }
        }
    }

    public enum Position implements ByteEnum {
        CLOSED((byte) 0x00),
        OPEN((byte) 0x01),
        INTERMEDIATE((byte) 0x02);
    
        public static Position fromByte(byte byteValue) throws CommandParseException {
            Position[] values = Position.values();
    
            for (int i = 0; i < values.length; i++) {
                Position state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Position(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}