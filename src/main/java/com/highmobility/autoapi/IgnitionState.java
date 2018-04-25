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
 * Command sent from the car every time the ignition state changes or when a Get Ignition State is
 * received. The new status is included in the message payload and may be the result of user, device
 * or car triggered action.
 */
public class IgnitionState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.ENGINE, 0x01);
    private static final byte ON_IDENTIFIER = 0x01;

    Boolean on;

    /**
     * @return The ignition state.
     */
    public Boolean isOn() {
        return on;
    }

    public IgnitionState(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case ON_IDENTIFIER:
                    on = Property.getBool(property.getValueByte());
                    break;
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private IgnitionState(Builder builder) {
        super(builder);
        on = builder.on;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Boolean on;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param isOn The ignition state.
         * @return The builder.
         */
        public Builder setIsOn(boolean isOn) {
            this.on = isOn;
            addProperty(new BooleanProperty(ON_IDENTIFIER, isOn));
            return this;
        }

        public IgnitionState build() {
            return new IgnitionState(this);
        }
    }
}