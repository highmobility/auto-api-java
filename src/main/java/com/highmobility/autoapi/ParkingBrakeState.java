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

import com.highmobility.autoapi.property.BooleanProperty;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Parking Brake State command is received by the car.
 */
public class ParkingBrakeState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.PARKING_BRAKE, 0x01);

    private static final byte ACTIVE_IDENTIFIER = 0x01;

    BooleanProperty active = new BooleanProperty(ACTIVE_IDENTIFIER);

    /**
     * @return Whether parking brake is active.
     */
    @Nullable public BooleanProperty isActive() {
        return active;
    }

    ParkingBrakeState(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                if (p.getPropertyIdentifier() == ACTIVE_IDENTIFIER) {
                    return active.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private ParkingBrakeState(Builder builder) {
        super(builder);
        active = builder.active;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private BooleanProperty active;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param active The parking brake state.
         * @return The builder.
         */
        public Builder setIsActive(BooleanProperty active) {
            this.active = active;
            active.setIdentifier(ACTIVE_IDENTIFIER);
            addProperty(active);
            return this;
        }

        public ParkingBrakeState build() {
            return new ParkingBrakeState(this);
        }
    }
}