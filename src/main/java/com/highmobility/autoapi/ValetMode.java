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
 * Command sent from the car every time the valet mode changes or when a Get Valet Mode command is
 * received.
 */
public class ValetMode extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.VALET_MODE, 0x01);

    private static final byte ACTIVE_IDENTIFIER = 0x01;

    BooleanProperty active;

    /**
     * @return The valet mode state.
     */
    @Nullable public BooleanProperty isActive() {
        return active;
    }

    ValetMode(byte[] bytes) {
        super(bytes);

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                if (p.getPropertyIdentifier() == ACTIVE_IDENTIFIER) {
                    active = new BooleanProperty(p);
                    return active;
                }

                return null;
            });
        }

    }

    @Override public boolean isState() {
        return true;
    }

    private ValetMode(Builder builder) {
        super(builder);
        active = builder.active;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private BooleanProperty active;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param active The valet mode state.
         * @return The builder.
         */
        public Builder setActive(BooleanProperty active) {
            this.active = active;
            active.setIdentifier(ACTIVE_IDENTIFIER);
            addProperty(active);
            return this;
        }

        public ValetMode build() {
            return new ValetMode(this);
        }
    }
}