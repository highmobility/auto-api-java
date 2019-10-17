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
 * The fueling state
 */
public class FuelingState extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.FUELING;

    public static final byte IDENTIFIER_GAS_FLAP_LOCK = 0x02;
    public static final byte IDENTIFIER_GAS_FLAP_POSITION = 0x03;

    Property<LockState> gasFlapLock = new Property(LockState.class, IDENTIFIER_GAS_FLAP_LOCK);
    Property<Position> gasFlapPosition = new Property(Position.class, IDENTIFIER_GAS_FLAP_POSITION);

    /**
     * @return The gas flap lock
     */
    public Property<LockState> getGasFlapLock() {
        return gasFlapLock;
    }

    /**
     * @return The gas flap position
     */
    public Property<Position> getGasFlapPosition() {
        return gasFlapPosition;
    }

    FuelingState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_GAS_FLAP_LOCK: return gasFlapLock.update(p);
                    case IDENTIFIER_GAS_FLAP_POSITION: return gasFlapPosition.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private FuelingState(Builder builder) {
        super(builder);

        gasFlapLock = builder.gasFlapLock;
        gasFlapPosition = builder.gasFlapPosition;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<LockState> gasFlapLock;
        private Property<Position> gasFlapPosition;

        public Builder() {
            super(IDENTIFIER);
        }

        public FuelingState build() {
            return new FuelingState(this);
        }

        /**
         * @param gasFlapLock The gas flap lock
         * @return The builder
         */
        public Builder setGasFlapLock(Property<LockState> gasFlapLock) {
            this.gasFlapLock = gasFlapLock.setIdentifier(IDENTIFIER_GAS_FLAP_LOCK);
            addProperty(this.gasFlapLock);
            return this;
        }
        
        /**
         * @param gasFlapPosition The gas flap position
         * @return The builder
         */
        public Builder setGasFlapPosition(Property<Position> gasFlapPosition) {
            this.gasFlapPosition = gasFlapPosition.setIdentifier(IDENTIFIER_GAS_FLAP_POSITION);
            addProperty(this.gasFlapPosition);
            return this;
        }
    }
}