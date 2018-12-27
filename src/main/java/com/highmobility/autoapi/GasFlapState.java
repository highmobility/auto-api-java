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
import com.highmobility.autoapi.property.value.Lock;
import com.highmobility.autoapi.property.value.Position;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Gas Flap State command is received by the car.
 */
public class GasFlapState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.FUELING, 0x01);
    private static final byte LOCK_IDENTIFIER = 0x02;
    private static final byte POSITION_IDENTIFIER = 0x03;

    Lock lock;
    Position position;

    /**
     * @return The gas flap lock.
     */
    @Nullable public Lock getLock() {
        return lock;
    }

    /**
     * @return The gas flap position.
     */
    @Nullable public Position getPosition() {
        return position;
    }

    GasFlapState(byte[] bytes) {
        super(bytes);

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                if (p.getPropertyIdentifier() == LOCK_IDENTIFIER) {
                    lock = Lock.fromByte(p.getValueByte());
                } else if (p.getPropertyIdentifier() == POSITION_IDENTIFIER) {
                    position = Position.fromByte(p.getValueByte());
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private GasFlapState(Builder builder) {
        super(builder);
        this.lock = builder.lock;
        this.position = builder.position;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        Lock lock;
        Position position;

        /**
         * @param lock The gas flap lock.
         * @return The builder.
         */
        public Builder setLock(Lock lock) {
            this.lock = lock;
            addProperty(new Property(LOCK_IDENTIFIER, lock.getByte()));
            return this;
        }

        /**
         * @param position The gas flap position.
         * @return The builder.
         */
        public Builder setPosition(Position position) {
            this.position = position;
            addProperty(new Property(POSITION_IDENTIFIER, position.getByte()));
            return this;
        }

        public Builder() {
            super(TYPE);
        }

        public GasFlapState build() {
            return new GasFlapState(this);
        }
    }
}