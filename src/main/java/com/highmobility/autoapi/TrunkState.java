/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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
    public static final Identifier identifier = Identifier.TRUNK;

    Property<LockState> lock = new Property(LockState.class, 0x01);
    Property<Position> position = new Property(Position.class, 0x02);

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
                    case 0x01: return lock.update(p);
                    case 0x02: return position.update(p);
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
            super(identifier);
        }

        public TrunkState build() {
            return new TrunkState(this);
        }

        /**
         * @param lock The lock
         * @return The builder
         */
        public Builder setLock(Property<LockState> lock) {
            this.lock = lock.setIdentifier(0x01);
            addProperty(this.lock);
            return this;
        }
        
        /**
         * @param position The position
         * @return The builder
         */
        public Builder setPosition(Property<Position> position) {
            this.position = position.setIdentifier(0x02);
            addProperty(this.position);
            return this;
        }
    }
}