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

import com.highmobility.autoapi.property.GasFlapStateProperty;
import com.highmobility.autoapi.property.Property;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Gas Flap State command is received by the car.
 */
public class GasFlapState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.FUELING, 0x01);
    private static final byte IDENTIFIER = 0x01;

    GasFlapStateProperty state;

    /**
     * @return The gas flap state.
     */
    @Nullable public GasFlapStateProperty getState() {
        return state;
    }

    public GasFlapState(byte[] bytes) throws CommandParseException {
        super(bytes);

        Property p = getProperty(IDENTIFIER);
        if (p != null) state = GasFlapStateProperty.fromByte(p.getValueByte());
    }

    @Override public boolean isState() {
        return true;
    }

    private GasFlapState(Builder builder) {
        super(builder);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        GasFlapStateProperty state;

        /**
         * @param state The gas flap state.
         * @return The builder.
         */
        public Builder setState(GasFlapStateProperty state) {
            this.state = state;
            addProperty(new Property(IDENTIFIER, state.getByte()));
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