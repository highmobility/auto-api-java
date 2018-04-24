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
import com.highmobility.autoapi.property.Property;

/**
 * Command sent when a Get Parking Brake State command is received by the car.
 */
public class ParkingBrakeState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.PARKING_BRAKE, 0x01);

    private static final byte ACTIVE_IDENTIFIER = 0x01;

    Boolean active;

    /**
     * @return True if parking brake is active
     */
    public Boolean isActive() {
        return active;
    }

    public ParkingBrakeState(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case ACTIVE_IDENTIFIER:
                    active = Property.getBool(property.getValueByte());
                    break;
            }
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
        private boolean active;

        public Builder() {
            super(TYPE);
        }

        public Builder setIsActive(boolean active) {
            this.active = active;
            addProperty(new BooleanProperty(ACTIVE_IDENTIFIER, active));
            return this;
        }

        public ParkingBrakeState build() {
            return new ParkingBrakeState(this);
        }
    }
}