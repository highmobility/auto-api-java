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
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.value.Lock;

import javax.annotation.Nullable;

/**
 * Command sent from the car every time the trunk state changes or when a Get Trunk State is
 * received. The new status is included in the command payload and may be the result of user, device
 * or car triggered action.
 */
public class TrunkState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.TRUNK_ACCESS, 0x01);

    private static final byte IDENTIFIER_LOCK = 0x01;
    private static final byte IDENTIFIER_POSITION = 0x02;

    Property<Lock> lock = new Property<>(Lock.class, IDENTIFIER_LOCK);
    Property<Position> position = new Property<>(Position.class, IDENTIFIER_POSITION);

    /**
     * @return the current lock status of the trunk.
     */
    public Property<Lock> getLockState() {
        return lock;
    }

    /**
     * @return the current position of the trunk.
     */
    @Nullable public Property<Position> getPosition() {
        return position;
    }

    TrunkState(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_LOCK:
                        return lock.update(p);
                    case IDENTIFIER_POSITION:
                        return position.update(p);
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

    public static final class Builder extends CommandWithProperties.Builder {
        private Property<Lock> lock;
        private Property<Position> position;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param lock The lock state of the trunk.
         * @return The builder.
         */
        public Builder setLockState(Property<Lock> lock) {
            addProperty(lock.setIdentifier(IDENTIFIER_LOCK));
            this.lock = lock;
            return this;
        }

        /**
         * @param position The position of the trunk.
         * @return The builder.
         */
        public Builder setPosition(Property<Position> position) {
            this.position = position;
            addProperty(position.setIdentifier(IDENTIFIER_POSITION));
            return this;
        }

        public TrunkState build() {
            return new TrunkState(this);
        }
    }
}