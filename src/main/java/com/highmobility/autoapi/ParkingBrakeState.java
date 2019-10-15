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
import com.highmobility.autoapi.value.ActiveState;

/**
 * The parking brake state
 */
public class ParkingBrakeState extends SetCommand {
    public static final Identifier IDENTIFIER = Identifier.PARKING_BRAKE;

    public static final byte IDENTIFIER_STATUS = 0x01;

    Property<ActiveState> status = new Property(ActiveState.class, IDENTIFIER_STATUS);

    /**
     * @return The status
     */
    public Property<ActiveState> getStatus() {
        return status;
    }

    ParkingBrakeState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_STATUS: return status.update(p);
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

        status = builder.status;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<ActiveState> status;

        public Builder() {
            super(IDENTIFIER);
        }

        public ParkingBrakeState build() {
            return new ParkingBrakeState(this);
        }

        /**
         * @param status The status
         * @return The builder
         */
        public Builder setStatus(Property<ActiveState> status) {
            this.status = status.setIdentifier(IDENTIFIER_STATUS);
            addProperty(this.status);
            return this;
        }
    }
}