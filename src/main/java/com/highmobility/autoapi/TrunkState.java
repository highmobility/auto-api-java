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

/**
 * The trunk state
 */
public class TrunkState extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.TRUNK;

    public static final byte IDENTIFIER_LOCK = 0x01;
    public static final byte IDENTIFIER_POSITION = 0x02;

    Property<LockState> lock = new Property(LockState.class, IDENTIFIER_LOCK);
    Property<Position> position = new Property(Position.class, IDENTIFIER_POSITION);

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

    TrunkState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_LOCK: return lock.update(p);
                    case IDENTIFIER_POSITION: return position.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private TrunkState(Builder builder) {
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

        public TrunkState build() {
            return new TrunkState(this);
        }

        /**
         * @param lock The lock
         * @return The builder
         */
        public Builder setLock(Property<LockState> lock) {
            this.lock = lock.setIdentifier(IDENTIFIER_LOCK);
            addProperty(this.lock);
            return this;
        }
        
        /**
         * @param position The position
         * @return The builder
         */
        public Builder setPosition(Property<Position> position) {
            this.position = position.setIdentifier(IDENTIFIER_POSITION);
            addProperty(this.position);
            return this;
        }
    }
}