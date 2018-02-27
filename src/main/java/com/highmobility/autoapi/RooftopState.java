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

import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;

/**
 * This is an evented message that is sent from the car every time the rooftop state changes. This
 * message is also sent when a Get Rooftop State is received by the car.
 */
public class RooftopState extends CommandWithExistingProperties {
    public static final Type TYPE = new Type(Identifier.ROOFTOP, 0x01);
    static final byte DIMMING_IDENTIFIER = 0x01;
    static final byte OPEN_IDENTIFIER = 0x02;

    Float dimmingPercentage;
    Float openPercentage;

    /**
     *
     * @return The dim percentage of the rooftop.
     */
    public Float getDimmingPercentage() {
        return dimmingPercentage;
    }

    /**
     *
     * @return The percentage of how much the rooftop is open.
     */
    public Float getOpenPercentage() {
        return openPercentage;
    }

    RooftopState(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case DIMMING_IDENTIFIER:
                    dimmingPercentage = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case OPEN_IDENTIFIER:
                    openPercentage = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
            }
        }
    }

    private RooftopState(Builder builder) {
        super(builder);
        openPercentage = builder.openPercentage;
        dimmingPercentage = builder.dimmingPercentage;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Float openPercentage;
        private Float dimmingPercentage;

        public Builder() {
            super(TYPE);
        }

        public Builder setOpenPercentage(Float openPercentage) {
            this.openPercentage = openPercentage;
            addProperty(new IntegerProperty(OPEN_IDENTIFIER, Property.floatToIntPercentage(openPercentage), 1));
            return this;
        }

        public Builder setDimmingPercentage(Float dimmingPercentage) {
            this.dimmingPercentage = dimmingPercentage;
            addProperty(new IntegerProperty(DIMMING_IDENTIFIER, Property.floatToIntPercentage(dimmingPercentage), 1));
            return this;
        }

        public RooftopState build() {
            return new RooftopState(this);
        }
    }
}
