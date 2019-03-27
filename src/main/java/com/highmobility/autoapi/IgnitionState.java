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

/**
 * Command sent from the car every time the ignition state changes or when a Get Ignition State is
 * received. The new status is included in the message payload and may be the result of user, device
 * or car triggered action.
 */
public class IgnitionState extends Command {
    public static final Type TYPE = new Type(Identifier.ENGINE, 0x01);
    private static final byte IDENTIFIER_ON = 0x01;
    private static final byte IDENTIFIER_ACCESSORIES = 0x02;

    Property<Boolean> on = new Property(Boolean.class, IDENTIFIER_ON);
    Property<Boolean> accessoriesIgnition = new Property(Boolean.class, IDENTIFIER_ACCESSORIES);

    /**
     * @return The ignition state.
     */
    public Property<Boolean> isOn() {
        return on;
    }

    /**
     * @return Whether ignition state is powering on accessories such as radio.
     */
    public Property<Boolean> isAccessoriesIgnitionOn() {
        return accessoriesIgnition;
    }

    IgnitionState(byte[] bytes) {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_ON:
                        return on.update(p);
                    case IDENTIFIER_ACCESSORIES:
                        return accessoriesIgnition.update(p);
                }
                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private IgnitionState(Builder builder) {
        super(builder);
        on = builder.on;
        accessoriesIgnition = builder.accessoriesIgnition;
    }

    public static final class Builder extends Command.Builder {
        private Property<Boolean> on;
        private Property<Boolean> accessoriesIgnition;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param isOn The ignition state.
         * @return The builder.
         */
        public Builder setIsOn(Property<Boolean> isOn) {
            this.on = isOn;
            isOn.setIdentifier(IDENTIFIER_ON);
            addProperty(isOn);
            return this;
        }

        /**
         * @param accessoriesIgnition Whether ignition state is powering on accessories such as
         *                            radio.
         * @return The builder.
         */
        public Builder setAccessoriesIgnition(Property<Boolean> accessoriesIgnition) {
            this.accessoriesIgnition = accessoriesIgnition;
            accessoriesIgnition.setIdentifier(IDENTIFIER_ACCESSORIES);
            addProperty(accessoriesIgnition);
            return this;
        }

        public IgnitionState build() {
            return new IgnitionState(this);
        }
    }
}