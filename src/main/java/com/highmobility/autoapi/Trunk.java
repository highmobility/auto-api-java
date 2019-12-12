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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import com.highmobility.value.Bytes;
import javax.annotation.Nullable;

/**
 * The Trunk capability
 */
public class Trunk {
    public static final int IDENTIFIER = Identifier.TRUNK;

    public static final byte PROPERTY_LOCK = 0x01;
    public static final byte PROPERTY_POSITION = 0x02;

    /**
     * Get all trunk properties
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
     * Get specific trunk properties
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
    
        GetProperties(byte[] bytes) throws CommandParseException {
            super(bytes);
            propertyIdentifiers = getRange(3, getLength());
        }
    }

    /**
     * The trunk state
     */
    public static class State extends SetCommand {
        Property<LockState> lock = new Property(LockState.class, PROPERTY_LOCK);
        Property<Position> position = new Property(Position.class, PROPERTY_POSITION);
    
        /**
         * @return The lock
         */
        public Property<LockState> getLock() {
            return lock;
        }
    
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
                        case PROPERTY_LOCK: return lock.update(p);
                        case PROPERTY_POSITION: return position.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            lock = builder.lock;
            position = builder.position;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<LockState> lock;
            private Property<Position> position;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param lock The lock
             * @return The builder
             */
            public Builder setLock(Property<LockState> lock) {
                this.lock = lock.setIdentifier(PROPERTY_LOCK);
                addProperty(this.lock);
                return this;
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

    /**
     * Control trunk
     */
    public static class ControlTrunk extends SetCommand {
        Property<LockState> lock = new Property(LockState.class, PROPERTY_LOCK);
        Property<Position> position = new Property(Position.class, PROPERTY_POSITION);
    
        /**
         * @return The lock
         */
        public Property<LockState> getLock() {
            return lock;
        }
        
        /**
         * @return The position
         */
        public Property<Position> getPosition() {
            return position;
        }
        
        /**
         * Control trunk
         *
         * @param lock The lock
         * @param position The position
         */
        public ControlTrunk(@Nullable LockState lock, @Nullable Position position) {
            super(IDENTIFIER);
        
            addProperty(this.lock.update(lock));
            addProperty(this.position.update(position));
            if (this.lock.getValue() == null && this.position.getValue() == null) throw new IllegalArgumentException();
            createBytes();
        }
    
        ControlTrunk(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_LOCK: return lock.update(p);
                        case PROPERTY_POSITION: return position.update(p);
                    }
                    return null;
                });
            }
            if (this.lock.getValue() == null && this.position.getValue() == null) throw new NoPropertiesException();
        }
    }
}